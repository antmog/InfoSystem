<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<%@ include file="/static/vendors/particle/index.html" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- ... -->
    <title>Password recovery</title>
    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/style.css">
    <link rel="stylesheet"
          href="/static/vendors/fontawesome-free-5.0.10/web-fonts-with-css/css/fontawesome-all.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/"><i class="fas fa-phone-square"></i> Info-System</a>
</nav>
<div class="form-signin-container login-div">
    <form class="form-signin mt-4" id="sendSmsForm">
        <div class="col-md-12"><h1 class="h3 mb-3 font-weight-normal text-center">Password recovery</h1>
            <label>Captcha: </label><label id="captchaLabel"></label>
            <input type="number" class="form-control" id="captcha" name="captcha" placeholder="Enter captcha" required
                   autofocus>
            <label class="sr-only">Phone number</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Phone number">
            <button class="btn btn-lg btn-primary btn-block" type="submit" value="Log in">Send me new password</button>
        </div>
    </form>
</div>
<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/vendors/bootstrap-notify/bootstrap-notify.min.js" defer></script>
<script src="/static/js/global.js" defer></script>
</body>
</html>