<#import "parts/common.ftlh" as c>

<@c.page title="Корзина">
    <div class="container">
        <table class="table" id="basket">
            <thead>
            <h2><p class="text-center">Мой заказ:</p></h2>
            <tr>
                <th></th>
                <th>Название</th>
                <th>Цена</th>
                <th>Количество</th>
                <th>Подытог</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list smallCarts?sort_by("id") as sm>
                <tr>
                    <td><a href="/products/${sm.product.id}"><img src="/img/${sm.product.getMainImage()}"
                                                                  style="width: 3rem;"></a></td>
                    <td><a href="/products/${sm.product.id}">${sm.product.name}</a></td>
                    <td class="price">${sm.product.price} грн.</td>
                    <td>
                        <form action="/smallcart/refresh" method="post">
                            <input type="number" value="${sm.quantity}" min="1" max="100" name="userQuantity">
                            <input type="hidden" name="productId" value="${sm.product.id}">
<#--                            <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                            <button type="submit" class="btn btn-info">Обновить</button>
                        </form>
                    </td>
                    <td class="subtotal">${sm.subtotal} грн.</td>
                    <td>
                        <form action="/cart/delete" method="post">
                            <input type="hidden" name="cartSmallId" value="${sm.id}">
<#--                            <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                            <button type="submit">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="my-3">
            <b>Итого: ${cart.total!} грн.</b>
        </div>
        <a href="/cart/order" class="btn btn-success" role="button">Оформить</a>
    </div>
</@c.page>
