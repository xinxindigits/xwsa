//package cn.com.xinxin.sass.job.jobconfig;
//
//import com.xxl.job.core.executor.XxlJobExecutor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
///**
// * @author: liuhangzhou
// * @created: 2020/5/7.
// * @updater:
// * @description: xxl-job配置
// */
//@Component
//@Configuration
//public class XxlJobConfig {
//    @Value("${xxl.META-INFO.admin.addresses}")
//    private String adminAddresses;
//
//    @Value("${xxl.META-INFO.executor.appname}")
//    private String appName;
//
//    @Value("${xxl.META-INFO.executor.ip}")
//    private String ip;
//
//    @Value("${xxl.META-INFO.executor.port}")
//    private int port;
//
//    @Value("${xxl.META-INFO.accessToken}")
//    private String accessToken;
//
//    @Value("${xxl.META-INFO.executor.logpath}")
//    private String logPath;
//
//    @Value("${xxl.META-INFO.executor.logretentiondays}")
//    private int logRetentionDays;
//
//    private static final Logger log = LoggerFactory.getLogger(XxlJobConfig.class);
//
//    @Bean(initMethod = "start", destroyMethod = "destroy")
//    public XxlJobExecutor xxlJobExecutor() {
//        log.info(">>>>>>>>>>> xxl-job config init.");
//        XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
//        xxlJobExecutor.setAdminAddresses(adminAddresses);
//        xxlJobExecutor.setAppName(appName);
//        xxlJobExecutor.setIp(ip);
//        xxlJobExecutor.setPort(port);
//        xxlJobExecutor.setAccessToken(accessToken);
//        xxlJobExecutor.setLogPath(logPath);
//        xxlJobExecutor.setLogRetentionDays(logRetentionDays);
//
//        return xxlJobExecutor;
//    }
//}
