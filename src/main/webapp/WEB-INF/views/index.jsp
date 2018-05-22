<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="ru">
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>
<jsp:include page="globalNavBar.jsp"/>
<main role="main">
    <%@ include file="/static/vendors/particle/index.html" %>
    <div class="container">
        <div class="row">
            <div class="col-md-12 div-with-link">
                <h1 class="display-4">Info-System</h1>
                <p>Cellular Carrier Information System</p>
                <sec:authorize var="loggedIn" access="isAuthenticated()"/>
                <c:choose>
                    <c:when test="${loggedIn}">
                        <strong>WELCOME, dear ${loggedinuser}</strong>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary" href="/login" role="button"> Log In  »</a>
                    </c:otherwise>
                </c:choose>
                <sec:authorize access="isAuthenticated()">
                    <sec:authorize access="hasAnyRole('ADMIN')">
                        <a href="/lk">
                            Admin Panel
                        </a>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('CUSTOMER')">
                        <a href="/lk">
                            User Panel
                        </a>
                    </sec:authorize>
                </sec:authorize>
            </div>
        </div>
        <div class="row">
            <c:forEach items="${tariffDtoList}" var="tariffDto">
                <div class="col-md-4">
                    <h3>${tariffDto.name}</h3>
                    <p>${tariffDto.price}</p>
                    <p><a class="btn btn-secondary" href="/lk" role="button">Learn more &raquo;</a></p>
                </div>
            </c:forEach>
        </div>

        <hr>
    </div>

</main>
<jsp:include page="footer.jsp"/>
</body>
</html>