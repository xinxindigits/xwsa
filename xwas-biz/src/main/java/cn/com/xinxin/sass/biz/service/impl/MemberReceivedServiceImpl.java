package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.MemberReceivedService;
import cn.com.xinxin.sass.repository.dao.MemberReceivedDOMapper;
import cn.com.xinxin.sass.repository.model.MemberReceivedDO;
import org.apache.commons.collections4.CollectionUtils;
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
}
