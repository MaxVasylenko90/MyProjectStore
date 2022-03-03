<#import "parts/common.ftlh" as c>
<@c.page title="Редактировать товар">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div class="md-6">
                    <h2>Редактирование товара</h2>
                </div>
                <form action="/products/edit" method="post" enctype="multipart/form-data">
                    <table class="my-3">
                        <tr>
                            <td><b>Название: </b><input type="text" style="width:635px;" name="name"
                                                        value="${product.name}"></td>
                        </tr>
                        <tr>
                            <td><b>Описание: </b><textarea class="form-control" name="description"
                                                           id="floatingTextarea2"
                                                           style="height: 300px; width:635px;">${product.description}</textarea>
                        </tr>
                        <tr>
                            <td><b>Количество: </b><input type="number" name="quantity" value="${product.quantity}">
                            </td>
                        </tr>
                        <tr>
                            <td><b>Цена: </b><input type="number" name="price" value="${product.price}"></td>
                        </tr>
                        <tr>
                            <td><b>Категория: </b>
                                <select class="form-select" name="category" aria-label="Default select example">
                                    <option selected>${product.category.name}</option>
                                    <#list categories as category>
                                        <option>${category.name}</option>
                                    </#list>
                                </select>
                            </td>
                        </tr>
                    </table>
                    <div class="bg-success p-2 text-dark bg-opacity-25">
                        <td><b>Главное изображение товара:</b></td>
                    </div>
                    <div class="bg-success p-2 text-dark bg-opacity-25">
                        <div>
                            <img src="/img/${product.getMainImage()!}" style="width: 10rem;">
                        </div>
                        <div>
                            <b>Изменить:</b>
                            <input class="form-control my-6 mr-6" type="file" name="mainImage">
                            <input type="hidden" value="${product.id}" name="productId">
<#--                            <input type="hidden" namn" name="_csrf" value="${_csrf.token}">-->
                        </div>
                    </div>
                    <div class="bg-success p-2 text-dark bg-opacity-25"></div>
                    <div class="bg-success p-2 text-dark bg-opacity-25">
                        <div><b>Галерея товара:</b></div>
                    </div>
                    <div class="bg-success p-2 text-dark bg-opacity-25">
                        <table>
                            <tr>
                                <#list product.getNonMainImages() as image>
                                    <form action="/products/image/delete" method="post">
                                        <td><img src="/img/${image.getFileName()}" style="width: 4rem;"></td>
                                    </form>
                                </#list>
                            </tr>
                        </table>
                    </div>
                    <div class="bg-success p-2 text-dark bg-opacity-25">
                        <b>Добавить новые картинки:</b>
                        <input class="form-control my-6 mr-6" type="file" name="images" multiple>
                        <input type="hidden" value="${product.id}" name="productId">
<#--                        <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                    </div>
                    <div class="my-5">
                        <button type="submit" class="btn btn-success">Применить изменения</button>
                    </div>
                </form>
            </div>
            <div class="col-md-6 my-5 bg-light text-dark">
                <b>Редактировать галерею товара:</b>
                <#list product.getNonMainImages() as image>
                    <form action="/products/image/delete" method="post">
                        <div><img src="/img/${image.getFileName()}" style="width: 7rem;">
                            <input type="hidden" value="${product.id}" name="productId">
                            <input type="hidden" value="${image.id}" name="imageId">
<#--                            <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                            <button type="submit">Удалить</button>
                        </div>
                    </form>
                </#list>
            </div>
        </div>
    </div>
</@c.page>
