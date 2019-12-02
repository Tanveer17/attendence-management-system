<%--
  Created by IntelliJ IDEA.
  User: tanveer
  Date: 11/27/19
  Time: 3:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css" >
    <style type="text/css">
        body{
            background-color: orange;
        }
    </style>
</head>
<body>
<c:url var = "loginLink" value="/servlet1">
    <c:param name="command" value="userLogin"/>
    <c:param name="authority" value="User"/>
</c:url>


<div class="container" id="btns">

          <div class="row text-center">
            <div class="col-sm-3 col">
                <a href="${loginLink}" class="btn btn-primary btn-lg btn-block">Login</a>
            </div>
        </div>
        <div class="row text-center">
            <div class="col-sm-3 col">
                <a href="../registerationForm/form.html" class="btn btn-danger btn-lg btn-block">Register</a>
            </div>
        </div>

</div>
</div>
</div>

</body>
</html>
