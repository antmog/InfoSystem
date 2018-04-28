<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/index.css">

    <title>Info-System</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <a class="btn btn-primary" href="#">Войти</a>
            </li>
        </ul>
    </div>
</nav>

<div class="form-signin-container">
    <form class="form-signin mt-4" action="#">
        <h1 class="h3 mb-3 font-weight-normal text-center">Авторизация</h1>
        <div class="alert alert-danger" role="alert">
            Пожалуйста, введите правильные имя пользователя и пароль
        </div>
        <label class="sr-only">Логин</label>
        <input type="text" class="form-control" placeholder="Логин" required autofocus>
        <label class="sr-only">Пароль</label>
        <input type="password" class="form-control" placeholder="Пароль" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
    </form>
</div>
<div class="panel-heading"><span class="lead">List of Users </span></div>
<table class="table table-hover">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>mail</th>
        <th>role</th>
        <th width="100"></th>
        <th width="100"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.mail}</td>
            <td>${user.role}</td>
            <td><a href="<c:url value='/edit-user-${user.id}' />"
                   class="btn btn-success custom-width">edit</a>
            </td>
            <td><a href="<c:url value='/delete-user-${user.id}' />"
                   class="btn btn-danger custom-width">delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>