<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>新心金融统一后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="${ctx}/static/bui/css/bs3/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/bui/css/bs3/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/bui/css/main.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/images/favicon.ico" type="image/x-icon" rel="shortcut icon">
</head>
<body>
<div class="header">
    <div class="dl-title"><span class="">新心金融统一后台管理系统</span></div>
    <div class="dl-log">欢迎您，<span class="dl-log-user">${username}</span>
        <a href="#" onclick="showModifyPasswd()" title="修改密码" class="dl-log-quit">[修改密码]</a>
        <a href="${ctx}/logout" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
</div>
<div class="content">
    <div class="dl-main-nav">
        <ul id="J_Nav"  class="nav-list ks-clear">
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-storage">首页</div></li>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>

</div>

<div id="modifyPasswdDialog" class="hidden" style="display: none">
    <form id="modify_passwd_form" class="form-horizontal">

        <div class="row">
            <div class="control-group span10">
                <label class="control-label"><s>*</s> 原密码:</label>
                <div class="controls">
                    <input type="text" id="originPasswd" name="originPasswd" data-rules="{required:true,maxlength:64}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span10">
                <label class="control-label"><s>*</s> 新密码:</label>
                <div class="controls">
                    <input type="text" id="newPasswd" name="newPasswd" data-rules="{required:true,maxlength:64}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="control-group span10">
                <label class="control-label"> <s>*</s>再次输入新密码:</label>
                <div class="controls">
                    <input type="text" id="newPasswd2" name="newPasswd2" data-rules="{required:true,maxlength:64}">
                </div>
            </div>
        </div>

    </form>

</div>
<script type="text/javascript" src="${ctx}/static/bui/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bui/js/bui-min.js"></script>
<script type="text/javascript" src="${ctx}/static/bui/js/config-min.js"></script>
<script>
    BUI.use('common/main',function(){
        var menus_list = ${menus};
        var ctxPath = "${ctx}";
        setUrl(menus_list,ctxPath);
        var config = [{
            id:'menu',
            //homePage : 'code',
            menu: menus_list
        }];
        new PageUtil.MainPage({
            modulesConfig : config
        });
    });

    function setUrl(menus,ctxPath) {
        for (var menu in menus) {
            var menuItem = menus[menu];
            menuItem.href = ctxPath + menuItem.href;
            if (menuItem.items.length > 0) {
                setUrl(menuItem.items,ctxPath);
            }
        }
    }

    function showDialog(title, divID, succCallBack) {
        BUI.use([ 'bui/overlay','bui/form' ], function(Overlay, Form) {
            var dialog = new Overlay.Dialog({
                title : title,
                contentId : divID,
                closeAction : "destroy",
                zIndex: 1000,
                buttons:[
                    {
                        text:'<i class="icon-remove icon-white"></i>取消',
                        elCls : 'button button-danger',
                        handler : function(){
                            this.close();
                        }
                    },

                    {
                        text: "<i class='icon-ok icon-white'></i>确定",
                        elCls: "button button-success",
                        handler: succCallBack
                    }
                ]
            });
            dialog.show();
        });
    }

    modify_passwd_form = new BUI.Form.Form({
        srcNode : '#modify_passwd_form'
    }).render();

    function showModifyPasswd() {
        var ctxPath = "${ctx}";
        var resetPasswdUrl = ctxPath + "/permission/user/modifyPassword";

        showDialog("修改密码", "modifyPasswdDialog", function()   {

            modify_passwd_form.valid();

            if(!modify_passwd_form.isValid()){
                console.log("校验失败");
                return;
            }
            var requestData = {
                originPasswd: $("#modify_passwd_form #originPasswd").val(),
                newPasswd:$("#modify_passwd_form #newPasswd").val()
            };

            var newPasswd2 = $("#modify_passwd_form #newPasswd2").val();
            if (requestData["newPasswd"] != newPasswd2){
                BUI.Message.Alert("两次输入的密码不一致","success");
                return;
            }

            $.ajax({
                type: "POST",
                url: resetPasswdUrl,
                data: JSON.stringify(requestData),
                dataType: "json",
                contentType:"application/json",
                async: false,
                success: function(responseData)   {
                    if(responseData.error == false)   {
                        BUI.Message.Confirm("操作成功!", "success");
                        return;
                    }else   {
                        BUI.Message.Alert(responseData.errorMsg, "error");
                        return;
                    }
                },
                error: function(e) {
                    if (e.responseText != null || e.responseText != ''){
                        var errorText = JSON.parse(e.responseText);
                        BUI.Message.Alert("操作失败：" + errorText.errorMsg,'error');
                    }
                }
            });

            this.close();
        });
    }



</script>
</body>
</html>