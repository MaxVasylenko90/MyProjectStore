<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>

<@c.page title="Категории товаров">
    <div class="container">
        <h3>Существующие категории товаров:</h3>
        <@p.pager url totalPages currentPage/>
        <#list categoryList as category>
            <p>- ${category.name}</p>
        </#list>
        <div>
            <a class="btn btn-primary my-3" data-bs-toggle="collapse" href="#collapseExample" role="button"
               aria-expanded="false"
               aria-controls="collapseExample">
                Добавить категорию
            </a>
            <div class="collapse" id="collapseExample">
                <form method="post" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col">
                            <input type="text" class="form-control my-3 mr-3" name="name"
                                   placeholder="Название категории"
                                   required/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
<#--                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
                            <button type="submit" class="btn btn-success my-3 mr-3">Создать</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <@p.pager url totalPages currentPage/>
    </div>
</@c.page>
