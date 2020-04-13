<%@page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${ctx}/static/images/favicon.ico">
    <script>
        if (self != top) {
            top.location.href='/login'
        }
    </script>
    <title>新心金融统一后台管理系统</title>

    <!-- Bootstrap core CSS -->
    <link href="${ctx}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="${ctx}/static/bootstrap/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${ctx}/static/bootstrap/css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="${pageContext.request.contextPath}/static/bootstrap/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="${ctx}/static/bootstrap/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${ctx}/static/bootstrap/js/html5shiv.min.js"></script>
    <script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
    <div class="wrapper">

        <form class="form-signin" method="post">

            <div class="form-signin-heading">
                <div class="form-header-content">
                    <img src="${ctx}/static/images/login/logo.png" alt="新心金融" width="140">
                    <img src="${ctx}/static/images/login/shuxian.png" alt="" style="margin:0 20px">
                    <span>统一后台管理系统</span>
                </div>
            </div>
            <c:if test="${not empty error}">
                <div id="message" class="alert alert-success">
                    <button rows-dismiss="alert" class="close">×</button>${error}
                </div>
            </c:if>
            <div class="input-group">
                <span class="input-group-addon" >
                    <img src="${ctx}/static/images/login/zhanghu.png" alt="">
                </span>
                <input type="text"  id="username" name="username" class="form-control" value="" placeholder="用户名" required>
            </div>
            <div class="input-group">
                <span class="input-group-addon " >
                    <img src="${ctx}/static/images/login/mima.png" alt="">
                </span>
                <input type="password" id="password" name="password" class="form-control" placeholder="密码" required>
            </div>    
            <div class="checkbox checkbox-custom">
                <input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe">
                <label for="rememberMe">
                    记住我
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block btn-cust" type="submit">登录</button>
        </form>
    </div>

</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${ctx}/static/bootstrap/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>