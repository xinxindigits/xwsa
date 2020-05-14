package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.*;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.*;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.*;
import cn.com.xinxin.sass.web.utils.RegexUtils;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.*;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: zhouyang
 * @created: 17/04/2020.
 * @updater:
 * @description: 权限相关的角色管理接口
 */
@RestController
@RequestMapping(value = "/tags",produces = "application/json; charset=UTF-8")
public class SassTagsRestController extends AclController {

    private static final Logger logger = LoggerFactory.getLogger(SassTagsRestController.class);

    private final TagsService tagsService;


    public SassTagsRestController(final TagsService tagsService) {
        this.tagsService = tagsService;
    }


    @RequestMapping(value = "/query",method = RequestMethod.POST)
    //@RequiresPermissions("/tags/query")
    public Object queryTags(@RequestBody TagForm tagForm, HttpServletRequest request){

        logger.info("SassTagsRestController,query,tagForm ={}",tagForm);

        if(null == tagForm){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"标签参数不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((tagForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : tagForm.getPageIndex());
        page.setPageSize((tagForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : tagForm.getPageSize());

        String tagName = tagForm.getName();
        SassUserInfo sassUserInfo = this.getSassUser(request);

        // 默认不带任何参数查询
        PageResultVO<TagsDO> tagsByPages = this.tagsService.queryTagsByPages(page,tagName);
        PageResultVO<TagsVO> resultVO = BaseConvert.convert(tagsByPages, PageResultVO.class);

        List<TagsVO> tagsVOS = BaseConvert.convertList(tagsByPages.getItems(),TagsVO.class);
        resultVO.setItems(tagsVOS);

        return resultVO;

    }


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    //@RequiresPermissions("/tags/list")
    public Object listTags(@RequestBody TagForm tagForm, HttpServletRequest request){

        logger.info("SassTagsRestController,list,tagForm ={}",tagForm);

        PageResultVO page = new PageResultVO();
        page.setPageNumber((tagForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : tagForm.getPageIndex());
        page.setPageSize((tagForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : tagForm.getPageSize());

        if(null == tagForm){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"标签参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 默认不带任何参数查询
        PageResultVO<TagsDO> tagsByPages = this.tagsService.queryTagsByPages(page,"");

        PageResultVO<TagsVO> resultVO = BaseConvert.convert(tagsByPages, PageResultVO.class);

        List<TagsVO> tagsVOS = BaseConvert.convertList(tagsByPages.getItems(),TagsVO.class);
        resultVO.setItems(tagsVOS);

        return resultVO;

    }


    @RequestMapping(value = "/routes",method = RequestMethod.POST)
    //@RequiresPermissions("/tags/rotes")
    public Object rotesTags(@RequestBody TagForm tagForm, HttpServletRequest request){

        logger.info("SassTagsRestController,rotesTags,tagForm ={}",tagForm);

        String tagName = tagForm.getName();

        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 默认不带任何参数查询
        List<TagsDO> tagsDOList =
                this.tagsService.queryAllTagsByNameAndTenantId(tagName,sassUserInfo.getTenantId());

        List<TagsVO> resultVO = BaseConvert.convertList(tagsDOList, TagsVO.class);


        return resultVO;

    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    //@RequiresPermissions("/tags/update")
    public Object updateTags(@RequestBody TagForm tagForm, HttpServletRequest request){

        logger.info("SassTagsRestController,update,tagForm ={}",tagForm);

        if(null == tagForm){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"标签参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);
        TagsDO tagsDO = BaseConvert.convert(tagForm,TagsDO.class);

        tagsDO.setTenantId(sassUserInfo.getTenantId());
        tagsDO.setGmtUpdater(sassUserInfo.getAccount());

        return this.tagsService.updateTags(tagsDO);

    }


    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    //@RequiresPermissions("/tags/delete")
    public Object deleteTags(@RequestParam Long tagId,HttpServletRequest request){

        logger.info("SassTagsRestController,deleteTags,tagId ={}",tagId);

        if(null == tagId){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"标签参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);

        return this.tagsService.deleteTagsByIds(tagId);

    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    //@RequiresPermissions("/tags/create")
    public Object createTags(@RequestBody TagForm tagForm, HttpServletRequest request){

        logger.info("SassTagsRestController,createTags,tagForm ={}",tagForm);

        if(null == tagForm){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"标签参数不能为空");
        }

        if(!RegexUtils.isDataCode(tagForm.getCode())){
            // 如果匹配不是useraccount格式
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "编码不能包含特殊字符或者长度超过16","编码不能包含特殊字符或者长度超过16");
        }


        SassUserInfo sassUserInfo = this.getSassUser(request);
        TagsDO createTags = BaseConvert.convert(tagForm,TagsDO.class);
        if(StringUtils.isEmpty(createTags.getCode())){
            createTags.setCode("TG"+System.currentTimeMillis());
        }
        if(StringUtils.isEmpty(createTags.getTagType())){
            createTags.setTagType("COMMON");
        }
        createTags.setTenantId(sassUserInfo.getTenantId());
        createTags.setGmtCreator(sassUserInfo.getAccount());
        createTags.setGmtUpdater(sassUserInfo.getAccount());
        int result = this.tagsService.createTags(createTags);

        return result;

    }


    @RequestMapping(value = "/fixdata",method = RequestMethod.POST)
    //@RequiresPermissions("/tags/fixdata")
    public Object fixdataTags(@RequestBody TagRelationForm tagForm, HttpServletRequest request){

        logger.info("SassTagsRestController,fixdata,tagForm ={}",tagForm);

        if(null == tagForm){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"标签参数不能为空");
        }

        String keyId = tagForm.getKeyId();
        String keyName = tagForm.getKeyName();
        List<String> tagIds = tagForm.getTagIds();
        SassUserInfo sassUserInfo = this.getSassUser(request);

        List<TagsRelationsDO> tagsRelationsDOS = Lists.newArrayList();

        for(String id: tagIds){

            TagsRelationsDO tagsRelationsDO = new TagsRelationsDO();
            tagsRelationsDO.setKeyId(keyId);
            tagsRelationsDO.setKeyName(keyName);
            tagsRelationsDO.setTagId(id);
            tagsRelationsDO.setDescription(tagForm.getDescription());
            tagsRelationsDO.setGmtCreator(sassUserInfo.getAccount());
            tagsRelationsDO.setGmtUpdater(sassUserInfo.getAccount());
            tagsRelationsDO.setTenantId(sassUserInfo.getTenantId());
            tagsRelationsDOS.add(tagsRelationsDO);

        }

        // 首先删除

        this.tagsService.deleteTagsBykeyId(keyId);

        int result = this.tagsService.createTagsRelations(tagsRelationsDOS);

        return result;

    }

}
