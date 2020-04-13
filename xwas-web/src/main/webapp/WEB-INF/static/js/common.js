/**
 *通用全局常量
 * @type Boolean
 */
var CTS_SUCCESS_RETURN_FLAG = false;
var CTS_REQUEST_METHOD_POST = "POST"; // POST请求方式
var CTS_REQUEST_METHOD_GET = "GET"; // GET请求方式
var CTS_RESPONSE_DATA_TYPE_JSON = "json"; // 服务端以json格式返回数据
var CTS_REQUEST_CONTENT_TYPE_APPLICATION_JSON = "application/json"; // 发送数据到服务端时内容编码类型
var CLIENT_WIDTH = document.documentElement.clientWidth; // 分页表格的宽度
var CLIENT_HEIGHT = document.documentElement.clientHeight - 61; // 获取当前高度
var CTS_PAGE_SIZE = 10;// 分页每页显示条数
var CTS_PAGE_START = 1;// 分页从1开始

function Map(){
    this.container = new Object();
}
Map.prototype.put = function(key, value){
    this.container[key] = value;
};
Map.prototype.get = function(key){
    return this.container[key];
};
Map.prototype.keyArray = function(){
    var keys = new Array();
    for(var key in this.container){
        keys.push(key);
    }
    return keys;
};
Array.prototype.contain = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
};

/**
 * 禁用backspace键的后退功能，但是可以删除文本内容
 */
document.onkeydown = onKeyDownCheck;
function onKeyDownCheck(event) {
    var code = null;
    event = window.event || event;
    if(!event){
        event = window.event;
    }
    if(event.keyCode){
        code = event.keyCode;
    }else if(event.which){
        code = event.which;
    }
    var readonlycheck = event.srcElement.readOnly || event.target.readOnly;
    if (code == 8 &&
        ((event.srcElement.type != "text" && event.srcElement.type != "textarea" && event.srcElement.type != "password") || readonlycheck == true)) {
        event.keyCode = 0;
        event.returnValue = false;
    }
    return true;
}

/**
 * 通用ajax请求
 * @param url：请求URL
 * @param requestData：请求参数
 * @param succCallback：服务成功响应后的回调
 */
function commonAjax(url, requestData, succCallback) {
    $.ajax({
        type: CTS_REQUEST_METHOD_POST,
        url: url,
        data: requestData,
        dataType: CTS_RESPONSE_DATA_TYPE_JSON,
        async: false,
        success: succCallback,
        error: function(e) {
            BUI.Message.Alert("哎哟！系统调皮了...",'error');
        }
    });
}

function initSysDictionaryMap(targetMap, selectId, removeItems, defaultKey)   {
    enumCommonSelectWithDefault(targetMap,selectId,removeItems,defaultKey);
}

/**
 * 动态加载select下拉框
 * @param map：加载的数据map
 * @param selectId：下拉控件ID
 * @param removeItems：移除选项,|区分多项
 * @param defaultKey：默认值
 */
function enumCommonSelectWithDefault(map, selectId, removeItems, defaultKey) {
    var selectStr = "";
    if (defaultKey == null || defaultKey == "") {
        selectStr = "<option value=\"\" selected=\"selected\">请选择</option>";
    } else {
        selectStr = "<option value=\"\">请选择</option>";
    }
    var keys = map.keyArray();
    var removeItemArray = removeItems ? removeItems.split("|") : new Array();
    for (var i = 0; i < keys.length; i++) {
        if (!removeItemArray.contain(keys[i])) {
            if (keys[i] == defaultKey) {
                selectStr += "<option value='" + keys[i]
                    + "' selected='selected'>" + map.get(keys[i])
                    + "</option>";
            } else {
                selectStr += "<option value='" + keys[i] + "'>"
                    + map.get(keys[i]) + "</option>";
            }
        }
    }
    if (undefined != selectId && "" != selectId) {
        var selectIdArray = selectId.split(",");
        var length = selectIdArray.length;
        for (var i = 0; i < length; i++) {
            $("#" + selectIdArray[i]).append(selectStr);
        }
    }
}

/**
 * 动态加载select下拉框,默认第一个set选中
 * @param map：加载的数据map
 * @param selectId：下拉控件ID
 * @param removeItems：移除选项,|区分多项
 */
function enumCommonSelectWithFirst(map, selectId, removeItems) {
    var selectStr = "";

    var keys = map.keyArray();
    var removeItemArray = removeItems ? removeItems.split("|") : new Array();
    for (var i = 0; i < keys.length; i++) {
        if (!removeItemArray.contain(keys[i])) {
            if (0 == i) {
                selectStr += "<option value='" + keys[i]
                    + "' selected='selected'>" + map.get(keys[i])
                    + "</option>";
            } else {
                selectStr += "<option value='" + keys[i] + "'>"
                    + map.get(keys[i]) + "</option>";
            }
        }
    }
    if (undefined != selectId && "" != selectId) {
        var selectIdArray = selectId.split(",");
        var length = selectIdArray.length;
        for (var i = 0; i < length; i++) {
            $("#" + selectIdArray[i]).append(selectStr);
        }
    }
}

/**
 * 显示简单分页表格
 * @param url：请求的URL
 * @param columns：表格列
 * @param divID：显示表格的div容器标识
 * @param requestData：请求参数
 * @param width：表格宽度,默认是屏幕宽度
 * @param height：表格高度,默认是屏幕高度
 */
function Table(){}
var table = new Table();
Table.prototype.queryByPage = function(url, columns, divID, requestData, width, height) {
    $("#"+divID).empty();
    BUI.use(['bui/grid','bui/data','bui/overlay'],function(Grid,Data)   {
        var store = new BUI.Data.Store({
            url: url,
            params: requestData,
            proxy : {// 设置请求相关的参数
                method: CTS_REQUEST_METHOD_POST,
                dataType: CTS_RESPONSE_DATA_TYPE_JSON, // 返回数据的类型
                pageStart: CTS_PAGE_START  // 分页从1开始
            },
            autoLoad:true,
            errorProperty:'errorMsg',    // 存放错误信息的字段(error)
            hasErrorProperty:'error',  // 是否错误的字段（hasError)
            root:"dataList",             // 存放数据的字段名(rows)
            totalProperty:"total",     	 //存放记录总数的字段名(results)
            pageSize: CTS_PAGE_SIZE	     //每页显示数目
        });
        var grid = new BUI.Grid.Grid({
            render:"#"+divID,
            columns:columns,
            loadMask:true,
            store:store,
            forceFit:true,// 列宽按百分比自适应
            width:width == null? CLIENT_WIDTH : width,
            height:height == null? CLIENT_HEIGHT-160 : height,
            emptyDataTpl:'<div class="centered">查询无数据</div>',
            bbar:{
                pagingBar:true
            }
        });
        store.on('exception',function (ev) {
            BUI.Message.Alert(ev.error,'error');
        });
        grid.render();
    });
};



/**
 * 显示指定宽度的分页表格
 * @param url：请求的URL
 * @param columns：表格列
 * @param divID：显示表格的div容器标识
 * @param requestData：请求参数
 * @param width：表格宽度,默认是屏幕宽度
 * @param height：表格高度,默认是屏幕高度
 */
Table.prototype.queryWidthByPage = function(url, columns, divID, requestData, width, height) {
    $("#"+divID).empty();
    BUI.use(['bui/grid','bui/data','bui/overlay'],function(Grid,Data)   {
        var flag = "true";
        /*if(null != requestData && Object.keys(requestData).length > 0){
            for(var i in requestData){
                if(null != requestData[i] && "" != requestData[i]){
                    flag = "true";
                }
            }
        }*/

        if(flag == "false"){
            var data = []; //显示的数据
            var store = new BUI.Data.Store({
                data : data,
                autoLoad:true
            });
        }else{
            var store = new BUI.Data.Store({
                url: url,
                params: requestData,
                proxy : {// 设置请求相关的参数
                    method: CTS_REQUEST_METHOD_POST,
                    dataType: CTS_RESPONSE_DATA_TYPE_JSON, // 返回数据的类型
                    pageStart: CTS_PAGE_START  // 分页从1开始
                },
                autoLoad:true,
                errorProperty:'errorMsg',    // 存放错误信息的字段(error)
                hasErrorProperty:'error',  // 是否错误的字段（hasError)
                root:"rows",             // 存放数据的字段名(rows)
                totalProperty:"total",     	 //存放记录总数的字段名(results)
                pageSize: CTS_PAGE_SIZE	     //每页显示数目
            });
        }
        var grid = new BUI.Grid.Grid({
            render:"#"+divID,
            columns:columns,
            loadMask:true,
            store:store,
            //width:width == null? CLIENT_WIDTH : width,
            width:'100%',
            //forceFit:true,//自适应宽度
            plugins:[Grid.Plugins.AutoFit],
            //height:height == null? CLIENT_HEIGHT-150 : height,
            emptyDataTpl:'<div class="centered">查询无数据</div>',
            bbar:{
                pagingBar:true
            }
        });
        store.on('exception',function (ev) {
            BUI.Message.Alert(ev.error,'error');
        });
        grid.render();
    });
};

/**
 * 显示指定宽度的分页表格
 * @param url：请求的URL
 * @param columns：表格列
 * @param divID：显示表格的div容器标识
 * @param requestData：请求参数
 * @param width：表格宽度,默认是屏幕宽度
 * @param height：表格高度,默认是屏幕高度
 */
Table.prototype.queryByPageWidth = function(url, columns, divID, requestData, width, height) {
    $("#"+divID).empty();
    BUI.use(['bui/grid','bui/data','bui/overlay'],function(Grid,Data)   {
        var flag = "true";
        /*if(null != requestData && Object.keys(requestData).length > 0){
            for(var i in requestData){
                if(null != requestData[i] && "" != requestData[i]){
                    flag = "true";
                }
            }
        }*/

        if(flag == "false"){
            var data = []; //显示的数据
            var store = new BUI.Data.Store({
                data : data,
                autoLoad:true
            });
        }else{
            var store = new BUI.Data.Store({
                url: url,
                params: requestData,
                proxy : {// 设置请求相关的参数
                    method: CTS_REQUEST_METHOD_POST,
                    dataType: CTS_RESPONSE_DATA_TYPE_JSON, // 返回数据的类型
                    pageStart: CTS_PAGE_START  // 分页从1开始
                },
                autoLoad:true,
                errorProperty:'errorMsg',    // 存放错误信息的字段(error)
                hasErrorProperty:'error',  // 是否错误的字段（hasError)
                root:"rows",             // 存放数据的字段名(rows)
                totalProperty:"total",     	 //存放记录总数的字段名(results)
                pageSize: CTS_PAGE_SIZE	     //每页显示数目
            });
        }
        var grid = new BUI.Grid.Grid({
            render:"#"+divID,
            columns:columns,
            loadMask:true,
            store:store,
            width:width,
            //width:'100%',
            //forceFit:true,//自适应宽度
            plugins:[Grid.Plugins.AutoFit],
            //height:height == null? CLIENT_HEIGHT : height,
            emptyDataTpl:'<div class="centered">查询无数据</div>',
            bbar:{
                pagingBar:true
            }
        });
        store.on('exception',function (ev) {
            BUI.Message.Alert(ev.error,'error');
        });
        grid.render();
    });
};

/**
 * 显示可折叠的分页表格
 * @param url：请求的URL
 * @param columns：表格列
 * @param divID：显示表格的div容器标识
 * @param requestData：请求参数
 * @param width：表格宽度,默认是屏幕宽度
 * @param height：表格高度,默认是屏幕高度
 */
function Table(){}
var table = new Table();
Table.prototype.queryCollapseByPage = function(url, columns, divID, requestData, width, height, expandDataQueryUrl) {
    $("#"+divID).empty();
    BUI.use(['bui/grid','bui/data','bui/overlay'],function(Grid,Data)   {
        var store = new BUI.Data.Store({
            url: url,
            params: requestData,
            proxy : {// 设置请求相关的参数
                method: CTS_REQUEST_METHOD_POST,
                dataType: CTS_RESPONSE_DATA_TYPE_JSON, // 返回数据的类型
                pageStart: CTS_PAGE_START  // 分页从1开始
            },
            autoLoad:true,
            errorProperty:'errorMsg',    // 存放错误信息的字段(error)
            hasErrorProperty:'error',  // 是否错误的字段（hasError)
            root:"rows",             // 存放数据的字段名(rows)
            totalProperty:"total",     	 //存放记录总数的字段名(results)
            pageSize: CTS_PAGE_SIZE	     //每页显示数目
        });

        // 实例化 Grid.Plugins.Cascade 插件
        var cascade = new Grid.Plugins.Cascade({
                renderer : function(record){
                    return '<div class="inner-grid"></div>';	//生成简单表格的容器
                }
            }),
            //简单表格的配置信息
            simpleGridConfig = {
                autoRender:true,                 //自动生成
                columns:BUI.cloneObject(columns)//这里为了简单起见，复制了配置信息，应用中需要自己配置
            };

        var grid = new BUI.Grid.Grid({
            render:"#"+divID,
            columns:columns,
            loadMask:true,
            store:store,
            plugins: [cascade],	// Grid.Plugins.Cascade 插件
            width:width == null? CLIENT_WIDTH : width,
            height:height == null? CLIENT_HEIGHT-160 : height,
            emptyDataTpl:'<div class="centered">查询无数据</div>',
            bbar:{
                pagingBar:true
            }
        });
        store.on('exception',function (ev) {
            BUI.Message.Alert(ev.error,'error');
        });
        grid.render();

        cascade.on('expand',function(ev) {
            var data = ev.record,
                row = ev.row,
                sgrid = $(row).data('sub-grid');

            commonAjax (expandDataQueryUrl, data, function(responseData) {
                if (responseData.isError == CTS_SUCCESS_RETURN_FLAG) {
                    if (!sgrid) {
                        var container = $(row).find('.inner-grid'),
                            gridConfig = BUI.cloneObject(simpleGridConfig);
                        gridConfig.render = container;
                        gridConfig.forceFit=false;
                        gridConfig.width= CLIENT_WIDTH;

                        sgrid = new Grid.SimpleGrid(gridConfig);
                        sgrid.showData(responseData.dataList);
                        $(row).data('sub-grid',sgrid);
                    } else {
                        sgrid.showData(responseData.dataList);
                    }
                } else {
                    BUI.Message.Alert(responseData.errorMsg, "error");
                }
            });
        });
    });
};



/**
 * 显示模式对话框
 * @param title：对话框标题
 * @param divID：显示对话框的div容器标识
 * @param succCallBack: 确定按钮触发后的回调
 */
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


/**
 * 显示批量更新模式对话框
 * @param title：对话框标题
 * @param divID：显示对话框的div容器标识
 * @param succCallBack: 确定按钮触发后的回调
 */
function showBatchUpdateDialog(title, divID, succCallBack) {
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
                    text: "<i class='icon-ok icon-white'></i>更新一条",
                    elCls: "button button-success",
                    handler: function(){
                        succCallBack("false", dialog);
                    }
                },

                {
                    text: "<i class='icon-ok icon-white'></i>批量更新",
                    elCls: "button button-success",
                    handler: function(){
                        succCallBack("true", dialog);
                    }
                }
            ]
        });
        dialog.show();
    });
}


/**
 * 显示模式表格对话框
 * @param title：对话框标题
 * @param divID：显示对话框的div容器标识
 * @param succCallBack: 确定按钮触发后的回调
 */
function showTableDialog(title, divID) {
    BUI.use([ 'bui/overlay','bui/form' ], function(Overlay, Form) {
        var dialog = new Overlay.Dialog({
            title : title,
            contentId : divID,
            closeAction : "destroy",
            zIndex: 1000,
            buttons:[


            ]
        });
        dialog.show();
    });
}

/**
 * 显示日期时间控件
 * 使用：class样式有calendar-time的，会自动渲染成日期时间控件
 */
function showDateTimeUI() {
    BUI.use('bui/calendar', function(Calendar) {
        new Calendar.DatePicker({
            trigger : '.calendar-time',
            showTime : true,
            autoRender : true
        });
    });
};

/**
 * 显示日期控件
 * 使用：class样式有calendar的，会自动渲染成日期控件
 */
function showDateUI() {
    BUI.use('bui/calendar', function(Calendar) {
        new Calendar.DatePicker({
            trigger : '.calendar',
            autoRender : true,
        });
    });
};

/**
 * 显示日期控件
 * 使用：class样式有calendar的，会自动渲染成日期控件
 */
function showDateUIWithFormat(format) {
    BUI.use('bui/calendar', function(Calendar) {
        new Calendar.DatePicker({
            trigger : '.calendar',
            autoRender : true,
            dateMask : format
        });
    });
};

function valto2precision(val){
    if (null==val||typeof(val)=="undefined") {
        return val;
    } else {
        return val.toFixed(2);
    }
};

/**
 * 显示当前系统日期
 * 格式：yyyy-MM-dd
 */
function getNowDate(){
    var now = new Date();
    var month=now.getMonth()+1;
    if(month < 10){
        month = "0" + month;
    }
    var day=now.getDate();
    if(day < 10){
        day="0"+day;
    }
    return now.getFullYear() +"-"+month+"-"+day;
}

/**
 * 获取当前系统日期
 * 格式：yyyyMMdd
 */
function getShortNowDate()   {
    var now = new Date();
    var month=now.getMonth() + 1;
    if(month < 10){
        month = "0" + month;
    }
    var day = now.getDate();
    if(day < 10){
        day = "0" + day;
    }
    return now.getFullYear() + month + day;
}

/**
 * 起时日期、结束日期不为空时,校验结果日期必须大于等于起时日期
 * @param beginDateId：起时日期
 * @param endDateId：结束日期
 */
function dateCompare(beginDateId, endDateId) {
    var beginDate = $("#"+beginDateId).val();
    var endDate = $("#"+endDateId).val();

    var bDate = beginDate.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
    var eDate = endDate.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
    if(eDate - bDate >= 0) {
        return true;
    }
    BUI.Message.Alert("结束日期必须大于等于起始日期", "warning");
    return false;
}

/**
 *比较两字符串是否相等，相等返回true
 */
function compareStr(str1 , str2)	{
    if(null == str1) {
        if(null == str2 || "" == str2){
            return true;
        }else{
            return false;
        }
    }else{
        if(null == str2){
            if("" == str1){
                return true;
            }else{
                return false;
            }
        }else if(str1 == str2){
            return true;
        }else{
            return false;
        }
    }
}

/**
 *比较两数字是否相等，相等返回true
 */
function compareNum(num1 , num2)	{
    if(null == num1) {
        if(null == num2 || "" == num2){
            return true;
        }else{
            return false;
        }
    }else{
        if(null == num2){
            if("" == num1){
                return true;
            }else{
                return false;
            }
        }else if(num1 == num2){
            return true;
        }else{
            return false;
        }
    }
}