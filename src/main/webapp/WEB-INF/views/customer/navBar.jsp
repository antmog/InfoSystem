<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/"><i class="fas fa-phone-square"></i> Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="/lk">
                    User Panel
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/customerPanel/cart">
                    Cart
                    <span class="p1 fa-stack fa-2x has-badge" data-count="${sessionCart.count}" id="cartItemsCount">
                        <i class="p3 fa fa-shopping-cart fa-stack-1x xfa-inverse"></i>
                    </span>
                </a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    ${loggedinuser}
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/lk">User Panel</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/logout">Log out</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

</body>
</html>
