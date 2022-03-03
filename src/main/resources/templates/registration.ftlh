<#import "parts/common.ftlh" as c>

<@c.page title="Регистрация">
    <div class="container">
        <div class="mb-5"><h3><b>Регистрация</b></h3></div>
        <p class="text-danger">${alertMessage!}</p>
        <form method="post">
            <div class="col-sm-2 mb-3">
                <label class="form-label">Имя</label>
                <input type="text" class="form-control" name="name" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label">Фамилия</label>
                <input type="text" class="form-control" name="surname" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label">Телефон</label>
                <input type="text" class="form-control" name="phone" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label">Эл.почта</label>
                <input type="email" class="form-control" name="username" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label">Пароль</label>
                <input type="password" class="form-control" name="password" required/>
            </div>
            <div class="col-sm-2 mb-3">
                <label class="form-label">Повторите пароль</label>
                <input type="password" class="form-control" name="password2" required/>
            </div>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
        </form>
    </div>
</@c.page>