package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.OplogService;
import cn.com.xinxin.sass.repository.dao.OplogDOMapper;
import cn.com.xinxin.sass.repository.model.OplogDO;
import cn.com.xinxin.sass.repository.model.OplogDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhouyang
 * @created: 09/05/2020.
 * @updater:
 * @description:
 */
@Service
public class OplogServiceImpl implements OplogService {


    @Autowired
    private  OplogDOMapper oplogDOMapper;


    @Override
    public int createOplog(OplogDOWithBLOBs oplogDO) {

        oplogDOMapper.insertSelective(oplogDO);

        return 0;
    }
}
