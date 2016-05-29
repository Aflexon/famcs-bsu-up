<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Login in chat</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <style type="text/css">
        .modal-footer {   border-top: 0px; }
    </style>
</head>
<body >

<div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>-->
                <h1 class="text-center">Login</h1>
            </div>
            <div class="modal-body">
                <form class="form col-md-12 center-block" method="post" action="/login">
                    <div class="form-group <%= request.getAttribute("error") ==  null ? "" : "has-error" %>">
                        <input
                            type="text"
                            name="login"
                            class="form-control input-lg"
                            placeholder="Login"
                            <% if(request.getAttribute("login") != null) { %>
                            value="<%= request.getAttribute("login") %>"
                            <% } %>
                        >
                    </div>
                    <div class="form-group <%= request.getAttribute("error") ==  null ? "" : "has-error" %>">
                        <input type="password" name="password" class="form-control input-lg" placeholder="Password">
                        <% if(request.getAttribute("error") != null) { %>
                            <span class="help-block"><%= (String)request.getAttribute("error") %></span>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-lg btn-block">Login</button>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="pull-right">
                    <p class="text-muted">&copy; 2016 Aleksey Afanasenko</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type='text/javascript' src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

</body>
</html>