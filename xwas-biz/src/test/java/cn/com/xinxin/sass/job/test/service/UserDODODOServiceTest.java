package cn.com.xinxin.sass.job.test.service;

import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.job.test.base.SpringBaseTest;
import cn.com.xinxin.sass.repository.model.UserDO;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by dengyunhui on 2018/5/2
 **/
public class UserDODODOServiceTest extends SpringBaseTest {

    @Autowired
    private UserService userService;


    @Test
    @Ignore
    public void testCreateUser(){
        UserDO userDO = new UserDO();
        userDO.setName("张三");
        userDO.setPassword("ss");
        userDO.setAccount("aggz1005");
        userDO.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO.setGender((byte)1);
        userDO.setStatus((byte)1);

        UserDO userDO2 = new UserDO();
        userDO2.setName("张三");
        userDO2.setPassword("ss");
        userDO2.setAccount("aggz1005");
        userDO2.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO2.setGender((byte)1);
        userDO2.setStatus((byte)1);

        UserDO userDO3 = new UserDO();
        userDO3.setName("张三");
        userDO3.setPassword("ss");
        userDO3.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO3.setGender((byte)1);
        userDO3.setStatus((byte)1);


        for (UserDO userDO1 : Arrays.asList(userDO,userDO2,userDO3)) {
            int n = userService.createUser(userDO1);
        }

    }

    @Test
    @Ignore
    public void testChangePassword(){
        userService.resetPassword("fhuserts1","123","admin");
    }
}
