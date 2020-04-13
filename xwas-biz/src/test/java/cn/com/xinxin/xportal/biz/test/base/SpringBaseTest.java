package cn.com.xinxin.xportal.biz.test.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by helloyy on 08/02/2018.
 */

@ContextConfiguration(locations = {"classpath:spring-application-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBaseTest {

    @Test
    public void testOK(){

    }
}
