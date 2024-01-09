<#-- @ftlvariable name="" type="com.rds.observato.view.ResourcesView" -->
<h1>Resources</h1>


<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Name</th>
        <th scope="col">Skills</th>
    </tr>
    </thead>
    <tbody>
    <#list  resources as x>
        <tr>
            <th scope="row">
                <a hx-get="/api/resources/${x.id()}"
                   hx-headers='{"Accept": "text/html"}'
                   hx-target="#content"
                   placeholder="Search..."
                   class="nav-link active"
                   aria-current="page">${x.id()}</a>
            </th>
            <td>${x.name()}</td>
            <td>
                <#list  x.skills() as key, value>
                    <span class="badge text-bg-info">${key}:${value}</span>
                </#list>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
