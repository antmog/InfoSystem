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
    <a class="navbar-brand" href="#"><i class="fas fa-phone-square"></i> Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item mr-4">
                <a class="nav-link" href="#">
                    Корзина
                    <i class="fas fa-cart-arrow-down"></i>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    Личный кабинет
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    Выйти
                </a>
            </li>
        </ul>
    </div>
</nav>

<main class="mt-4">
    <div class="container">
        <div class="alert alert-danger" role="alert">
           Ваш номер заблокирован
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4 border-dark">
                    <h5 class="card-header">
                        Ваш контракт
                    </h5>
                    <div class="card-body">
                        <p>+7 (981) 707 18 94</p>
                        <p>Тариф: <a href="#">горячий</a></p>
                        <a href="#" class="btn btn-sm btn-primary">Блокировка номера</a>
                        <a href="#" class="btn btn-sm btn-primary">Сменить тариф</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        Мои услуги
                    </h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Черный список
                            <span class="badge badge-secondary badge-pill">
                                *111*442#
                            </span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Черный список
                            <span class="badge badge-secondary badge-pill">
                                *111*442#
                            </span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Черный список
                            <span class="badge badge-secondary badge-pill">
                                *111*442#
                            </span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            Черный список
                            <span class="badge badge-secondary badge-pill">
                                *111*442#
                            </span>
                        </li>
                    </ul>
                    <div class="card-body">
                        <a href="#" class="card-link">Все услуги</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4"></div>
        </div>
    </div>

</main>


<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>