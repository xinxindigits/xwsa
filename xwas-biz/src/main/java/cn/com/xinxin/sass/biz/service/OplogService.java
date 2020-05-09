package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.common.model.PageResultVO;
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


    /**
     * 分页查询列表
     * @param page
     * @param account
     * @return
     */
    PageResultVO<OplogDOWithBLOBs> queryOplogByPages(PageResultVO page, String account);


    /**
     * 查询某个日志
     * @param id
     * @return
     */
    OplogDOWithBLOBs queryOplogDetailById(String id);


}
