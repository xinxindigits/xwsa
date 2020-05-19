package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.MemberReceivedService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.MemberReceivedDOMapper;
import cn.com.xinxin.sass.repository.model.MemberReceivedDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 成员信息暂存表数据库服务
 */
@Service
public class MemberReceivedServiceImpl implements MemberReceivedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberReceivedServiceImpl.class);

    private final MemberReceivedDOMapper memberReceivedDOMapper;

    public MemberReceivedServiceImpl(final MemberReceivedDOMapper memberReceivedDOMapper) {
        this.memberReceivedDOMapper = memberReceivedDOMapper;
    }

    /**
     * 批量插入记录
     * @param memberReceivedDOS 记录
     * @return 插入成功条数
     */
    @Override
    public int insertBatch(List<MemberReceivedDO> memberReceivedDOS) {
        if (CollectionUtils.isEmpty(memberReceivedDOS)) {
            LOGGER.warn("此次批量插入数据到成员信息暂存表，数据为空");
            return 0;
        }

        return memberReceivedDOMapper.insertBatch(memberReceivedDOS);
    }

    /**
     * 通过takId和orgId和部门id查询记录
     * @param taskId 任务id
     * @param orgId 机构id
     * @param departmentId 部门id
     * @return 部门暂存信息
     */
    @Override
    public List<MemberReceivedDO> queryByTaskIdAndOrgIdAndDepartmentId(String taskId, String orgId, String departmentId){
        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("通过takId和orgId和部门id查询记录， taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过takId和orgId和部门id查询记录， taskId不能为空");
        }
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过takId和orgId和部门id查询记录， orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过takId和orgId和部门id查询记录， orgId不能为空");
        }
        if (StringUtils.isBlank(departmentId)) {
            LOGGER.error("通过takId和orgId和部门id查询记录， departmentId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过takId和orgId和部门id查询记录， departmentId不能为空");
        }
        return memberReceivedDOMapper.queryByTaskIdAndOrgIdAndDepartmentId(taskId, orgId, departmentId);
    }

    /**
     * 分批批量插入记录
     * @param memberReceivedDOS 记录
     * @param size 每次插入的数量
     */
    @Override
    public void insertBatchPartially(List<MemberReceivedDO> memberReceivedDOS, int size) {
        List<List<MemberReceivedDO>> memberReceivedDOSList = CommonUtils.split(memberReceivedDOS, size);
        memberReceivedDOSList.forEach(this::insertBatch);
    }
}
