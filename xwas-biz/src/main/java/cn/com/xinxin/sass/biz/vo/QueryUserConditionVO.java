package cn.com.xinxin.sass.biz.vo;

import java.util.Date;

/**
 * Created by dengyunhui on 2018/5/7
 **/
public class QueryUserConditionVO {
    /**
     * 用户号
     */
    private String account;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 用户名
     */
    private String name;
    /**
     * 创建时间开始
     */
    private Date startTime;
    /**
     * 创建时间结束
     */
    private Date endTime;
    /**
     * 状态
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
