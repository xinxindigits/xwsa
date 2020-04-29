package cn.com.xinxin.sass.sal.model.wechatwork.response;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkDepartmentBO;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信部门查询BO
 */
public class WeChatWorkDepartmentQueryResponseBO extends WeChatWorkResponseBaseBO{

    /**
     * 部门列表
     */
    @JSONField(name = "department")
    private List<WeChatWorkDepartmentBO> weChatWorkDepartmentBOS;

    public List<WeChatWorkDepartmentBO> getWeChatWorkDepartmentBOS() {
        return weChatWorkDepartmentBOS;
    }

    public void setWeChatWorkDepartmentBOS(List<WeChatWorkDepartmentBO> weChatWorkDepartmentBOS) {
        this.weChatWorkDepartmentBOS = weChatWorkDepartmentBOS;
    }

    @Override
    public String toString() {
        return "WeChatWorkDepartmentQueryResponseBO{" +
                "weChatWorkDepartmentBOS=" + weChatWorkDepartmentBOS +
                '}';
    }
}
