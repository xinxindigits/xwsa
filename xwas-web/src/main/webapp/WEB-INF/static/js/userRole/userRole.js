
/**
 *
 */

var COL_NUM = 2;
var ctxPath = $("#contextPath").val();

function query() {
    var columns =  [
        {title:"", width:'2%',renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"tableselect\" value=\""+obj.id+"\" />";
            }},

        {title : "用户名",width:'15%',sortable:false,dataIndex: "userName"},
        {title : "用户编号",width:'15%',sortable:false,dataIndex: "userNo"},
        {title : "角色名称",width:'15%', sortable:false,dataIndex: "roleName"},
        {title : "角色编码",width:'15%', sortable:false,dataIndex: "roleCode"},

    ];
    var requestData = {
        /*roleCode: $("#search_form #roleCode").val(),
        userNo: $("#search_form #userNo").val(),*/
        roleName: $("#search_form #roleName").val(),
        userName: $("#search_form #userName").val()
    };

    var queryUrl =  ctxPath + "/permission/userRole/list";

    table.queryWidthByPage(queryUrl, columns, "querytable", requestData, null, null);
}

function showUserDialog() {
    var columns =  [
        {title:"",  width: '2%',renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"usertableselect\" value=\""+obj.id+"\" />";
            }},

        {title : "用户名", width: '10%',sortable:false,dataIndex: "name"},

        {title : "用户编号", width: '10%',sortable:false,dataIndex: "no"},

        {title : " 移动电话",width: '20%',sortable:false,dataIndex: "mobile"},

        {title : " 邮箱", width: '40%',sortable:false,dataIndex: "email"},

        {title : " 所属机构",  width: '10%',sortable:false,dataIndex: "organizationName"}

    ];
    var requestData = {
        no: $("#user_search_form #no").val(),
        name: $("#user_search_form #name").val()
    };

    var queryUrl = ctxPath + "/permission/user/list";

    table.queryByPageWidth(queryUrl, columns, "userquerytable", requestData, CLIENT_WIDTH - 400, null);
}

function showAddUserDialog() {
    showUserDialog();
    showDialog("用户列表","userDiv",function () {
        var tr = $('input:radio[name="usertableselect"]:checked').parent().parent().parent().parent();
        var tds = tr.find('td');
        var checkedUserName = tds.eq(1).text();
        var checkedUserCode = tds.eq(2).text();

        $('#add_form #userName').val(checkedUserName);
        $('#add_form #userNo').val(checkedUserCode);
        this.close();
    })
}

function showUpdateUserDialog() {
    showUserDialog();

    showDialog("用户列表","userDiv",function () {
        var tr = $('input:radio[name="usertableselect"]:checked').parent().parent().parent().parent();
        var tds = tr.find('td');
        var checkedUserName = tds.eq(1).text();
        var checkedUserCode = tds.eq(2).text();

        $('#edit_form #userName').val(checkedUserName);
        $('#edit_form #userNo').val(checkedUserCode);
        this.close();
    })
}


var roleTypeMap = new Map();
roleTypeMap.put("NORMAL","普通角色");
roleTypeMap.put("ADMIN","管理员角色");
function showRoleDialog() {
    var columns =  [
        {title:"", width:'2%',renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"roletableselect\" value=\""+obj.id+"\" />";
            }},

        {title : "角色名称",width:'20%', sortable:false,dataIndex: "name"},

        {title : "角色编码", width:'15%',sortable:false,dataIndex: "code"},

        {title : "角色类型", width:'15%',sortable:false,dataIndex: "roleType", renderer: function(value)   {
                return roleTypeMap.get(value);
            }}
    ];
    var requestData = {
        name: $("#role_search_form #name").val(),
        roleType: $("#role_search_form #roleType").val(),
        code: null
    };

    var queryUrl =  ctxPath + "/permission/role/list";

    table.queryByPageWidth(queryUrl, columns, "rolequerytable", requestData, CLIENT_WIDTH - 400, null);
}

function showAddRoleDialog(){
    showRoleDialog();

    showDialog("角色列表","roleDiv",function () {
        var tr = $('input:radio[name="roletableselect"]:checked').parent().parent().parent().parent();
        var tds = tr.find('td');
        var checkedRoleName = tds.eq(1).text();
        var checkedRoleCode = tds.eq(2).text();

        $('#add_form #roleName').val(checkedRoleName);
        $('#add_form #roleCode').val(checkedRoleCode);
        this.close();
    })
}

function showUpdateRoleDialog(){
    showRoleDialog();

    showDialog("角色列表","roleDiv",function () {
        var tr = $('input:radio[name="roletableselect"]:checked').parent().parent().parent().parent();
        var tds = tr.find('td');
        var checkedRoleName = tds.eq(1).text();
        var checkedRoleCode = tds.eq(2).text();

        $('#edit_form #roleName').val(checkedRoleName);
        $('#edit_form #roleCode').val(checkedRoleCode);
        this.close();
    })

}

function deleteById() {
    var queryParams={};
    var key=$('input:radio[name="tableselect"]:checked').val();

    queryParams.id=key;
    if(key == null){
        BUI.Message.Alert('请选择一行数据!',function(){
        },'info');
        return ;
    }

    var deleteUrl =  ctxPath + "/permission/userRole/delete/" + key;

    BUI.Message.Confirm("确定删除？",function(){
        setTimeout(function(){

            $.ajax({
                type: CTS_REQUEST_METHOD_GET,
                url: deleteUrl,
                dataType: CTS_RESPONSE_DATA_TYPE_JSON,
                async: false,
                success: function(responseData)   {
                    if(responseData.error == CTS_SUCCESS_RETURN_FLAG)   {
                        BUI.Message.Alert("操作成功!", "success");
                        query();
                    }else   {
                        BUI.Message.Alert(responseData.errorMsg, "error");
                    }
                },
                error: function(e) {
                    if (e.responseText != null || e.responseText != ''){
                        var errorText = JSON.parse(e.responseText);
                        BUI.Message.Alert("操作失败：" + errorText.errorMsg,'error');
                    }
                }
            });
        },'question');
    });

}

function getAndShowModify() {
    var queryParams={};
    var key=$('input:radio[name="tableselect"]:checked').val();

    queryParams.id=key;
    if(key == null){
        BUI.Message.Alert('请选择一行数据!',function(){
        },'info');
        return ;
    }
    var getUrl =  ctxPath + "/permission/userRole/findById/" + key

    $.ajax({
        type: CTS_REQUEST_METHOD_GET,
        url: getUrl,
        dataType: CTS_RESPONSE_DATA_TYPE_JSON,
        async: false,
        success: function(responseData)   {
            if(responseData.error == CTS_SUCCESS_RETURN_FLAG)   {
                if (responseData.data == null) {
                    BUI.Message.Alert("找不到数据!", "error");
                    return;
                }
                showModify(responseData.data);
            }else   {
                BUI.Message.Alert(responseData.errorMsg, "error");
            }
        },
        error: function(e) {
            if (e.responseText != null || e.responseText != ''){
                var errorText = JSON.parse(e.responseText);
                BUI.Message.Alert("操作失败：" + errorText.errorMsg,'error');
            }
        }
    });

}

function showModify(jsonObject) {

    $("#edit_form #id").val(jsonObject.id);

    var roleCode = jsonObject.roleCode;
    $("#edit_form #roleCode").val(jsonObject.roleCode);

    var userNo = jsonObject.userNo;
    $("#edit_form #userNo").val(jsonObject.userNo);

    var roleName = jsonObject.roleName;
    $("#edit_form #roleName").val(jsonObject.roleName);

    var userName = jsonObject.userName;
    $("#edit_form #userName").val(jsonObject.userName);

    showDialog("修改", "modifydialog", function()   {

        edit_form.valid();

        if(!edit_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            id:$("#edit_form #id").val(),

            roleCode:$("#edit_form #roleCode").val(),

            userNo:$("#edit_form #userNo").val(),

            roleName:$("#edit_form #roleName").val(),

            userName:$("#edit_form #userName").val()
        };

        if(compareStr(roleCode,requestData["roleCode"])&& compareStr(userNo,requestData["userNo"])
        && compareStr(roleName,requestData["roleName"])&& compareStr(userName,requestData["userName"])){
            BUI.Message.Alert("数据未做修改，请修改后重新提交！","success");
            return;
        }

        var edit_dialog = this;
        var msg = "确认要进行修改操作吗?";
        var updateUrl =  ctxPath + "/permission/userRole/update";
        BUI.Message.Confirm(msg,function(){
            setTimeout(function(){

                $.ajax({
                    type: CTS_REQUEST_METHOD_POST,
                    url: updateUrl,
                    data: JSON.stringify(requestData),
                    dataType: CTS_RESPONSE_DATA_TYPE_JSON,
                    contentType:"application/json",
                    async: false,
                    success: function(responseData)   {
                        if(responseData.error == CTS_SUCCESS_RETURN_FLAG)   {
                            BUI.Message.Alert("操作成功!", "success");
                            edit_dialog.close();
                            query();
                        }else   {
                            BUI.Message.Alert(responseData.errorMsg, "error");
                        }
                    },
                    error: function(e) {
                        if (e.responseText != null || e.responseText != ''){
                            var errorText = JSON.parse(e.responseText);
                            BUI.Message.Alert("操作失败：" + errorText.errorMsg,'error');
                        }
                    }
                });
            },'question');
        });
    })
}

function showadd()   {
    var addUrl =  ctxPath + "/permission/userRole/create"

    showDialog("新增", "adddialog", function()   {

        add_form.valid();

        if(!add_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            roleCode:$("#add_form #roleCode").val(),

            userNo:$("#add_form #userNo").val(),

            roleName:$("#add_form #roleName").val(),

            userName:$("#add_form #userName").val()
        };

        $.ajax({
            type: CTS_REQUEST_METHOD_POST,
            url: addUrl,
            data: JSON.stringify(requestData),
            dataType: CTS_RESPONSE_DATA_TYPE_JSON,
            contentType:"application/json",
            async: false,
            success: function(responseData)   {
                if(responseData.error == CTS_SUCCESS_RETURN_FLAG)   {
                    BUI.Message.Alert("操作成功!", "success");
                    query();
                }else   {
                    BUI.Message.Alert(responseData.errorMsg, "error");
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


// 页面入口函数
$(function () {
    $("#bodyDiv").css({width: CLIENT_WIDTH - 30});
    $("div[name=queryCondition]").css({width: CLIENT_WIDTH - 30});
    $("div[name=userQueryCondition]").css({width: CLIENT_WIDTH - 400});
    $("div[name=roleQueryCondition]").css({width: CLIENT_WIDTH - 400});


    initSysDictionaryMap(roleTypeMap, "role_search_form #roleType", "", "");

    query();//初始分页查询

    $("#searchBtn").unbind("click").click(function()   {
        query();
    });

    $("#addBtn").unbind("click").click(function()   {
        showadd();
    });

    $("#editBtn").unbind("click").click(function()   {
        getAndShowModify();
    });

    $("#deleteBtn").unbind("click").click(function()   {
        deleteById();
    });

    $("#userSearchBtn").unbind("click").click(function()   {
        showUserDialog();
    });

    $("#roleSearchBtn").unbind("click").click(function()   {
        showRoleDialog();
    });

    add_form = new BUI.Form.Form({
        srcNode : '#add_form'
    }).render();

    edit_form = new BUI.Form.Form({
        srcNode : '#edit_form'
    }).render();
});