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
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable">name</label>
                            <div class="col-md-7">
                                <input type="text" id="name" class="form-control input-sm"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable">price</label>
                            <div class="col-md-7">
                                <input type="text"  id="price" class="form-control input-sm"/>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="addTariff">Add Tariff</button>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Selected options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
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
                    </div>
                    <button type="button" class="btn btn-success" id="addTariffDelOption">Delete selected option</button>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Available options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
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
                                        <td>${option.costOfAdd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="addTariffAddOption">Add selected option</button>
                </div>
            </div>

        </div>
    </div>

</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>