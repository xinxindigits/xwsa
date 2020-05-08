package cn.com.xinxin.sass.biz.convert;

import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.repository.model.MemberReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkUserBO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 成员转换类
 */
public class MemberConvert {

    /**
     * 将WeChatWorkUserBO转化为MemberReceivedDO
     * @param weChatWorkUserBO  企业微信部门成员BO
     * @param taskId 任务id
     * @param orgId 机构id
     * @return MemberReceivedDO
     */
    public static MemberReceivedDO convert2MemberReceivedDO(WeChatWorkUserBO weChatWorkUserBO,
                                                            String taskId, String orgId) {
        MemberReceivedDO memberReceivedDO = new MemberReceivedDO();
        memberReceivedDO.setTaskId(taskId);
        memberReceivedDO.setTenantId(orgId);
        memberReceivedDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        if (null != weChatWorkUserBO) {
            memberReceivedDO.setUserId(weChatWorkUserBO.getUserId());
            memberReceivedDO.setMemberName(weChatWorkUserBO.getUserName());
            memberReceivedDO.setMobile(weChatWorkUserBO.getMobile());
            memberReceivedDO.setDepartmentIdList(convertList(weChatWorkUserBO.getDepartmentIds()));
            memberReceivedDO.setOrderList(convertList(weChatWorkUserBO.getDepartmentOrders()));
            memberReceivedDO.setMemberPosition(weChatWorkUserBO.getPosition());
            memberReceivedDO.setGender(weChatWorkUserBO.getGender());
            memberReceivedDO.setMail(weChatWorkUserBO.getEmail());
            memberReceivedDO.setIsLeaderInDept(weChatWorkUserBO.getLeadingDepartments().toString());
            memberReceivedDO.setAvatar(weChatWorkUserBO.getAvatar());
            memberReceivedDO.setThumbAvatar(weChatWorkUserBO.getThumbAvatar());
            memberReceivedDO.setTelephone(weChatWorkUserBO.getTelephone());
            memberReceivedDO.setAlias(weChatWorkUserBO.getAlias());
            memberReceivedDO.setStatus(weChatWorkUserBO.getStatus());
            memberReceivedDO.setExtAttr(weChatWorkUserBO.getExternalAttributes());
            memberReceivedDO.setQrCode(weChatWorkUserBO.getQRCode());
            memberReceivedDO.setExternalProfile(weChatWorkUserBO.getExternalProfile());
            memberReceivedDO.setExternalPosition(weChatWorkUserBO.getExternalPosition());
            memberReceivedDO.setAddress(weChatWorkUserBO.getAddress());
            memberReceivedDO.setHideMobile(weChatWorkUserBO.getHideMobile());
            memberReceivedDO.setEnglishName(weChatWorkUserBO.getEnglishName());
            memberReceivedDO.setOpenUserid(weChatWorkUserBO.getOpenUserId());
            memberReceivedDO.setMainDepartment(weChatWorkUserBO.getMainDepartment());
        }
        return memberReceivedDO;
    }

    /**
     * 将WeChatWorkUserBOList转化为MemberReceivedDOList
     * @param weChatWorkUserBOS  企业微信部门成员BOList
     * @param taskId 任务id
     * @param orgId 机构id
     * @return memberReceivedDOList
     */
    public static List<MemberReceivedDO> convert2MemberReceivedDOList(List<WeChatWorkUserBO> weChatWorkUserBOS,
                                                                      String taskId, String orgId) {
        List<MemberReceivedDO> memberReceivedDOList = new ArrayList<>();
        weChatWorkUserBOS.forEach(u -> memberReceivedDOList.add(convert2MemberReceivedDO(u, taskId, orgId)));
        return memberReceivedDOList;
    }

    /**
     * 将列表转成固定格式的字符串 格式"@1@2@"
     * @param list 列表
     * @return 字符串
     */
    private static String convertList(List<Integer> list) {
        StringBuilder sb = new StringBuilder(CommonConstants.SEPARATOR);
        list.forEach(l -> sb.append(l).append(CommonConstants.SEPARATOR));
        return sb.toString();
    }

}
