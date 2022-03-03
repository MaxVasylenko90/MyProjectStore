<#import "parts/common.ftlh" as c>

<@c.page title="Войти">
    <div class="container">
        <div class="mb-5"><h3><b>Войти</b></h3></div>
        <p class="text-danger">${alertMessage!}</p>
        <p class="text-success">${message!}</p>
        <form method="post">
            <div class="col-sm-2 mb-3">
                <input type="email" class="form-control" name="username" placeholder="E-mail"/>
            </div>
            <div class="col-sm-2 mb-3">
                <input type="password" class="form-control" name="password" placeholder="Пароль"/>
            </div>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            <button type="submit" class="btn btn-primary">Войти</button>
        </form>
        <div><a href="/forgetPassword">Забыли пароль?</a></div>
    </div>
</@c.page>