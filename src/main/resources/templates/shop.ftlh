<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>

<@c.page title="Luadjo интернет-магазин">
    <div class="container">
        <div class="container my-3">
            <div class="dropdown">
                <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                    Сортировка
                </a>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <li><a class="dropdown-item" href="${url}page/1?sortField=price&sortDir=asc">По возрастанию цены</a></li>
                    <li><a class="dropdown-item" href="${url}page/1?sortField=price&sortDir=desc">По убыванию цены</a></li>
                    <li><a class="dropdown-item" href="${url}page/1?sortField=id&sortDir=desc">По новизне</a></li>
                </ul>
            </div>
            <div class="float-md-end pl-3" style="width: 15rem">
                <form class="d-flex" method="get" action="/search">
                    <input class="form-control me-2" name="filter" value="${filter!}">
                    <button class="btn btn-outline-primary" type="submit">Найти</button>
                </form>
            </div>
        </div>
        <br>
        <br>
        <div class="container my-3">
            <div class="row align-items-start">
                <#list productList as product>
                    <div class="col mb-3">
                        <div class="card text-center" style="width: 15rem;">
                            <a href="/products/${product.id}"><img src="/img/${product.getMainImage()!}"
                                                                   class="card-img-top"></a>
                            <div class="card-body">
                                <a href="/products/${product.id}" class="link-dark"><h5
                                            class="card-title">${product.name}</h5></a>
                                <p class="card-text">${product.price} грн.</p>
                                <form action="/smallcart/addToCart" method="post">
                                    <input type="hidden" value="${product.id}" name="productId">
<#--                                    <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                                    <button type="submit" class="btn btn-success">Купить</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
    <@p.pager url totalPages currentPage/>
</@c.page>


