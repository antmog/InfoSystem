<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="text-center font-1">
<jsp:include page="navBar.jsp"/>
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column add-funds">
    <main role="main" class="inner cover content">
        <div class="col-md-12">
            <br>
            <p>Wallet balance: <label id="walletBalance">${userFundsDto.amount} €</label></p>
            <h3 class="cover-heading">Enter the amount you want to add.</h3>
            <div class="form-group">
                <p><input type="number" class="form-control" id="addFundsInput" placeholder="Amount"></p>
            </div>
            <p><button type="submit" class="btn btn-primary" id="addFunds">add funds</button></p>
            <br>
        </div>
    </main>
</div>
<script>
    var user_id = ${userFundsDto.userId};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>