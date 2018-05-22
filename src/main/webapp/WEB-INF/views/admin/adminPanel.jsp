<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-1">
<jsp:include page="navBar.jsp"/>
<main class="mt-4 ml-4 mr-4">
    <main class="mt-4">
        <div class="container content">
            <div class="pt-4 pb-4">
                <div class="row">
                    <div class="col-md-6">
                        <h1 class="h2 mb-0">
                            ${userDto.login}
                            <span class="badge badge-secondary">Id: ${userDto.id}</span>
                        </h1>
                    </div>
                </div>
                <div class="row pt-5">
                    <div class="col-md-4">
                        <div class="card">
                            <h5 class="card-header">
                                Info
                            </h5>
                            <div class="card-body">
                                <dl>
                                    <dt>First name</dt>
                                    <dd>${userDto.firstName}</dd>
                                    <dt>Last name</dt>
                                    <dd>${userDto.lastName}</dd>
                                    <dt>Address</dt>
                                    <dd>${userDto.address}</dd>
                                    <dt>Birth date</dt>
                                    <dd>${userDto.birthDate}</dd>
                                    <dt>E-mail</dt>
                                    <dd>${userDto.mail}</dd>
                                    <dt>Passport</dt>
                                    <dd>${userDto.passport}</dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>