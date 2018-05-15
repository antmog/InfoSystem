<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                <div class="card mb-4 border-dark">
                    <h5 class="card-header">
                        ${user.firstName} ${user.lastName}
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover">
                            <tbody>
                            <tr class="editable">
                                <td>Money</td>
                                <td>${user.balance}</td>
                            </tr>
                            </tbody>
                        </table>
                        <button type="button" class="btn btn-primary btn-sm">TBD add funds</button>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card mb-8">
                    <h5 class="card-header">
                        My Contracts
                    </h5>
                    <div class="card-body">
                        <c:if test="${empty sessionCart.options}">
                            Empty cart.
                        </c:if>
                        ${sessionCart}
                        <table class="table table-hover contracts-table">
                            <thead>
                            <tr>
                                <th>Contract id</th>
                                <th>Option name</th>
                                <th>Option id</th>
                                <th>Option price</th>
                                <th>Option costofadd</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${sessionCart.options}" var="item">
                                <c:forEach items="${item.value}" var="option">
                                    <tr class="move-row">
                                        <td>${item.key}</td>
                                        <td>${option.name}</td>
                                        <td>${option.id}</td>
                                        <td>${option.price}</td>
                                        <td>${option.costOfAdd}</td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
                        <a href="#" class="card-link">buy and continue shopping</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</main>
<script>
    var user_id = ${user.id};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>