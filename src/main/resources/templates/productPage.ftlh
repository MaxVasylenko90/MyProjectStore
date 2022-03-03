<#import "parts/common.ftlh" as c>

<@c.page title="${product.name}">
    <link rel="stylesheet" type="text/css" href="/css/productPage.css">
<#--    <div class="clearfix">-->
    <div class="container">
        <h1><p class="text-center">${product.name}</p></h1>
        <div class="col">
            <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0"
                            class="active" aria-current="true" aria-label="Slide 1"></button>
                    <#list count as c>
                        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${c}"
                                aria-label="Slide 2"></button>
                    </#list>
                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="/img/${start}" class="d-block w-100">
                    </div>
                    <#list images as image>
                        <div class="carousel-item">
                            <img src="/img/${image.fileName}" class="d-block w-100">
                        </div>
                    </#list>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <div class="col">
            <p>
            <h5><b>Цена: ${product.price} грн.</b></h5>
            </p>
            Категория: <a href="/categories/${product.category.name}">${product.category.name}</a>
            <p>
                Артикул: 000${product.id}
            </p>
            <p>
            <form action="/smallcart/addToCart" method="post">
                <input type="hidden" value="${product.id}" name="productId">
<#--                <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                <button type="submit" class="btn btn-success">Купить</button>
            </form>
            </p>
            <p>
                <b>ОПИСАНИЕ:</b>
            </p>
            <p>
                ${product.description}
            </p>
        </div>
    </div>
<#--    </div>-->
</@c.page>