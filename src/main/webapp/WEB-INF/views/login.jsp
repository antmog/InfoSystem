<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- ... -->
    <title>Login page</title>
    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/style.css">
    <link rel="stylesheet"
          href="/static/vendors/fontawesome-free-5.0.10/web-fonts-with-css/css/fontawesome-all.min.css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/"><i class="fas fa-phone-square"></i> Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
</nav>


<div class="form-signin-container">
    <c:url var="loginUrl" value="/login"/>
    <form class="form-signin mt-4" action="${loginUrl}" method="POST">
        <h1 class="h3 mb-3 font-weight-normal text-center">Log In</h1>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger" role="alert">
                <p>You've entered wrong login/password</p>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">
                <p>You have been logged out successfully.</p>
            </div>
        </c:if>
        <label class="sr-only">Login</label>
        <input type="text" class="form-control" id="username" name="login" placeholder="Login" required autofocus>
        <label class="sr-only">Password</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
        <div class="input-group input-sm">
            <div class="checkbox">
                <label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit" value="Log in">Log in</button>
    </form>
</div>
</body>
</html>