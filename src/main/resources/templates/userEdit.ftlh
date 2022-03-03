<#import "parts/common.ftlh" as c>

<@c.page title="Редактировать пользователя">
    <div class="container">
        <h2>Редактирование пользователя</h2>
        <form action="/user" method="post">
            <p><b>Имя: </b><input type="text" name="name" value="${user.name!}"></p>
            <p><b>Фамилия: </b> <input type="text" name="surname" value="${user.surname!}"></p>
            <p><b>E-mail: </b> <input type="email" name="username" value="${user.username!}"></p>
            <p><b>Телефон: </b> <input type="text" name="phone" value="${user.phone!}"></p>
            <p><b>Город: </b> <input type="text" name="city" value="${user.city!}"></p>
            <p><b>Роль: </b><#list roles as role>
                <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
            </#list></p>
            <input type="hidden" value="${user.id}" name="userId">
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}">-->
            <p><button type="submit">Изменить</button></p>
        </form>
    </div>
</@c.page>