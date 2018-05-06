<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <c:if test="${user.status == 'BLOCKED'}">
            <div class="alert alert-danger" role="alert">
                Your profile is blocked!
            </div>
        </c:if>
        <c:if test="${user.status == 'INACTIVE'}">
            <div class="alert alert-warning" role="alert">
                Your profile is inactive.
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
                        <button type="button" class="btn btn-primary btn-sm">Change first/last name</button>
                        <button type="button" class="btn btn-primary btn-sm">Change password</button>
                    </div>
                    <c:choose>
                        <c:when test="${user.status == 'INACTIVE'}">
                            <label value="Inactive">Inactive (deactivated)
                            </label>
                            <button type="button" class="btn btn-success" id="unBlockUserButton">Activate
                            </button>
                        </c:when>
                        <c:when test="${user.status == 'ACTIVE'}">
                            <label value="Inactive">Active
                            </label>
                            <button type="button" class="btn btn-warning" id="deactivateUserButton">Deactivate
                            </button>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card mb-8">
                    <h5 class="card-header">
                        My Contracts
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover contracts-table">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>phoneNumber</th>
                                <th>price</th>
                                <th>active options</th>
                                <th>tariff</th>
                                <th>status</th>
                                <th width="100"></th>
                                <th width="100"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${user.userContracts}" var="contract">
                                <tr class="contract-row">
                                    <td>${contract.id}</td>
                                    <td>${contract.phoneNumber}</td>
                                    <td>${contract.price}</td>
                                    <td><c:forEach items="${contract.activeOptions}" var="option">
                                        ${option.id};
                                    </c:forEach></td>
                                    <td>${contract.tariff.name}</td>
                                    <td>
                                        <c:if test="${contract.status == 'ACTIVE'}"><span value="ACTIVE"
                                                                                          class="badge badge-pill badge-success">ACTIVE</span></c:if>
                                        <c:if test="${contract.status == 'BLOCKED'}"><span value="BLOCKED"
                                                                                           class="badge badge-pill badge-danger">BLOCKED</span></c:if>
                                        <c:if test="${contract.status == 'INACTIVE'}"><span value="INACTIVE"
                                                                                            class="badge badge-pill badge-warning">INACTIVE</span></c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <a href="#" class="card-link">All contracts</a>
                    </div>
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