package cn.com.xinxin.sass.biz.service.wechatwork;

import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkImportDataBO;


/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 企业微信数据同步服务
 */
public interface WeChatWorkDataService {

    /**
     * 获取通讯录相关数据
     * @param weChatWorkFetchDataBO 获取数据BO
     */
    void fetchData(WeChatWorkFetchDataBO weChatWorkFetchDataBO);

    /**
     * 导入通讯录相关数据
     * @param weChatWorkImportDataBO 导入数据BO
     */
    void importData(WeChatWorkImportDataBO weChatWorkImportDataBO);
}
