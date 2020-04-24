package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author: zhouyang
 * @created: 13/07/2018.
 * @updater:
 * @description: 用户接收前端请求表单实现
 */
public class UserForm extends ToString {

    private static final long serialVersionUID = 1238156342334706373L;

    private Long id;
    /**
     * 用户号
     */
    private String account;
    /**
     * 用户名
     */
    private String name;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态
     */
    private String extension;
    /**
     *
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    /**
     *
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 页码
     */
    private Integer pageNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
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
