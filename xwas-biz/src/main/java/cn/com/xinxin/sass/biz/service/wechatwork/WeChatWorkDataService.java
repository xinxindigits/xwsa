package cn.com.xinxin.sass.biz.service.wechatwork;

import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;


/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 企业微信数据同步服务
 */
public interface WeChatWorkDataService {

    void fetchData(WeChatWorkFetchDataBO weChatWorkFetchDataBO);
}
