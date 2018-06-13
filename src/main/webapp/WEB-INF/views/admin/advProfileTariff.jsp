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
    <div class="container content">
        <div class="pt-4 pb-4">
            <div class="row">
                <div class="col-md-6">
                    <h1 class="h2 mb-0">
                        Tariff ${advProfileTariffDto.tariffName} options for profile
                        <span class="badge badge-secondary">Id: ${advProfileTariffDto.advProfileId}</span>
                        Current img: ${advProfileTariffDto.img}
                    </h1>
                </div>
                <div class="col-md-6">
                    <div class="form-row justify-content-end">
                        <div class="col-auto">
                            <button id="advProfileTariffSubmit" class="btn btn-outline-primary btn-sm">Submit changes
                            </button>
                            <button id="advProfileTariffDelete" class="btn btn-outline-danger btn-sm">Delete tariff from profile</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row pt-5">
                <div class="col-md-12">
                    <div id="myCarousel" class="carousel slide" data-ride="carousel">
                        <div id="carouselNav">
                            <ol class="carousel-indicators">
                                <c:forEach items="${advProfileTariffDto.imgs}" var="img" varStatus="loop">
                                    <li data-target="#myCarousel" data-slide-to="${loop.index}"></li>
                                </c:forEach>
                            </ol>
                        </div>
                        <div class="carousel-inner" id="carouselInner">
                            <c:forEach items="${advProfileTariffDto.imgs}" var="img" varStatus="loop">
                                <div class="carousel-item" id="carouselItem" data-name="${img}">
                                    <img class="first-slide"
                                         src="/static/images/adv/${img}.jpg"
                                         alt="First slide">
                                    <div class="container">
                                        <div class="carousel-caption text-left">
                                            <h1>${img}</h1>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
</main>
<jsp:include page="../footer.jsp"/>
<script>
    var tariffId = ${advProfileTariffDto.tariffId};
    var advProfileId = ${advProfileTariffDto.advProfileId};
</script>
</body>
</html>