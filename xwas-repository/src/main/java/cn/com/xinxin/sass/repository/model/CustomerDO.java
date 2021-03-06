package cn.com.xinxin.sass.repository.model;

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

import java.util.Date;

public class CustomerDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.org_id
     *
     * @mbg.generated
     */
    private String tenantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.member_user_id
     *
     * @mbg.generated
     */
    private String memberUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.customer_name
     *
     * @mbg.generated
     */
    private String customerName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.avatar
     *
     * @mbg.generated
     */
    private String avatar;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.customer_type
     *
     * @mbg.generated
     */
    private Integer customerType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.gender
     *
     * @mbg.generated
     */
    private Integer gender;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.union_id
     *
     * @mbg.generated
     */
    private String unionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.customer_position
     *
     * @mbg.generated
     */
    private String customerPosition;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.corp_name
     *
     * @mbg.generated
     */
    private String corpName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.corp_full_name
     *
     * @mbg.generated
     */
    private String corpFullName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.external_profile
     *
     * @mbg.generated
     */
    private String externalProfile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.follow_user
     *
     * @mbg.generated
     */
    private String followUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.task_id
     *
     * @mbg.generated
     */
    private String taskId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.extension
     *
     * @mbg.generated
     */
    private String extension;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.gmt_created
     *
     * @mbg.generated
     */
    private Date gmtCreated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.gmt_creator
     *
     * @mbg.generated
     */
    private String gmtCreator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.gmt_updated
     *
     * @mbg.generated
     */
    private Date gmtUpdated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.gmt_updater
     *
     * @mbg.generated
     */
    private String gmtUpdater;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.deleted
     *
     * @mbg.generated
     */
    private Byte deleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.id
     *
     * @return the value of customer.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.id
     *
     * @param id the value for customer.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.org_id
     *
     * @return the value of customer.org_id
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.org_id
     *
     * @param tenantId the value for customer.org_id
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.member_user_id
     *
     * @return the value of customer.member_user_id
     *
     * @mbg.generated
     */
    public String getMemberUserId() {
        return memberUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.member_user_id
     *
     * @param memberUserId the value for customer.member_user_id
     *
     * @mbg.generated
     */
    public void setMemberUserId(String memberUserId) {
        this.memberUserId = memberUserId == null ? null : memberUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.user_id
     *
     * @return the value of customer.user_id
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.user_id
     *
     * @param userId the value for customer.user_id
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.customer_name
     *
     * @return the value of customer.customer_name
     *
     * @mbg.generated
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.customer_name
     *
     * @param customerName the value for customer.customer_name
     *
     * @mbg.generated
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.avatar
     *
     * @return the value of customer.avatar
     *
     * @mbg.generated
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.avatar
     *
     * @param avatar the value for customer.avatar
     *
     * @mbg.generated
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.customer_type
     *
     * @return the value of customer.customer_type
     *
     * @mbg.generated
     */
    public Integer getCustomerType() {
        return customerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.customer_type
     *
     * @param customerType the value for customer.customer_type
     *
     * @mbg.generated
     */
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.gender
     *
     * @return the value of customer.gender
     *
     * @mbg.generated
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.gender
     *
     * @param gender the value for customer.gender
     *
     * @mbg.generated
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.union_id
     *
     * @return the value of customer.union_id
     *
     * @mbg.generated
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.union_id
     *
     * @param unionId the value for customer.union_id
     *
     * @mbg.generated
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.customer_position
     *
     * @return the value of customer.customer_position
     *
     * @mbg.generated
     */
    public String getCustomerPosition() {
        return customerPosition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.customer_position
     *
     * @param customerPosition the value for customer.customer_position
     *
     * @mbg.generated
     */
    public void setCustomerPosition(String customerPosition) {
        this.customerPosition = customerPosition == null ? null : customerPosition.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.corp_name
     *
     * @return the value of customer.corp_name
     *
     * @mbg.generated
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.corp_name
     *
     * @param corpName the value for customer.corp_name
     *
     * @mbg.generated
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.corp_full_name
     *
     * @return the value of customer.corp_full_name
     *
     * @mbg.generated
     */
    public String getCorpFullName() {
        return corpFullName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.corp_full_name
     *
     * @param corpFullName the value for customer.corp_full_name
     *
     * @mbg.generated
     */
    public void setCorpFullName(String corpFullName) {
        this.corpFullName = corpFullName == null ? null : corpFullName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.external_profile
     *
     * @return the value of customer.external_profile
     *
     * @mbg.generated
     */
    public String getExternalProfile() {
        return externalProfile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.external_profile
     *
     * @param externalProfile the value for customer.external_profile
     *
     * @mbg.generated
     */
    public void setExternalProfile(String externalProfile) {
        this.externalProfile = externalProfile == null ? null : externalProfile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.follow_user
     *
     * @return the value of customer.follow_user
     *
     * @mbg.generated
     */
    public String getFollowUser() {
        return followUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.follow_user
     *
     * @param followUser the value for customer.follow_user
     *
     * @mbg.generated
     */
    public void setFollowUser(String followUser) {
        this.followUser = followUser == null ? null : followUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.status
     *
     * @return the value of customer.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.status
     *
     * @param status the value for customer.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.task_id
     *
     * @return the value of customer.task_id
     *
     * @mbg.generated
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.task_id
     *
     * @param taskId the value for customer.task_id
     *
     * @mbg.generated
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.extension
     *
     * @return the value of customer.extension
     *
     * @mbg.generated
     */
    public String getExtension() {
        return extension;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.extension
     *
     * @param extension the value for customer.extension
     *
     * @mbg.generated
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.gmt_created
     *
     * @return the value of customer.gmt_created
     *
     * @mbg.generated
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.gmt_created
     *
     * @param gmtCreated the value for customer.gmt_created
     *
     * @mbg.generated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.gmt_creator
     *
     * @return the value of customer.gmt_creator
     *
     * @mbg.generated
     */
    public String getGmtCreator() {
        return gmtCreator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.gmt_creator
     *
     * @param gmtCreator the value for customer.gmt_creator
     *
     * @mbg.generated
     */
    public void setGmtCreator(String gmtCreator) {
        this.gmtCreator = gmtCreator == null ? null : gmtCreator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.gmt_updated
     *
     * @return the value of customer.gmt_updated
     *
     * @mbg.generated
     */
    public Date getGmtUpdated() {
        return gmtUpdated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.gmt_updated
     *
     * @param gmtUpdated the value for customer.gmt_updated
     *
     * @mbg.generated
     */
    public void setGmtUpdated(Date gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.gmt_updater
     *
     * @return the value of customer.gmt_updater
     *
     * @mbg.generated
     */
    public String getGmtUpdater() {
        return gmtUpdater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.gmt_updater
     *
     * @param gmtUpdater the value for customer.gmt_updater
     *
     * @mbg.generated
     */
    public void setGmtUpdater(String gmtUpdater) {
        this.gmtUpdater = gmtUpdater == null ? null : gmtUpdater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.deleted
     *
     * @return the value of customer.deleted
     *
     * @mbg.generated
     */
    public Byte getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.deleted
     *
     * @param deleted the value for customer.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }
}