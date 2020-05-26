package cn.com.xinxin.sass.biz.service.impl;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

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
