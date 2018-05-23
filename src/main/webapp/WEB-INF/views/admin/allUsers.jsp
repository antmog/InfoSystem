<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-1">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content">
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-6">
                    <h1 class="h2 mb-0">Users</h1>
                </div>
                <div class="col-6">
                    <div class="row justify-content-end">
                        <div class="col-auto">
                            <a href="/adminPanel/addUser" class="btn btn-primary">Add user</a>
                        </div>
                        <div class="col-auto">
                            <form id="searchUserByPhoneNumberForm">
                                <div class="form-row justify-content-end">
                                    <div class="col-auto">
                                        <input id="searchUserByNumberInput" type="text" class="form-control mb-2"
                                               placeholder="Search by phone number">
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="btn btn-primary mb-2">
                                            <i class="fas fa-search search-icon"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive">
        <table class="table table-hover users-table">
            <thead>
            <tr>
                <th>Id</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Passport</th>
                <th>Address</th>
                <th>E-mail</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allUsersDto.entityDtoList}" var="user">
                <tr class="user-row">
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.passport}</td>
                    <td>${user.address}</td>
                    <td>${user.mail}</td>
                    <td>${user.status}</td>
                    <td><span class="badge badge-success">${user.status}</span></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item" id="prevPage"><a class="page-link">Previous</a></li>
                <c:forEach begin="1" end="${allUsersDto.pageCount}" varStatus="loop">
                    <c:if test = "${allUsersDto.pageNumber == loop.index }">
                        <li class="page-item active"><a class="page-link" href="/adminPanel/allUsers/${loop.index}">${loop.index}</a></li>
                    </c:if>
                    <c:if test = "${allUsersDto.pageNumber != loop.index }">
                        <li class="page-item"><a class="page-link" href="/adminPanel/allUsers/${loop.index}">${loop.index}</a></li>
                    </c:if>
                </c:forEach>
                <li class="page-item" id="nextPage"><a class="page-link">Next</a></li>
            </ul>
        </nav>
        <br>
    </div>
</main>

<script>
    var pageNumber = ${allUsersDto.pageNumber};
    var pageCount = ${allUsersDto.pageCount};
    var entities = "Users";
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>