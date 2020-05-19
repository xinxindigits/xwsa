package cn.com.xinxin.sass.biz.convert;

import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkDepartmentBO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 部门转换类
 */
public class DepartmentConvert {

    /**
     * 将WeChatWorkDepartmentBO转化为DepartmentReceivedDO
     * @param weChatWorkDepartmentBO 企业微信部门BO
     * @param taskId 任务id
     * @param orgId 机构id
     * @return DepartmentReceivedDO
     */
    public static DepartmentReceivedDO convert2DepartmentReceivedDO(WeChatWorkDepartmentBO weChatWorkDepartmentBO,
                                                                    String taskId, String orgId) {
        DepartmentReceivedDO departmentReceivedDO = new DepartmentReceivedDO();
        departmentReceivedDO.setTaskId(taskId);
        departmentReceivedDO.setTenantId(orgId);
        departmentReceivedDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        if (null != weChatWorkDepartmentBO) {
            departmentReceivedDO.setDepartmentId(weChatWorkDepartmentBO.getDepartmentId());
            departmentReceivedDO.setDepartmentName(weChatWorkDepartmentBO.getDepartmentName());
            departmentReceivedDO.setDepartmentOrder(weChatWorkDepartmentBO.getDepartmentOrder());
            departmentReceivedDO.setEnglishName(weChatWorkDepartmentBO.getDepartmentEnglishName());
            departmentReceivedDO.setParentId(weChatWorkDepartmentBO.getDepartmentParentId());
        }
        return departmentReceivedDO;
    }

    /**
     * 将WeChatWorkDepartmentBOList转化为DepartmentReceivedDOList
     * @param weChatWorkDepartmentBOList 企业微信部门BO列表
     * @param taskId 任务id
     * @param orgId 机构id
     * @return DepartmentReceivedDOList
     */
    public static List<DepartmentReceivedDO> convert2DepartmentReceivedDOList(
            List<WeChatWorkDepartmentBO> weChatWorkDepartmentBOList, String taskId, String orgId) {
        List<DepartmentReceivedDO> departmentReceivedDOS = new ArrayList<>();
        weChatWorkDepartmentBOList.forEach(d -> departmentReceivedDOS.add(convert2DepartmentReceivedDO(d, taskId, orgId)));
        return departmentReceivedDOS;
    }
}
