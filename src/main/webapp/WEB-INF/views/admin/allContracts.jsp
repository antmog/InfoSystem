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
                                    <th>price</th>
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
                                        <td>${contract.price}</td>
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
<jsp:include page="../footer.jsp"/>
</body>
</html>