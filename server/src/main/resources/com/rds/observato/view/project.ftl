<#-- @ftlvariable name="" type="com.rds.observato.view.ProjectView" -->
<h1>Project: ${project.id()}</h1>
<p>Name: ${project.name()}</p>
<p>Description: ${project.description()}</p>

<button type="button" class="btn btn-primary" hx-put="/api/projects/">Edit project ${project.id()}</button>
<button type="button" class="btn btn-danger" hx-delete="/account"
        hx-confirm="Are you sure you wish to delete project: ${project.name()}">
    Delete project ${project.id()}
</button>