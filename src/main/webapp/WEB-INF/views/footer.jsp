<%--
  Created by IntelliJ IDEA.
  User: antmog
  Date: 06.05.2018
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
</head>
<body>
<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/vendors/jquery/jquery.tabletojson.min.js" defer></script>
<script src="/static/js/global.js" defer></script>
<sec:authorize access="isAuthenticated()">
    <sec:authorize access="hasAnyRole('ADMIN')">
        <script src="/static/js/admin.js" defer></script>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('CUSTOMER')">
        <script src="/static/js/customer.js" defer></script>
    </sec:authorize>
</sec:authorize>
<script src="/static/vendors/bootstrap-4.1.0/js/bootstrap.min.js" defer></script>
<script src="/static/vendors/bootstrap-notify/bootstrap-notify.min.js" defer></script>
<script src="/static/vendors/datatables/jquery.dataTables.min.js" defer></script>
</body>
</html>
