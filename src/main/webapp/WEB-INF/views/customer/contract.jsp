<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-3">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content">
        <c:if test="${contractPageDto.contract.status == 'BLOCKED'}">
            <br>
            <div class="alert alert-danger mt-3" role="alert">
                Contract is blocked!
            </div>
        </c:if>
        <c:if test="${contractPageDto.contract.status == 'INACTIVE'}">
            <br>
            <div class="alert alert-warning mt-3" role="alert">
                Contract is inactive.
            </div>
        </c:if>
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-md-6">
                    <h1 class="h2 mb-0">
                        Contract №${contractPageDto.contract.id}
                        <span class="badge badge-secondary">Phone number: ${contractPageDto.contract.phoneNumber}</span>
                    </h1>
                </div>
                <div class="col-md-6 text-right">
                    <c:choose>
                        <c:when test="${contractPageDto.contract.status== 'INACTIVE'}">
                            <button type="button" class="btn btn-sm btn-success" id="unBlockContractButton">Activate
                                contract
                            </button>
                        </c:when>
                        <c:when test="${contractPageDto.contract.status == 'ACTIVE'}">
                            <button type="button" class="btn btn-sm btn-primary" id="deactivateContractButton">
                                Deactivate contract
                            </button>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="row pt-5">
                <div class="col-md-4">
                    <div class="card text-white bg-info mb-4">
                        <div class="card-body">
                            <h5 class="card-title">Wallet balance: ${contractPageDto.contract.user.balance} €</h5>
                            <a href="/customerPanel/addFunds" class="btn btn-outline-light btn-sm">Add funds</a>
                        </div>
                    </div>
                    <div class="card">
                        <h5 class="card-header">
                            Info
                        </h5>
                        <div class="card-body">
                            <dl>
                                <dt>Tariff</dt>
                                <dd>${contractPageDto.contract.tariff.id} : ${contractPageDto.contract.tariff.name}</dd>
                                <dt>Price</dt>
                                <dd>${contractPageDto.contract.price} €</dd>
                            </dl>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Active options
                        </h5>
                        <table class="table table-hover contracts-table" id="contractCurrentOptions">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Cost of add</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${contractPageDto.contract.activeOptions}" var="option">
                                <tr class="move-row">
                                    <td>${option.id}</td>
                                    <td>${option.name}</td>
                                    <td>${option.price}</td>
                                    <td>${option.costofadd}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="card-footer">
                            <c:if test="${contractPageDto.contract.status == 'ACTIVE'}">
                                <p id="contractDelOption"><a class="btn btn-outline-primary btn-sm">Delete selected
                                    options</a></p>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Options available for tariff
                        </h5>
                        <table class="table table-hover contracts-table" id="contractAvailableOptions">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Cost of add</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${contractPageDto.contract.tariff.availableOptions}"
                                       var="availableOption">
                                <tr class="move-row">
                                    <td>${availableOption.id}</td>
                                    <td>${availableOption.name}</td>
                                    <td>${availableOption.price}</td>
                                    <td>${availableOption.costofadd}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="card-footer">
                            <c:if test="${contractPageDto.contract.status == 'ACTIVE'}">
                                <p id="contractAddOption"><a class="btn btn-outline-primary btn-sm">Add selected options</a></p>
                            </c:if>
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
                        <table class="table table-hover " id="addContractTariffs">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Price</th>
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
                        <div class="card-footer">
                            <c:if test="${contractPageDto.contract.status == 'ACTIVE'}">
                                <p id="switchTariff"><a class="btn btn-outline-primary btn-sm">Swith to this tariff</a></p>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Available options
                        </h5>
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