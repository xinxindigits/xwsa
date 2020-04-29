package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.MsgRecordService;
import cn.com.xinxin.sass.repository.dao.MsgRecordDOMapper;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 消息记录服务
 */
@Service
public class MsgRecordServiceImpl implements MsgRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgRecordServiceImpl.class);

    private final MsgRecordDOMapper msgRecordDOMapper;

    public MsgRecordServiceImpl(final MsgRecordDOMapper msgRecordDOMapper) {
        this.msgRecordDOMapper = msgRecordDOMapper;
    }

    /**
     * 批量插入记录
     * @param msgRecordDOS 消息记录
     * @return 插入记录条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int insertBatch(List<MsgRecordDO> msgRecordDOS) {
        if (CollectionUtils.isEmpty(msgRecordDOS)) {
            LOGGER.warn("批量插入消息记录， msgRecordDOS为空");
            return 0;
        }
        return msgRecordDOMapper.insertBatch(msgRecordDOS);
    }
}
