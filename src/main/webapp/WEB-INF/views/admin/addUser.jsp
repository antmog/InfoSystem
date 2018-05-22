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
                User Registration Form
            </h1>
            <div class="row pt-5">
                <div class="col-md-6">
                    <form:form method="POST" modelAttribute="addUserDto" class="form-horizontal">
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
                            <label for="login">Login</label>
                            <form:input type="text" path="login" id="login" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="login" class="help-inline"/>
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
                            <label for="password">Password</label>
                            <form:input type="password" path="password" id="password" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="password" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="birthDate">Birth date</label>
                            <form:input type="date" path="birthDate" id="birthDate" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="birthDate" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="passport">Passport</label>
                            <form:input type="text" path="passport" id="passport" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="passport" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="mail">E-mail</label>
                            <form:input type="text" path="mail" id="mail" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="mail" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Role</label>
                            <form:select path="role" items="${roles}" multiple="true"
                                         class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="role" class="help-inline"/>
                            </div>
                        </div>
                        <input type="submit" value="Save" class="btn btn-success btn-sm"/> or
                        <a class="btn btn-danger btn-sm" href="/adminPanel/allUsers">Cancel</a>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <br>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>