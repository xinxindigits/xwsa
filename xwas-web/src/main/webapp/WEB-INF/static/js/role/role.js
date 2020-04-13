
/**
 *
 */

var COL_NUM = 3;
var ctxPath = $("#contextPath").val();

var roleTypeMap = new Map();
roleTypeMap.put("NORMAL","普通角色");
roleTypeMap.put("ADMIN","管理员角色");

function query()   {
    var columns =  [
        {title:"", width:'2%',renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"tableselect\" value=\""+obj.id+"\" />";
            }},

        {title : "角色名称",width:'20%', sortable:false,dataIndex: "name"},

        {title : "角色编码", width:'15%',sortable:false,dataIndex: "code"},

        {title : "角色类型", width:'15%',sortable:false,dataIndex: "roleType", renderer: function(value)   {
                return roleTypeMap.get(value);
            }}
    ];
    var requestData = {
        name: $("#search_form #name").val(),
        roleType: $("#search_form #roleType").val(),
        code: $("#search_form #code").val()
    };

    var queryUrl =  ctxPath + "/permission/role/list";

    table.queryWidthByPage(queryUrl, columns, "querytable", requestData, null, null);
}

function getTree(treeUrl) {
    $("#tree").empty();
    $.ajax({
        type: CTS_REQUEST_METHOD_GET,
        url: treeUrl,
        dataType: CTS_RESPONSE_DATA_TYPE_JSON,
        async: false,
        success: function(responseData){
            var _data = '';
            if(responseData.error == CTS_SUCCESS_RETURN_FLAG)   {
                _data = responseData.data;
                if (_data != null && _data != '') {
                    //$("#treeDialog").empty();

                    BUI.use('bui/tree',function (Tree) {
                        var tree = new Tree.TreeList({
                            render: '#tree',
                            nodes:_data,
                            checkType:'custom',
                            cascadeCheckd:false,//是否级联勾选,默认为true,级联勾选。若想取消级联勾选，可以将这个属性设置为false
                            showLine:true
                        });

                        tree.render();

                        tree.on('checkedchange',function (ev) {
                            var checkedNodes = tree.getCheckedNodes();
                            var str = '';
                            BUI.each(checkedNodes,function (node) {
                                str += node.id + ',';
                            });

                            $('#tree_form #checkedNodes').val(str);
                        });
                    });
                }
            }
        }
    });
}

function getPlainTree() {
    var treeUrl =  ctxPath + "/permission/resource/plainTree/";
    getTree(treeUrl);
}

function getCheckedTree(code ) {
    var treeUrl =  ctxPath + "/permission/resource/checkedTree/" + code;
    getTree(treeUrl);
}

/**
 * 新增时
 */
function showPlainTree() {
    getPlainTree();

    showDialog("资源树","treeDialog",function () {
        var newCheckedNodes = $('#tree_form #checkedNodes').val();
        $('#add_form #roleResources').val(newCheckedNodes);
        this.close();
    })
}

/**
 * 修改时
 */
function showCheckedTree() {
    var code = $('#edit_form #code').val();
    getCheckedTree(code);

    showDialog("资源树","treeDialog",function () {
        var newCheckedNodes = $('#tree_form #checkedNodes').val();
        $('#edit_form #roleResources').val(newCheckedNodes);
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

    var deleteUrl =  ctxPath + "/permission/role/delete/" + key;

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
    var getUrl =  ctxPath + "/permission/role/findById/" + key

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

    var name = jsonObject.name;
    $("#edit_form #name").val(jsonObject.name);

    var roleType = jsonObject.roleType;
    $("#edit_form #roleType").val(jsonObject.roleType);

    var code = jsonObject.code;
    $("#edit_form #code").val(jsonObject.code);

    showDialog("修改", "modifydialog", function()   {

        edit_form.valid();

        if(!edit_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            id:$("#edit_form #id").val(),

            name:$("#edit_form #name").val(),

            roleType:$("#edit_form #roleType").val(),

            code:$("#edit_form #code").val(),

            roleResources:$("#edit_form #roleResources").val()
        };

        if(compareStr(name,requestData["name"])
            && compareStr(roleType,requestData["roleType"])
            && compareStr(code,requestData["code"]) && (requestData["roleResources"] == "")){
            BUI.Message.Alert("数据未做修改，请修改后重新提交！","success");
            return;
        }

        var edit_dialog = this;
        var msg = "确认要进行修改操作吗?";
        var updateUrl =  ctxPath + "/permission/role/update";
        BUI.Message.Confirm(msg,function(){
            setTimeout(function(){

                $.ajax({
                    type: CTS_REQUEST_METHOD_POST,
                    url: updateUrl,
                    data: JSON.stringify(requestData),
                    dataType: CTS_RESPONSE_DATA_TYPE_JSON,
                    contentType:CTS_REQUEST_CONTENT_TYPE_APPLICATION_JSON,
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
    var addUrl =  ctxPath + "/permission/role/create"

    showDialog("新增", "adddialog", function()   {

        add_form.valid();

        if(!add_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            name:$("#add_form #name").val(),

            roleType:$("#add_form #roleType").val(),

            code:$("#add_form #code").val(),

            roleResources:$("#add_form #roleResources").val()
        };

        $.ajax({
            type: CTS_REQUEST_METHOD_POST,
            url: addUrl,
            data: JSON.stringify(requestData),
            dataType: CTS_RESPONSE_DATA_TYPE_JSON,
            contentType:CTS_REQUEST_CONTENT_TYPE_APPLICATION_JSON,
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

    initSysDictionaryMap(roleTypeMap, "search_form #roleType,add_form #roleType,edit_form #roleType", "", "");

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

    add_form = new BUI.Form.Form({
        srcNode : '#add_form'
    }).render();

    edit_form = new BUI.Form.Form({
        srcNode : '#edit_form'
    }).render();
});