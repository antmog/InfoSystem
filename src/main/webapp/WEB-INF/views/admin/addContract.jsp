<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-1">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content mb-4">
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-md-6">
                    <h1 class="h2 mb-0">
                        Contract registration form
                    </h1>
                </div>
            </div>
            <div class="row pt-5">
                <div class="col-md-4">
                    <div class="card mb-4">
                        <h5 class="card-header">
                            Contract common data
                        </h5>
                        <br/>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${empty userId}">
                                    <label class="control-lable" for="userId">userId</label>
                                    <input type="number" id="userId" class="form-control input-sm"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" path="userId" id="userId" class="form-control input-sm"
                                           value="${userId}"/>
                                </c:otherwise>
                            </c:choose>
                            <label class="control-lable" for="phoneNumber">Phone number</label>
                            <input type="number" id="phoneNumber" class="form-control input-sm"/>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="addContract">Add contract
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card">
                        <h5 class="card-header">
                            Selected options
                        </h5>
                        <div class="card-body">
                            <div class="container">
                                <div class="table-responsive">
                                    <table class="table" id="addContractAddedOptions">
                                        <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Name</th>
                                            <th>Price</th>
                                            <th>Cost of add</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="addContractDelOption">
                                Delete selected options
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Tariffs
                        </h5>
                        <div class="table-responsive">
                            <table class="table" id="addContractTariffs">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
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
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Available options
                        </h5>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table" id="addContractAvailableOptions">
                                    <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Name</th>
                                        <th>Price</th>
                                        <th>Cost of add</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="addContractAddOption">Add
                                selected options
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>
    </div>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>