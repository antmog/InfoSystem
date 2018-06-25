<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-4">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content mb-4">
        <c:if test="${tariffPageDto.tariff.status == 'INACTIVE'}">
            <br>
            <div class="alert alert-warning" role="alert">
                Tariff is inactive (archived).
            </div>
        </c:if>
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-md-6">
                    <h1 class="h2 mb-0">
                        ${tariffPageDto.tariff.name}
                        <span class="badge badge-secondary">Id: ${tariffPageDto.tariff.id}</span>
                    </h1>
                </div>
            </div>
            <div class="row pt-5">
                <div class="col-md-4">
                    <div class="card mb-4">
                        <h5 class="card-header">
                            Tariff info
                        </h5>
                        <div class="card-body">
                            <dl>
                                <dt>Price</dt>
                                <dd>${tariffPageDto.tariff.price} €</dd>
                            </dl>
                            <dl>
                                <dt>Description</dt>
                                <dd>${tariffPageDto.tariff.description}</dd>
                            </dl>
                            <c:choose>
                                <c:when test="${tariffPageDto.tariff.status == 'INACTIVE'}">
                                    <button id="unArchiveTariff" type="button" class="btn btn-sm btn-success">Unarchive
                                        tariff
                                    </button>
                                    <button id="deleteTariff" type="button" class="btn btn-sm btn-danger">Delete
                                        tariff
                                    </button>
                                </c:when>
                                <c:when test="${tariffPageDto.tariff.status == 'ACTIVE'}">
                                    <button id="archiveTariff" type="button" class="btn btn-sm btn-primary">Archive
                                        tariff
                                    </button>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Options available for tariff
                        </h5>
                        <div class="card-body">
                            <div class="table-responsive">
                            <table class="table" id="tariffAddedOptions">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Cost of add</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffPageDto.tariff.availableOptions}" var="availableOption">
                                    <tr class="move-row">
                                        <td>${availableOption.id}</td>
                                        <td>${availableOption.name}</td>
                                        <td>${availableOption.price}</td>
                                        <td>${availableOption.costofadd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="tariffDelOption">Delete
                                selected option
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            All available options
                        </h5>
                        <div class="card-body">
                            <div class="table-responsive">
                            <table class="table" id="tariffAvailableOptions">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Cost of add</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffPageDto.options}" var="option">
                                    <tr class="move-row">
                                        <td>${option.id}</td>
                                        <td>${option.name}</td>
                                        <td>${option.price}</td>
                                        <td>${option.costofadd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="tariffAddOption">Add
                                selected option
                            </button>
                        </div>
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
    var tariff_id = ${tariffPageDto.tariff.id};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>