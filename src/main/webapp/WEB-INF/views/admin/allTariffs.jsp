<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-3">

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container content">
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-6">
                    <h1 class="h2 mb-0">Tariffs</h1>
                </div>
                <div class="col-6">
                    <div class="row justify-content-end">
                        <div class="col-auto">
                            <a href="/adminPanel/addTariff" class="btn btn-primary">Add tariff</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <table class="table table-hover tariffs-table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Options count</th>
                <th>Price</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allTariffsDto.entityDtoList}" var="tariff">
                <tr class="tariff-row">
                    <td>${tariff.id}</td>
                    <td>${tariff.name}</td>
                    <td>${fn:length(tariff.availableOptions)}</td>
                    <td>${tariff.name}</td>
                    <td>${tariff.price}</td>
                    <td><span class="badge badge-success">${tariff.status}</span></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item" id="prevPage"><a class="page-link">Previous</a></li>
                <c:forEach begin="1" end="${allTariffsDto.pageCount}" varStatus="loop">
                    <c:if test = "${allTariffsDto.pageNumber == loop.index }">
                        <li class="page-item active"><a class="page-link" href="/adminPanel/allTariffs/${loop.index}">${loop.index}</a></li>
                    </c:if>
                    <c:if test = "${allTariffsDto.pageNumber != loop.index }">
                        <li class="page-item"><a class="page-link" href="/adminPanel/allTariffs/${loop.index}">${loop.index}</a></li>
                    </c:if>
                </c:forEach>
                <li class="page-item" id="nextPage"><a class="page-link">Next</a></li>
            </ul>
        </nav>
        <br>
    </div>
</main>

<script>
    var pageNumber = ${allTariffsDto.pageNumber};
    var pageCount = ${allTariffsDto.pageCount};
    var entities = "Tariffs";
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>