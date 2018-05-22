<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-2">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content">
        <div class="pt-4 pb-4">
            <h1 class="h2 mb-0">
                Edit user info
            </h1>
            <div class="row pt-5">
                <div class="col-md-6">
                    <form:form method="POST" modelAttribute="customerEditUserDto" class="form-horizontal">
                        <form:input type="hidden" path="id" id="id"/>
                        <div class="form-group">
                            <label for="firstName">First name</label>
                            <form:input type="text" path="firstName" id="firstName" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="firstName" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last name</label>
                            <form:input type="text" path="lastName" id="lastName" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="lastName" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="address">Address</label>
                            <form:input type="text" path="address" id="address" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="address" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="mail">E-mail</label>
                            <form:input type="" path="mail" id="mail" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="mail" class="help-inline"/>
                            </div>
                        </div>
                        <input type="submit" value="Save" class="btn btn-success btn-sm"/> or
                        <a class="btn btn-danger btn-sm" href="/customerPanel">Cancel</a>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>