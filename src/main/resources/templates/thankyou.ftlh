<#import "parts/common.ftlh" as c>
<@c.page title="Спасибо за Ваш заказ!">
    <div class="container">
        <h3>Заказ №${order.id} оформлен успешно!</h3>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Название</th>
                <th scope="col">Количество</th>
                <th scope="col">Стоимость</th>
            </tr>
            </thead>
            <tbody>
            <#list smallCart as sm>
                <tr>
                    <td>${sm.product.name}</td>
                    <td>${sm.quantity}</td>
                    <td>${sm.subtotal}</td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="d-flex justify-content-end my-3">
            <b>ОБЩАЯ СУММА: ${user.findPreviousLastCart().total} грн </b>
        </div>
    </div>
</@c.page>