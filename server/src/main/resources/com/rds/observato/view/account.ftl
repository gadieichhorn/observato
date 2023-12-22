<#-- @ftlvariable name="" type="com.rds.observato.view.AccountView" -->
<h1>Account: ${account.id()}</h1>

<p>Name: ${account.name()}</p>
<button type="button" class="btn btn-primary" hx-put="/api/accounts/view/${account.id()}">Use this
    account ${account.id()}</button>
