<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration Confirmation Page</title>
    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/index.css">

</head>
<body>

<jsp:include page="navBar.jsp"/>


<div class="alert alert-success lead">
    ${success}
</div>

<span class="well floatRight">
			Go to <a href="<c:url value='/adminPanel' />">Admin Panel</a>
		</span>

</body>

</html>