<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>

<@c.page title="Список пользователей">
    <div class="container">
        <div class="float-md-end pl-3" style="width: 15rem">
            <form class="d-flex" method="get" action="/user/search">
                <input class="form-control me-2" name="filter" value="${filter!}">
                <button class="btn btn-outline-primary" type="submit">Найти</button>
            </form>
        </div>
        <div class="my-3">
            <@p.pager url totalPages currentPage/>
        </div>
        <table>
            <thead>
            <tr>
                <th><a href="${url}page/1?sortField=id&sortDir=${reverseSortDir}">Id</a></th>
                <th><a href="${url}page/1?sortField=name&sortDir=${reverseSortDir}">Имя</a></th>
                <th><a href="${url}page/1?sortField=surname&sortDir=${reverseSortDir}">Фамилия</a></th>
                <th><a href="${url}page/1?sortField=username&sortDir=${reverseSortDir}">E-mail</a></th>
                <th><a href="${url}page/1?sortField=phone&sortDir=${reverseSortDir}">Телефон</a></th>
                <th><a href="${url}page/1?sortField=city&sortDir=${reverseSortDir}">Город</a></th>
                <th><a href="${url}page/1?sortField=active&sortDir=${reverseSortDir}">Активный</a></th>
                <th><a href="${url}page/1?sortField=roles&sortDir=${reverseSortDir}">Role</a></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list userList as user>
                <tr>
                    <td>${user.id!}</td>
                    <td>${user.name!} </td>
                    <td>${user.surname!} </td>
                    <td>${user.username!} </td>
                    <td>${user.phone!} </td>
                    <td>${user.city!}</td>
                    <td>${user.active?c} </td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">Редактировать</a></td>
                    <td>
                        <form action="/user/delete" method="post">
                            <input type="hidden" value="${user.id}" name="userId">
<#--                            <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                            <button type="submit">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <a href="/">На главную</a>
        <div class="my-3">
            <@p.pager url totalPages currentPage/>
        </div>
    </div>
</@c.page>