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

            <div class="col-md-8">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>All tariffs</strong>
                    </h5>
                    <div class="card-body">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <table class="table table-hover tariffs-table">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>available options id</th>
                                    <th>status</th>
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
                                        <td><c:forEach items="${tariff.availableOptions}" var="option">
                                            ${option.id};
                                        </c:forEach></td>
                                        <td>${tariff.status}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </li>
                    </div>
                    <button type="button" class="btn btn-success" id="addTariffButton">Add tariff</button>
                </div>
            </div>

        </div>
    </div>

</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>