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

public class OplogDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.tenant_id
     *
     * @mbg.generated
     */
    private String tenantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.ip
     *
     * @mbg.generated
     */
    private String ip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.operation
     *
     * @mbg.generated
     */
    private String operation;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.account
     *
     * @mbg.generated
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.method
     *
     * @mbg.generated
     */
    private String method;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.uri
     *
     * @mbg.generated
     */
    private String uri;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.http_method
     *
     * @mbg.generated
     */
    private String httpMethod;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.ua
     *
     * @mbg.generated
     */
    private String ua;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.gmt_creator
     *
     * @mbg.generated
     */
    private String gmtCreator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.gmt_created
     *
     * @mbg.generated
     */
    private Date gmtCreated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.gmt_updater
     *
     * @mbg.generated
     */
    private String gmtUpdater;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.gmt_updated
     *
     * @mbg.generated
     */
    private Date gmtUpdated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.extension
     *
     * @mbg.generated
     */
    private String extension;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oplog.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.id
     *
     * @return the value of oplog.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.id
     *
     * @param id the value for oplog.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.tenant_id
     *
     * @return the value of oplog.tenant_id
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.tenant_id
     *
     * @param tenantId the value for oplog.tenant_id
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.ip
     *
     * @return the value of oplog.ip
     *
     * @mbg.generated
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.ip
     *
     * @param ip the value for oplog.ip
     *
     * @mbg.generated
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.operation
     *
     * @return the value of oplog.operation
     *
     * @mbg.generated
     */
    public String getOperation() {
        return operation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.operation
     *
     * @param operation the value for oplog.operation
     *
     * @mbg.generated
     */
    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.account
     *
     * @return the value of oplog.account
     *
     * @mbg.generated
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.account
     *
     * @param account the value for oplog.account
     *
     * @mbg.generated
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.method
     *
     * @return the value of oplog.method
     *
     * @mbg.generated
     */
    public String getMethod() {
        return method;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.method
     *
     * @param method the value for oplog.method
     *
     * @mbg.generated
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.uri
     *
     * @return the value of oplog.uri
     *
     * @mbg.generated
     */
    public String getUri() {
        return uri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.uri
     *
     * @param uri the value for oplog.uri
     *
     * @mbg.generated
     */
    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.http_method
     *
     * @return the value of oplog.http_method
     *
     * @mbg.generated
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.http_method
     *
     * @param httpMethod the value for oplog.http_method
     *
     * @mbg.generated
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod == null ? null : httpMethod.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.ua
     *
     * @return the value of oplog.ua
     *
     * @mbg.generated
     */
    public String getUa() {
        return ua;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.ua
     *
     * @param ua the value for oplog.ua
     *
     * @mbg.generated
     */
    public void setUa(String ua) {
        this.ua = ua == null ? null : ua.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.gmt_creator
     *
     * @return the value of oplog.gmt_creator
     *
     * @mbg.generated
     */
    public String getGmtCreator() {
        return gmtCreator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.gmt_creator
     *
     * @param gmtCreator the value for oplog.gmt_creator
     *
     * @mbg.generated
     */
    public void setGmtCreator(String gmtCreator) {
        this.gmtCreator = gmtCreator == null ? null : gmtCreator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.gmt_created
     *
     * @return the value of oplog.gmt_created
     *
     * @mbg.generated
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.gmt_created
     *
     * @param gmtCreated the value for oplog.gmt_created
     *
     * @mbg.generated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.gmt_updater
     *
     * @return the value of oplog.gmt_updater
     *
     * @mbg.generated
     */
    public String getGmtUpdater() {
        return gmtUpdater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.gmt_updater
     *
     * @param gmtUpdater the value for oplog.gmt_updater
     *
     * @mbg.generated
     */
    public void setGmtUpdater(String gmtUpdater) {
        this.gmtUpdater = gmtUpdater == null ? null : gmtUpdater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.gmt_updated
     *
     * @return the value of oplog.gmt_updated
     *
     * @mbg.generated
     */
    public Date getGmtUpdated() {
        return gmtUpdated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.gmt_updated
     *
     * @param gmtUpdated the value for oplog.gmt_updated
     *
     * @mbg.generated
     */
    public void setGmtUpdated(Date gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.extension
     *
     * @return the value of oplog.extension
     *
     * @mbg.generated
     */
    public String getExtension() {
        return extension;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.extension
     *
     * @param extension the value for oplog.extension
     *
     * @mbg.generated
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oplog.deleted
     *
     * @return the value of oplog.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oplog.deleted
     *
     * @param deleted the value for oplog.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}