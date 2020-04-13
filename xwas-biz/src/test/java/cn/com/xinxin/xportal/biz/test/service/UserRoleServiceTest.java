package cn.com.xinxin.xportal.biz.test.service;

import cn.com.xinxin.portal.biz.service.UserRoleService;
import cn.com.xinxin.portal.repository.model.UserRoleDO;
import cn.com.xinxin.xportal.biz.test.base.SpringBaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public class UserRoleServiceTest extends SpringBaseTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    @Ignore
    public void TestCreateUserRole(){
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserNo("asdfasf");
        userRoleDO.setRoleCode("fsfdsf");

        UserRoleDO userRoleDO1 = userRoleService.createUserRole(userRoleDO);
    }

    @Test
    @Ignore
    public void testBatchInsert(){
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserNo("asdfasf1");
        userRoleDO.setRoleCode("fsfdsf1");
        userRoleDO.setExtension("");
        userRoleDO.setGmtCreator("");
        userRoleDO.setGmtUpdater("");

        UserRoleDO userRoleDO2 = new UserRoleDO();
        userRoleDO2.setUserNo("asdfasf2");
        userRoleDO2.setRoleCode("fsfdsf2");
        userRoleDO2.setExtension("");
        userRoleDO2.setGmtCreator("");
        userRoleDO2.setGmtUpdater("");

        boolean rst = userRoleService.createUserRoles(Arrays.asList(userRoleDO,userRoleDO2));
        Assert.assertTrue(rst);
    }

}
