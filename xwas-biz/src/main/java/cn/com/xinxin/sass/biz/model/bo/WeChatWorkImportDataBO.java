package cn.com.xinxin.sass.biz.model.bo;

import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 导入企业微信数据BO
 */
public class WeChatWorkImportDataBO {

    /**
     * 数据同步日志
     */
    private TenantDataSyncLogDO tenantDataSyncLogDO;

    /**
     * 此次同步部门信息记录表id
     */
    List<Long> departmentReceivedIdS;

    /**
     * 此次同步成员信息记录表userid;
     */
    List<String> memberReceivedUserIdS;

    public TenantDataSyncLogDO getTenantDataSyncLogDO() {
        return tenantDataSyncLogDO;
    }

    public void setTenantDataSyncLogDO(TenantDataSyncLogDO tenantDataSyncLogDO) {
        this.tenantDataSyncLogDO = tenantDataSyncLogDO;
    }

    public List<Long> getDepartmentReceivedIdS() {
        return departmentReceivedIdS;
    }

    public void setDepartmentReceivedIdS(List<Long> departmentReceivedIdS) {
        this.departmentReceivedIdS = departmentReceivedIdS;
    }

    public List<String> getMemberReceivedUserIdS() {
        return memberReceivedUserIdS;
    }

    public void setMemberReceivedUserIdS(List<String> memberReceivedUserIdS) {
        this.memberReceivedUserIdS = memberReceivedUserIdS;
    }
}
