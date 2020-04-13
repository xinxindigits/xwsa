
/**
 *
 */

var COL_NUM = 7;
var ctxPath = $("#contextPath").val();

var resourceTypeMap = new Map();
resourceTypeMap.put("MENU","菜单");
resourceTypeMap.put("FUNCTION","功能");

var rootMap = new Map();
rootMap.put(true,"是");
rootMap.put(false,"否");

function query()   {
    var columns =  [
        {title:"", width:'2%',renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"tableselect\" value=\""+obj.id+"\" />";
            }},

        {title : " 资源名称",width:'25%', sortable:false,dataIndex: "name"},

        {title : "资源编码", width:'15%',sortable:false,dataIndex: "code"},

        {title : "资源类型", width:'5%',sortable:false,dataIndex: "resourceType", renderer: function(value)   {
                return resourceTypeMap.get(value);
            }},

        {title : "资源路径", width:'25%',sortable:false,dataIndex: "url"},

        {title : "资源id", width:'5%',sortable:false,dataIndex: "id"},

        {title : "父级资源id", width:'8%',sortable:false,dataIndex: "parentId"},

        {title : " 是否根目录",width:'8%', sortable:false,dataIndex: "root", renderer: function(value)   {
                return rootMap.get(value);
            }}
    ];
    var requestData = {
        code: $("#search_form #code").val(),
        resourceType: $("#search_form #resourceType").val(),
        name: $("#search_form #name").val(),
        url: $("#search_form #url").val(),
        root: $("#search_form #root").val()
    };

    var queryUrl =  ctxPath + "/permission/resource/list";

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
                            checkType: 'all', //checkType:勾选模式，提供了4中，all,onlyLeaf,none,custom
                            cascadeCheckd : false,//是否级联勾选,默认为true,级联勾选。若想取消级联勾选，可以将这个属性设置为false
                            showLine:true //显示连接线
                        });

                        tree.render();

                        tree.on('checkedchange',function (ev) {
                            var checkedNodes = tree.getCheckedNodes();
                            var str = '';
                            BUI.each(checkedNodes,function (node) {
                                str += node.id + ',';
                            });

                            if (str != ''){
                               str = str.substr(0,str.length - 1);
                            }

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

function showAddTree() {
    getPlainTree();

    showDialog("资源树","treeDialog",function () {
        var newCheckedNodes = $('#tree_form #checkedNodes').val();

        if (newCheckedNodes){
            var s = newCheckedNodes.split(',');
            if (s != null && s.length > 1){
                BUI.Message.Alert("只能选择一个节点",'error');
            }else {
                $('#add_form #parentId').val(newCheckedNodes);
            }
        } else {
            BUI.Message.Alert("请选择一个节点",'error');
        }

        this.close();
    })
}

function showUpdateTree() {
    getPlainTree();

    showDialog("资源树","treeDialog",function () {
        var newCheckedNodes = $('#tree_form #checkedNodes').val();

        if (newCheckedNodes){
            var s = newCheckedNodes.split(',');
            if (s != null && s.length > 1){
                BUI.Message.Alert("只能选择一个节点",'error');
            }else {
                $('#edit_form #parentId').val(newCheckedNodes);
            }
        } else {
            BUI.Message.Alert("请选择一个节点",'error');
        }

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

    var deleteUrl =  ctxPath + "/permission/resource/delete/" + key;

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
    var getUrl =  ctxPath + "/permission/resource/findById/" + key

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

    var resourceType = jsonObject.resourceType;
    $("#edit_form #resourceType").val(jsonObject.resourceType);

    var name = jsonObject.name;
    $("#edit_form #name").val(jsonObject.name);

    var url = jsonObject.url;
    $("#edit_form #url").val(jsonObject.url);

    var root = jsonObject.root;
    $("#edit_form #isRoot").val(jsonObject.root);

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

            resourceType:$("#edit_form #resourceType").val(),

            name:$("#edit_form #name").val(),

            url:$("#edit_form #url").val(),

            root:$("#edit_form #isRoot").val()
        };

        if(compareStr(code,requestData["code"])&& compareStr(parentId,requestData["parentId"])
            && compareStr(resourceType,requestData["resourceType"])
            && compareStr(name,requestData["name"])&& compareStr(url,requestData["url"])
            && compareStr(root,requestData["root"])){
            BUI.Message.Alert("数据未做修改，请修改后重新提交！","success");
            return;
        }

        var edit_dialog = this;
        var msg = "确认要进行修改操作吗?";
        var updateUrl =  ctxPath + "/permission/resource/update";
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
    var addUrl =  ctxPath + "/permission/resource/create"

    showDialog("新增", "adddialog", function()   {

        add_form.valid();
        //add_form.reset();

        if(!add_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            code:$("#add_form #code").val(),

            parentId:$("#add_form #parentId").val(),

            resourceType:$("#add_form #resourceType").val(),

            name:$("#add_form #name").val(),

            url:$("#add_form #url").val(),

            root:$("#add_form #root").val()
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

    initSysDictionaryMap(resourceTypeMap, "search_form #resourceType,add_form #resourceType,edit_form #resourceType", "", "");
    initSysDictionaryMap(rootMap, "search_form #root,add_form #root,edit_form #root", "", "");

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