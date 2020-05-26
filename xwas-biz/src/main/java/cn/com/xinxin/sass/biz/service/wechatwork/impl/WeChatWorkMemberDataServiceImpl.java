package cn.com.xinxin.sass.biz.service.wechatwork.impl;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

import cn.com.xinxin.sass.biz.convert.MemberConvert;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkImportDataBO;
import cn.com.xinxin.sass.biz.service.MemberReceivedService;
import cn.com.xinxin.sass.biz.service.MemberService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDataService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.AddressListEnum;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.MemberDO;
import cn.com.xinxin.sass.repository.model.MemberReceivedDO;
import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkUserBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkUserClient;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @description: 企业微信成员相关数据服务
 */
@Service(value = "weChatWorkMemberDataServiceImpl")
public class WeChatWorkMemberDataServiceImpl implements WeChatWorkDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkDepartmentDataServiceImpl.class);

    private final WeChatWorkUserClient weChatWorkUserClient;
    private final MemberReceivedService memberReceivedService;
    private final WeChatWorkInteractionClient weChatWorkInteractionClient;
    private final MemberService memberService;

    public WeChatWorkMemberDataServiceImpl(final WeChatWorkUserClient weChatWorkUserClient,
                                           final MemberReceivedService memberReceivedService,
                                           final WeChatWorkInteractionClient weChatWorkInteractionClient,
                                           final MemberService memberService) {
        this.weChatWorkUserClient = weChatWorkUserClient;
        this.memberReceivedService = memberReceivedService;
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
        this.memberService = memberService;
    }

    /**
     * 获取成员相关数据
     * @param weChatWorkFetchDataBO 获取数据BO
     */
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

    /**
     * 导入成员相关数据
     * @param weChatWorkImportDataBO 导入数据BO
     */
    @Override
    public void importData(WeChatWorkImportDataBO weChatWorkImportDataBO) {

        weChatWorkImportDataBO.getDepartmentReceivedIdS().forEach(departmentId -> {
            //成员信息暂存表
            List<MemberReceivedDO> memberReceivedDOS = memberReceivedService.queryByTaskIdAndOrgIdAndDepartmentId(
                    weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId(),
                    weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId(),
                    CommonConstants.SEPARATOR + departmentId + CommonConstants.SEPARATOR);

            if (CollectionUtils.isEmpty(memberReceivedDOS)) {
                LOGGER.warn("该部门不存在成员，租户[{}]，部门id[{}]",
                        weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId(), departmentId);
                return;
            }

            //成员信息
            List<MemberDO> memberDOS = memberService.queryByOrgIdAndUserId(
                    weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId(),
                    memberReceivedDOS.stream().map(MemberReceivedDO::getUserId).collect(Collectors.toList()));

            //待插入的记录
            List<MemberDO> insertMemberDOS = new ArrayList<>();
            //待更新的记录
            List<MemberDO> updateMemberDOS = new ArrayList<>();

            memberReceivedDOS.forEach(m -> {
                //获取userId相同的成员
                MemberDO memberDO = memberDOS.stream()
                        .filter(b -> StringUtils.equals(m.getUserId(), b.getUserId()))
                        .findFirst().orElse(null);

                if (null == memberDO) {
                    //如果找不到成员，说明该成员需要新增
                    fetchInsertMember(m, insertMemberDOS);
                } else {
                    //对比成员信息，如果需要更新，则将更新信息后的DO放在updatememberDOS中
                    fetchUpdateMember(m, memberDO, updateMemberDOS);
                }
            });
            //插入记录
            memberService.insertBatchPartially(insertMemberDOS, CommonConstants.ONE_HUNDRED);
            //更新记录
            memberService.updateBatchByIdPartially(updateMemberDOS, CommonConstants.ONE_HUNDRED);

            //此次成员改变的记录数
            weChatWorkImportDataBO.getTenantDataSyncLogDO().setMemberCount(
                    weChatWorkImportDataBO.getTenantDataSyncLogDO().getMemberCount()
                            + insertMemberDOS.size() + updateMemberDOS.size());

            //此次同步成员信息记录表userid
            weChatWorkImportDataBO.getMemberReceivedUserIdS().addAll(memberReceivedDOS.stream()
                    .map(MemberReceivedDO::getUserId).collect(Collectors.toList()));
        });

        //失效记录
        int inactiveCount = memberService.updateInactiveStatus(
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId(),
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId());
        //此次成员改变的记录数
        weChatWorkImportDataBO.getTenantDataSyncLogDO().setMemberCount(
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getMemberCount()
                        + inactiveCount);
    }

    /**
     * 参数检查
     *
     * @param tenantDataSyncLogDO 租户同步日志
     * @param departmentId 部门id
     */
    private void checkParam(TenantDataSyncLogDO tenantDataSyncLogDO, String departmentId) {
        if (null == tenantDataSyncLogDO) {
            LOGGER.error("同步部门成员，tenantDataSyncLogDO不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "同步部门成员，tenantDataSyncLogDO不能为空");
        }
        if (StringUtils.isBlank(tenantDataSyncLogDO.getTaskId())) {
            LOGGER.error("同步部门成员，taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "同步部门成员，taskId不能为空");
        }

        if (StringUtils.isBlank(tenantDataSyncLogDO.getTenantId())) {
            LOGGER.error("同步部门成员，TenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "同步部门成员，TenantId不能为空");
        }

        if (StringUtils.isBlank(departmentId)) {
            LOGGER.error("同步部门成员，departmentId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "同步部门成员，departmentId不能为空");
        }
    }

    /**
     * 获取需要插入的记录
     * @param memberReceivedDO 成员信息暂存表
     * @param insertMemberDOS 待插入的成员信息
     */
    private void fetchInsertMember(MemberReceivedDO memberReceivedDO, List<MemberDO> insertMemberDOS) {
        MemberDO memberDO = new MemberDO();
        memberDO.setTenantId(memberReceivedDO.getTenantId());
        memberDO.setUserId(memberReceivedDO.getUserId());
        memberDO.setMemberName(memberReceivedDO.getMemberName());
        memberDO.setMobile(memberReceivedDO.getMobile());
        memberDO.setDepartmentIdList(memberReceivedDO.getDepartmentIdList());
        memberDO.setOrderList(memberReceivedDO.getOrderList());
        memberDO.setMemberPosition(memberReceivedDO.getMemberPosition());
        memberDO.setGender(memberReceivedDO.getGender());
        memberDO.setMail(memberReceivedDO.getMail());
        memberDO.setIsLeaderInDept(memberReceivedDO.getIsLeaderInDept());
        memberDO.setAvatar(memberReceivedDO.getAvatar());
        memberDO.setThumbAvatar(memberReceivedDO.getThumbAvatar());
        memberDO.setTelephone(memberReceivedDO.getTelephone());
        memberDO.setAlias(memberReceivedDO.getAlias());
        memberDO.setStatus(memberReceivedDO.getStatus());
        memberDO.setExtAttr(memberReceivedDO.getExtAttr());
        memberDO.setQrCode(memberReceivedDO.getQrCode());
        memberDO.setExternalProfile(memberReceivedDO.getExternalProfile());
        memberDO.setExternalPosition(memberReceivedDO.getExternalPosition());
        memberDO.setAddress(memberReceivedDO.getAddress());
        memberDO.setHideMobile(memberReceivedDO.getHideMobile());
        memberDO.setEnglishName(memberReceivedDO.getEnglishName());
        memberDO.setOpenUserid(memberReceivedDO.getOpenUserid());
        memberDO.setMainDepartment(memberReceivedDO.getMainDepartment());
        memberDO.setMemberStatus(AddressListEnum.ACTIVE.getStatus());
        memberDO.setTaskId(memberReceivedDO.getTaskId());
        memberDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        LOGGER.info("此次同步成员列表，新增成员[{}]", memberDO.getMemberName());
        insertMemberDOS.add(memberDO);
    }

    /**
     * 获取需要更新到成员信息
     * @param memberReceivedDO 成员信息暂存表
     * @param memberDO 成员信息
     * @param updateMemberDOS 待更新的成员信息
     */
    private void fetchUpdateMember(MemberReceivedDO memberReceivedDO, MemberDO memberDO, List<MemberDO> updateMemberDOS) {
        MemberDO updatememberDO = new MemberDO();
        int count = 0;
        if (!StringUtils.equals(memberDO.getMemberName(), memberReceivedDO.getMemberName())) {
            updatememberDO.setMemberName(memberReceivedDO.getMemberName());
            count++;
        }
        if (!StringUtils.equals(memberDO.getMobile(), memberReceivedDO.getMobile())) {
            updatememberDO.setMobile(memberReceivedDO.getMobile());
            count++;
        }
        if (!StringUtils.equals(memberDO.getDepartmentIdList(), memberReceivedDO.getDepartmentIdList())) {
            updatememberDO.setDepartmentIdList(memberReceivedDO.getDepartmentIdList());
            count++;
        }
        if (!StringUtils.equals(memberDO.getOrderList(), memberReceivedDO.getOrderList())) {
            updatememberDO.setOrderList(memberReceivedDO.getOrderList());
            count++;
        }
        if (!StringUtils.equals(memberDO.getMemberPosition(), memberReceivedDO.getMemberPosition())) {
            updatememberDO.setMemberPosition(memberReceivedDO.getMemberPosition());
            count++;
        }
        if (null != memberReceivedDO.getGender() && !memberReceivedDO.getGender().equals(memberDO.getGender())) {
            updatememberDO.setGender(memberReceivedDO.getGender());
            count++;
        }
        if (!StringUtils.equals(memberDO.getMail(), memberReceivedDO.getMail())) {
            updatememberDO.setMail(memberReceivedDO.getMail());
            count++;
        }
        if (!StringUtils.equals(memberDO.getIsLeaderInDept(), memberReceivedDO.getIsLeaderInDept())) {
            updatememberDO.setIsLeaderInDept(memberReceivedDO.getIsLeaderInDept());
            count++;
        }
        if (!StringUtils.equals(memberDO.getAvatar(), memberReceivedDO.getAvatar())) {
            updatememberDO.setAvatar(memberReceivedDO.getAvatar());
            count++;
        }
        if (!StringUtils.equals(memberDO.getThumbAvatar(), memberReceivedDO.getThumbAvatar())) {
            updatememberDO.setThumbAvatar(memberReceivedDO.getThumbAvatar());
            count++;
        }
        if (!StringUtils.equals(memberDO.getTelephone(), memberReceivedDO.getTelephone())) {
            updatememberDO.setTelephone(memberReceivedDO.getTelephone());
            count++;
        }
        if (!StringUtils.equals(memberDO.getAlias(), memberReceivedDO.getAlias())) {
            updatememberDO.setAlias(memberReceivedDO.getAlias());
            count++;
        }
        if (null != memberReceivedDO.getStatus() && !memberReceivedDO.getStatus().equals(memberDO.getStatus())) {
            updatememberDO.setStatus(memberReceivedDO.getStatus());
            count++;
        }
        if (!StringUtils.equals(memberDO.getQrCode(), memberReceivedDO.getQrCode())) {
            updatememberDO.setQrCode(memberReceivedDO.getQrCode());
            count++;
        }
        if (!StringUtils.equals(memberDO.getExtAttr(), memberReceivedDO.getExtAttr())) {
            updatememberDO.setExtAttr(memberReceivedDO.getExtAttr());
            count++;
        }
        if (!StringUtils.equals(memberDO.getExternalProfile(), memberReceivedDO.getExternalProfile())) {
            updatememberDO.setExternalProfile(memberReceivedDO.getExternalProfile());
            count++;
        }
        if (!StringUtils.equals(memberDO.getExternalPosition(), memberReceivedDO.getExternalPosition())) {
            updatememberDO.setExternalPosition(memberReceivedDO.getExternalPosition());
            count++;
        }
        if (!StringUtils.equals(memberDO.getAddress(), memberReceivedDO.getAddress())) {
            updatememberDO.setAddress(memberReceivedDO.getAddress());
            count++;
        }
        if (null != memberReceivedDO.getHideMobile() && !memberReceivedDO.getHideMobile().equals(memberDO.getHideMobile())) {
            updatememberDO.setHideMobile(memberReceivedDO.getHideMobile());
            count++;
        }
        if (!StringUtils.equals(memberDO.getEnglishName(), memberReceivedDO.getEnglishName())) {
            updatememberDO.setEnglishName(memberReceivedDO.getEnglishName());
            count++;
        }
        if (!StringUtils.equals(memberDO.getOpenUserid(), memberReceivedDO.getOpenUserid())) {
            updatememberDO.setOpenUserid(memberReceivedDO.getOpenUserid());
            count++;
        }
        if (null != memberReceivedDO.getMainDepartment()
                && !memberReceivedDO.getMainDepartment().equals(memberDO.getMainDepartment())) {
            updatememberDO.setMainDepartment(memberReceivedDO.getMainDepartment());
            count++;
        }
        if (StringUtils.equals(memberDO.getMemberStatus(), AddressListEnum.INACTIVE.getStatus())) {
            updatememberDO.setMemberStatus(AddressListEnum.ACTIVE.getStatus());
            count++;
        }

        if (count > 0) {
            updatememberDO.setId(memberDO.getId());
            updatememberDO.setTaskId(memberReceivedDO.getTaskId());
            updatememberDO.setGmtUpdater(CommonConstants.GMT_CREATOR_SYSTEM);
            LOGGER.info("此次同步成员列表，成员[{}]更新[{}]个属性", memberReceivedDO.getMemberName(), count);
            updateMemberDOS.add(updatememberDO);
        }
    }
}
