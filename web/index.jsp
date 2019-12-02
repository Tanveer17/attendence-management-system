<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <style type="text/css">
      body{
        background-color: orange;
      }
    </style>
  </head>
  <body>
  <c:url var = "adminLink" value="servlet1">
    <c:param name="command" value="adminLogin"/>
    <c:param name="authority" value="Admin"/>
  </c:url>



  <div class="container" id="btns">


    <div class="row text-center">
      <div class="col-sm-3 col">

        <a href="${adminLink}" class="btn btn-primary btn-lg btn-block" >Admin</a>
      </div>
    </div>
    <div class="row text-center">
      <div class="col-sm-3 col">
        <a href="user/User.jsp" class="btn btn-success btn-lg btn-block" >User</a>
      </div>
    </div>
    <!--</form>-->
  </div>
  </div>
  </div>



  </body>
</html>