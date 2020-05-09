package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.OplogDO;
import cn.com.xinxin.sass.repository.model.OplogDOWithBLOBs;

/**
 * @author: zhouyang
 * @created: 09/05/2020.
 * @updater:
 * @description:
 */
public interface OplogService {

    /**
     * 创建oplog
     * @param oplogDO
     * @return
     */
    int createOplog(OplogDOWithBLOBs oplogDO);



}
