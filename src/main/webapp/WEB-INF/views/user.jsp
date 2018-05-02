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
    <a class="navbar-brand" href="/"><i class="fas fa-phone-square"></i> Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item mr-4">
                <a class="nav-link" href="#">
                    Busket
                    <i class="fas fa-cart-arrow-down"></i>
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

<main class="mt-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4 border-dark">
                    <h5 class="card-header">
                        <strong> ${user.id} : ${user.firstName} ${user.lastName}</strong>
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover">
                            <tbody>
                            <tr>
                                <td>address</td>
                                <td>${user.address}</td>
                            </tr>
                            <tr>
                                <td>birth date</td>
                                <td>${user.birthDate}</td>
                            </tr>
                            <tr>
                                <td>mail</td>
                                <td>${user.mail}</td>
                            </tr>
                            <tr>
                                <td>passport number</td>
                                <td>${user.passport}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <br/>
            </div>
            <div class="col-md-8">
                <div class="card mb-4">

                    <h5 class="card-header">
                        Contract list
                    </h5>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <table class="table table-hover contracts-table">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>phoneNumber</th>
                                        <th>tariff</th>
                                        <th>active options</th>
                                        <th>status</th>
                                        <th width="100"></th>
                                        <th width="100"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${user.userContracts}" var="usercontract">
                                        <tr class="contract-row">
                                            <td>${usercontract.id}</td>
                                            <td>${usercontract.phoneNumber}</td>
                                            <td>${usercontract.tariff.name}</td>
                                            <td><c:forEach items="${usercontract.activeOptions}" var="option">
                                                ${option.id};
                                            </c:forEach></td>
                                            <td>${usercontract.status}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </li>
                        </ul>
                    </div>
                    <button type="button" class="btn btn-success" id="addContractToUserButton">Add contract
                    </button>
                </div>
            </div>

        </div>
    </div>

</main>

<script>
    var user_id = ${user.id}
</script>
<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>