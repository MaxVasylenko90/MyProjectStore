<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h3>Заказ №${order.id} :</h3>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Название</th>
            <th></th>
            <th scope="col">Количество</th>
            <th></th>
            <th scope="col">Стоимость</th>
        </tr>
        </thead>
        <tbody>
        <#list smallCart as sm>
            <tr>
                <td>${sm.product.name}</td>
                <td></td>
                <td>${sm.quantity} шт.</td>
                <td></td>
                <td>${sm.subtotal} грн.</td>
            </tr>
        </#list>
        </tbody>
    </table>
    <p></p>
    <p><b>Общая сумма: ${user.findPreviousLastCart().total} грн </b></p>
    <p></p>
    <div><b>Данные для доставки:</b></div>
    <p></p>
    <div><b>ФИО:</b> ${user.name} ${user.surname}</div>
    <div><b>Телефон:</b> ${user.phone}</div>
    <div><b>E-mail:</b> ${user.username}</div>
    <div><b>Город:</b> ${user.city}</div>
    <div><b>Комментарий:</b> ${order.comment!}</div>
    <div><b>Доставка: </b>${order.delivery}</div>
    <div><b>Оплата: </b>${order.payment}</div>
</div>
</body>
</html>