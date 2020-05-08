package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.service.MemberReceivedService;
import cn.com.xinxin.sass.biz.service.MemberService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkCustomerSyncService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkMemberSyncService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.AddressListEnum;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.MemberDO;
import cn.com.xinxin.sass.repository.model.MemberReceivedDO;
import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
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
 * @created: 2020/4/22.
 * @updater:
 * @description: 企业微信同步成员服务
 */
@Service
@Deprecated
public class WeChatWorkMemberSyncServiceImpl implements WeChatWorkMemberSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkMemberSyncServiceImpl.class);

    private final MemberReceivedService memberReceivedService;
    private final MemberService memberService;
    private final WeChatWorkCustomerSyncService weChatWorkCustomerSyncService;

    public WeChatWorkMemberSyncServiceImpl(final MemberReceivedService memberReceivedService,
                                           final MemberService memberService,
                                           final WeChatWorkCustomerSyncService weChatWorkCustomerSyncService) {
        this.memberReceivedService = memberReceivedService;
        this.memberService = memberService;
        this.weChatWorkCustomerSyncService = weChatWorkCustomerSyncService;
    }

    /**
     * 同步部门成员
     *
     * @param departmentId 部门id
     * @param tenantDataSyncLogDO 租户同步日志
     */
    @Override
    public void syncMember(String departmentId, TenantDataSyncLogDO tenantDataSyncLogDO) {
        //参数检查
        checkParam(tenantDataSyncLogDO, departmentId);

        //成员信息暂存表
        List<MemberReceivedDO> memberReceivedDOS = memberReceivedService.queryByTaskIdAndOrgIdAndDepartmentId(
                tenantDataSyncLogDO.getTaskId(), tenantDataSyncLogDO.getTenantId(),
                CommonConstants.SEPARATOR + departmentId + CommonConstants.SEPARATOR);

        if (CollectionUtils.isEmpty(memberReceivedDOS)) {
            LOGGER.warn("该部门不存在成员，租户[{}]，部门id[{}]", tenantDataSyncLogDO.getTenantId(), departmentId);
            return;
        }

        //成员信息
        List<MemberDO> memberDOS = memberService.queryByOrgIdAndUserId(tenantDataSyncLogDO.getTenantId(),
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
        tenantDataSyncLogDO.setMemberCount(tenantDataSyncLogDO.getMemberCount() + insertMemberDOS.size() + updateMemberDOS.size());

        //同步客户信息
        weChatWorkCustomerSyncService.syncCustomer(memberReceivedDOS.stream().map(MemberReceivedDO::getUserId)
                .collect(Collectors.toList()), tenantDataSyncLogDO);
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
