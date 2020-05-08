package cn.com.xinxin.sass.biz.model.bo;

import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkUserBO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 获取企业微信数据BO
 */
public class WeChatWorkFetchDataBO {

    /**
     * 租户基础配置信息
     */
    private TenantBaseInfoDO tenantBaseInfoDO;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 部门id， 获取企业微信成员数据时需要
     */
    private List<Long> departmentIdS;

    /**
     * 企业微信成员列表
     */
    private List<WeChatWorkUserBO> weChatWorkUserBOS;

    public TenantBaseInfoDO getTenantBaseInfoDO() {
        return tenantBaseInfoDO;
    }

    public void setTenantBaseInfoDO(TenantBaseInfoDO tenantBaseInfoDO) {
        this.tenantBaseInfoDO = tenantBaseInfoDO;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<Long> getDepartmentIdS() {
        return departmentIdS;
    }

    public void setDepartmentIdS(List<Long> departmentIdS) {
        this.departmentIdS = departmentIdS;
    }

    public List<WeChatWorkUserBO> getWeChatWorkUserBOS() {
        return weChatWorkUserBOS;
    }

    public void setWeChatWorkUserBOS(List<WeChatWorkUserBO> weChatWorkUserBOS) {
        this.weChatWorkUserBOS = weChatWorkUserBOS;
    }
}
