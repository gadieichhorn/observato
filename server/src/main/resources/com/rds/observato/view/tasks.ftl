<#-- @ftlvariable name="" type="com.rds.observato.view.TasksView" -->
<h1>Tasks</h1>

<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Name</th>
        <th scope="col">Description</th>
        <th scope="col">Skills</th>
    </tr>
    </thead>
    <tbody>
    <#list  tasks as x>
        <tr>
            <th scope="row">
                <a hx-get="/api/tasks/view/${x.id()}"
                   hx-target="#content"
                   placeholder="Search..."
                   class="nav-link active"
                   aria-current="page">${x.id()}</a>
            </th>
            <td>${x.name()}</td>
            <td>${x.description()}</td>
            <td>
                <#list  x.skills() as key, value>
                    <span class="badge text-bg-info">${key}:${value}</span>
                </#list>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
