<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <c:if test="${user.status == 'BLOCKED'}">
            <div class="alert alert-danger" role="alert">
                Your number is blocked!
            </div>
        </c:if>
        <c:if test="${user.status == 'INACTIVE'}">
            <div class="alert alert-warning" role="alert">
                Your number is inactive.
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4 border-dark">
                    <h5 class="card-header">
                        Welcome, dear ${user.firstName} ${user.lastName}!
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover userEditableTable">
                            <tbody>
                            <tr class="editable">
                                <td>address</td>
                                <td>${user.address}</td>
                            </tr>
                            <tr>
                                <td>birth date</td>
                                <td>${user.birthDate}</td>
                            </tr>
                            <tr class="editable">
                                <td>mail</td>
                                <td>${user.mail}</td>
                            </tr>
                            <tr class="editable">
                                <td>passport</td>
                                <td>${user.passport}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4 border-dark">
                    <h5 class="card-header">
                        Welcome, dear ${user.firstName} ${user.lastName}!
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
<script>
    var user_id = ${user.id};
</script>
<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>