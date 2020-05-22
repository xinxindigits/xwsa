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

import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatDetailBO;
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

    /**
     * 企业微信群聊详情
     */
    private List<WeChatWorkGroupChatDetailBO> weChatWorkGroupChatDetailBOS;

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

    public List<WeChatWorkGroupChatDetailBO> getWeChatWorkGroupChatDetailBOS() {
        return weChatWorkGroupChatDetailBOS;
    }

    public void setWeChatWorkGroupChatDetailBOS(List<WeChatWorkGroupChatDetailBO> weChatWorkGroupChatDetailBOS) {
        this.weChatWorkGroupChatDetailBOS = weChatWorkGroupChatDetailBOS;
    }
}
