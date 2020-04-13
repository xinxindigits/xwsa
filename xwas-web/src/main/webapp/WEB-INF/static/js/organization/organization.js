
/**
 *
 */

var COL_NUM = 7;
var ctxPath = $("#contextPath").val();

var orgTypeMap = new Map();
orgTypeMap.put("TOP","顶级机构");
orgTypeMap.put("TECH","技术类");
orgTypeMap.put("PROD","产品类");
orgTypeMap.put("OPERATION","运营类");
orgTypeMap.put("MARKET","市场类");
orgTypeMap.put("MANAGE","管理类");
orgTypeMap.put("CUSTOM_SERVICE","客服类");

var isLeafMap = new Map();
isLeafMap.put(true,"是");
isLeafMap.put(false,"否");

var stateMap = new Map();
stateMap.put("VALID","有效");
stateMap.put("INVALID","无效");

function query()   {
    var columns =  [
        {title:"",width:'2%',  renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"tableselect\" value=\""+obj.id+"\" />";
            }},

        {title : " 组织机构名字", width:'15%', sortable:false,dataIndex: "name"},

        {title : "组织机构编号",  width:'10%',sortable:false,dataIndex: "code"},

        {title : "组织结构类型", width:'8%', sortable:false,dataIndex: "orgType", renderer: function(value)   {
                return orgTypeMap.get(value);
            }},

        {title : "状态", width:'5%', sortable:false,dataIndex: "state", renderer: function(value)   {
                return stateMap.get(value);
            }},

        {title : "组织结构id", width:'10%', sortable:false,dataIndex: "id"},

        {title : "父级组织机构id", width:'10%', sortable:false,dataIndex: "parentId"},

        {title : " 是否叶子组织机构", width:'10%', sortable:false,dataIndex: "isLeaf", renderer: function(value)   {
                return isLeafMap.get(value);
            }},

        {title : " 备注",  width:'30%',sortable:false,dataIndex: "remark"}
    ];
    var requestData = {
        name: $("#search_form #name").val(),
        code: $("#search_form #code").val(),
        orgType:$("#search_form #orgType").val()
    };

    var queryUrl = ctxPath + "/permission/organization/list";

    table.queryWidthByPage(queryUrl, columns, "querytable", requestData, null, null);
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

    var deleteUrl =  ctxPath + "/permission/organization/delete/" + key;

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
    var getUrl =  ctxPath + "/permission/organization/findById/" + key

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

    var code = jsonObject.code;
    $("#edit_form #code").val(jsonObject.code);

    var parentId = jsonObject.parentId;
    $("#edit_form #parentId").val(jsonObject.parentId);

    var name = jsonObject.name;
    $("#edit_form #name").val(jsonObject.name);

    var orgType = jsonObject.orgType;
    $("#edit_form #orgType").val(jsonObject.orgType);

    var isLeaf = jsonObject.isLeaf;
    $("#edit_form #isLeaf").val(jsonObject.isLeaf);

    var state = jsonObject.state;
    $("#edit_form #state").val(jsonObject.state);

    var remark = jsonObject.remark;
    $("#edit_form #remark").val(jsonObject.remark);

    showDialog("修改", "modifydialog", function()   {

        edit_form.valid();

        if(!edit_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            id:$("#edit_form #id").val(),

            code:$("#edit_form #code").val(),

            parentId:$("#edit_form #parentId").val(),

            orgType:$("#edit_form #orgType").val(),

            name:$("#edit_form #name").val(),

            isLeaf:$("#edit_form #isLeaf").val(),

            state:$("#edit_form #state").val(),

            remark:$("#edit_form #remark").val()
        };

        if(compareStr(code,requestData["code"])&& compareStr(parentId,requestData["parentId"])
            && compareStr(orgType,requestData["orgType"])
            && compareStr(name,requestData["name"])&& compareStr(isLeaf,requestData["isLeaf"])
            && compareStr(state,requestData["state"]) && compareStr(remark,requestData["remark"])){
            BUI.Message.Alert("数据未做修改，请修改后重新提交！","success");
            return;
        }

        var edit_dialog = this;
        var msg = "确认要进行修改操作吗?";
        var updateUrl =  ctxPath + "/permission/organization/update";
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
    var addUrl =  ctxPath + "/permission/organization/create"

    showDialog("新增", "adddialog", function()   {

        add_form.valid();

        if(!add_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            code:$("#add_form #code").val(),

            parentId:$("#add_form #parentId").val(),

            orgType:$("#add_form #orgType").val(),

            name:$("#add_form #name").val(),

            isLeaf:$("#add_form #isLeaf").val(),

            state:$("#add_form #state").val(),

            remark:$("#add_form #remark").val()
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

    initSysDictionaryMap(orgTypeMap, "search_form #orgType,add_form #orgType,edit_form #orgType", "", "");
    initSysDictionaryMap(isLeafMap, "search_form #isLeaf,add_form #isLeaf,edit_form #isLeaf", "", "");
    initSysDictionaryMap(stateMap, "add_form #state,edit_form #state", "", "");

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