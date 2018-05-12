<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <div class="row">

            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong> ${tariffOption.id} : ${tariffOption.name}</strong>
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover">
                            <tbody>
                            <tr class="contract-row">
                                <td>price</td>
                                <td>${tariffOption.price}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>cost of add</td>
                                <td>${tariffOption.costOfAdd}</td>
                            </tr>
                            </tbody>
                        </table>
                        <button id="deleteOption" type="button" class="btn btn-primary btn-sm btn-danger">Delete
                            option
                        </button>
                    </div>
                </div>
                <br/>
            </div>

        </div>
        <div class="row">
123//todorwr
        </div>
    </div>
</main>
<script>
    var option_id = ${tariffOption.id};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>