<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- ... -->


    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/index.css">


    <title>Info-System</title>
</head>
<body>

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <div class="row">

            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong> ${contract.id} : ${contract.phoneNumber}</strong>
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover contracts-table">
                            <tbody>
                            <tr class="contract-row">
                                <td>owner id</td>
                                <td>${contract.user.id}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>tariff_id</td>
                                <td>${contract.tariff.id}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>active options</td>
                                <td><c:forEach items="${contract.activeOptions}" var="option">
                                    ${option.id};
                                </c:forEach></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <c:choose>
                        <c:when test="${contract.status == 'BLOCKED'}">
                            <label value="Inactive">Blocked
                            </label>
                            <button type="button" class="btn btn-success" id="unBlockContractButton">Unblock contract
                            </button>
                        </c:when>
                        <c:when test="${contract.status == 'INACTIVE'}">
                            <label value="Inactive">Inactive (deactivated)
                            </label>
                            <button type="button" class="btn btn-success" id="unBlockContractButton">Activate contract
                            </button>
                        </c:when>
                        <c:otherwise>
                            <label value="Inactive">Active
                            </label>
                            <button type="button" class="btn btn-danger" id="blockContractButton">Block contract
                            </button>
                            <button type="button" class="btn btn-warning" id="deactivateContractButton">Deactivate contract
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
                <br/>
            </div>
        </div>
    </div>

</main>
<script>
    var contract_id = ${contract.id}
</script>

<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>