<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="header.jsp"/>
    <link rel="stylesheet" type="text/css" href="/static/css/404.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/"><i class="fas fa-phone-square"></i> Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasAnyRole('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="/lk">
                            Admin Panel
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('CUSTOMER')">
                    <li class="nav-item">
                        <a class="nav-link" href="/lk">
                            User Panel
                        </a>
                    </li>
                </sec:authorize>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                            ${loggedinuser}
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="/logout">Log out</a>
                    </div>
                </li>
            </sec:authorize>
        </ul>
    </div>
</nav>
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column add-funds">
    <main role="main" class="inner cover">
        <div><img src="/static/images/telegram.png" height="100" width="100" id="myButton"></div>
    </main>
</div>
<jsp:include page="footer.jsp"/>
<script src="/static/js/404.js" defer></script>
</body>
</html>
