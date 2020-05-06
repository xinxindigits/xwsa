package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.service.DepartmentService;
import cn.com.xinxin.sass.biz.service.DepartmentReceivedService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDepartmentSyncService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.AddressListEnum;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.DepartmentDO;
import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/4/22.
 * @updater:
 * @description: 企业微信同步部门服务
 */
@Service
public class WeChatWorkDepartmentSyncServiceImpl implements WeChatWorkDepartmentSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkDepartmentSyncServiceImpl.class);

    private final DepartmentReceivedService departmentReceivedService;
    private final DepartmentService departmentService;

    public WeChatWorkDepartmentSyncServiceImpl(final DepartmentReceivedService departmentReceivedService,
                                                final DepartmentService departmentService) {
        this.departmentReceivedService = departmentReceivedService;
        this.departmentService = departmentService;
    }

    /**
     * 同步部门信息
     *
     * @param taskId 任务id
     * @param orgId 机构编码
     */
    @Override
    public void syncDepartment(String taskId, String orgId) {

        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("同步部门信息，taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "同步部门信息，taskId不能为空");
        }

        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("同步部门信息，orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "同步部门信息，orgId不能为空");
        }

        //根据taskID查询部门信息暂存表的记录
        List<DepartmentReceivedDO> departmentReceivedDOS = departmentReceivedService.selectByTaskIdAndOrgId(taskId, orgId);

        //部门暂存信息的部门id列表
        List<Long> departmentReceivedIdS = departmentReceivedDOS.stream()
                .map(DepartmentReceivedDO::getDepartmentId).collect(Collectors.toList());

        //根据机构编码查询部门信息
        List<DepartmentDO> departmentDOS = departmentService.selectByOrgId(orgId);

        //此次同步待插入的记录
        List<DepartmentDO> insertRecord = new ArrayList<>();

        //此次同步待更新的记录
        List<DepartmentDO> updateRecord = new ArrayList<>();

        //获取此次同步需要将部门置为不活跃的记录
        fetchInvalidDepartment(departmentReceivedIdS, departmentDOS, updateRecord, taskId);

        departmentReceivedDOS.forEach(d -> {
            //获取部门id相同的部门记录
            DepartmentDO departmentDO = departmentDOS.stream()
                    .filter(p -> d.getDepartmentId().equals(p.getDepartmentId()))
                    .findFirst().orElse(null);

            if (null == departmentDO) {
                //将此次同步待插入的部门记录保存到insertRecord
                fetchInsertDepartment(d, taskId, insertRecord);
            } else {
                //将此次需要更新的部门记录保存到updateRecord
                fetchUpdateDepartment(d, departmentDO, updateRecord, taskId);
            }
        });

        //插入记录
        departmentService.insertBatchPartially(insertRecord, CommonConstants.ONE_HUNDRED);

        //更新记录
        departmentService.updateBatchByIdPartially(updateRecord, CommonConstants.ONE_HUNDRED);
    }

    /**
     * 将此次同步需要将部门置为不活跃的记录保存到updateRecord中
     * @param departmentReceivedIdS 部门暂存信息的部门id列表
     * @param departmentDOS 部门信息
     * @param updateRecord 此次同步待更新的记录
     * @param taskId 任务id
     */
    private void fetchInvalidDepartment(List<Long> departmentReceivedIdS, List<DepartmentDO> departmentDOS,
                                       List<DepartmentDO> updateRecord, String taskId) {
        //此次失效的记录
        List<DepartmentDO> invalidRecord = departmentDOS.stream()
                .filter(d -> !departmentReceivedIdS.contains(d.getDepartmentId()))
                .filter(d -> StringUtils.equals(d.getStatus(), AddressListEnum.ACTIVE.getStatus()))
                .collect(Collectors.toList());
        invalidRecord.forEach(r -> {
            DepartmentDO departmentDO = new DepartmentDO();
            departmentDO.setId(r.getId());
            departmentDO.setStatus(AddressListEnum.INACTIVE.getStatus());
            departmentDO.setTaskId(taskId);
            departmentDO.setGmtUpdater(CommonConstants.GMT_CREATOR_SYSTEM);
            LOGGER.info("此次同步部门列表，部门[{}]将被置为不活跃状态", r.getDepartmentName());
            updateRecord.add(departmentDO);
        });
    }

    /**
     * 将此次同步待插入的部门记录保存到insertRecord
     * @param departmentReceivedDO 部门暂存信息
     * @param taskId 任务id
     * @param insertRecord 此次同步待插入的记录
     */
    private void fetchInsertDepartment(DepartmentReceivedDO departmentReceivedDO, String taskId,
                                       List<DepartmentDO> insertRecord) {
        DepartmentDO insertDepartmentDO = new DepartmentDO();
        insertDepartmentDO.setOrgId(departmentReceivedDO.getOrgId());
        insertDepartmentDO.setDepartmentId(departmentReceivedDO.getDepartmentId());
        insertDepartmentDO.setDepartmentName(departmentReceivedDO.getDepartmentName());
        insertDepartmentDO.setEnglishName(departmentReceivedDO.getEnglishName());
        insertDepartmentDO.setParentId(departmentReceivedDO.getParentId());
        insertDepartmentDO.setDepartmentOrder(departmentReceivedDO.getDepartmentOrder());
        insertDepartmentDO.setStatus(AddressListEnum.ACTIVE.getStatus());
        insertDepartmentDO.setTaskId(taskId);
        insertDepartmentDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        LOGGER.info("此次同步部门列表，新增部门[{}]", insertDepartmentDO.getDepartmentName());
        insertRecord.add(insertDepartmentDO);
    }

    /**
     * 将此次需要更新的部门记录保存到updateRecord
     *
     * @param departmentReceivedDO 部门暂存信息
     * @param departmentDO 部门信息
     * @param updateRecord 此次同步待更新的记录
     * @param taskId 任务id
     */
    private void fetchUpdateDepartment(DepartmentReceivedDO departmentReceivedDO, DepartmentDO departmentDO,
                                       List<DepartmentDO> updateRecord, String taskId) {
        DepartmentDO updateDepartmentDO = new DepartmentDO();
        int count = 0;
        if (!StringUtils.equals(departmentReceivedDO.getDepartmentName(), departmentDO.getDepartmentName())) {
            updateDepartmentDO.setDepartmentName(departmentReceivedDO.getDepartmentName());
            count++;
        }
        if (!departmentReceivedDO.getDepartmentOrder().equals(departmentDO.getDepartmentOrder())) {
            updateDepartmentDO.setDepartmentOrder(departmentReceivedDO.getDepartmentOrder());
            count++;
        }
        if (!StringUtils.equals(departmentReceivedDO.getEnglishName(), departmentDO.getEnglishName())) {
            updateDepartmentDO.setEnglishName(departmentReceivedDO.getEnglishName());
            count++;
        }
        if (!departmentReceivedDO.getParentId().equals(departmentDO.getParentId())) {
            updateDepartmentDO.setParentId(departmentReceivedDO.getParentId());
            count++;
        }
        if (StringUtils.equals(departmentDO.getStatus(), AddressListEnum.INACTIVE.getStatus())) {
            updateDepartmentDO.setStatus(AddressListEnum.ACTIVE.getStatus());
            count++;
        }

        if (count > 0) {
            updateDepartmentDO.setId(departmentDO.getId());
            updateDepartmentDO.setTaskId(taskId);
            updateDepartmentDO.setGmtUpdater(CommonConstants.GMT_CREATOR_SYSTEM);
            LOGGER.info("此次同步部门列表，部门[{}]更新[{}]个属性", departmentReceivedDO.getDepartmentName(), count);
            updateRecord.add(updateDepartmentDO);
        }
    }

}
