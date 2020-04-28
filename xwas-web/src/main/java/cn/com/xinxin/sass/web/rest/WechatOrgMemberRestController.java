package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.MemberService;
import cn.com.xinxin.sass.common.enums.GenderTypeEnums;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.MemberDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.WechatmemberQueryForm;
import cn.com.xinxin.sass.web.vo.MemberVO;
import cn.com.xinxin.sass.web.vo.UserInfoVO;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 微信部门员工相关的功能管理接口
 */

@RestController
@RequestMapping(value = "/wechat/member",produces = "application/json; charset=UTF-8")
public class WechatOrgMemberRestController extends AclController {



    @Autowired
    private MemberService memberService;



    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Object listWechatMembers(HttpServletRequest request,
                                    @RequestBody WechatmemberQueryForm queryForm){


        return null;
    }



    @RequestMapping(value = "/dept/query",method = RequestMethod.POST)
    @ResponseBody
    public Object listWechatMembersByDeptId(HttpServletRequest request,
                                    @RequestBody WechatmemberQueryForm queryForm){

        String deptId = queryForm.getDeptId();

        Integer pageIndex = queryForm.getPageIndex();
        Integer pageSize = queryForm.getPageSize();

        PageResultVO page = new PageResultVO();
        page.setPageNumber((pageIndex == null) ? PageResultVO.DEFAULT_PAGE_NUM : pageIndex);
        page.setPageSize((pageSize == null) ? PageResultVO.DEFAULT_PAGE_SIZE : pageSize);


        PageResultVO<MemberDO> pageResultDO = this.memberService.queryByDeptId(deptId, page);


        PageResultVO<MemberVO> results = BaseConvert.convert(pageResultDO,PageResultVO.class);

        List<MemberVO> memberVOList = Lists.newArrayList();
        // 转换数据
        if(!CollectionUtils.isEmpty(pageResultDO.getItems())){
            memberVOList = pageResultDO.getItems().stream().map(memberDO -> {
                MemberVO memberVO = BaseConvert.convert(memberDO, MemberVO.class);
                memberVO.setGender(GenderTypeEnums.getEnumByNum(String.valueOf(memberDO.getGender())).getDesc());
                return memberVO;
            }).collect(Collectors.toList());
        }

        results.setItems(memberVOList);

        return results;
    }



}