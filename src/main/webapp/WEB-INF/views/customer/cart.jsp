<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-1">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content mt-1">
        <div class="row">
            <div class="col-md-4 mt-3">
                <div class="card mb-4 border-dark">
                    <h5 class="card-header">
                        ${user.firstName} ${user.lastName}
                    </h5>
                </div>
                <div class="card text-white bg-info mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Wallet balance: <label id="walletBalance">${user.balance} €</label></h5>
                        <div class="col-md-4"><a href="/customerPanel/addFunds" class="btn btn-outline-light btn-sm">
                            Add funds</a></div>
                        <div class="col-md-4"><p id="buy"><a class="btn btn-outline-light btn-sm">Buy all</a></p></div>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${empty sessionCart.options}">
                    <div class="col-md-8 mt-3">
                        <div class="alert alert-info" role="alert">
                            Cart is empty.
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-md-8 mt-3">
                        <div class="card mb-8">
                            <h5 class="card-header">
                                My Contracts
                            </h5>
                            <div class="card-body">
                                <table class="table table-hover contracts-table" id="cartTable">
                                    <thead>
                                    <tr>
                                        <th>Contract id</th>
                                        <th>Option id</th>
                                        <th>Option name</th>
                                        <th>Option price</th>
                                        <th>Option costofadd</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${sessionCart.optionsDto}" var="item">
                                        <c:forEach items="${item.value}" var="option">
                                            <tr>
                                                <td>${item.key}</td>
                                                <td>${option.id}</td>
                                                <td>${option.name}</td>
                                                <td>${option.price}</td>
                                                <td>${option.costofadd}</td>
                                                <td>
                                                    <button type="button" class="btn btn-sm btn-danger"
                                                            id="deleteFromCart">delete
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</main>
<script>
    var user_id = ${user.id};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>