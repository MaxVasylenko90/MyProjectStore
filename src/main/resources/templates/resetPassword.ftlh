<#import "parts/common.ftlh" as c>

<@c.page title="Восстановление пароля">
    <div class="container">
        <h3>Восстановление пароля</h3>
        <p class="text-danger">${passwordError!}</p>
        <form method="post">
            <div>Введите новый пароль:</div>
            <div><input type="password" name="password" required/></div>
            <p></p>
            <div>Повторите новый пароль:</div>
            <div><input type="password" name="password2" required/></div>
            <#--        <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            <div class="mt-3">
                <button type="submit" class="btn btn-primary">Готово</button>
            </div>
        </form>
    </div>
</@c.page>