package cn.com.xinxin.sass.sal.model.wechatwork.request;

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
