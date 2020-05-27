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
import cn.com.xinxin.sass.repository.model.DepartmentDO;
import cn.com.xinxin.sass.web.form.WechatOrgQueryForm;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.DeptTreeVO;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 微信部门相关的功能管理接口
 */

@RestController
@RequestMapping(value = "/wechat/dept/",produces = "application/json; charset=UTF-8")
public class WechatOrgMngRestController extends AclController {



    private static final Logger log = LoggerFactory.getLogger(WechatOrgMngRestController.class);



    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object listAllDeptsTree(HttpServletRequest request){

        List<DepartmentDO> wechatDepts = this.departmentService.listAllWechatDepts();


        List<DeptTreeVO> deptTreeVOS = Lists.newArrayList();


        wechatDepts.stream().forEach(
                departmentDO -> {

                    DeptTreeVO deptTreeVO = new DeptTreeVO();
                    deptTreeVO.setId(String.valueOf(departmentDO.getId()));
                    deptTreeVO.setTenantId(departmentDO.getTenantId());
                    deptTreeVO.setDepartmentId(String.valueOf(departmentDO.getDepartmentId()));
                    deptTreeVO.setDepartmentName(departmentDO.getDepartmentName());
                    deptTreeVO.setEnglishName(departmentDO.getEnglishName());
                    deptTreeVO.setParentId(String.valueOf(departmentDO.getParentId()));
                    deptTreeVO.setDepartmentOrder(departmentDO.getDepartmentOrder());
                    deptTreeVO.setStatus(departmentDO.getStatus());
                    deptTreeVO.setExtension(departmentDO.getExtension());
                    deptTreeVO.setGmtCreated(departmentDO.getGmtCreated());
                    deptTreeVO.setGmtUpdated(departmentDO.getGmtUpdated());
                    deptTreeVOS.add(deptTreeVO);

        });

        List<DeptTreeVO> resultTrees = TreeResultUtil.buildDeptTrees(deptTreeVOS);

        return resultTrees;
    }




    @RequestMapping(value = "/query",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryDeptsTree(@RequestBody WechatOrgQueryForm orgQueryForm, HttpServletRequest request){


        log.info("WechatOrgMngRestController.queryDeptsTree,orgQueryForm ={} ", orgQueryForm);

        String deptId = orgQueryForm.getDepartmentId();

        String deptName = orgQueryForm.getDepartmentName();

        String deptEngName = orgQueryForm.getEnglishName();


        log.info("WechatOrgMngRestController.queryDeptsTree,deptId ={},deptName={},deptEngName={}",
                deptId,deptName,deptEngName);

        List<DepartmentDO> wechatDepts = this.departmentService.queryDeptsByNameOrId(deptId,deptName,deptEngName);


        List<DeptTreeVO> deptTreeVOS = Lists.newArrayList();


        wechatDepts.stream().forEach(
                departmentDO -> {
                    // 组装必要的参数
                    DeptTreeVO deptTreeVO = new DeptTreeVO();
                    deptTreeVO.setId(String.valueOf(departmentDO.getId()));
                    deptTreeVO.setTenantId(departmentDO.getTenantId());
                    deptTreeVO.setDepartmentId(String.valueOf(departmentDO.getDepartmentId()));
                    deptTreeVO.setDepartmentName(departmentDO.getDepartmentName());
                    deptTreeVO.setEnglishName(departmentDO.getEnglishName());
                    deptTreeVO.setParentId(String.valueOf(departmentDO.getParentId()));
                    deptTreeVO.setDepartmentOrder(departmentDO.getDepartmentOrder());
                    deptTreeVO.setStatus(departmentDO.getStatus());
                    deptTreeVO.setExtension(departmentDO.getExtension());
                    deptTreeVO.setGmtCreated(departmentDO.getGmtCreated());
                    deptTreeVO.setGmtUpdated(departmentDO.getGmtUpdated());
                    deptTreeVOS.add(deptTreeVO);

                });

        List<DeptTreeVO> resultTrees = TreeResultUtil.buildDeptTrees(deptTreeVOS);

        return resultTrees;
    }

}
