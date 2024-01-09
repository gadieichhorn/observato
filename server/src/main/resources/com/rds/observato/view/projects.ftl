<#-- @ftlvariable name="" type="com.rds.observato.view.ProjectsView" -->
<h1>Projects</h1>


<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Name</th>
        <th scope="col">Description</th>
    </tr>
    </thead>
    <tbody>
    <#list  projects as x>
        <tr>
            <th scope="row">
                <a hx-get="/api/projects/${x.id()}"
                   hx-headers='{"Accept": "text/html"}'
                   hx-target="#content"
                   placeholder="Search..."
                   class="nav-link active"
                   aria-current="page">${x.id()}</a>
            </th>
            <td>${x.name()}</td>
            <td>${x.description()}</td>
        </tr>
    </#list>
    </tbody>
</table>
