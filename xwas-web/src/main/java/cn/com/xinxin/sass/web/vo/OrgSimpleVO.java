package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: yanghaoxian
 * @created: 2020/4/24.
 * @updater:
 * @description:组织架构VO
 */
public class OrgSimpleVO extends ToString {

    private String code;

    private String name;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
