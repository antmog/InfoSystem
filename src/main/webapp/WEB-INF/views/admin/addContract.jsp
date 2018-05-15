<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <jsp:include page="../header.jsp"/>
</head>

<body>
<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Contract registration form</strong>
                    </h5>
                    <br/>
                    <c:choose>
                        <c:when test="${empty userId}">
                            <label class="control-lable" for="userId">userId</label>
                            <input type="text" id="userId" class="form-control input-sm"/>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" path="userId" id="userId" class="form-control input-sm" value="${userId}"/>
                        </c:otherwise>
                    </c:choose>
                    <label class="control-lable" for="phoneNumber">phoneNumber</label>
                    <input type="number" id="phoneNumber" class="form-control input-sm"/>
                    <button type="button" class="btn btn-success" id="addContract">Add contract</button>
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
<jsp:include page="../footer.jsp"/>
</body>
</html>