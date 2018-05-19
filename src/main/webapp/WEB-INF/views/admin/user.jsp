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
                        <strong> ${user.id} : ${user.firstName} ${user.lastName}</strong>
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover userEditableTable">
                            <tbody>
                            <tr class = "editable">
                                <td>Address</td>
                                <td>${user.address}</td>
                            </tr>
                            <tr>
                                <td>Birth date</td>
                                <td>${user.birthDate}</td>
                            </tr>
                            <tr class = "editable">
                                <td>Mail</td>
                                <td>${user.mail}</td>
                            </tr>
                            <tr class = "editable">
                                <td>Passport</td>
                                <td>${user.passport}</td>
                            </tr>
                            </tbody>
                        </table>
                        <button id="deleteUser" type="button" class="btn btn-primary btn-sm btn-danger">Delete user</button>
                    </div>
                    <c:choose>
                        <c:when test="${user.status == 'BLOCKED'}">
                            <label value="Inactive">Blocked
                            </label>
                            <button type="button" class="btn btn-success" id="unBlockUserButton">Unblock user
                            </button>
                        </c:when>
                        <c:when test="${user.status == 'INACTIVE'}">
                            <label value="Inactive">Inactive (deactivated)
                            </label>
                            <button type="button" class="btn btn-success" id="unBlockUserButton">Activate user
                            </button>
                        </c:when>
                        <c:otherwise>
                            <label value="Inactive">Active
                            </label>
                            <button type="button" class="btn btn-danger" id="blockUserButton">Block User
                            </button>
                            <button type="button" class="btn btn-warning" id="deactivateUserButton">Deactivate user
                            </button>
                        </c:otherwise>
                    </c:choose>
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
                                        <th>price</th>
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
                                            <td>${usercontract.price}</td>
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
    var user_id = ${user.id};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>