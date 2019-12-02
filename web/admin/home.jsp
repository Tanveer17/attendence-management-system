<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="icon" type="image/png" href="/web_war_exploded/assets/img/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Attendence Management System</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />


    <link href="/web_war_exploded/assets/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Animation library for notifications   -->
    <link href="/web_war_exploded/assets/css/animate.min.css" rel="stylesheet"/>

    <!--  Light Bootstrap Table core CSS    -->
    <link href="/web_war_exploded/assets/css/light-bootstrap-dashboard.css?v=1.4.0" rel="stylesheet"/>


    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="/web_war_exploded/assets/css/demo.css" rel="stylesheet" />


    <!--     Fonts and icons     -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
    <link href="/web_war_exploded/assets/css/pe-icon-7-stroke.css" rel="stylesheet" />
</head>
<body>

<%
    boolean authorized = (boolean) (session.getAttribute("AUTHORIZED") == null? false:  session.getAttribute("AUTHORIZED"));

    if(!authorized){
        response.sendRedirect("index.jsp");
    }


%>

<div class="wrapper">
    <div class="sidebar" data-color="purple" data-image="assets/img/sidebar-5.jpg">

    <!--   you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple" -->


    	<div class="sidebar-wrapper">
            <div class="logo">
                <a href="http://www.creative-tim.com" class="simple-text">
                    Attendence Management System
                </a>
            </div>
            <ul class="nav">
                <li>
                    <a href="/web_war_exploded/admin/home.jsp">
                        <i class=""></i>
                        <p id="pp">Home</p>
                    </a>
                </li>

                <li>
                    <c:url var="studentLink" value="/servlet1">
                        <c:param name="command" value="Show Students"/>
                    </c:url>
                    <a href="${studentLink}">
                        <i class=""></i>
                        <p id="p">Students</p>
                    </a>
                </li>
                <li>
                    <c:url var="studentAttendenceLink" value="/servlet1">
                        <c:param name="command" value="Students Attendence"/>
                    </c:url>
                    <a href="${studentAttendenceLink}">
                        <i class=""></i>
                        <p >Student Attendence</p>
                    </a>
                </li>
                <li>
                    <a href="admin/report_of_user.jsp">
                        <i class=""></i>
                        <p>Create Report</p>
                    </a>
                </li>
                <li>
                    <c:url var="leavesLink" value="servlet1">
                        <c:param name="command" value="Leave Requests"/>
                    </c:url>
                    <a href="admin/report_of_user.jsp">
                        <i class=""></i>
                        <p>Leave Requests</p>
                    </a>
                </li>



            </ul>
    	</div>
    </div>

    <div class="main-panel">
        <%@include file="navigations.jsp" %>


        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="header">
                                <h4 class="title" style="color:red;">${BUG.name}</h4>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>
</div>


</body>


    <!--   Core JS Files   -->
<!--   Core JS Files   -->
<script src="/web_war_exploded/assets/js/jquery.3.2.1.min.js" type="text/javascript"></script>
<script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>

<!--  Charts Plugin -->
<script src="/web_war_exploded/assets/js/chartist.min.js"></script>

<!--  Notifications Plugin    -->
<script src="/web_war_exploded/assets/js/bootstrap-notify.js"></script>

<!--  Google Maps Plugin    -->
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

<!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
<script src="/web_war_exploded/assets/js/light-bootstrap-dashboard.js?v=1.4.0"></script>

<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->


</html>
