package cn.com.xinxin.sass.biz.model.bo;

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

import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatDetailBO;

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

    /**
     * 企业微信群聊详情
     */
    private List<WeChatWorkGroupChatDetailBO> weChatWorkGroupChatDetailBOS;

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

    public List<WeChatWorkGroupChatDetailBO> getWeChatWorkGroupChatDetailBOS() {
        return weChatWorkGroupChatDetailBOS;
    }

    public void setWeChatWorkGroupChatDetailBOS(List<WeChatWorkGroupChatDetailBO> weChatWorkGroupChatDetailBOS) {
        this.weChatWorkGroupChatDetailBOS = weChatWorkGroupChatDetailBOS;
    }
}
