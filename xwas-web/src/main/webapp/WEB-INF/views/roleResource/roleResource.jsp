<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>资源管理</title>
<body>
<div id="bodyDiv" style="margin-top: 1px; margin-left: 10px">
    <div class="row" style="margin: 10px 0px 10px">
        <div class="span24" name="queryCondition" style="margin-left: 0px">
            <form id="search_form" class="form-horizontal well"
                  style="outline: none; padding-bottom: 0px; margin-bottom: 0px;" onSubmit="return false;">

                <input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>">

                <div class="row">

                    <div class="control-group span7">
                        <label class="control-label" style="width: 60px;">角色名称:</label>
                        <div class="controls">
                            <input type="text" id="roleName" name="roleName" class="control-text bui-form-field"/>
                        </div>
                    </div>

                    <div class="control-group span7">
                        <label class="control-label" style="width: 60px;">资源名称:</label>
                        <div class="controls">
                            <input type="text" id="resourceName" name="resourceName" class="control-text bui-form-field"/>
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
    </div>
    <!-- 显示查询数据 -->
    <div id="querytable" class="search-grid-container"></div>
</div>


<div id="adddialog" class="hidden" style="display: none">
    <form id="add_form" class="form-horizontal">

        <div class="row">

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 资源名称:</label>
                <div class="controls">
                    <input type="text" id="resourceName" name="resourceName" onclick="showAddResourceDialog()" data-rules="{required:true,maxlength:128}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 资源编码:</label>
                <div class="controls">
                    <input type="text" id="resourceCode" name="resourceCode"  data-rules="{required:true,maxlength:32}">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 角色名称:</label>
                <div class="controls">
                    <input type="text" id="roleName" name="roleName" onclick="showAddRoleDialog()" data-rules="{required:true,maxlength:128}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 角色编码:</label>
                <div class="controls">
                    <input type="text" id="roleCode" name="roleCode"  data-rules="{required:true,maxlength:32}">
                </div>
            </div>
        </div>

    </form>
</div>

<div id="modifydialog" class="hidden" style="display: none">
    <form id="edit_form" class="form-horizontal">

        <div class="row">
            <input type="hidden" id="id" name="id">

            <div class="row">

                <div class="control-group span12">
                    <label class="control-label"><s>*</s> 角色名称:</label>
                    <div class="controls">
                        <input type="text" id="roleName" name="roleName" onclick="showUpdateRoleDialog()" data-rules="{required:true,maxlength:128}">
                    </div>
                </div>

                <div class="control-group span12">
                    <label class="control-label"><s>*</s> 角色编码:</label>
                    <div class="controls">
                        <input type="text" id="roleCode" name="roleCode"  data-rules="{required:true,maxlength:32}">
                    </div>
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 资源名称:</label>
                <div class="controls">
                    <input type="text" id="resourceName" name="resourceName" onclick="showUpdateResourceDialog()" data-rules="{required:true,maxlength:128}">
                </div>
            </div>

            <div class="control-group span12">
                <label class="control-label"><s>*</s> 资源编码:</label>
                <div class="controls">
                    <input type="text" id="resourceCode" name="resourceCode"  data-rules="{required:true,maxlength:32}">
                </div>
            </div>
        </div>



    </form>

</div>

<div id="resourceDiv" style="display: none">
    <div class="row" style="margin: 10px 0px 10px">
        <div class="span24" name="resourceQueryCondition" style="margin-left: 0px">
            <form id="resource_search_form" class="form-horizontal well"
                  style="outline: none; padding-bottom: 0px; margin-bottom: 0px;" onSubmit="return false;">

                <div class="row">
                    <div class="control-group span7">
                        <label class="control-label" style="width:60px">资源名称:</label>
                        <div class="controls">
                            <input type="text" id="name" name="name" class="control-text bui-form-field"/>
                        </div>
                    </div>


                    <div class="control-group span7">
                        <label class="control-label" style="width:60px">资源编号:</label>
                        <div class="controls">
                            <input type="text" id="code" name="code" class="control-text bui-form-field"/>
                        </div>
                    </div>

                    <div class="span3">
                        <button id="resource_searchBtn" class="button button-primary"><i
                                class="icon-search icon-white"></i>查询
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- 显示查询数据 -->
    <div id="resourcequerytable" class="search-grid-container"></div>
</div>

<div id="roleDiv" style="display: none">
    <div class="row" style="margin: 10px 0px 10px">
        <div class="span24" name="roleQueryCondition" style="margin-left: 0px">
            <form id="role_search_form" class="form-horizontal well"
                  style="outline: none; padding-bottom: 0px; margin-bottom: 0px;" onSubmit="return false;">
                <div class="row">
                    <div class="control-group span7">
                        <label class="control-label" style="width:60px">角色名称:</label>
                        <div class="controls">
                            <input type="text" id="name" name="name" class="control-text bui-form-field"/>
                        </div>
                    </div>

                    <div class="control-group span7">
                        <label class="control-label" style="width:60px">角色类型:</label>
                        <div class="controls">
                            <select id="roleType" name="roleType"></select>
                        </div>
                    </div>

                    <div class="span3">
                        <button id="roleSearchBtn" class="button button-primary"><i
                                class="icon-search icon-white"></i>查询
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- 显示查询数据 -->
    <div id="rolequerytable" class="search-grid-container"></div>
</div>


</body>
<script src="<%=request.getContextPath()%>/static/js/roleResource/roleResource.js"></script>
</html>