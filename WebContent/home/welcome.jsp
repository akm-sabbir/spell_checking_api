<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=devsice-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <title>SSO</title>

    <link rel="stylesheet" type="text/css" href="http://account.beta.doptor.gov.bd/sso/lib/style.css"/>
    <script type="text/javascript" src="http://account.beta.doptor.gov.bd/sso/lib/script.2.min.js"></script>

</head>
<body style="background:#d6dedd">

<nav class="navbar navbar-expand-lg navbar-light" style="color: #1d643b; background: green">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" style="font-weight: bold; color: #fff;" href="#">
                    SSO Dashboard
                    <span class="sr-only">(current)</span>
                    <span style="padding-left:5px; font-size:20px;cursor:pointer" onclick="openNav()">&#9776;</span>
                </a>
            </li>
        </ul>
        <div class="form-inline my-2 my-lg-0">
        
            <div class="align-div">
                <div class="mr-sm-2" style="padding-top: 15px">
                    <li class="dropdown dropdown-language" id="sso-widget"></li>
                </div>
                <div class="my-2 my-sm-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                           style="color: #fff; font-size: 13px; font-weight: bold">
                            <%
                                HttpSession httpSession = request.getSession();
                                String name = (String) httpSession.getAttribute("name");
                                String designation = (String) httpSession.getAttribute("designation");
                                String officeName = (String) httpSession.getAttribute("officeName");
                            %>
                            <%=name%> <%=designation%>
                            <br>
                            <%=officeName%>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" style="color: red" href="/ssologout">Logout</a>
                        </div>
                    </li>
                </div>
            </div>
            
        </div>
    </div>
</nav>

<div id="sso-sidenav" class="sidenav" style="background:#fff">
    <span style="padding-left:30px">ড্যাশবোর্ড</span>
    <a href="javascript:void(0)" style="font-size:20px;" class="closebtn" onclick="closeNav()">
        &times;
    </a>
    <a href="javascript:void(0)" style="font-size:15px;">ব্যবস্থাপনা -
        ১</a>
    <a href="javascript:void(0)" style="font-size:15px;">ব্যবস্থাপনা - ২</a>
    <a href="javascript:void(0)" style="font-size:15px;">ব্যবস্থাপনা - ৩</a>
</div>

<script>
    function openNav() {
        document.getElementById("sso-sidenav").style.width = "250px";
    }

    function closeNav() {
        document.getElementById("sso-sidenav").style.width = "0";
    }
</script>

</body>
</html>

<script>
    widget.init({
        "widgetColor": "light",
        "widgetSize": "20px",
        "appPermissionURL": 'http://119.148.4.20:8087/rootstocktest6/Widget'
    });
</script>


<style>
    li {
        list-style: none;
        margin: 0;
    }

    .align-div div {
        float: left;
        clear: none;
    }

    .sso-service-name {
        font-size: 14px;
    }

    /* Left side logo style */
    .sso-left-logo-box {
        margin: 10px;
    }

    .sso-left-logo {
        width: 45px;
        height: 45px;
    }

    /* Side bar style */

    .sidenav {
        height: 100%;
        width: 0;
        position: fixed;
        z-index: 1;
        top: 12%;
        left: 0;
        background-color: #111;
        overflow-x: hidden;
        transition: 0.5s;
        padding-top: 60px;
    }

    .sidenav a {
        padding: 8px 8px 8px 32px;
        text-decoration: none;
        font-size: 25px;
        color: #818181;
        display: block;
        transition: 0.3s;
    }

    .sidenav a:hover {
        color: #f1f1f1;
    }

    .sidenav .closebtn {
        position: absolute;
        top: 0;
        right: 25px;
        font-size: 36px;
        margin-left: 50px;
    }

    @media screen and (max-height: 450px) {
        .sidenav {
            padding-top: 15px;
        }

        .sidenav a {
            font-size: 18px;
        }
    }

</style>
