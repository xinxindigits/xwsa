package cn.com.xinxin.xportal.biz.test.service;

import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.xportal.biz.test.base.SpringBaseTest;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by dengyunhui on 2018/5/2
 **/
public class UserServiceTest extends SpringBaseTest{

    @Autowired
    private UserService userService;


    @Test
    @Ignore
    public void testCreateUser(){
        UserDO userDO = new UserDO();
        userDO.setName("张三");
        userDO.setPassword("ss");
        userDO.setAccount("aggz1005");
        userDO.setCity("gz");
        userDO.setCountry("china");
        userDO.setEmail("zhang.san@xinxinfinance.com");
        userDO.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO.setGender(1);
        userDO.setMobile("19876543456");
        userDO.setNo("sfdasf");
        userDO.setUserType("NORMAL");
        userDO.setOfficePhone("17890876543");
        userDO.setOrganizationCode("fasdf");
        userDO.setOrganizationName("fasfdsfdasf");
        userDO.setProvince("gd");
        userDO.setOfficeAddress("fsdadfasfa");
        userDO.setStatus((byte)1);

        UserDO userDO2 = new UserDO();
        userDO2.setName("张三");
        userDO2.setPassword("ss");
        userDO2.setAccount("aggz1005");
        userDO2.setCity("gz");
        userDO2.setCountry("china");
        userDO2.setEmail("zhang.san@xinxinfinance.com");
        userDO2.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO2.setGender(1);
        userDO2.setMobile("19876543456");
        userDO2.setNo("sfdasf");
        userDO2.setUserType("NORMAL");
        userDO2.setOfficePhone("17890876543");
        userDO2.setOrganizationCode("fasdf");
        userDO2.setOrganizationName("fasfdsfdasf");
        userDO2.setProvince("gd");
        userDO2.setOfficeAddress("fsdadfasfa");
        userDO2.setStatus((byte)1);

        UserDO userDO3 = new UserDO();
        userDO3.setName("张三");
        userDO3.setPassword("ss");
        userDO3.setAccount("aggz1005");
        userDO3.setCity("gz");
        userDO3.setCountry("china");
        userDO3.setEmail("zhang.san@xinxinfinance.com");
        userDO3.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO3.setGender(1);
        userDO3.setMobile("19876543456");
        userDO3.setNo("sfdasf");
        userDO3.setUserType("NORMAL");
        userDO3.setOfficePhone("17890876543");
        userDO3.setOrganizationCode("fasdf");
        userDO3.setOrganizationName("fasfdsfdasf");
        userDO3.setProvince("gd");
        userDO3.setOfficeAddress("fsdadfasfa");
        userDO3.setStatus((byte)1);


        UserDO userDO4 = new UserDO();
        userDO4.setName("张三");
        userDO4.setPassword("ss");
        userDO4.setAccount("aggz1005");
        userDO4.setCity("gz");
        userDO4.setCountry("china");
        userDO4.setEmail("zhang.san@xinxinfinance.com");
        userDO4.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO4.setGender(1);
        userDO4.setMobile("19876543456");
        userDO4.setNo("sfdasf");
        userDO4.setUserType("NORMAL");
        userDO4.setOfficePhone("17890876543");
        userDO4.setOrganizationCode("fasdf");
        userDO4.setOrganizationName("fasfdsfdasf");
        userDO4.setProvince("gd");
        userDO4.setOfficeAddress("fsdadfasfa");
        userDO4.setStatus((byte)1);


        UserDO userDO5 = new UserDO();
        userDO5.setName("张三");
        userDO5.setPassword("ss");
        userDO5.setAccount("aggz1005");
        userDO5.setCity("gz");
        userDO5.setCountry("china");
        userDO5.setEmail("zhang.san@xinxinfinance.com");
        userDO5.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO5.setGender(1);
        userDO5.setMobile("19876543456");
        userDO5.setNo("sfdasf");
        userDO5.setUserType("NORMAL");
        userDO5.setOfficePhone("17890876543");
        userDO5.setOrganizationCode("fasdf");
        userDO5.setOrganizationName("fasfdsfdasf");
        userDO5.setProvince("gd");
        userDO5.setOfficeAddress("fsdadfasfa");
        userDO5.setStatus((byte)1);

        UserDO userDO6 = new UserDO();
        userDO6.setName("张三");
        userDO6.setPassword("ss");
        userDO6.setAccount("aggz1005");
        userDO6.setCity("gz");
        userDO6.setCountry("china");
        userDO6.setEmail("zhang.san@xinxinfinance.com");
        userDO6.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO6.setGender(1);
        userDO6.setMobile("19876543456");
        userDO6.setNo("sfdasf");
        userDO6.setUserType("NORMAL");
        userDO6.setOfficePhone("17890876543");
        userDO6.setOrganizationCode("fasdf");
        userDO6.setOrganizationName("fasfdsfdasf");
        userDO6.setProvince("gd");
        userDO6.setOfficeAddress("fsdadfasfa");
        userDO6.setStatus((byte)1);

        UserDO userDO7 = new UserDO();
        userDO7.setName("张三");
        userDO7.setPassword("ss");
        userDO7.setAccount("aggz1005");
        userDO7.setCity("gz");
        userDO7.setCountry("china");
        userDO7.setEmail("zhang.san@xinxinfinance.com");
        userDO7.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO7.setGender(1);
        userDO7.setMobile("19876543456");
        userDO7.setNo("sfdasf");
        userDO7.setUserType("NORMAL");
        userDO7.setOfficePhone("17890876543");
        userDO7.setOrganizationCode("fasdf");
        userDO7.setOrganizationName("fasfdsfdasf");
        userDO7.setProvince("gd");
        userDO7.setOfficeAddress("fsdadfasfa");
        userDO7.setStatus((byte)1);

        UserDO userDO8 = new UserDO();
        userDO8.setName("张三");
        userDO8.setPassword("ss");
        userDO8.setAccount("aggz1005");
        userDO8.setCity("gz");
        userDO8.setCountry("china");
        userDO8.setEmail("zhang.san@xinxinfinance.com");
        userDO8.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO8.setGender(1);
        userDO8.setMobile("19876543456");
        userDO8.setNo("sfdasf");
        userDO8.setUserType("NORMAL");
        userDO8.setOfficePhone("17890876543");
        userDO8.setOrganizationCode("fasdf");
        userDO8.setOrganizationName("fasfdsfdasf");
        userDO8.setProvince("gd");
        userDO8.setOfficeAddress("fsdadfasfa");
        userDO8.setStatus((byte)1);

        UserDO userDO9 = new UserDO();
        userDO9.setName("张三");
        userDO9.setPassword("ss");
        userDO9.setAccount("aggz1005");
        userDO9.setCity("gz");
        userDO9.setCountry("china");
        userDO9.setEmail("zhang.san@xinxinfinance.com");
        userDO9.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO9.setGender(1);
        userDO9.setMobile("19876543456");
        userDO9.setNo("sfdasf");
        userDO9.setUserType("NORMAL");
        userDO9.setOfficePhone("17890876543");
        userDO9.setOrganizationCode("fasdf");
        userDO9.setOrganizationName("fasfdsfdasf");
        userDO9.setProvince("gd");
        userDO9.setOfficeAddress("fsdadfasfa");
        userDO9.setStatus((byte)1);

        UserDO userDO10 = new UserDO();
        userDO10.setName("张三");
        userDO10.setPassword("ss");
        userDO10.setAccount("aggz1005");
        userDO10.setCity("gz");
        userDO10.setCountry("china");
        userDO10.setEmail("zhang.san@xinxinfinance.com");
        userDO10.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO10.setGender(1);
        userDO10.setMobile("19876543456");
        userDO10.setNo("sfdasf");
        userDO10.setUserType("NORMAL");
        userDO10.setOfficePhone("17890876543");
        userDO10.setOrganizationCode("fasdf");
        userDO10.setOrganizationName("fasfdsfdasf");
        userDO10.setProvince("gd");
        userDO10.setOfficeAddress("fsdadfasfa");
        userDO10.setStatus((byte)1);

        UserDO userDO11 = new UserDO();
        userDO11.setName("张三");
        userDO11.setPassword("ss");
        userDO11.setAccount("aggz1005");
        userDO11.setCity("gz");
        userDO11.setCountry("china");
        userDO11.setEmail("zhang.san@xinxinfinance.com");
        userDO11.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO11.setGender(1);
        userDO11.setMobile("19876543456");
        userDO11.setNo("sfdasf");
        userDO11.setUserType("NORMAL");
        userDO11.setOfficePhone("17890876543");
        userDO11.setOrganizationCode("fasdf");
        userDO11.setOrganizationName("fasfdsfdasf");
        userDO11.setProvince("gd");
        userDO11.setOfficeAddress("fsdadfasfa");
        userDO11.setStatus((byte)1);

        UserDO userDO12 = new UserDO();
        userDO12.setName("张三");
        userDO12.setPassword("ss");
        userDO12.setAccount("aggz1005");
        userDO12.setCity("gz");
        userDO12.setCountry("china");
        userDO12.setEmail("zhang.san@xinxinfinance.com");
        userDO12.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO12.setGender(1);
        userDO12.setMobile("19876543456");
        userDO12.setNo("sfdasf");
        userDO12.setUserType("NORMAL");
        userDO12.setOfficePhone("17890876543");
        userDO12.setOrganizationCode("fasdf");
        userDO12.setOrganizationName("fasfdsfdasf");
        userDO12.setProvince("gd");
        userDO12.setOfficeAddress("fsdadfasfa");
        userDO12.setStatus((byte)1);

        UserDO userDO13 = new UserDO();
        userDO13.setName("张三");
        userDO13.setPassword("ss");
        userDO13.setAccount("aggz1005");
        userDO13.setCity("gz");
        userDO13.setCountry("china");
        userDO13.setEmail("zhang.san@xinxinfinance.com");
        userDO13.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO13.setGender(1);
        userDO13.setMobile("19876543456");
        userDO13.setNo("sfdasf");
        userDO13.setUserType("NORMAL");
        userDO13.setOfficePhone("17890876543");
        userDO13.setOrganizationCode("fasdf");
        userDO13.setOrganizationName("fasfdsfdasf");
        userDO13.setProvince("gd");
        userDO13.setOfficeAddress("fsdadfasfa");
        userDO13.setStatus((byte)1);

        UserDO userDO14 = new UserDO();
        userDO14.setName("张三");
        userDO14.setPassword("ss");
        userDO14.setAccount("aggz1005");
        userDO14.setCity("gz");
        userDO14.setCountry("china");
        userDO14.setEmail("zhang.san@xinxinfinance.com");
        userDO14.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO14.setGender(1);
        userDO14.setMobile("19876543456");
        userDO14.setNo("sfdasf");
        userDO14.setUserType("NORMAL");
        userDO14.setOfficePhone("17890876543");
        userDO14.setOrganizationCode("fasdf");
        userDO14.setOrganizationName("fasfdsfdasf");
        userDO14.setProvince("gd");
        userDO14.setOfficeAddress("fsdadfasfa");
        userDO14.setStatus((byte)1);

        UserDO userDO15 = new UserDO();
        userDO15.setName("张三");
        userDO15.setPassword("ss");
        userDO15.setAccount("aggz1005");
        userDO15.setCity("gz");
        userDO15.setCountry("china");
        userDO15.setEmail("zhang.san@xinxinfinance.com");
        userDO15.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO15.setGender(1);
        userDO15.setMobile("19876543456");
        userDO15.setNo("sfdasf");
        userDO15.setUserType("NORMAL");
        userDO15.setOfficePhone("17890876543");
        userDO15.setOrganizationCode("fasdf");
        userDO15.setOrganizationName("fasfdsfdasf");
        userDO15.setProvince("gd");
        userDO15.setOfficeAddress("fsdadfasfa");
        userDO15.setStatus((byte)1);

        UserDO userDO16 = new UserDO();
        userDO16.setName("张三");
        userDO16.setPassword("ss");
        userDO16.setAccount("aggz1005");
        userDO16.setCity("gz");
        userDO16.setCountry("china");
        userDO16.setEmail("zhang.san@xinxinfinance.com");
        userDO16.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO16.setGender(1);
        userDO16.setMobile("19876543456");
        userDO16.setNo("sfdasf");
        userDO16.setUserType("NORMAL");
        userDO16.setOfficePhone("17890876543");
        userDO16.setOrganizationCode("fasdf");
        userDO16.setOrganizationName("fasfdsfdasf");
        userDO16.setProvince("gd");
        userDO16.setOfficeAddress("fsdadfasfa");
        userDO16.setStatus((byte)1);

        UserDO userDO17 = new UserDO();
        userDO17.setName("张三");
        userDO17.setPassword("ss");
        userDO17.setAccount("aggz1005");
        userDO17.setCity("gz");
        userDO17.setCountry("china");
        userDO17.setEmail("zhang.san@xinxinfinance.com");
        userDO17.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO17.setGender(1);
        userDO17.setMobile("19876543456");
        userDO17.setNo("sfdasf");
        userDO17.setUserType("NORMAL");
        userDO17.setOfficePhone("17890876543");
        userDO17.setOrganizationCode("fasdf");
        userDO17.setOrganizationName("fasfdsfdasf");
        userDO17.setProvince("gd");
        userDO17.setOfficeAddress("fsdadfasfa");
        userDO17.setStatus((byte)1);

        UserDO userDO18 = new UserDO();
        userDO18.setName("张三");
        userDO18.setPassword("ss");
        userDO18.setAccount("aggz1005");
        userDO18.setCity("gz");
        userDO18.setCountry("china");
        userDO18.setEmail("zhang.san@xinxinfinance.com");
        userDO18.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO18.setGender(1);
        userDO18.setMobile("19876543456");
        userDO18.setNo("sfdasf");
        userDO18.setUserType("NORMAL");
        userDO18.setOfficePhone("17890876543");
        userDO18.setOrganizationCode("fasdf");
        userDO18.setOrganizationName("fasfdsfdasf");
        userDO18.setProvince("gd");
        userDO18.setOfficeAddress("fsdadfasfa");
        userDO18.setStatus((byte)1);

        UserDO userDO19 = new UserDO();
        userDO19.setName("张三");
        userDO19.setPassword("ss");
        userDO19.setAccount("aggz1005");
        userDO19.setCity("gz");
        userDO19.setCountry("china");
        userDO19.setEmail("zhang.san@xinxinfinance.com");
        userDO19.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO19.setGender(1);
        userDO19.setMobile("19876543456");
        userDO19.setNo("sfdasf");
        userDO19.setUserType("NORMAL");
        userDO19.setOfficePhone("17890876543");
        userDO19.setOrganizationCode("fasdf");
        userDO19.setOrganizationName("fasfdsfdasf");
        userDO19.setProvince("gd");
        userDO19.setOfficeAddress("fsdadfasfa");
        userDO19.setStatus((byte)1);

        UserDO userDO20 = new UserDO();
        userDO20.setName("张三");
        userDO20.setPassword("ss");
        userDO20.setAccount("aggz1005");
        userDO20.setCity("gz");
        userDO20.setCountry("china");
        userDO20.setEmail("zhang.san@xinxinfinance.com");
        userDO20.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO20.setGender(1);
        userDO20.setMobile("19876543456");
        userDO20.setNo("sfdasf");
        userDO20.setUserType("NORMAL");
        userDO20.setOfficePhone("17890876543");
        userDO20.setOrganizationCode("fasdf");
        userDO20.setOrganizationName("fasfdsfdasf");
        userDO20.setProvince("gd");
        userDO20.setOfficeAddress("fsdadfasfa");
        userDO20.setStatus((byte)1);

        UserDO userDO21 = new UserDO();
        userDO21.setName("张三");
        userDO21.setPassword("ss");
        userDO21.setAccount("aggz1005");
        userDO21.setCity("gz");
        userDO21.setCountry("china");
        userDO21.setEmail("zhang.san@xinxinfinance.com");
        userDO21.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO21.setGender(1);
        userDO21.setMobile("19876543456");
        userDO21.setNo("sfdasf");
        userDO21.setUserType("NORMAL");
        userDO21.setOfficePhone("17890876543");
        userDO21.setOrganizationCode("fasdf");
        userDO21.setOrganizationName("fasfdsfdasf");
        userDO21.setProvince("gd");
        userDO21.setOfficeAddress("fsdadfasfa");
        userDO21.setStatus((byte)1);

        UserDO userDO22 = new UserDO();
        userDO22.setName("张三");
        userDO22.setPassword("ss");
        userDO22.setAccount("aggz1005");
        userDO22.setCity("gz");
        userDO22.setCountry("china");
        userDO22.setEmail("zhang.san@xinxinfinance.com");
        userDO22.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO22.setGender(1);
        userDO22.setMobile("19876543456");
        userDO22.setNo("sfdasf");
        userDO22.setUserType("NORMAL");
        userDO22.setOfficePhone("17890876543");
        userDO22.setOrganizationCode("fasdf");
        userDO22.setOrganizationName("fasfdsfdasf");
        userDO22.setProvince("gd");
        userDO22.setOfficeAddress("fsdadfasfa");
        userDO22.setStatus((byte)1);

        UserDO userDO23 = new UserDO();
        userDO23.setName("张三");
        userDO23.setPassword("ss");
        userDO23.setAccount("aggz1005");
        userDO23.setCity("gz");
        userDO23.setCountry("china");
        userDO23.setEmail("zhang.san@xinxinfinance.com");
        userDO23.setExpireDate(DateUtils.addDays(new Date(),30));
        userDO23.setGender(1);
        userDO23.setMobile("19876543456");
        userDO23.setNo("sfdasf");
        userDO23.setUserType("NORMAL");
        userDO23.setOfficePhone("17890876543");
        userDO23.setOrganizationCode("fasdf");
        userDO23.setOrganizationName("fasfdsfdasf");
        userDO23.setProvince("gd");
        userDO23.setOfficeAddress("fsdadfasfa");
        userDO23.setStatus((byte)1);

        for (UserDO userDO1 : Arrays.asList(userDO,userDO2,userDO3,userDO4,userDO5,
                userDO6,userDO7,userDO8,userDO9,userDO10,
                userDO11,userDO12,userDO13,userDO14,userDO15,
                userDO16,userDO17,userDO18,userDO19,userDO20,userDO21,userDO22,userDO23)) {
            int n = userService.createUser(userDO1);
        }

    }

    @Test
    @Ignore
    public void testChangePassword(){
        userService.resetPassword(59l,"123","admin");


    }
}
