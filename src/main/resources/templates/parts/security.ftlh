<#assign autorize = Session.SPRING_SECURITY_CONTEXT??>
<#if autorize>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        username = user.getUsername()
        isAdmin = user.isAdmin()
        id = user.getId()
        name = user.getName()
        surname = user.getSurname()
        >
<#else>
    <#assign
        username = ""
        isAdmin = false
        >
</#if>