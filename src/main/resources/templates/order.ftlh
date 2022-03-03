<#import "parts/common.ftlh" as c>
<@c.page title="Оформление заказа">
    <div class="container">
        <form class="row g-3" action="/cart/order" method="post">
            <div class="row">
                <div class="col-md-6 my-5">
                    <div class="col-md-6">
                        <label class="form-label"><b>Имя</b></label>
                        <input type="text" name="name" class="form-control" value="${user.name}"
                               required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label"><b>Фамилия</b></label>
                        <input type="text" name="surname" class="form-control" value="${user.surname}" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label"><b>Телефон</b></label>
                        <input type="number" name="phone" class="form-control" value="${user.phone}" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label"><b>E-mail</b></label>
                        <input type="email" name="email" class="form-control mb-3" name="email" value="${user.username}" aria-label="Disabled input example" readonly required>
                        <p class="fs-6">(изменить email можно в личном кабинете)</p>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label"><b>Город</b></label>
                        <input type="text" name="city" class="form-control mb-3" value="${user.city!}" required>
                    </div>
                    <div>
                        <fieldset class="row mb-3" id="group1">
                            <legend class="col-form-label col-sm-2 pt-0"><b>Оплата: </b></legend>
                            <div class="col-sm-10">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="group1" id="gridRadios1"
                                           value="Наложенный платеж" checked>
                                    <label class="form-check-label" for="gridRadios1">
                                        Наложенный платеж
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="group1" id="gridRadios2"
                                           value="Перевод на карту">
                                    <label class="form-check-label" for="gridRadios2">
                                        Перевод на карту
                                    </label>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div>
                        <fieldset class="row mb-3" id="group2">
                            <legend class="col-form-label col-sm-2 pt-0"><b>Доставка: </b></legend>
                            <div class="col-sm-10">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="group2" id="gridRadios1"
                                           value="Самовывоз" checked>
                                    <label class="form-check-label" for="gridRadios1">
                                        Самовывоз
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="group2" id="gridRadios2"
                                           value="Новая почта">
                                    <label class="form-check-label" for="gridRadios2">
                                        Новая почта (укажите № отделения и город в примечании)
                                    </label>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div class="form-floating">
                        <textarea class="form-control" name="comment" placeholder="Leave a comment here"
                                  id="floatingTextarea2"
                                  style="height: 100px"></textarea>
                        <label for="floatingTextarea2">Комментарии к заказу: </label>
                    </div>
                </div>
                <div class="col-md-6 my-5 bg-light text-dark">
                    <h4>Ваш заказ:</h4>
                    <#list smallCarts as sm>
                        <p>${sm.product.name}  ${sm.quantity} шт. ${sm.subtotal} грн </p>
                    </#list>
                    <b>Итого: ${user.findLastCart().total} грн. </b>
                    <div class="my-3">
<#--                        <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                        <button type="submit" class="btn btn-success">Подтверждаю заказ</button>
                    </div>
                </div>
            </div>
        </form>
    </div>

</@c.page>