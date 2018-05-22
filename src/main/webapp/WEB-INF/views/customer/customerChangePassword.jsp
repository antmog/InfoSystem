<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body>
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container">
        <div class="pt-4 pb-4">
            <h1 class="h2 mb-0">
                Chaning password
            </h1>
            <div class="row pt-5">
                <div class="col-md-6">
                    <form:form method="POST" modelAttribute="changePasswordDto" class="form-horizontal" id="changePasswordForm">
                        <form:input type="hidden" path="userId" id="id"/>
                        <div class="form-group">
                            <label for="password">Enter current password</label>
                            <form:input type="password" path="password" id="password" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="password" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password">Enter new password</label>
                            <form:input type="password" path="newPassword" id="newPassword" class="form-control input-sm"/>
                            <div class="has-error">
                                <form:errors path="newPassword" class="help-inline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password">Repeat new password</label>
                            <input type="password" id="newPasswordRepeat" class="form-control input-sm"/>
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