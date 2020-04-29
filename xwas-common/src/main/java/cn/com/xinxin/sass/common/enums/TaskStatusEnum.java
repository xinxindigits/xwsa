package cn.com.xinxin.sass.common.enums;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 任务状态枚举
 */
public enum TaskStatusEnum {
    INIT("INIT", "初始化"),
    RECEIVING("RECEIVING", "获取数据中"),
    IMPORTING("IMPORTING", "数据导入中"),
    SUCCESS("SUCCESS", "成功同步"),
    FAILURE("FAILURE", "任务失败"),
    ;
    /**
     * 状态
     */
    private String status;
    /**
     * 描述
     */
    private String desc;

    TaskStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
