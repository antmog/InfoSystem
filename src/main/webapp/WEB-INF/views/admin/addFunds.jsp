<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="text-center">
<jsp:include page="navBar.jsp"/>
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column add-funds">
    <main role="main" class="inner cover">
        <p>Wallet balance: <label id="walletBalance">${adminFundsDto.amount}</label></p>
        <h1 class="cover-heading">Enter the amount you want to add.</h1>
        <div class="form-group">
            <p><input type="text" class="form-control" id="addFundsInput" placeholder="Amount"></p>
        </div>
            <button type="submit" class="btn btn-primary" id="addFunds">add funds</button>
    </main>
</div>
<script>
    var user_id = ${adminFundsDto.userId};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>