<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-3">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content">
        <div class="pt-4 pb-4">
            <h1 class="h2 mb-0">
                Add option form
            </h1>
            <div class="row pt-5">
                <div class="col-md-6">
                    <form:form method="POST" modelAttribute="addTariffOptionDto" class="form-horizontal" id="addOptionSubmit">
                        <div class="form-group">
                            <label for="name">Option name</label>
                            <form:input type="text" path="name" id="addOptionName" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="name" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="price">Option price</label>
                            <form:input type="number" path="price" id="addOptionPrice" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="price" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="costofadd">Cost of add</label>
                            <form:input type="number" path="costofadd" id="addOptionCostOfAdd" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="costofadd" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description">Option description</label>
                            <form:input type="text" path="description" id="addOptionDescription" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="description" class="help-inline"/>
                            </div>
                        </div>
                        <input type="submit" value="Add" class="btn btn-success btn-sm"/> or <a href="/adminPanel/allOptions/">Cancel</a>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>