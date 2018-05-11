<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<jsp:include page="navBar.jsp"/>


Adding option
<br/>
<form:form method="POST" modelAttribute="addTariffOptionDto" class="form-horizontal">

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="name">name</label>
            <div class="col-md-7">
                <form:input type="text" path="name" id="name" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="name" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable">price</label>
            <div class="col-md-7">
                <form:input type="text" path="price" id="price" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="price" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable">cost of add</label>
            <div class="col-md-7">
                <form:input type="text" path="costofadd" id="costofadd" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="costofadd" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <c:choose>
        <c:when test="${edit}">
            <input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a
                href="/adminPanel">Cancel</a>
        </c:when>
        <c:otherwise>
            <input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a
                href="/adminPanel">Cancel</a>
        </c:otherwise>
    </c:choose>
</form:form>
<jsp:include page="../footer.jsp"/>
</body>
</html>