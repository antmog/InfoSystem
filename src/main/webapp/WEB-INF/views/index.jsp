<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/vendors/fontawesome-free-5.0.10/fontawesome-all.min.css">

    <title>Info-System</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Info-System</a>
</nav>

<main role="main">

    <div class="jumbotron">
        <div class="container">
            <h1 class="display-4">Info-System</h1>
            <p>Информационная система оператора сотовой связи</p>
            <a class="btn btn-primary btn-lg" href="/login" role="button">Войти »</a>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h2>Тарифы</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                <p><a class="btn btn-secondary" href="#" role="button">Подробнее &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>Личный кабинет</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                <p><a class="btn btn-secondary" href="#" role="button">Подробнее &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>Поиск</h2>
                <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
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