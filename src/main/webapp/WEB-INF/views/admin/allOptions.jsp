<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-2">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content">
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-6">
                    <h1 class="h2 mb-0">Options</h1>
                </div>
                <div class="col-6">
                    <div class="row justify-content-end">
                        <div class="col-auto">
                            <a href="/adminPanel/addOption" class="btn btn-primary">Add option</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive">
        <table class="table table-hover options-table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Cost of add</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allTariffOptionsDto.entityDtoList}" var="option">
                <tr class="option-row">
                    <td>${option.id}</td>
                    <td>${option.name}</td>
                    <td>${option.price}</td>
                    <td>${option.costofadd}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item" id="prevPage"><a class="page-link">Previous</a></li>
                <c:forEach begin="1" end="${allTariffOptionsDto.pageCount}" varStatus="loop">
                    <c:if test = "${allTariffOptionsDto.pageNumber == loop.index }">
                        <li class="page-item active"><a class="page-link" href="/adminPanel/allOptions/${loop.index}">${loop.index}</a></li>
                    </c:if>
                    <c:if test = "${allTariffOptionsDto.pageNumber != loop.index }">
                        <li class="page-item"><a class="page-link" href="/adminPanel/allOptions/${loop.index}">${loop.index}</a></li>
                    </c:if>
                </c:forEach>
                <li class="page-item" id="nextPage"><a class="page-link">Next</a></li>
            </ul>
        </nav>
        <br>
    </div>
</main>
<script>
    var pageNumber = ${allTariffOptionsDto.pageNumber};
    var pageCount = ${allTariffOptionsDto.pageCount};
    var entities = "Options";
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>