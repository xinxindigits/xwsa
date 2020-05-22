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
import cn.com.xinxin.sass.biz.service.DepartmentService;
import cn.com.xinxin.sass.biz.service.MemberService;
import cn.com.xinxin.sass.common.enums.GenderTypeEnums;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.DepartmentDO;
import cn.com.xinxin.sass.repository.model.MemberDO;
import cn.com.xinxin.sass.web.form.WechatmemberQueryForm;
import cn.com.xinxin.sass.web.vo.DepartmentVO;
import cn.com.xinxin.sass.web.vo.MemberDetailVO;
import cn.com.xinxin.sass.web.vo.MemberVO;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 微信部门员工相关的功能管理接口
 */

@RestController
@RequestMapping(value = "/wechat/member",produces = "application/json; charset=UTF-8")
public class WechatOrgMemberRestController extends AclController {


    private static final Logger log = LoggerFactory.getLogger(WechatOrgMemberRestController.class);


    @Autowired
    private MemberService memberService;

    @Autowired
    private DepartmentService departmentService;



    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"SASS_WEXIN_STAFF_MNG"},logical= Logical.OR)
    public Object listWechatMembers(HttpServletRequest request,
                                    @RequestBody WechatmemberQueryForm queryForm){

        Integer pageIndex = queryForm.getPageIndex();
        Integer pageSize = queryForm.getPageSize();

        PageResultVO page = new PageResultVO();
        page.setPageNumber((pageIndex == null) ? PageResultVO.DEFAULT_PAGE_NUM : pageIndex);
        page.setPageSize((pageSize == null) ? PageResultVO.DEFAULT_PAGE_SIZE : pageSize);

        PageResultVO<MemberDO> pageResultDO = this.memberService.queryMembersByPages(page);

        List<String> memberDeptsIds = pageResultDO.getItems()
                .stream().map(x->String.valueOf(x.getMainDepartment()))
                .distinct()
                .collect(toList());

        List<DepartmentDO> departmentDOList = this.departmentService.queryDeptsByIds(memberDeptsIds);

        PageResultVO<MemberVO> results = BaseConvert.convert(pageResultDO,PageResultVO.class);

        List<MemberVO> memberVOList = Lists.newArrayList();
        // 转换数据
        if(!CollectionUtils.isEmpty(pageResultDO.getItems())){
            memberVOList = this.convertMemberGendersAndDepts(pageResultDO.getItems(),departmentDOList);
        }
        results.setItems(memberVOList);

        return results;
    }


    @RequestMapping(value = "/detail/{memberId}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = {"SASS_WEXIN_STAFF_MNG"},logical= Logical.OR)
    public Object queryWechatMemberDetail(HttpServletRequest request,
                                          @PathVariable String memberId){

        log.info("queryWechatMemberDetail, account = {}",memberId);

        MemberDO  memberDO = this.memberService.queryMemberDetailById(memberId);

        String deptIdString = memberDO.getDepartmentIdList();

        String[] deptIdArray = deptIdString.split("@");

        List<String> deptIdsList = Lists.newArrayList(deptIdArray);



        DepartmentDO mainDepartmentDO =
                this.departmentService.queryDeptByDeptId(String.valueOf(memberDO.getMainDepartment()));

        MemberDetailVO memberDetailVO = BaseConvert.convert(memberDO,MemberDetailVO.class);

        memberDetailVO.setGender(GenderTypeEnums.getEnumByNum(String.valueOf(memberDO.getGender())).getDesc());
        memberDetailVO.setMainDepartmentName(mainDepartmentDO.getDepartmentName());

        if(CollectionUtils.isNotEmpty(deptIdsList)){

            List<DepartmentDO> departmentDOList = this.departmentService.queryDeptsByIds(deptIdsList);

            List<DepartmentVO> departmentVOList = BaseConvert.convertList(departmentDOList,DepartmentVO.class);

            memberDetailVO.setDepartments(departmentVOList);
        }


        return memberDetailVO;
    }



    @RequestMapping(value = "/dept/query",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"SASS_WEXIN_STAFF_MNG"},logical= Logical.OR)
    public Object listWechatMembersByDeptId(HttpServletRequest request,
                                    @RequestBody WechatmemberQueryForm queryForm){

        String deptId = queryForm.getDeptId();

        Integer pageIndex = queryForm.getPageIndex();
        Integer pageSize = queryForm.getPageSize();

        PageResultVO page = new PageResultVO();
        page.setPageNumber((pageIndex == null) ? PageResultVO.DEFAULT_PAGE_NUM : pageIndex);
        page.setPageSize((pageSize == null) ? PageResultVO.DEFAULT_PAGE_SIZE : pageSize);


        PageResultVO<MemberDO> pageResultDO = this.memberService.queryByDeptId(deptId, page);

        List<String> memberDeptsIds = pageResultDO.getItems()
                .stream().map(x->String.valueOf(x.getMainDepartment()))
                .distinct()
                .collect(toList());

        List<DepartmentDO> departmentDOList = this.departmentService.queryDeptsByIds(memberDeptsIds);

        PageResultVO<MemberVO> results = BaseConvert.convert(pageResultDO,PageResultVO.class);

        List<MemberVO> memberVOList = Lists.newArrayList();
        // 转换数据
        if(!CollectionUtils.isEmpty(pageResultDO.getItems())){
            memberVOList = this.convertMemberGendersAndDepts(pageResultDO.getItems(),departmentDOList);
        }
        results.setItems(memberVOList);

        return results;
    }


    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"SASS_WEXIN_STAFF_MNG"},logical= Logical.OR)
    public Object queryMembers(HttpServletRequest request,
                                            @RequestBody WechatmemberQueryForm queryForm){


        String memberName = queryForm.getMemberName();
        String mobile = queryForm.getMobile();


        Integer pageIndex = queryForm.getPageIndex();
        Integer pageSize = queryForm.getPageSize();

        PageResultVO page = new PageResultVO();
        page.setPageNumber((pageIndex == null) ? PageResultVO.DEFAULT_PAGE_NUM : pageIndex);
        page.setPageSize((pageSize == null) ? PageResultVO.DEFAULT_PAGE_SIZE : pageSize);


        PageResultVO<MemberDO> pageResultDO = this.memberService.queryByNameAndMobile(memberName, mobile, page);



        List<String> memberDeptsIds = pageResultDO.getItems()
                .stream().map(x->String.valueOf(x.getMainDepartment()))
                .distinct()
                .collect(toList());

        List<DepartmentDO> departmentDOList = this.departmentService.queryDeptsByIds(memberDeptsIds);

        PageResultVO<MemberVO> results = BaseConvert.convert(pageResultDO,PageResultVO.class);

        List<MemberVO> memberVOList = Lists.newArrayList();
        // 转换数据
        if(!CollectionUtils.isEmpty(pageResultDO.getItems())){
            memberVOList = this.convertMemberGendersAndDepts(pageResultDO.getItems(),departmentDOList);
        }
        results.setItems(memberVOList);

        return results;
    }


    private List<MemberVO> convertMemberGendersAndDepts(List<MemberDO> memberVOList,
                                                        List<DepartmentDO> departmentDOList){

        Map<String,DepartmentDO> deptMap = departmentDOList.stream()
                .collect(Collectors.toMap(departmentDO -> String.valueOf(departmentDO.getDepartmentId()),
                        departmentDO->departmentDO));

        List<MemberVO> result = memberVOList.stream()
                .map(memberDO -> {
                    MemberVO memberVO = BaseConvert.convert(memberDO, MemberVO.class);
                    memberVO.setGender(GenderTypeEnums.getEnumByNum(String.valueOf(memberDO.getGender())).getDesc());
                    memberVO.setDeptName(
                            deptMap.get(String.valueOf(memberDO.getMainDepartment())).getDepartmentName()
                    );
                    return memberVO;
                }).collect(Collectors.toList());

        return result;
    }



}
