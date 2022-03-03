<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>
<@c.page title="Список товаров">
    <div class="container">
        <div>
            <a class="btn btn-primary my-3" data-bs-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="false"
               aria-controls="collapseExample">
                Добавить товар
            </a>
            <div class="collapse" id="collapseExample">
                <form method="post" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col">
                            <input type="text" class="form-control my-3 mr-3" name="name" placeholder="Название товара"
                                   required/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-floating">
                        <textarea class="form-control" name="description" id="floatingTextarea2" style="height: 100px"
                                  required></textarea>
                            <label for="floatingTextarea2">Описание товара:</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <input type="number" class="form-control my-3 mr-3" name="quantity" placeholder="Количество"
                                   required/>
                        </div>
                        <div class="col">
                            <input type="number" class="form-control my-3 mr-3" name="price" placeholder="Цена"
                                   required/>
                        </div>
                        <div class="col">
                            <select class="form-select" name="category" aria-label="Default select example">
                                <option selected>Выбрать категорию</option>
                                <#list categories as category>
                                    <option>${category.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-3">
                                    Главное изображение товара:
                                    <input class="form-control my-3 mr-3" type="file" name="mainImage">
                                </div>
                                <div class="col-md-9"></div>
                            </div>
                        </div>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-3">
                                    Галерея товара:
                                    <input class="form-control my-3 mr-3" type="file" name="images" multiple>
                                </div>
                                <div class="col-md-9"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
<#--                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
                        <div class="col">
                            <button type="submit" class="btn btn-success my-3 mr-3">Добавить</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="float-md-end pl-3" style="width: 15rem">
                <form class="d-flex" method="get" action="/products/search">
                    <input class="form-control me-2" name="filter" value="${filter!}">
                    <button class="btn btn-outline-primary" type="submit">Найти</button>
                </form>
            </div>
        </div>
        <@p.pager url totalPages currentPage/>
        <table class="table-primary">
            <thead>
            <tr>
                <th></th>
                <th><a href="${url}page/1?sortField=name&sortDir=${reverseSortDir}">Название</a></th>
                <th><a href="${url}page/1?sortField=description&sortDir=${reverseSortDir}">Описание</a></th>
                <th><a href="${url}page/1?sortField=category&sortDir=${reverseSortDir}">Категория</a></th>
                <th><a href="${url}page/1?sortField=quantity&sortDir=${reverseSortDir}">Количество</a></th>
                <th><a href="${url}page/1?sortField=price&sortDir=${reverseSortDir}">Цена</a></th>
            </tr>
            </thead>
            <tbody>

            <#list productList as product>
                <tr>
                    <td><a href="/products/${product.id}"><img src="/img/${product.getMainImage()!}"
                                                               style="width: 3rem;"></a></td>
                    <td><a href="/products/${product.id}">${product.name}</a></td>
                    <td>${product.description}</td>
                    <td>${product.category.name}</td>
                    <td>${product.quantity} шт.</td>
                    <td>${product.price} грн.</td>
                    <td><a href="/products/edit/${product.id}">Редактировать</a></td>
                    <td>
                        <form action="/products/delete" method="post">
                            <input type="hidden" value="${product.id}" name="productId">
<#--                            <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                            <button type="submit">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>

            </tbody>
        </table>
        <a href="/">На главную</a>
        <div class="my-3">
            <@p.pager url totalPages currentPage/>
        </div>
    </div>
</@c.page>