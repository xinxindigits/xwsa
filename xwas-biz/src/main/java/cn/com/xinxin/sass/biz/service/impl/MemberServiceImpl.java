package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.MemberService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.MemberDOMapper;
import cn.com.xinxin.sass.repository.model.MemberDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/24.
 * @updater:
 * @description: 成员信息表数据库服务
 */
@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberDOMapper memberDOMapper;

    public MemberServiceImpl(final MemberDOMapper memberDOMapper) {
        this.memberDOMapper = memberDOMapper;
    }

    /**
     * 通过机构id和用户id查询记录
     * @param orgId 机构id
     * @param userIdS 用户id
     * @return 成员列表
     */
    @Override
    public List<MemberDO> queryByOrgIdAndUserId(String orgId, List<String> userIdS) {
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过机构id和用户id查询记录, orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和用户id查询记录, orgId不能为空");
        }
        if (CollectionUtils.isEmpty(userIdS)) {
            LOGGER.error("通过机构id和用户id查询记录, userId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和用户id查询记录, userId不能为空");
        }
        return memberDOMapper.queryByOrgIdAndUserId(orgId, userIdS);
    }

    /**
     * 批量插入
     * @param memberDOS 成员列表
     * @return 插入成功数量
     */
    @Override
    public int insertBatch(List<MemberDO> memberDOS) {
        if (CollectionUtils.isEmpty(memberDOS)) {
            LOGGER.warn("批量插入记录, memberDOS为空");
            return 0;
        }
        return memberDOMapper.insertBatch(memberDOS);
    }

    /**
     * 批量更新
     * @param memberDOS 成员列表
     * @return 更新成功数量
     */
    @Override
    public int updateBatchById(List<MemberDO> memberDOS) {
        if (CollectionUtils.isEmpty(memberDOS)) {
            LOGGER.warn("批量更新记录, memberDOS为空");
            return 0;
        }
        return memberDOMapper.updateBatchById(memberDOS);
    }
}
