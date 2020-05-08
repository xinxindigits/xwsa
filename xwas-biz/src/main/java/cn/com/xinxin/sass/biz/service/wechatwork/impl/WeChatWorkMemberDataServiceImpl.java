package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.convert.MemberConvert;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.service.MemberReceivedService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDataService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.repository.model.MemberReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkUserBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 企业微信成员相关数据服务
 */
@Service
public class WeChatWorkMemberDataServiceImpl implements WeChatWorkDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkDepartmentDataServiceImpl.class);

    private final WeChatWorkUserClient weChatWorkUserClient;
    private final MemberReceivedService memberReceivedService;
    private final WeChatWorkInteractionClient weChatWorkInteractionClient;

    public WeChatWorkMemberDataServiceImpl(final WeChatWorkUserClient weChatWorkUserClient,
                                           final MemberReceivedService memberReceivedService,
                                           final WeChatWorkInteractionClient weChatWorkInteractionClient) {
        this.weChatWorkUserClient = weChatWorkUserClient;
        this.memberReceivedService = memberReceivedService;
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
    }

    @Override
    public void fetchData(WeChatWorkFetchDataBO weChatWorkFetchDataBO) {

        //获取成员数据所需要的token
        String token = weChatWorkInteractionClient.fetchToken(
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCorpId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getAddressListSecret());

        //根据部门id获取成员列表
        List<WeChatWorkUserBO> weChatWorkUserBOS = weChatWorkUserClient.queryUserList(token,
                weChatWorkFetchDataBO.getDepartmentIdS());

        //将BO转化为DO
        List<MemberReceivedDO> memberReceivedDOS = MemberConvert.convert2MemberReceivedDOList(
                weChatWorkUserBOS, weChatWorkFetchDataBO.getTaskId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getTenantId());

        //将拉取的成员数据保存在成员数据记录表中
        memberReceivedService.insertBatchPartially(memberReceivedDOS.stream()
                .filter(CommonUtils.distinctByKey(m -> (m.getUserId()))).collect(Collectors.toList()),
                CommonConstants.ONE_HUNDRED);

        weChatWorkFetchDataBO.setWeChatWorkUserBOS(weChatWorkUserBOS);
    }
}
