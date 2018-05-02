<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- ... -->
    <title>User Registration Form</title>
    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/index.css">


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
            <li class="nav-item active">
                <a class="nav-link" href="/lk">
                    LK
                </a>
            </li>
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
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Contract registration form</strong>
                    </h5>
                    <form:form method="POST" modelAttribute="contractUserIdDto" class="form-horizontal">
                        <div class="form-group col-md-12">
                            <br/>
                            <label class="control-lable" for="user_id">user_id</label>
                            <div class="col-md-7">
                                <form:input type="text" path="user_id" id="user_id" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="user_id" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group col-md-12">
                            <label class="control-lable" for="phoneNumber">phoneNumber</label>
                            <div class="col-md-7">
                                <form:input type="text" path="phoneNumber" id="phoneNumber"
                                            class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="phoneNumber" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${edit}">
                                <input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a
                                    href="<c:url value='/adminPanel' />">Cancel</a>
                            </c:when>
                            <c:otherwise>
                                <input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a
                                    href="<c:url value='/adminPanel' />">Cancel</a>
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Selected options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="addContractAddedOptions">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>cost</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariff.availableOptions}" var="option">
                                    <tr class="move-row">
                                        <td>${option.id}</td>
                                        <td>${option.name}</td>
                                        <td>${option.price}</td>
                                        <td>${option.costOfAdd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="addContractDelOption">Delete selected options
                    </button>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card mb-6">
                    <div class="row">
                        <div class="col-sm-4">
                            <h5 class="card-header">
                                <strong>Tariffs</strong>
                            </h5>
                            <div class="card-body">
                                <table class="table" id="addContractTariffs">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>name</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${tariffs}" var="tariff">
                                        <tr class="t-row">
                                            <td>${tariff.id}</td>
                                            <td>${tariff.name}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="col-sm-8">
                            <h5 class="card-header">
                                <strong>Available options</strong>
                            </h5>
                            <div class="card-body">
                                <table class="table" id="addContractAvailableOptions">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>name</th>
                                        <th>price</th>
                                        <th>cost</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="addContractAddOption">Add selected options
                    </button>
                </div>
            </div>


        </div>
    </div>

</main>

<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/vendors/jquery/jquery.tabletojson.min.js" defer></script>
<script src="/static/js/main.js" defer></script>

</body>
</html>