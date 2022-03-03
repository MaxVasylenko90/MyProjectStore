<#include "security.ftlh">

<nav class="navbar navbar-expand-lg navbar-light bg-light mb-3" >
    <div class="container-fluid">
        <a class="navbar-brand" href="/">
            <img src="/static/logo.png" width="180">
        </a>
        <div class="collapse navbar-collapse d-flex justify-content-center" id="navbarSupportedContent">
            <ul class="navbar-nav  me-1 mb-2 mb-lg-0">
                <#if !isAdmin>
                    <b>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Каталог товаров
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="/categories/Пиньяты">Пиньяты</a></li>
                                <li><a class="dropdown-item" href="/categories/Игрушки">Игрушки</a></li>
                                <li><a class="dropdown-item" href="/categories/Воздушные шары">Воздушные шары</a></li>
                            </ul>
                        </li>
                    </b>
                    <b>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Доставка и оплата</a>
                        </li>
                    </b>
                    <b>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Контакты</a>
                        </li>
                    </b>
                <#else>
                    <b>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="/" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Каталог товаров
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="/categories/Пиньяты">Пиньяты</a></li>
                                <li><a class="dropdown-item" href="/categories/Игрушки">Игрушки</a></li>
                                <li><a class="dropdown-item" href="/categories/Телефоны">Телефоны</a></li>
                            </ul>
                        </li>
                    </b>
                    <b>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Доставка и оплата</a>
                        </li>
                    </b>
                    <b>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Контакты</a>
                        </li>
                    </b>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/user">Список пользователей</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/products/">Список товаров</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/categories">Список категорий</a>
                    </li>
                </#if>
            </ul>
        </div>
        <div class="mx-3">
            <a href="/cart">
                <img src="/static/cart.png" width="30" height="30">
            </a>
        </div>
        <div class="mx-3"><img src="/static/phone.png" width="20" height="20"> <b><a href="tel:+380506828175"><span
                            class="mini-contacts phone">+38 (050) 682 81 75</span></a></b>
        </div>
        <#if !(username == "")>
            <div>
                <form action="/logout" method="post">
                    <div class="navbar-text text-center">
                        <a href="/user/profile">Мой кабинет</a>
                    </div>
<#--                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
                    <div class="d-flex justify-content-center">
                        <button type="submit" class="btn btn-primary btn-sm">Выйти</button>
                    </div>
                </form>
            </div>
        <#else>
            <div>
                <a class="ml-3" href="/login">Войти</a> / <a href="/registration">Регистрация</a>
            </div>
        </#if>
    </div>
</nav>