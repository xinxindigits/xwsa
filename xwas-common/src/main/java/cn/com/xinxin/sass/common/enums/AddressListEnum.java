package cn.com.xinxin.sass.common.enums;

/**
 * @author: liuhangzhou
 * @created: 2020/4/22.
 * @updater:
 * @description: 通讯录相关的状态枚举
 */
public enum AddressListEnum {
    INACTIVE("INACTIVE", "非活跃"),
    ACTIVE("ACTIVE", "活跃")
    ;

    private String status;
    private String description;

    AddressListEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
