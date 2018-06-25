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
    <div class="container content mb-4">
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-md-6">
                    <h1 class="h2 mb-0">
                        ${tariffOptionPageDto.tariffOption.name}
                        <span class="badge badge-secondary">Id: ${tariffOptionPageDto.tariffOption.id}</span>
                    </h1>
                </div>
            </div>
            <div class="row pt-5">
                <div class="col-md-4">
                    <div class="card mb-4">
                        <h5 class="card-header">
                            Option info
                        </h5>
                        <div class="card-body">
                            <dl>
                                <dt>Cost of add</dt>
                                <dd>${tariffOptionPageDto.tariffOption.costofadd}</dd>
                                <dt>Price</dt>
                                <dd>${tariffOptionPageDto.tariffOption.price} €</dd>
                                <dt>Price</dt>
                                <dd>${tariffOptionPageDto.tariffOption.description}</dd>
                            </dl>
                        </div>
                        <div class="card-footer">
                            <button id="deleteOption" type="button" class="btn btn-primary btn-sm btn-danger">Delete
                                option
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Related options
                        </h5>
                        <div class="card-body"><div class="table-responsive">
                            <table class="table" id="optionAddedOptionsRelated">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Cost of add</th>
                                </tr>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffOptionPageDto.tariffOption.relatedTariffOptions}"
                                           var="availableOption">
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
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="optionDelOptionRelated">Delete selected
                                option
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            All available options
                        </h5>
                        <div class="card-body">
                            <div class="table-responsive">
                            <table class="table" id="optionAvailableOptionsRelated">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Cost of add</th>
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
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="optionAddOptionRelated">Add selected
                                option
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Excluding options
                        </h5>
                        <div class="card-body">
                            <div class="table-responsive">
                            <table class="table" id="optionAddedOptionsExcluding">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Cost of add</th>
                                </tr>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffOptionPageDto.tariffOption.excludingTariffOptions}"
                                           var="availableOption">
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
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="optionDelOptionExcluding">Delete selected
                                option
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            All available options
                        </h5>
                        <div class="card-body">
                            <div class="table-responsive">
                            <table class="table" id="optionAvailableOptionsExcluding">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Cost of add</th>
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
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="optionAddOptionExcluding">Add selected
                                option
                            </button>
                        </div>
                    </div>
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