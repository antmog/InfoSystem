<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-1">
<jsp:include page="navBar.jsp"/>
<main class="mt-4 ml-4 mr-4">
    <main class="mt-4">
        <div class="container content">
            <br>
            <c:if test="${adminPanelDto.advProfileDtoList.get(0).status == 'INACTIVE'}">
                <div id="profileStatus" class="alert alert-warning" role="alert">
                    Profile is inactive.
                </div>
            </c:if>
            <c:if test="${adminPanelDto.advProfileDtoList.get(0).status == 'ACTIVE'}">
                <div id="profileStatus" class="alert alert-success" role="alert">
                    Profile is active.
                </div>
            </c:if>
            <div class="pt-4 pb-4">
                <div class="row">
                    <div class="col-md-4">
                        <h1 class="h2 mb-0">
                            ${adminPanelDto.userDto.login}
                            <span class="badge badge-secondary">Id: ${adminPanelDto.userDto.id}</span>
                        </h1>
                    </div>
                    <div class="col-md-4">
                        <h1 class="h2 mb-0">
                            Advertisment profiles
                        </h1>
                    </div>
                </div>
                <div class="row pt-5">
                    <div class="col-md-4">
                        <div class="card">
                            <h5 class="card-header">
                                Info
                            </h5>
                            <div class="card-body">
                                <dl>
                                    <dt>First name</dt>
                                    <dd>${adminPanelDto.userDto.firstName}</dd>
                                    <dt>Last name</dt>
                                    <dd>${adminPanelDto.userDto.lastName}</dd>
                                    <dt>Address</dt>
                                    <dd>${adminPanelDto.userDto.address}</dd>
                                    <dt>E-mail</dt>
                                    <dd>${adminPanelDto.userDto.mail}</dd>
                                    <dt>Passport</dt>
                                    <dd>${adminPanelDto.userDto.passport}</dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card">
                            <h5 class="card-header">
                                <div class="adv">
                                    <c:forEach items="${adminPanelDto.advProfileDtoList}" var="profile"
                                               varStatus="loop">
                                        <c:choose>
                                            <c:when test="${loop.index == 0}">
                                                <label class="advProfiles add-tariff-table-selected"
                                                       data-id="${loop.index+1}">${profile.name}</label>
                                            </c:when>
                                            <c:otherwise>
                                                <label class="advProfiles"
                                                       data-id="${loop.index+1}">${profile.name}</label>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                            </h5>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table " id="advProfileTable">
                                        <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Name</th>
                                            <th>Image</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${adminPanelDto.advProfileDtoList[0].advProfileTariffsList}"
                                                   var="tariff">
                                            <tr class="move-row">
                                                <td>${tariff.tariffId}</td>
                                                <td>${tariff.tariffName}</td>
                                                <td>${tariff.img}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="row">
                                    <button id="addAdvProfileTariff" class="btn btn-outline-primary btn-sm">Add tariff
                                    </button>
                                    <button visibility="hidden" id="advProfileActivate"
                                            class="btn btn-outline-primary btn-sm">Activate profile
                                    </button>
                                </div>
                                <c:if test="${adminPanelDto.advProfileDtoList.get(0).status == 'INACTIVE'}">
                                    <script>
                                        document.getElementById("advProfileActivate").style.visibility = "visible";
                                    </script>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>