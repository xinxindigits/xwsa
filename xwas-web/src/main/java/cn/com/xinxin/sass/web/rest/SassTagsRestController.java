package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.*;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.*;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.*;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.MenuTreeVO;
import cn.com.xinxin.sass.web.vo.ResourceVO;
import cn.com.xinxin.sass.web.vo.RoleVO;
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



    @RequestMapping(value = "/create",method = RequestMethod.POST)
    //@RequiresPermissions("/tags/create")
    public Object createTags(@RequestBody TagForm tagForm, HttpServletRequest request){

        logger.info("SassTagsRestController,createTags,tagForm ={}",tagForm);

        if(null == tagForm){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"标签参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);
        TagsDO createTags = BaseConvert.convert(tagForm,TagsDO.class);
        if(StringUtils.isEmpty(createTags.getCode())){
            createTags.setCode("TG"+System.currentTimeMillis());
        }
        if(StringUtils.isEmpty(createTags.getTagType())){
            createTags.setCode("COMMON");
        }
        createTags.setTenantId(sassUserInfo.getTenantId());
        createTags.setGmtCreator(sassUserInfo.getAccount());
        createTags.setGmtUpdater(sassUserInfo.getAccount());
        int result = this.tagsService.createTags(createTags);

        return result;

    }
}
