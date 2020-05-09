package cn.com.xinxin.sass.web.rest;

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
    public Object queryOplogsByAccount(@RequestBody OplogForm oplogForm, HttpServletRequest request){

        if(oplogForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"参数不能为空");
        }

        String account = oplogForm.getAccount();

        if(StringUtils.isBlank(account)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"参数不能为空");
        }

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
    public Object deltailOplogs(@PathVariable String id, HttpServletRequest request){

        if(id == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"参数不能为空");
        }

        OplogDOWithBLOBs oplogDO = this.oplogService.queryOplogDetailById(id);

        OplogVO oplogVO =  BaseConvert.convert(oplogDO, OplogVO.class);


        return oplogVO;
    }




}
