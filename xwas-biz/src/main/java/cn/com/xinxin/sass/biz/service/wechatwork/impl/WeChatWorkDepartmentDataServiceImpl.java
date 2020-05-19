package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.convert.DepartmentConvert;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkImportDataBO;
import cn.com.xinxin.sass.biz.service.DepartmentReceivedService;
import cn.com.xinxin.sass.biz.service.DepartmentService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDataService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.AddressListEnum;
import cn.com.xinxin.sass.repository.model.DepartmentDO;
import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkDepartmentBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkDepartmentClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 企业微信部门相关数据服务
 */
@Service(value = "weChatWorkDepartmentDataServiceImpl")
public class WeChatWorkDepartmentDataServiceImpl implements WeChatWorkDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkDepartmentDataServiceImpl.class);

    private final WeChatWorkDepartmentClient weChatWorkDepartmentClient;
    private final DepartmentReceivedService departmentReceivedService;
    private final WeChatWorkInteractionClient weChatWorkInteractionClient;
    private final DepartmentService departmentService;

    public WeChatWorkDepartmentDataServiceImpl(final WeChatWorkDepartmentClient weChatWorkDepartmentClient,
                                               final DepartmentReceivedService departmentReceivedService,
                                               final WeChatWorkInteractionClient weChatWorkInteractionClient,
                                               final DepartmentService departmentService) {
        this.weChatWorkDepartmentClient = weChatWorkDepartmentClient;
        this.departmentReceivedService = departmentReceivedService;
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
        this.departmentService = departmentService;
    }

    /**
     * 获取部门相关数据
     * @param weChatWorkFetchDataBO 获取数据BO
     */
    @Override
    public void fetchData(WeChatWorkFetchDataBO weChatWorkFetchDataBO) {

        //获取部门数据所需要的token
        String token = weChatWorkInteractionClient.fetchToken(
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCorpId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getAddressListSecret());

        //从企业微信获取部门列表
        List<WeChatWorkDepartmentBO> weChatWorkDepartmentBOS = weChatWorkDepartmentClient.queryDepartmentList(token);

        //将BO转化为DO
        List<DepartmentReceivedDO> departmentReceivedDOS = DepartmentConvert.convert2DepartmentReceivedDOList(
                weChatWorkDepartmentBOS, weChatWorkFetchDataBO.getTaskId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getTenantId());

        //将拉取的部门数据保存在部门数据记录表中
        departmentReceivedService.insertBatchPartially(departmentReceivedDOS, CommonConstants.ONE_HUNDRED);

        //将此次获取的部门id放到BO中，用于成员数据的拉取
        weChatWorkFetchDataBO.setDepartmentIdS(weChatWorkDepartmentBOS.stream()
                .map(WeChatWorkDepartmentBO::getDepartmentId).collect(Collectors.toList()));
    }

    /**
     * 导入部门相关数据
     * @param weChatWorkImportDataBO 导入数据BO
     */
    @Override
    public void importData(WeChatWorkImportDataBO weChatWorkImportDataBO) {
        //根据taskID查询部门信息暂存表的记录
        List<DepartmentReceivedDO> departmentReceivedDOS = departmentReceivedService.selectByTaskIdAndOrgId(
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId(),
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId());

        //部门暂存信息的部门id列表
        List<Long> departmentReceivedIdS = departmentReceivedDOS.stream()
                .map(DepartmentReceivedDO::getDepartmentId).collect(Collectors.toList());

        //根据机构编码查询部门信息
        List<DepartmentDO> departmentDOS = departmentService.selectByOrgId(
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId());

        //此次同步待插入的记录
        List<DepartmentDO> insertRecord = new ArrayList<>();

        //此次同步待更新的记录
        List<DepartmentDO> updateRecord = new ArrayList<>();

        //获取此次同步需要将部门置为不活跃的记录
        fetchInvalidDepartment(departmentReceivedIdS, departmentDOS, updateRecord,
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId());

        departmentReceivedDOS.forEach(d -> {
            //获取部门id相同的部门记录
            DepartmentDO departmentDO = departmentDOS.stream()
                    .filter(p -> d.getDepartmentId().equals(p.getDepartmentId()))
                    .findFirst().orElse(null);

            if (null == departmentDO) {
                //将此次同步待插入的部门记录保存到insertRecord
                fetchInsertDepartment(d, weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId(), insertRecord);
            } else {
                //将此次需要更新的部门记录保存到updateRecord
                fetchUpdateDepartment(d, departmentDO, updateRecord, weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId());
            }
        });

        //插入记录
        departmentService.insertBatchPartially(insertRecord, CommonConstants.ONE_HUNDRED);

        //更新记录
        departmentService.updateBatchByIdPartially(updateRecord, CommonConstants.ONE_HUNDRED);

        //失效记录
        int inactiveCount = departmentService.updateInactiveStatus(weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId(),
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId());

        //此次成员改变的记录数
        weChatWorkImportDataBO.getTenantDataSyncLogDO().setDepartmentCount(insertRecord.size() + updateRecord.size() + inactiveCount);

        //此次同步部门信息记录表的id
        weChatWorkImportDataBO.setDepartmentReceivedIdS(departmentReceivedIdS);
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
        insertDepartmentDO.setTenantId(departmentReceivedDO.getTenantId());
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
