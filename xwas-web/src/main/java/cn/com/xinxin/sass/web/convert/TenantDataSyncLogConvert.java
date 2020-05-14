package cn.com.xinxin.sass.web.convert;


import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import cn.com.xinxin.sass.web.vo.TenantDataSyncLogVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/13.
 * @updater:
 * @description:
 */
public class TenantDataSyncLogConvert {
    public static TenantDataSyncLogVO convert2TenantDataSyncLogVO(TenantDataSyncLogDO tenantDataSyncLogDO) {
        TenantDataSyncLogVO tenantDataSyncLogVO = new TenantDataSyncLogVO();
        if (null == tenantDataSyncLogDO)  {
            return tenantDataSyncLogVO;
        }
        tenantDataSyncLogVO.setId(tenantDataSyncLogDO.getId());
        tenantDataSyncLogVO.setTaskId(tenantDataSyncLogDO.getTaskId());
        tenantDataSyncLogVO.setTenantId(tenantDataSyncLogDO.getTenantId());
        tenantDataSyncLogVO.setTaskType(tenantDataSyncLogDO.getTaskType());
        tenantDataSyncLogVO.setTaskDate(tenantDataSyncLogDO.getTaskDate());
        tenantDataSyncLogVO.setTaskTime(tenantDataSyncLogDO.getTaskTime());
        tenantDataSyncLogVO.setMessageCount(tenantDataSyncLogDO.getMessageCount());
        tenantDataSyncLogVO.setDepartmentCount(tenantDataSyncLogDO.getDepartmentCount());
        tenantDataSyncLogVO.setMemberCount(tenantDataSyncLogDO.getMemberCount());
        tenantDataSyncLogVO.setCustomerCount(tenantDataSyncLogDO.getCustomerCount());
        tenantDataSyncLogVO.setTaskStatus(tenantDataSyncLogDO.getTaskStatus());
        tenantDataSyncLogVO.setErrorCode(tenantDataSyncLogDO.getErrorCode());
        tenantDataSyncLogVO.setErrorDesc(tenantDataSyncLogDO.getErrorDesc());
        return tenantDataSyncLogVO;
    }

    public static List<TenantDataSyncLogVO> convert2TenantDataSyncLogVOList(List<TenantDataSyncLogDO> tenantDataSyncLogDOS) {
        List<TenantDataSyncLogVO> tenantDataSyncLogVOS = new ArrayList<>();
        tenantDataSyncLogDOS.forEach(t -> tenantDataSyncLogVOS.add(convert2TenantDataSyncLogVO(t)));
        return tenantDataSyncLogVOS;
    }
}
