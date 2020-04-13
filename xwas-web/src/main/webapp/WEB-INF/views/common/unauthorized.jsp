<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>没有权限</title>
    <link href="<%=request.getContextPath()%>/static/bui/css/bs3/dpl-min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/static/bui/css/bs3/bui-min.css" rel="stylesheet">
</head>
<body>
    <div class="row">
        <div class="span6">
            <p>
            <div class="tips tips-warning">
                <span class="x-icon x-icon-small x-icon-error"><i class="icon icon-white icon-bell"></i></span>
                <div class="tips-content">您没有相应的权限
                </div>
            </div>
            </p>
        </div>
    </div>

</body>
</html>