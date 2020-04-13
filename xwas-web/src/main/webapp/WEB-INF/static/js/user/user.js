
/**
 *
 */

var COL_NUM = 15;
var ctxPath = $("#contextPath").val();

var statusMap = new Map();
statusMap.put("0","可用");
statusMap.put("1","停用");

var genderMap = new Map();
genderMap.put("0","男");
genderMap.put("1","女");

var userTypeMap = new Map();
userTypeMap.put("NORMAL","普通用户");
userTypeMap.put("ADMIN","管理员用户");

function query()   {
    var columns =  [
        {title:"",  width: '2%',renderer:function(value, obj) {
                return "<input type=\"radio\" name=\"tableselect\" value=\""+obj.id+"\" />";
            }},

        {title : "用户编号", width: '6%',sortable:false,dataIndex: "no"},

        {title : "用户名", width: '5%',sortable:false,dataIndex: "name"},

        {title : "状态",  width: '3%',sortable:false,dataIndex: "status", renderer: function(value)   {
                return statusMap.get(value);
            }},

        {title : "用户账号", width: '6%',sortable:false,dataIndex: "account"},

        {title : " 性别",  width: '3%',sortable:false,dataIndex: "gender", renderer: function(value)   {
                return genderMap.get(value);
            }},

        {title : "用户类型",width: '6%',sortable:false,dataIndex: "userType", renderer: function(value)   {
                return userTypeMap.get(value);
            }},

        {title : " 移动电话",width: '7%',sortable:false,dataIndex: "mobile"},

        {title : " 办公电话",width: '8%',sortable:false,dataIndex: "officePhone"},

        {title : " 邮箱", width: '18%',sortable:false,dataIndex: "email"},

        {title : " 省",  width: '5%',sortable:false,dataIndex: "province"},

        {title : " 市",width: '5%',  sortable:false,dataIndex: "city"},

        {title : " 办公室地址", width: '20%', sortable:false,dataIndex: "officeAddress"},

        {title : " 所属机构编码", width: '6%', sortable:false,dataIndex: "organizationName"},

        {title : " 所属机构",  width: '6%',sortable:false,dataIndex: "organizationName"}

    ];
    var requestData = {
        no: $("#search_form #no").val(),
        name: $("#search_form #name").val()
    };

    var queryUrl = ctxPath + "/permission/user/list";
    var width = 0 ;
    if (CLIENT_WIDTH < 1683){
        width = CLIENT_WIDTH + 800;
    }
    table.queryByPageWidth(queryUrl, columns, "querytable", requestData, width, null);
}

function deleteById() {
    var queryParams={};
    var key=$('input:radio:checked').val();

    queryParams.id=key;
    if(key == null){
        BUI.Message.Alert('请选择一行数据!',function(){
        },'info');
        return ;
    }

    var deleteUrl = ctxPath + "/permission/user/delete/" + key;

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
    var getUrl = ctxPath + "/permission/user/findById/" + key

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

    var no = jsonObject.no;
    $("#edit_form #no").val(jsonObject.no);

    var account = jsonObject.account;
    $("#edit_form #account").val(jsonObject.account);

    var status = jsonObject.status;
    $("#edit_form #status").val(jsonObject.status);

    var name = jsonObject.name;
    $("#edit_form #name").val(jsonObject.name);

    var gender = jsonObject.gender;
    $("#edit_form #gender").val(jsonObject.gender);

    var userType = jsonObject.userType;
    $("#edit_form #userType").val(jsonObject.userType);

    var mobile = jsonObject.mobile;
    $("#edit_form #mobile").val(jsonObject.mobile);

    var officePhone = jsonObject.officePhone;
    $("#edit_form #officePhone").val(jsonObject.officePhone);

    var email = jsonObject.email;
    $("#edit_form #email").val(jsonObject.email);

    var country = jsonObject.country;
    $("#edit_form #country").val(jsonObject.country);

    var province = jsonObject.province;
    $("#edit_form #province").val(jsonObject.province);

    var city = jsonObject.city;
    $("#edit_form #city").val(jsonObject.city);

    var officeAddress = jsonObject.officeAddress;
    $("#edit_form #officeAddress").val(jsonObject.officeAddress);

    var organizationCode = jsonObject.organizationCode;
    $("#edit_form #organizationCode").val(jsonObject.organizationCode);

    var organizationName = jsonObject.organizationName;
    $("#edit_form #organizationName").val(jsonObject.organizationName);

    showDialog("修改", "modifydialog", function()   {

        edit_form.valid();

        if(!edit_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            id:$("#edit_form #id").val(),

            no:$("#edit_form #no").val(),

            account:$("#edit_form #account").val(),

            status:$("#edit_form #status").val(),

            name:$("#edit_form #name").val(),

            gender:$("#edit_form #gender").val(),

            userType:$("#edit_form #userType").val(),

            mobile:$("#edit_form #mobile").val(),

            officePhone:$("#edit_form #officePhone").val(),

            email:$("#edit_form #email").val(),

            country:$("#edit_form #country").val(),

            province:$("#edit_form #province").val(),

            city:$("#edit_form #city").val(),

            officeAddress:$("#edit_form #officeAddress").val(),

            organizationCode:$("#edit_form #organizationCode").val(),

            organizationName:$("#edit_form #organizationName").val()
        };

        if(compareStr(no,requestData["no"])&& compareStr(status,requestData["status"]) && compareStr(account,requestData["account"])
            && compareStr(name,requestData["name"])&& compareStr(gender,requestData["gender"])
            && compareStr(userType,requestData["userType"])&& compareStr(mobile,requestData["mobile"])
            && compareStr(officePhone,requestData["officePhone"])&& compareStr(email,requestData["email"])
            && compareStr(country,requestData["country"])&& compareStr(province,requestData["province"])
            && compareStr(city,requestData["city"])&& compareStr(officeAddress,requestData["officeAddress"])
            && compareStr(organizationCode,requestData["organizationCode"])&& compareStr(organizationName,requestData["organizationName"])){
            BUI.Message.Alert("数据未做修改，请修改后重新提交！","success");
            return;
        }

        var edit_dialog = this;
        var msg = "确认要进行修改操作吗?";
        var updateUrl = ctxPath + "/permission/user/modify";
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
    var addUrl = ctxPath + "/permission/user/create"

    showDialog("新增", "adddialog", function()   {

        add_form.valid();

        if(!add_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            no:$("#add_form #no").val(),

            account:$("#add_form #account").val(),

            name:$("#add_form #name").val(),

            gender:$("#add_form #gender").val(),

            password:$("#add_form #password").val(),

            userType:$("#add_form #userType").val(),

            mobile:$("#add_form #mobile").val(),

            officePhone:$("#add_form #officePhone").val(),

            email:$("#add_form #email").val(),

            country:$("#add_form #country").val(),

            province:$("#add_form #province").val(),

            city:$("#add_form #city").val(),

            officeAddress:$("#add_form #officeAddress").val(),

            remark:$("#add_form #remark").val(),

            organizationCode:$("#add_form #organizationCode").val(),

            organizationName:$("#add_form #organizationName").val(),

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

function resetPasswd()   {
    var key=$('input:radio:checked').val();
    if(key == null){
        BUI.Message.Alert('请选择一行数据!',function(){
        },'info');
        return ;
    }

    var resetPasswdUrl = ctxPath + "/permission/user/resetpasswd"

    showDialog("重置密码", "resetPasswdDialog", function()   {

        resetpasswd_form.valid();

        if(!resetpasswd_form.isValid()){
            console.log("校验失败");
            return;
        }

        var requestData = {
            id:key,
            newPasswd:$("#resetpasswd_form #newPasswd").val()
        };

        var newPasswd2 = $("#resetpasswd_form #newPasswd2").val();
        if (requestData["newPasswd"] != newPasswd2){
            BUI.Message.Alert("两次输入的密码不一致","success");
            return;
        }

        $.ajax({
            type: CTS_REQUEST_METHOD_POST,
            url: resetPasswdUrl,
            data: JSON.stringify(requestData),
            dataType: CTS_RESPONSE_DATA_TYPE_JSON,
            contentType:CTS_REQUEST_CONTENT_TYPE_APPLICATION_JSON,
            async: false,
            success: function(responseData)   {
                if(responseData.error == CTS_SUCCESS_RETURN_FLAG)   {
                    BUI.Message.Alert("操作成功!", "success");
                    //query();
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

    initSysDictionaryMap(statusMap, "add_form #status,edit_form #status", "", "");
    initSysDictionaryMap(genderMap, "add_form #gender,edit_form #gender", "", "");
    initSysDictionaryMap(userTypeMap, "add_form #userType,edit_form #userType", "", "");

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

    $("#resetPasswdBtn").unbind("click").click(function () {
        resetPasswd();
    });

    add_form = new BUI.Form.Form({
        srcNode : '#add_form'
    }).render();

    edit_form = new BUI.Form.Form({
        srcNode : '#edit_form'
    }).render();

    resetpasswd_form = new BUI.Form.Form({
        srcNode : '#resetpasswd_form'
    }).render();

});