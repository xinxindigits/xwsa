package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.DepartmentService;
import cn.com.xinxin.sass.repository.model.DepartmentDO;
import cn.com.xinxin.sass.web.form.WechatOrgQueryForm;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.DeptTreeVO;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions(value = {"SASS_WEXIN_DEPT_MNG"},logical= Logical.OR)
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
    @RequiresPermissions(value = {"SASS_WEXIN_DEPT_MNG"},logical= Logical.OR)
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
