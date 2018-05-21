<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-6">
                    <h1 class="h2 mb-0">Contracts</h1>
                </div>
                <div class="col-6">
                    <div class="row justify-content-end">
                        <div class="col-auto">
                            <a href="/adminPanel/addContract" class="btn btn-primary">Add contract</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <table class="table table-hover contracts-table">
            <thead>
            <tr>
                <th>Id</th>
                <th>User id</th>
                <th>Phone Number</th>
                <th>Options count </th>
                <th>Tariff</th>
                <th>Price</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allContractsDto.entityDtoList}" var="contract">
                <tr class="contract-row">
                    <td>${contract.id}</td>
                    <td>${contract.user.id}</td>
                    <td>${contract.phoneNumber}</td>
                    <td>${fn:length(contract.activeOptions)}</td>
                    <td>${contract.tariff.name}</td>
                    <td>${contract.price}</td>
                    <td><span class="badge badge-success">${contract.status}</span></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item" id="prevPage"><a class="page-link">Previous</a></li>
                <c:forEach begin="1" end="${allContractsDto.pageCount}" varStatus="loop">
                    <c:if test = "${allContractsDto.pageNumber == loop.index }">
                        <li class="page-item active"><a class="page-link" href="/adminPanel/allContracts/${loop.index}">${loop.index}</a></li>
                    </c:if>
                    <c:if test = "${allContractsDto.pageNumber != loop.index }">
                        <li class="page-item"><a class="page-link" href="/adminPanel/allContracts/${loop.index}">${loop.index}</a></li>
                    </c:if>
                </c:forEach>
                <li class="page-item" id="nextPage"><a class="page-link">Next</a></li>
            </ul>
        </nav>
    </div>
</main>

<script>
    var pageNumber = ${allContractsDto.pageNumber};
    var pageCount = ${allContractsDto.pageCount};
    var entities = "Contracts";
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>