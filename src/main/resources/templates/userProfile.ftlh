<#import "parts/common.ftlh" as c>

<@c.page title="Мой кабинет">

    <div class="container">
        <p class="text-success">${message!}</p>
        <p class="text-danger">${alertMessage!}</p>
        <div>
            <a class="btn btn-primary my-3" data-bs-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="false"
               aria-controls="collapseExample">
                Мои заказы
            </a>
            <div class="collapse" id="collapseExample">
                <form method="post" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col">
                            <#list user.order as order>
                                <h5>Заказ №${order.id}</h5>
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th scope="col">Название</th>
                                        <th scope="col">Количество</th>
                                        <th scope="col">Стоимость</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list order.cart.cartSmall as cs>
                                        <tr>
                                            <td><a href="/products/${cs.product.id}">${cs.product.name}</a></td>
                                            <td>${cs.quantity}</td>
                                            <td>${cs.subtotal}</td>
                                        </tr>
                                    </#list>
                                    </tbody>
                                </table>
                                <div class="d-flex justify-content-end my-3">
                                    <b>ОБЩАЯ СУММА: ${order.cart.total} грн </b>
                                </div>
                            </#list>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <h3 class="my-3">Редактировать мои данные: </h3>
        <form method="post">
            <div class="col-sm-2 mb-3">
                <label class="form-label"><b>Имя</b></label>
                <input type="text" class="form-control" name="name" value="${user.name}" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label"><b>Фамилия</b></label>
                <input type="text" class="form-control" name="surname" value="${user.surname}" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label"><b>Телефон</b></label>
                <input type="text" class="form-control" name="phone" value="${user.phone}" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label"><b>Эл.почта</b></label>
                <input type="email" class="form-control" name="username" value="${user.username}" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label"><b>Город</b></label>
                <input type="text" class="form-control" name="city" value="${user.city!}"/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label"><b>Изменить пароль</b></label>
                <input type="password" class="form-control" name="password"/>
                <label class="form-label">Повторите пароль</label>
                <input type="password" class="form-control" name="password2"/>
            </div>
            <input type="hidden" name="user" value="${user.id}">
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            <button type="submit" class="btn btn-primary">Изменить данные</button>
        </form>
    </div>
</@c.page>