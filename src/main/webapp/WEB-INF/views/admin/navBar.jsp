<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/"><i class="fas fa-phone-square"></i> Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="/adminPanel/allUsers/1">
                    All users
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/adminPanel/allContracts/1">
                    All contracts
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/adminPanel/allTariffs/1">
                    All tariffs
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/adminPanel/allOptions/1">
                    All options
                </a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    ${loggedinuser}
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/lk">Admin Panel</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/logout">Log out</a>
                </div>
            </li>
        </ul>
    </div>
</nav>
</html>
