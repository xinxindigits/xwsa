package cn.com.xinxin.sass.job.test.service;

import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.repository.model.UserRoleDO;
import cn.com.xinxin.sass.job.test.base.SpringBaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public class UserDODODORoleServiceTest extends SpringBaseTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    @Ignore
    public void TestCreateUserRole(){
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserAccount("asdfasf");
        userRoleDO.setRoleCode("fsfdsf");

        UserRoleDO userRoleDO1 = userRoleService.createUserRole(userRoleDO);
    }

    @Test
    @Ignore
    public void testBatchInsert(){
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserAccount("asdfasf1");
        userRoleDO.setRoleCode("fsfdsf1");
        userRoleDO.setExtension("");
        userRoleDO.setGmtCreator("");
        userRoleDO.setGmtUpdater("");

        UserRoleDO userRoleDO2 = new UserRoleDO();
        userRoleDO2.setUserAccount("asdfasf2");
        userRoleDO2.setRoleCode("fsfdsf2");
        userRoleDO2.setExtension("");
        userRoleDO2.setGmtCreator("");
        userRoleDO2.setGmtUpdater("");

        boolean rst = userRoleService.createUserRoles(Arrays.asList(userRoleDO,userRoleDO2));
        Assert.assertTrue(rst);
    }

    @Test
    public void testSaveOrUpdate(){
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserAccount("admin004");
        userRoleDO.setUserName("admin004");
        userRoleDO.setDeleted(false);
        userRoleDO.setGmtCreator("admin004");
        userRoleDO.setGmtUpdater("admin004");
        userRoleDO.setRoleCode("RO1001");
        userRoleDO.setRoleName("系统管理员");

        userRoleService.saveOrUpdate(userRoleDO);
    }

}
