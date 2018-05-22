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
<form:form method="POST" modelAttribute="addTariffOptionDto" class="form-horizontal" id="addOptionSubmit">

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="name">Name</label>
            <div class="col-md-7">
                <form:input type="text" path="name" id="addOptionName" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="name" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable">Price</label>
            <div class="col-md-7">
                <form:input type="number" path="price" id="addOptionPrice" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="price" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable">Cost of add</label>
            <div class="col-md-7">
                <form:input type="number" path="costofadd" id="addOptionCostOfAdd" class="form-control input-sm"/>
                <div class="has-error">
                    <form:errors path="costofadd" class="help-inline"/>
                </div>
            </div>
        </div>
    </div>

    <input type="submit" value="Add" class="btn btn-primary btn-sm"/> or <a href="/adminPanel">Cancel</a>

</form:form>
<jsp:include page="../footer.jsp"/>
</body>
</html>