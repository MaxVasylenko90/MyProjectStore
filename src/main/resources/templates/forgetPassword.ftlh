<#import "parts/common.ftlh" as c>

<@c.page title="Забыли пароль?">
    <div class="container">
        <p class="text-danger">${message!}</p>
        <h3>Восстановление пароля</h3>
        <form method="post">
            <p>Введите e-mail:</p>
            <input type="email" name="email" required/>
            <#--        <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            <button type="submit" class="btn btn-primary">Восстановить пароль</button>
        </form>
    </div>
</@c.page>