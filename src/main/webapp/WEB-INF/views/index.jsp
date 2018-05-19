<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="ru">
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/"><i class="fas fa-phone-square"></i> Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasAnyRole('ADMIN')">
                    <li class="nav-item active">
                        <a class="nav-link" href="/lk">
                            Admin Panel
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('CUSTOMER')">
                    <li class="nav-item active">
                        <a class="nav-link" href="/lk">
                            User Panel
                        </a>
                    </li>
                </sec:authorize>
                <li class="nav-item">
                    <a class="nav-link" href="/logout">
                        <strong>${loggedinuser}</strong>, Log out
                    </a>
                </li>
            </sec:authorize>
        </ul>
    </div>
</nav>

<main role="main">

    <div class="jumbotron">
        <div class="container">
            <h1 class="display-4">Info-System</h1>
            <p>Cellular Carrier Information System</p>
            <sec:authorize var="loggedIn" access="isAuthenticated()"/>
            <c:choose>
                <c:when test="${loggedIn}">
                    <strong>WELCOME, dear ${loggedinuser}</strong>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-primary btn-lg" href="/login" role="button">Log In »</a>
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

    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h2>Tariffs</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor
                    mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna
                    mollis euismod. Donec sed odio dui. </p>
                <p><a class="btn btn-secondary" href="#" role="button">Подробнее &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>LK</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor
                    mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna
                    mollis euismod. Donec sed odio dui. </p>
                <p><a class="btn btn-secondary" href="#" role="button">Подробнее &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>Search</h2>
                <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula
                    porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
                    ut fermentum massa justo sit amet risus.</p>
                <p><a class="btn btn-secondary" href="#" role="button">Подробнее &raquo;</a></p>
            </div>
        </div>

        <hr>
    </div>

</main>
<jsp:include page="footer.jsp"/>
</body>
</html>