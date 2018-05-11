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
        <c:if test="${tariffPageDto.tariff.status == 'INACTIVE'}">
            <div class="alert alert-warning" role="alert">
                Tariff is inactive (archived).
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong> ${tariffPageDto.tariff.id} : ${tariffPageDto.tariff.name}</strong>
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover">
                            <tbody>
                            <tr>
                                <td>price</td>
                                <td>${tariffPageDto.tariff.price}</td>
                            </tr>
                            </tbody>
                        </table>
                        <c:choose>
                            <c:when test="${tariffPageDto.tariff.status == 'INACTIVE'}">
                                <button id="unArchiveTariff" type="button" class="btn btn-primary btn-sm btn-success">Unarchive tariff</button>
                                <button id="deleteTariff" type="button" class="btn btn-primary btn-sm btn-danger">Delete tariff</button>
                            </c:when>
                            <c:when test="${tariffPageDto.tariff.status == 'ACTIVE'}">
                                <button id="archiveTariff" type="button" class="btn btn-primary btn-sm btn-warning">Archive tariff</button>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Options available for tariff</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="tariffAddedOptions">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffPageDto.tariff.availableOptions}" var="availableOption">
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
                    <button type="button" class="btn btn-success" id="tariffDelOption">Delete selected option</button>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>All available options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="tariffAvailableOptions">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffPageDto.options}" var="option">
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
                    <button type="button" class="btn btn-success" id="tariffAddOption">Add selected option</button>
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