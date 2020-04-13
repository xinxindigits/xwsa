
/**
 *
 */

var COL_NUM = 2;
var ctxPath = $("#contextPath").val();

function query()   {
    var columns =  [
        {title:"",width:'2%', renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"tableselect\" value=\""+obj.id+"\" />";
            }},

        {title : "角色名称",width:'15%', sortable:false,dataIndex: "roleName"},

        {title : "角色编码",width:'15%', sortable:false,dataIndex: "roleCode"},

        {title : "资源名称",width:'15%', sortable:false,dataIndex: "resourceName"},

        {title : "资源编码",width:'15%', sortable:false,dataIndex: "resourceCode"}
    ];
    var requestData = {
        roleName: $("#search_form #roleName").val(),
        resourceName: $("#search_form #resourceName").val()
        /*roleCode: $("#search_form #roleCode").val(),
        resourceCode: $("#search_form #resourceCode").val()*/
    };

    var queryUrl =  ctxPath + "/permission/roleResource/list";

    table.queryWidthByPage(queryUrl, columns, "querytable", requestData, null, null);
}

function showResourceDialog() {
    var columns =  [
        {title:"", width:'2%',renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"resourcetableselect\" value=\""+obj.id+"\" />";
            }},

        {title : " 资源名称",width:'40%', sortable:false,dataIndex: "name"},

        {title : "资源编码", width:'30%',sortable:false,dataIndex: "code"}
    ];

    var requestData = {
        code: $("#resource_search_form #code").val(),
        name: $("#resource_search_form #name").val(),
        resourceType: null,
        url: null,
        root: null
    };

    var queryUrl =  ctxPath + "/permission/resource/list";

    table.queryByPageWidth(queryUrl, columns, "resourcequerytable", requestData, CLIENT_WIDTH - 400, null);
}

function showAddResourceDialog(){
    showResourceDialog();

    showDialog("资源列表","resourceDiv",function () {
        var tr = $('input:radio[name="resourcetableselect"]:checked').parent().parent().parent().parent();
        var tds = tr.find('td');
        var checkedResourceName = tds.eq(1).text();
        var checkedResourceCode = tds.eq(2).text();

        $('#add_form #resourceName').val(checkedResourceName);
        $('#add_form #resourceCode').val(checkedResourceCode);
        this.close();
    })
}

function showUpdateResourceDialog(){
    showResourceDialog();

    showDialog("资源列表","resourceDiv",function () {
        var tr = $('input:radio[name="resourcetableselect"]:checked').parent().parent().parent().parent();
        var tds = tr.find('td');
        var checkedResourceName = tds.eq(1).text();
        var checkedResourceCode = tds.eq(2).text();

        $('#edit_form #resourceName').val(checkedResourceName);
        $('#edit_form #resourceCode').val(checkedResourceCode);
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

    var deleteUrl =  ctxPath + "/permission/roleResource/delete/" + key;

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
    var getUrl =  ctxPath + "/permission/roleResource/findById/" + key

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

    var resourceCode = jsonObject.resourceCode;
    $("#edit_form #resourceCode").val(jsonObject.resourceCode);

    var roleCode = jsonObject.roleCode;
    $("#edit_form #roleCode").val(jsonObject.roleCode);

    var resourceName = jsonObject.resourceName;
    $("#edit_form #resourceName").val(jsonObject.resourceName);

    var roleName = jsonObject.roleName;
    $("#edit_form #roleName").val(jsonObject.roleName);

    showDialog("修改", "modifydialog", function()   {

        edit_form.valid();

        if(!edit_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            id:$("#edit_form #id").val(),

            resourceCode:$("#edit_form #resourceCode").val(),

            roleCode:$("#edit_form #roleCode").val(),
            resourceName:$("#edit_form #resourceName").val(),

            roleName:$("#edit_form #roleName").val()
        };

        if(compareStr(resourceCode,requestData["resourceCode"])&& compareStr(roleCode,requestData["roleCode"])
          && compareStr(resourceName,requestData["resourceName"])&& compareStr(roleName,requestData["roleName"])){
            BUI.Message.Alert("数据未做修改，请修改后重新提交！","success");
            return;
        }

        var edit_dialog = this;
        var msg = "确认要进行修改操作吗?";
        var updateUrl =  ctxPath + "/permission/roleResource/update";
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
    var addUrl =  ctxPath + "/permission/roleResource/create"

    showDialog("新增", "adddialog", function()   {

        add_form.valid();

        if(!add_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            roleCode:$("#add_form #roleCode").val(),
            resourceCode:$("#add_form #resourceCode").val(),
            roleName:$("#add_form #roleName").val(),
            resourceName:$("#add_form #resourceName").val()
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
    $("div[name=roleQueryCondition]").css({width: CLIENT_WIDTH - 400});
    $("div[name=resourceQueryCondition]").css({width: CLIENT_WIDTH - 400});

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

    $("#resource_searchBtn").unbind("click").click(function()   {
        showResourceDialog();
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