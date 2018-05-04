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

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <div class="row">

            <div class="col-md-8">
                <div class="card mb-4">
                    <h5 class="card-header">
                        Contract list
                    </h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <table class="table table-hover contracts-table">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>user_id</th>
                                    <th>phoneNumber</th>
                                    <th>tariff</th>
                                    <th>active options</th>
                                    <th>status</th>
                                    <th width="100"></th>
                                    <th width="100"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${contracts}" var="contract">
                                    <tr class="contract-row">
                                        <td>${contract.id}</td>
                                        <td>${contract.user.id}</td>
                                        <td>${contract.phoneNumber}</td>
                                        <td>${contract.tariff.name}</td>
                                        <td><c:forEach items="${contract.activeOptions}" var="option">
                                            ${option.id};
                                        </c:forEach></td>
                                        <td>${contract.status}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </li>
                    </ul>
                    <button type="button" class="btn btn-success" id="addContractButton">Add contract</button>
                </div>
            </div>

        </div>
    </div>

</main>


<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>