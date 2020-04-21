package cn.com.xinxin.sass.sal.wechatwork;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信交互Service
 */
public interface WeChatWorkInteractionClient {

    /**
     * 获取企业微信token
     * @param corporationId 企业id
     * @param secret 应用secret
     * @return token
     */
    String fetchToken(String corporationId, String secret);

    /**
     * 检查响应状态
     * @param errorCode 企业微信返回的返回码
     * @param errorMessage 对返回码的文本描述内容
     * @param errorDescription 如果响应不成功应该输出的日志信息
     */
    void checkResponseStatus(Integer errorCode, String errorMessage, String errorDescription);
}
