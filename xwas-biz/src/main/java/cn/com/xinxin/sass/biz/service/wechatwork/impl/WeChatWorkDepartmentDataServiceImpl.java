package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.convert.DepartmentConvert;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.service.DepartmentReceivedService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDataService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkDepartmentBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkDepartmentClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 企业微信部门相关数据服务
 */
@Service
public class WeChatWorkDepartmentDataServiceImpl implements WeChatWorkDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkDepartmentDataServiceImpl.class);

    private final WeChatWorkDepartmentClient weChatWorkDepartmentClient;
    private final DepartmentReceivedService departmentReceivedService;
    private final WeChatWorkInteractionClient weChatWorkInteractionClient;

    public WeChatWorkDepartmentDataServiceImpl(final WeChatWorkDepartmentClient weChatWorkDepartmentClient,
                                               final DepartmentReceivedService departmentReceivedService,
                                               final WeChatWorkInteractionClient weChatWorkInteractionClient) {
        this.weChatWorkDepartmentClient = weChatWorkDepartmentClient;
        this.departmentReceivedService = departmentReceivedService;
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
    }

    @Override
    public void fetchData(WeChatWorkFetchDataBO weChatWorkFetchDataBO) {

        //获取部门数据所需要的token
        String token = weChatWorkInteractionClient.fetchToken(
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCorpId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getAddressListSecret());

        //从企业微信获取部门列表
        List<WeChatWorkDepartmentBO> weChatWorkDepartmentBOS = weChatWorkDepartmentClient.queryDepartmentList(token);

        //将BO转化为DO
        List<DepartmentReceivedDO> departmentReceivedDOS = DepartmentConvert.convert2DepartmentReceivedDOList(
                weChatWorkDepartmentBOS, weChatWorkFetchDataBO.getTaskId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getTenantId());

        //将拉取的部门数据保存在部门数据记录表中
        departmentReceivedService.insertBatchPartially(departmentReceivedDOS, CommonConstants.ONE_HUNDRED);

        //将此次获取的部门id放到BO中，用于成员数据的拉取
        weChatWorkFetchDataBO.setDepartmentIdS(weChatWorkDepartmentBOS.stream()
                .map(WeChatWorkDepartmentBO::getDepartmentId).collect(Collectors.toList()));
    }

}
