<#macro pager url totalPages currentPage>
<#--    gt > 3; lt < 3-->
    <#if totalPages gt 6>
        <#assign
        pageNumber = currentPage
        firstPage = (pageNumber > 3)?then([1, -1], [1, 2])
        lastPage = (pageNumber < totalPages - 2)?then([-1, totalPages], [totalPages - 1, totalPages])
        bodyBefore = (pageNumber > 3 && pageNumber < totalPages)?then([pageNumber - 1],[])
        bodyAfter = (pageNumber > 1 && pageNumber < totalPages - 2)?then([pageNumber + 1],[])
        body = firstPage + bodyBefore + (pageNumber > 2 && pageNumber < totalPages - 1)?then([pageNumber],[]) + bodyAfter + lastPage
        >
    <#else>
        <#assign body = 1..totalPages>
    </#if>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <#if currentPage == 1>
                    <#else>
                        <a class="page-link" href="${url}page/${currentPage - 1}?sortField=${sortField}&sortDir=${sortDir}">Предыдущая</a>
                    </#if>
                </li>


                <#list body as p>
                    <#if p == currentPage>
                        <li class="page-item"><a class="page-link">${p}</a></li>
                    <#elseif p == -1>
                        <li class="page-item"><a class="page-link">...</a></li>
                    <#else>
                        <li class="page-item"><a class="page-link" href="${url}page/${p}?sortField=${sortField}&sortDir=${sortDir}">${p}</a></li>
                    </#if>
                </#list>


               <li class="page-item">
                    <#if currentPage== totalPages>
                    <#else>
                        <a class="page-link" href="${url}page/${currentPage + 1}?sortField=${sortField}&sortDir=${sortDir}">Следующая</a>
                    </#if>
                </li>
            </ul>
        </nav>
    </div>
</#macro>