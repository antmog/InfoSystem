<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4 border-dark">
                    <h5 class="card-header">
                        <strong>${loggedinuser}</strong>
                    </h5>
                    <div class="card-body">
                        <br/>
                        <br/>
                        <br/>
                        <br/>
                    </div>
                </div>
                <br/>
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Tariffs</strong>
                    </h5>
                    <div class="card-body">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <table class="table table-hover tariffs-table">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th width="100"></th>
                                    <th width="100"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffs}" var="tariff">
                                    <tr class="tariff-row">
                                        <td>${tariff.id}</td>
                                        <td>${tariff.name}</td>
                                        <td>${tariff.price}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </li>
                    </div>
                    <a href="/adminPanel/allTariffs" class="card-link">All tariffs</a>
                    <button type="button" class="btn btn-success" id="addTariffButton">Add tariff</button>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <div class="row">
                            <div class="col-auto mr-auto">
                                Users list
                            </div>
                            <div class="col-auto">
                                <div class="input-group">
                                    Search by number:
                                    <input class="searchUserByNumberInput" type="number" height="3">
                                    <i class="fas fa-search search-icon searchUserByNumber"></i>
                                </div>
                            </div>
                        </div>
                    </h5>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <table class="table table-hover users-table">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>firstName</th>
                                        <th>lastName</th>
                                        <th>passport</th>
                                        <th>mail</th>
                                        <th>contracts</th>
                                        <th>status</th>
                                        <th width="100"></th>
                                        <th width="100"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${users}" var="user">
                                        <tr class="user-row">
                                            <td>${user.id}</td>
                                            <td>${user.firstName}</td>
                                            <td>${user.lastName}</td>
                                            <td>${user.passport}</td>
                                            <td>${user.mail}</td>
                                            <td><c:forEach items="${user.userContracts}" var="contract">
                                                ${contract.id};
                                            </c:forEach></td>
                                            <td>${user.status}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </li>
                        </ul>
                    </div>
                    <a href="/adminPanel/allUsers" class="card-link">All Users</a>
                    <a href="/adminPanel/allContracts" class="card-link">All Contracts</a>
                    <button type="button" class="btn btn-success" id="addUserButton">Add user</button>
                    <button type="button" class="btn btn-success" id="addContractButton">Add contract</button>
                </div>
            </div>

            <div class="card mb-4">
                <h5 class="card-header">
                    <div class="row">
                        <div class="col-auto mr-auto">
                            Options list
                        </div>
                    </div>
                </h5>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <table class="table table-hover options-table">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${options}" var="option">
                                    <tr class="option-row">
                                        <td>${option.id}</td>
                                        <td>${option.name}</td>
                                        <td>${option.price}</td>
                                        <td>${option.costOfAdd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </li>
                    </ul>
                </div>
                <a href="/adminPanel/allOptions" class="card-link">All Options</a>
                <button type="button" class="btn btn-success" id="addOptionButton">Add option</button>
            </div>
        </div>
    </div>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>