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
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong> ${contractPageDto.contract.id} : ${contractPageDto.contract.phoneNumber}</strong>
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover" id="tariffTable">
                            <tbody>
                            <tr class="contract-row">
                                <td>owner id</td>
                                <td>${contractPageDto.contract.user.id}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>tariff_id</td>
                                <td>${contractPageDto.contract.tariff.id}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>price</td>
                                <td>${contractPageDto.contract.price}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>active options</td>
                                <td><c:forEach items="${contractPageDto.contract.activeOptions}" var="option">
                                    ${option.id};
                                </c:forEach></td>
                            </tr>
                            </tbody>
                        </table>
                        <button id="deleteContract" type="button" class="btn btn-primary btn-sm btn-danger">Delete contract</button>
                    </div>
                    <c:choose>
                        <c:when test="${contractPageDto.contract.status == 'BLOCKED'}">
                            <label value="Inactive">Blocked
                            </label>
                            <button type="button" class="btn btn-success" id="unBlockContractButton">Unblock contract
                            </button>
                        </c:when>
                        <c:when test="${contractPageDto.contract.status == 'INACTIVE'}">
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


            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Current options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="contractCurrentOptions">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${contractPageDto.contract.activeOptions}" var="option">
                                    <tr class="move-row">
                                        <td>${option.id}</td>
                                        <td>${option.name}</td>
                                        <td>${option.price}</td>
                                        <td>${option.costOfAdd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="contractDelOption">Delete selected option</button>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Options available for tariff</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="contractAvailableOptions">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${contractPageDto.contract.tariff.availableOptions}" var="availableOption">
                                    <tr class="move-row">
                                        <td>${availableOption.id}</td>
                                        <td>${availableOption.name}</td>
                                        <td>${availableOption.price}</td>
                                        <td>${availableOption.costOfAdd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="contractAddOption">Add selected option</button>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card mb-6">
                    <div class="row">
                        <div class="col-md-6">
                            <h5 class="card-header">
                                <strong>Tariffs</strong>
                            </h5>
                            <div class="card-body">
                                <table class="table" id="addContractTariffs">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>name</th>
                                        <th>price</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${contractPageDto.tariffs}" var="tariff">
                                        <tr class="t-row">
                                            <td>${tariff.id}</td>
                                            <td>${tariff.name}</td>
                                            <td>${tariff.price}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="col-md-6">
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
                    <button type="button" class="btn btn-success" id="switchTariff">Swith to this tariff
                    </button>
                </div>
            </div>
        </div>
    </div>

    <table style="visibility:hidden" class="table" id="parseTable">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>price</th>
            <th>costofadd</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</main>
<script>
    var contract_id = ${contractPageDto.contract.id};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>