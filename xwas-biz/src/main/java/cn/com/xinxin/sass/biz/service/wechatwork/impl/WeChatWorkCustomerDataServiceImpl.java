package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.convert.CustomerConvert;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.service.CustomerReceivedService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDataService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkCustomerBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkCustomerClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 企业微信客户相关数据服务
 */
@Service
public class WeChatWorkCustomerDataServiceImpl implements WeChatWorkDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkCustomerDataServiceImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;
    private final WeChatWorkCustomerClient weChatWorkCustomerClient;
    private final CustomerReceivedService customerReceivedService;

    public WeChatWorkCustomerDataServiceImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient,
                                             final WeChatWorkCustomerClient weChatWorkCustomerClient,
                                             final CustomerReceivedService customerReceivedService) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
        this.weChatWorkCustomerClient = weChatWorkCustomerClient;
        this.customerReceivedService = customerReceivedService;
    }

    @Override
    public void fetchData(WeChatWorkFetchDataBO weChatWorkFetchDataBO) {
        //获取外部联系人管理应用api所需要的token
        String token =  weChatWorkInteractionClient.fetchToken(
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCorpId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCustomerContactSecret());

        //获取客户列表
        List<CustomerReceivedDO> customerReceivedDOS = new ArrayList<>();
        weChatWorkFetchDataBO.getWeChatWorkUserBOS().forEach(u -> {
            List<String> customerUserIdS = weChatWorkCustomerClient.queryCustomerUserId(token, u.getUserId());
            List<WeChatWorkCustomerBO> weChatWorkCustomerBOS = weChatWorkCustomerClient.queryCustomerDetail(
                    token, customerUserIdS);
            customerReceivedDOS.addAll(CustomerConvert.convert2CustomerReceivedDOList(
                    weChatWorkCustomerBOS, weChatWorkFetchDataBO.getTaskId(),
                    weChatWorkFetchDataBO.getTenantBaseInfoDO().getTenantId(), u.getUserId()));
        });

        //将数据保存在客户数据记录表中
        customerReceivedService.insertBatchPartially(customerReceivedDOS.stream()
                .filter(CommonUtils.distinctByKey(m -> (m.getUserId()))).collect(Collectors.toList()),
                CommonConstants.ONE_HUNDRED);
    }
}
