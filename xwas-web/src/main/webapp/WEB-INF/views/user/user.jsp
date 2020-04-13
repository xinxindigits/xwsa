<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户管理</title>
<body>
<div id="bodyDiv" style="margin-top: 1px; margin-left: 10px">
    <div class="row" style="margin: 10px 0px 10px">
        <div class="span24" name="queryCondition" style="margin-left: 0px">
            <form id="search_form" class="form-horizontal well"
                  style="outline: none; padding-bottom: 0px; margin-bottom: 0px;" onSubmit="return false;">
                <div class="row">

                    <input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>">

                    <div class="control-group span7">
                        <label class="control-label" style="width: 60px;">用户名:</label>
                        <div class="controls">
                            <input type="text" id="name" class="control-text bui-form-field" name="name"/>
                        </div>
                    </div>

                    <div class="control-group span7">
                        <label class="control-label" style="width: 60px;">用户编号:</label>
                        <div class="controls">
                            <input type="text" id="no" name="no" class="control-text bui-form-field"/>
                        </div>
                    </div>

                    <div class="span3">
                        <button id="searchBtn" class="button button-primary"><i
                                class="icon-search icon-white"></i>查询
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div style="margin-bottom: 5px">
        <button id="addBtn" class="button button-primary"><i class="icon-plus icon-white"></i>新增</button>
        <button id="editBtn" class="button button-primary"><i class="icon-edit icon-white"></i>修改</button>
        <button id="deleteBtn" class="button button-primary"><i class="icon-remove icon-white"></i>删除</button>
        <button id="resetPasswdBtn" class="button button-primary"><i class="icon-edit icon-white"></i>修改密码</button>
    </div>
    <!-- 显示查询数据 -->
    <div id="querytable" class="search-grid-container"></div>
</div>


<div id="adddialog" class="hidden" style="display: none">
    <form id="add_form" class="form-horizontal">

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"><s>*</s> 用户编码:</label>
                <div class="controls">
                    <input type="text" id="no" name="no" data-rules="{required:true,maxlength:16}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> <s>*</s>用户账户:</label>
                <div class="controls">
                    <input type="text" id="account" name="account" data-rules="{required:true,maxlength:64}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"> <s>*</s>用户名:</label>
                <div class="controls">
                    <input type="text" id="name" name="name" data-rules="{required:true,maxlength:64}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> <s>*</s>性别:</label>
                <div class="controls">
                    <select id="gender" name="gender"></select>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"><s>*</s> 密码:</label>
                <div class="controls">
                    <input type="text" id="password" name="password" data-rules="{required:true}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 用户类型:</label>
                <div class="controls">
                    <select id="userType" name="userType"></select>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"> 移动电话:</label>
                <div class="controls">
                    <input type="text" id="mobile" name="mobile" data-rules="{maxlength:13}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 办公电话:</label>
                <div class="controls">
                    <input type="text" id="officePhone" name="officePhone" data-rules="{maxlength:13}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"> 邮箱:</label>
                <div class="controls">
                    <input type="text" id="email" name="email" data-rules="{maxlength:64}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 国家:</label>
                <div class="controls">
                    <input type="text" id="country" name="country" data-rules="{maxlength:6}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"> 省:</label>
                <div class="controls">
                    <input type="text" id="province" name="province" data-rules="{maxlength:6}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 市:</label>
                <div class="controls">
                    <input type="text" id="city" name="city" data-rules="{maxlength:6}">
                </div>
            </div>
        </div>

        <div class="row">

            <div class="control-group span12">
                <label class="control-label"> 办公室地址:</label>
                <div class="controls">
                    <input type="text" id="officeAddress" name="officeAddress"
                           data-rules="{maxlength:192}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 备注</label>
                <div class="controls">
                    <input type="text" id="remark" name="remark" data-rules="{maxlength:256}">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="control-group span12">
                <label class="control-label"> 所属组织机构编码:</label>
                <div class="controls">
                    <input type="text" id="organizationCode" name="organizationCode"
                           data-rules="{maxlength:16}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 组织机构名称</label>
                <div class="controls">
                    <input type="text" id="organizationName" name="organizationName"
                           data-rules="{maxlength:128}">
                </div>
            </div>
        </div>

    </form>
</div>

<div id="modifydialog" class="hidden" style="display: none">
    <form id="edit_form" class="form-horizontal">

        <div class="row">
            <input type="hidden" id="id" name="id">

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 用户账号:</label>
                <div class="controls">
                    <input type="text" id="account" name="account" data-rules="{required:true,maxlength:64}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> <s>*</s>用户编号:</label>
                <div class="controls">
                    <input type="text" id="no" name="no" data-rules="{required:true,maxlength:16}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"> 状态:</label>
                <div class="controls">
                    <select id="status" name="status" ></select>
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 用户名:</label>
                <div class="controls">
                    <input type="text" id="name" name="name" data-rules="{required:true,maxlength:64}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"><s>*</s> 性别:</label>
                <div class="controls">
                    <select id="gender" name="gender"></select>
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 用户类型:</label>
                <div class="controls">
                    <select id="userType" name="userType"></select>
                </div>
            </div>
        </div>

        <div class="row">

            <div class="control-group span12">
                <label class="control-label"> 移动电话:</label>
                <div class="controls">
                    <input type="text" id="mobile" name="mobile" data-rules="{maxlength:13}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 办公电话:</label>
                <div class="controls">
                    <input type="text" id="officePhone" name="officePhone" data-rules="{maxlength:13}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label"> 邮箱:</label>
                <div class="controls">
                    <input type="text" id="email" name="email" data-rules="{maxlength:64}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 国家:</label>
                <div class="controls">
                    <input type="text" id="country" name="country" data-rules="{maxlength:6}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                <label class="control-label">  省:</label>
                <div class="controls">
                    <input type="text" id="province" name="province" data-rules="{maxlength:2}"/>
                </div>
            </div>
            <div class="control-group span12">
                <label class="control-label">  市:</label>
                <div class="controls">
                    <input type="text" id="city" name="city" data-rules="{maxlength:6}">
                </div>
            </div>
        </div>

        <div class="row">

            <div class="control-group span12">
                <label class="control-label">  办公室地址:</label>
                <div class="controls">
                    <input type="text" id="officeAddress" name="officeAddress" data-rules="{maxlength:192}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> 备注</label>
                <div class="controls">
                    <input type="text" id="remark" name="remark" data-rules="{maxlength:256}">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="control-group span12">
                <label class="control-label">  所属组织机构编码:</label>
                <div class="controls">
                    <input type="text" id="organizationCode" name="organizationCode"
                           data-rules="{maxlength:16}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label">  组织机构名称</label>
                <div class="controls">
                    <input type="text" id="organizationName" name="organizationName"
                           data-rules="{maxlength:128}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                &nbsp;
            </div>
        </div>
    </form>

</div>

<div id="resetPasswdDialog" class="hidden" style="display: none">
    <form id="resetpasswd_form" class="form-horizontal">

        <div class="row">
            <input type="hidden" id="id" name="id">

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 新密码:</label>
                <div class="controls">
                    <input type="text" id="newPasswd" name="newPasswd" data-rules="{required:true,maxlength:64}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"> <s>*</s>确认新密码:</label>
                <div class="controls">
                    <input type="text" id="newPasswd2" name="newPasswd2" data-rules="{required:true,maxlength:64}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span12">
                &nbsp;
            </div>
        </div>
    </form>

</div>

</body>
<script src="<%=request.getContextPath()%>/static/js/user/user.js"></script>
</html>