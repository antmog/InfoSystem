<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<!-- ... -->
<title>Info-System</title>
<link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/static/css/style.css">
<link rel="stylesheet"
      href="/static/vendors/fontawesome-free-5.0.10/web-fonts-with-css/css/fontawesome-all.min.css">
<sec:authorize access="isAuthenticated()">
    <sec:authorize access="hasAnyRole('ADMIN')">

    </sec:authorize>
    <sec:authorize access="hasAnyRole('CUSTOMER')">

    </sec:authorize>
</sec:authorize>
</html>
