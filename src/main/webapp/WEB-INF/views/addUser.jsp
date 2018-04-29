<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Registration Form</title>
    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/vendors/fontawesome-free-5.0.10/fontawesome-all.min.css">

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
            <li class="nav-item active">
                <a class="nav-link" href="/lk">
                    LK
                </a>
            </li>
            <li class="nav-item mr-4">
                <a class="nav-link" href="#">
                    Busket
                    <i class="fas fa-cart-arrow-down"></i>
                </a>
            </li>

            <sec:authorize access="isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link" href="/logout">
                        <strong>${loggedinuser}</strong>, Log out
                    </a>
                </li>
            </sec:authorize>
        </ul>
    </div>
</nav>


User Registration Form

<form:form method="POST" modelAttribute="user" class="form-horizontal">
    <form:input type="hidden" path="id" id="id"/>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="lastName">firstName</label>
            <div class="col-md-7">
                <form:input type="text" path="firstName" id="firstName" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="firstName" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>


    <div class="form-group col-md-12">
        <label class="col-md-3 control-lable" for="lastName">lastName</label>
        <div class="col-md-7">
            <form:input type="text" path="lastName" id="lastName" class="form-control input-sm"/>
            <div class="has-error">
                <form:errors path="lastName" class="help-inline"/>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="login">login</label>
            <div class="col-md-7">
                <form:input type="text" path="login" id="login" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="login" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="address">address</label>
            <div class="col-md-7">
                <form:input type="text" path="address" id="address" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="address" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="password">Password</label>
            <div class="col-md-7">
                <form:input type="password" path="password" id="password" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="password" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="birthDate">birthDate</label>
            <div class="col-md-7">
                <form:input type="text" path="birthDate" id="birthDate" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="birthDate" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="passport">passport</label>
            <div class="col-md-7">
                <form:input type="text" path="passport" id="passport" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="passport" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="mail">eMail</label>
            <div class="col-md-7">
                <form:input type="text" path="mail" id="mail" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="mail" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="role">Roles</label>
            <div class="col-md-7">
                <form:select path="role" items="${roles}" multiple="true"
                             class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="role" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="form-actions floatRight">
            <c:choose>
                <c:when test="${edit}">
                    <input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a
                        href="<c:url value='/adminPanel' />">Cancel</a>
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a
                        href="<c:url value='/adminPanel' />">Cancel</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</form:form>

</body>
</html>