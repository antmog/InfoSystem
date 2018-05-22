<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-4">
<jsp:include page="navBar.jsp"/>
<main class="mt-4">
    <div class="container content mb-4">
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-md-6">
                    <h1 class="h2 mb-0">
                        Tariff registration form
                    </h1>
                </div>
            </div>
            <div class="row pt-5">
                <div class="col-md-4">
                    <div class="card mb-4">
                        <h5 class="card-header">
                            Tariff common data
                        </h5>
                        <div class="card-body">
                            <label class="col-md-3 control-lable">name</label>
                            <input type="text" id="name" class="form-control input-sm"/>
                            <label class="col-md-3 control-lable">price</label>
                            <input type="number" id="price" class="form-control input-sm"/>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="addTariff">Add Tariff</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Selected options
                        </h5>
                        <div class="card-body">
                            <table class="table" id="addTariffAddedOptions">
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
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="addTariffDelOption">Delete selected
                                option
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h5 class="card-header">
                            Available options
                        </h5>
                        <div class="card-body">
                            <table class="table" id="addTariffAvailableOptions">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${options}" var="option">
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
                        <div class="card-footer">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="addTariffAddOption">Add selected option</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>