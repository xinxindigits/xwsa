package cn.com.xinxin.sass.web.rest;

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

import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.OplogService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.OplogDO;
import cn.com.xinxin.sass.repository.model.OplogDOWithBLOBs;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.web.form.OplogForm;
import cn.com.xinxin.sass.web.vo.OplogVO;
import cn.com.xinxin.sass.web.vo.UserInfoVO;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 操作日志记录相关的查询接口
 */

@RestController
@RequestMapping(value = "/oplog",produces = "application/json; charset=UTF-8")
public class OplogMngRestController extends AclController {


    private final OplogService oplogService;

    public OplogMngRestController(OplogService oplogService) {
        this.oplogService = oplogService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object listOplogs(@RequestBody OplogForm oplogForm, HttpServletRequest request){

        if(oplogForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"参数不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((oplogForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : oplogForm.getPageIndex());
        page.setPageSize((oplogForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : oplogForm.getPageSize());
        PageResultVO<OplogDOWithBLOBs> pageResultVO = this.oplogService.queryOplogByPages(page,"");

        PageResultVO<OplogVO> resultVO = BaseConvert.convert(pageResultVO, PageResultVO.class);

        List<OplogVO> oplogVOList =  BaseConvert.convertList(pageResultVO.getItems(),OplogVO.class);
        resultVO.setItems(oplogVOList);

        return resultVO;
    }


    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryOplogsByAccount(@RequestBody OplogForm oplogForm, HttpServletRequest request){

        if(oplogForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"参数不能为空");
        }

        String account = oplogForm.getAccount();

        PageResultVO page = new PageResultVO();
        page.setPageNumber((oplogForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : oplogForm.getPageIndex());
        page.setPageSize((oplogForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : oplogForm.getPageSize());

        PageResultVO<OplogDOWithBLOBs> pageResultVO = this.oplogService.queryOplogByPages(page,account);

        PageResultVO<OplogVO> resultVO = BaseConvert.convert(pageResultVO, PageResultVO.class);

        List<OplogVO> oplogVOList =  BaseConvert.convertList(pageResultVO.getItems(),OplogVO.class);

        resultVO.setItems(oplogVOList);

        return resultVO;
    }


    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    @RequiresRoles(value = {"sass_admin","admin"},logical= Logical.OR)
    public Object deltailOplogs(@PathVariable String id, HttpServletRequest request){

        if(id == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"参数不能为空");
        }

        OplogDOWithBLOBs oplogDO = this.oplogService.queryOplogDetailById(id);

        OplogVO oplogVO =  BaseConvert.convert(oplogDO, OplogVO.class);


        return oplogVO;
    }




}
