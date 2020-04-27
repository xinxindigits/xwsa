package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.MsgRecordDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 消息记录服务
 */
public interface MsgRecordService {

    /**
     * 批量插入记录
     * @param msgRecordDOS 消息记录
     * @return 插入记录条数
     */
    int insertBatch(List<MsgRecordDO> msgRecordDOS);
}
