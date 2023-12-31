<#-- @ftlvariable name="" type="com.rds.observato.view.ScheduleView" -->
<h1>Schedule</h1>
<h3>Signup Form</h3>
<form hx-post="/contact">
    <div hx-target="this" hx-swap="outerHTML">
        <label>Email Address</label>
        <input name="email" hx-post="/contact/email" hx-indicator="#ind">
        <img id="ind" src="/img/bars.svg" class="htmx-indicator"/>
    </div>
    <div class="form-group">
        <label>First Name</label>
        <input type="text" class="form-control" name="firstName">
    </div>
    <div class="form-group">
        <label>Last Name</label>
        <input type="text" class="form-control" name="lastName">
    </div>
    <button class="btn btn-default">Submit</button>
</form>