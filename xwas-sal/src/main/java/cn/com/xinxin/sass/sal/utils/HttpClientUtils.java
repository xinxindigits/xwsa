package cn.com.xinxin.sass.sal.utils;


import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author: liuhangzhou
 * @created: 2020/4/15.
 * @updater:
 * @description: http请求工具类
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 发送post请求
     *
     * @param urlParam url
     * @param message 消息
     * @return 响应结果
     */
    public static String sendPost(String urlParam, String message) throws IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(urlParam);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        //设置post请求头
        postMethod.addRequestHeader("Content-Type", "application/json");
        //设置post请求内容
        postMethod.setRequestEntity(new StringRequestEntity(message ,"application/json" ,"UTF-8"));

        String result;
        try {
            httpClient.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (IOException e) {
            LOGGER.error("发送post请求异常", e);
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "发送post请求异常");
        }

        postMethod.releaseConnection();

        return result;
    }

    /**
     * 发送get请求
     *
     * @param urlParam url
     * @return 响应结果
     */
    public static String sendGet(String urlParam) {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建GET请求方法实例对象
        GetMethod getMethod = new GetMethod(urlParam);
        // 设置get请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        //设置get请求头
        getMethod.addRequestHeader("Content-Type", "application/json");

        String result;

        try {
            httpClient.executeMethod(getMethod);
            result = getMethod.getResponseBodyAsString();
        } catch (IOException e) {
            LOGGER.error("发送get请求异常", e);
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "发送get请求异常");
        }

        getMethod.releaseConnection();
        return result;
    }
}
