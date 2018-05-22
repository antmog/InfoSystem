<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-1">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content">
        <c:if test="${userPageDto.status == 'BLOCKED'}">
            <br>
            <div class="alert alert-danger mt-3" role="alert">
                User is blocked!
            </div>
        </c:if>
        <c:if test="${userPageDto.status == 'INACTIVE'}">
            <br>
            <div class="alert alert-warning mt-3" role="alert">
                User is inactive.
            </div>
        </c:if>
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-md-6">
                    <h1 class="h2 mb-0">
                        ${userPageDto.firstName} ${userPageDto.lastName}
                        <span class="badge badge-secondary">Id: ${userPageDto.id}</span>
                    </h1>
                </div>
                <div class="col-md-6 text-right">
                    <c:choose>
                        <c:when test="${userPageDto.status == 'INACTIVE'}">
                            <button type="button" class="btn btn-sm btn-success" id="unBlockUserButton">Activate user</button>
                            </button>
                        </c:when>
                        <c:when test="${userPageDto.status == 'ACTIVE'}">
                            <button type="button" class="btn btn-sm btn-primary" id="deactivateUserButton">Deactivate user</button>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="row pt-5">
                <div class="col-md-4">
                    <div class="card text-white bg-info mb-4">
                        <div class="card-body">
                            <h5 class="card-title">Wallet balance: ${userPageDto.balance} €</h5>
                            <a href="/customerPanel/addFunds" class="btn btn-outline-light btn-sm">Add funds</a>
                        </div>
                    </div>

                    <div class="card">
                        <h5 class="card-header">
                            Info
                        </h5>
                        <div class="card-body">
                            <dl>
                                <dt>First name</dt>
                                <dd>${userPageDto.firstName}</dd>
                                <dt>Last name</dt>
                                <dd>${userPageDto.lastName}</dd>
                                <dt>Address</dt>
                                <dd>${userPageDto.address}</dd>
                                <dt>Birth date</dt>
                                <dd>${userPageDto.birthDate}</dd>
                                <dt>E-mail</dt>
                                <dd>${userPageDto.mail}</dd>
                                <dt>Passport</dt>
                                <dd>${userPageDto.passport}</dd>
                            </dl>
                        </div>
                        <div class="card-footer">
                            <a href="/customerPanel/editUser/${userPageDto.id}" class="btn btn-outline-primary btn-sm">Edit</a>
                            <a href="/customerPanel/editUser/changePassword/${userPageDto.id}" class="btn btn-outline-primary btn-sm">Change password</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card">
                        <h5 class="card-header">
                            Contract list
                        </h5>
                        <table class="table table-hover contracts-table">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Phone number</th>
                                <th>Options count</th>
                                <th>Tariff</th>
                                <th>Price</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${userPageDto.userContracts}" var="contract">
                                <tr class="contract-row">
                                    <td>${contract.id}</td>
                                    <td>${contract.phoneNumber}</td>
                                    <td>${fn:length(contract.activeOptions)}</td>
                                    <td>${contract.tariff.name}</td>
                                    <td>${contract.price}</td>
                                    <td>${contract.status}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<script>
    var user_id = ${userPageDto.id};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>