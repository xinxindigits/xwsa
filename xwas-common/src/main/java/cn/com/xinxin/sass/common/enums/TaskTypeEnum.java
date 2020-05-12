package cn.com.xinxin.sass.common.enums;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构数据同步任务类型枚举
 */
public enum TaskTypeEnum {
    CONTACT_SYNC("CONTACT_SYNC", "通讯录同步"),
    MESSAGE_SYNC("MESSAGE_SYNC", "会话信息同步"),
    ;
    /**
     * 任务类型
     */
    private String type;
    /**
     * 描述
     */
    private String desc;

    TaskTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
