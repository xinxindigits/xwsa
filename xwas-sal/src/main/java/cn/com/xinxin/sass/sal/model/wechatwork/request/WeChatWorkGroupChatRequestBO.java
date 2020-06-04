package cn.com.xinxin.sass.sal.model.wechatwork.request;

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

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatOwnerFilterBO;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/5/15.
 * @updater:
 * @description: 企业微信群列表请求BO
 */
public class WeChatWorkGroupChatRequestBO {
    /**
     * 群状态过滤。
     * 0 - 普通列表
     * 1 - 离职待继承
     * 2 - 离职继承中
     * 3 - 离职继承完成
     *
     * 默认为0
     */
    @JSONField(name = "status_filter")
    private Integer statusFilter;

    /**
     * 群主过滤。如果不填，表示获取全部群主的数据
     */
    @JSONField(name = "owner_filter")
    private WeChatWorkGroupChatOwnerFilterBO ownerFilterBO;

    /**
     * 分页，偏移量
     */
    private Integer offset;

    /**
     * 分页，预期请求的数据量，取值范围 1 ~ 1000
     */
    private Integer limit;

    public Integer getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(Integer statusFilter) {
        this.statusFilter = statusFilter;
    }

    public WeChatWorkGroupChatOwnerFilterBO getOwnerFilterBO() {
        return ownerFilterBO;
    }

    public void setOwnerFilterBO(WeChatWorkGroupChatOwnerFilterBO ownerFilterBO) {
        this.ownerFilterBO = ownerFilterBO;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
