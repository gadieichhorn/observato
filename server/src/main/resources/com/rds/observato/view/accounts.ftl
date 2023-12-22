<#-- @ftlvariable name="" type="com.rds.observato.view.AccountsView" -->
<h1>Accounts</h1>

<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">Account</th>
        <th scope="col">Role</th>
    </tr>
    </thead>
    <tbody>
    <#list accounts as x>
        <tr>
            <th scope="row">
                <a hx-get="/api/accounts/view/${x.account()}"
                   hx-target="#content"
                   class="nav-link active"
                   aria-current="page">${x.account()}</a>
            </th>
            <td>${x.role()}</td>
        </tr>
    </#list>
    </tbody>
</table>
