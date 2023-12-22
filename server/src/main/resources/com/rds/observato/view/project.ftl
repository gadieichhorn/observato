<#-- @ftlvariable name="" type="com.rds.observato.view.ProjectView" -->

<#include "index.ftl">

<#macro page_head>
    <title>Project: ${project.id()}</title>
</#macro>

<#macro page_body>
    <h1>Project: ${project.name()}</h1>
    <p>${project.description()}</p>
    <button type="button" class="btn btn-danger" hx-delete="/account"
            hx-confirm="Are you sure you wish to delete project: ${project.name()}">
        Delete project ${project.id()}
    </button>
</#macro>

<@display_page/>
