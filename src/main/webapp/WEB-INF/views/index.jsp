<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/lk">
                    LK
                </a>
            </li>
            <sec:authorize access="isAuthenticated()">
            <li class="nav-item">
                <a class="nav-link" href="/logout">
                    <strong>${loggedinuser}</strong>, Log out
                </a>
            </li>
            </sec:authorize>
        </ul>
    </div>
</nav>

<main role="main">

    <div class="jumbotron">
        <div class="container">
            <h1 class="display-4">Info-System</h1>
            <p>Cellular Carrier Information System</p>
            <sec:authorize var="loggedIn" access="isAuthenticated()" />
            <c:choose>
                <c:when test="${loggedIn}">
                    <strong>WELCOME, dear ${loggedinuser}</strong>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-primary btn-lg" href="/login" role="button">Log In »</a>
                </c:otherwise>
            </c:choose>

        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h2>Tariffs</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor
                    mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna
                    mollis euismod. Donec sed odio dui. </p>
                <p><a class="btn btn-secondary" href="#" role="button">Подробнее &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>LK</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor
                    mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna
                    mollis euismod. Donec sed odio dui. </p>
                <p><a class="btn btn-secondary" href="#" role="button">Подробнее &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>Search</h2>
                <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula
                    porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
                    ut fermentum massa justo sit amet risus.</p>
                <p><a class="btn btn-secondary" href="#" role="button">Подробнее &raquo;</a></p>
            </div>
        </div>

        <hr>
    </div>

</main>
<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>