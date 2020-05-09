package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.OplogService;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.OplogDOMapper;
import cn.com.xinxin.sass.repository.model.OplogDOWithBLOBs;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhouyang
 * @created: 09/05/2020.
 * @updater:
 * @description:
 */
@Service
public class OplogServiceImpl implements OplogService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OplogServiceImpl.class);


    @Autowired
    private  OplogDOMapper oplogDOMapper;


    @Override
    public int createOplog(OplogDOWithBLOBs oplogDO) {

        return oplogDOMapper.insertSelective(oplogDO);

    }

    @Override
    public PageResultVO queryOplogByPages(PageResultVO page, String account) {

        LOGGER.info("queryOplogByPages:{},account={}",JSONObject.toJSONString(page),account);

        com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());

        List<OplogDOWithBLOBs> oplogDOS = this.oplogDOMapper.selectOplogsByAccount(account);

        PageResultVO<OplogDOWithBLOBs> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotal(doPage.getTotal());
        result.setItems(oplogDOS);
        return result;

    }

    @Override
    public OplogDOWithBLOBs queryOplogDetailById(String id) {

        LOGGER.info("queryOplogDetailById,id={}",id);

        OplogDOWithBLOBs oplogDOWithBLOBs = this.oplogDOMapper.selectByPrimaryKey(Long.valueOf(id));

        return oplogDOWithBLOBs;
    }

}
