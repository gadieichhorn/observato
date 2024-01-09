<#macro page_head>
    <title xmlns="http://www.w3.org/1999/html">Observato</title>
</#macro>

<#macro page_body>
    <h1>Home</h1>
</#macro>

<#macro page_nav>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="/api/home">Observato</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a hx-get="/api/accounts"
                           hx-headers='{"Accept": "text/html"}'
                           hx-target="#content"
                           hx-indicator="#spinner"
                           class="nav-link active"
                           aria-current="page">Accounts</a>
                    </li>
                    <li class="nav-item">
                        <a hx-get="/api/tasks"
                           hx-headers='{"Accept": "text/html"}'
                           hx-target="#content"
                           hx-indicator="#spinner"
                           class="nav-link active"
                           aria-current="page">Tasks</a>
                    </li>
                    <li class="nav-item">
                        <a hx-get="/api/resources"
                           hx-headers='{"Accept": "text/html"}'
                           hx-target="#content"
                           hx-indicator="#spinner"
                           class="nav-link active"
                           aria-current="page">Resources</a>
                    </li>
                    <li class="nav-item">
                        <a hx-get="/api/users"
                           hx-headers='{"Accept": "text/html"}'
                           hx-target="#content"
                           hx-indicator="#spinner"
                           class="nav-link active"
                           aria-current="page">User</a>
                    </li>
                    <li class="nav-item">
                        <a hx-get="/api/projects"
                           hx-headers='{"Accept": "text/html"}'
                           hx-target="#content"
                           hx-indicator="#spinner"
                           class="nav-link active"
                           aria-current="page">Projects</a>
                    </li>
                    <li class="nav-item">
                        <a hx-get="/api/schedule"
                           hx-headers='{"Accept": "text/html"}'
                           hx-target="#content"
                           hx-indicator="#spinner"
                           class="nav-link active"
                           aria-current="page">Schedule</a>
                    </li>
                </ul>
                <form class="d-flex" role="search">
                    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </div>
    </nav>
</#macro>

<#macro display_page>
    <!doctype html>
    <html>
    <head>
        <@page_head/>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
              crossorigin="anonymous">
        <link rel="stylesheet" href="/styles.css">
    </head>
    <body>
    <div class="container">
        <@page_nav/>
        <div id="spinner" class="htmx-indicator spinner-border" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <div class="container" id="content">
            <@page_body/>
        </div>
        <script src="https://unpkg.com/htmx.org@1.9.10"
                integrity="sha384-D1Kt99CQMDuVetoL1lrYwg5t+9QdHe7NLX/SoJYkXDFfX37iInKRy5xLSi8nO7UC"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
                crossorigin="anonymous"></script>
    </div>
    </body>
    </html>
</#macro>