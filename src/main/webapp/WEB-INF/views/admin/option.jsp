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
                        <strong> ${tariffOptionPageDto.tariffOption.id} : ${tariffOptionPageDto.tariffOption.name}</strong>
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover">
                            <tbody>
                            <tr class="contract-row">
                                <td>price</td>
                                <td>${tariffOptionPageDto.tariffOption.price}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>cost of add</td>
                                <td>${tariffOptionPageDto.tariffOption.costofadd}</td>
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
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Related options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="optionAddedOptionsRelated">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffOptionPageDto.tariffOption.relatedTariffOptions}" var="availableOption">
                                    <tr class="move-row">
                                        <td>${availableOption.id}</td>
                                        <td>${availableOption.name}</td>
                                        <td>${availableOption.price}</td>
                                        <td>${availableOption.costofadd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="optionDelOptionRelated">Delete selected option</button>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>All available options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="optionAvailableOptionsRelated">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffOptionPageDto.options}" var="option">
                                    <tr class="move-row">
                                        <td>${option.id}</td>
                                        <td>${option.name}</td>
                                        <td>${option.price}</td>
                                        <td>${option.costofadd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="optionAddOptionRelated">Add selected option</button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Excluding options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="optionAddedOptionsExcluding">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffOptionPageDto.tariffOption.excludingTariffOptions}" var="availableOption">
                                    <tr class="move-row">
                                        <td>${availableOption.id}</td>
                                        <td>${availableOption.name}</td>
                                        <td>${availableOption.price}</td>
                                        <td>${availableOption.costofadd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="optionDelOptionExcluding">Delete selected option</button>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>All available options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="optionAvailableOptionsExcluding">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffOptionPageDto.options}" var="option">
                                    <tr class="move-row">
                                        <td>${option.id}</td>
                                        <td>${option.name}</td>
                                        <td>${option.price}</td>
                                        <td>${option.costofadd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="optionAddOptionExcluding">Add selected option</button>
                </div>
            </div>
        </div>
    </div>
    <table style="visibility:hidden" class="table" id="parseTable">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>price</th>
            <th>costofadd</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</main>
<script>
    var option_id = ${tariffOptionPageDto.tariffOption.id};
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>