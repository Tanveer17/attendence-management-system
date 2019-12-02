<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>${role} Login</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="loginAssets/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="loginAssets/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="loginAssets/loginAssetsfonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="loginAssets/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="loginAssets/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="loginAssets/vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="loginAssets/css/util.css">
	<link rel="stylesheet" type="text/css" href="loginAssets/css/main.css">
<!--===============================================================================================-->
</head>
<body>
<%
//	String authority = (String) session.getAttribute("authority");
//	boolean notAuthorized = ((Boolean) request.getAttribute("NOT_AUTHORIZED")) == null ? false : ((boolean) request.getAttribute("NOT_AUTHORIZED"));
//	request.setAttribute("AUTHORITY",authority);
//	request.setAttribute("NOT_AUTHORIZED",notAuthorized);

	String authority =(String) session.getAttribute("AUTHORITY");
	request.setAttribute("AUTHORITY",authority);

//	if(authority == null){
//		response.sendRedirect("form.html");
//	}
%>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div class="login100-pic js-tilt" data-tilt>
					<img src="loginAssets/images/img-01.png" alt="IMG">
				</div>

				<form action="user" method="POST" class="login100-form validate-form">
					<input type="hidden" name="AUTHORITY" value="${AUTHORITY}">
					<span class="login100-form-title">
						${AUTHORITY} Login
					</span>
					<span>


						<c:if test="${NOT_AUTHORIZED}">
							<label for="" style="color: red">invalid username or password</label>
		  				</c:if>
						</span>

					<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
						<input class="input100" type="email" name="email" placeholder="Email">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-envelope" aria-hidden="true"></i>
						</span>
					</div>

					<div class="wrap-input100 validate-input" data-validate = "Password is required">
						<input class="input100" type="password" name="pass" placeholder="Password">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-lock" aria-hidden="true"></i>
						</span>
					</div>
					
					<div class="container-login100-form-btn">
						<input type="submit" value="Login"  class="login100-form-btn"/>


					</div>

					<div class="text-center p-t-12">
						<span class="txt1">
							Forgot
						</span>
						<a class="txt2" href="#">
							Username / Password?
						</a>
					</div>

					
				</form>
			</div>
		</div>
	</div>
	
	

	
<!--===============================================================================================-->	
	<script src="loginAssets/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="loginAssets/vendor/bootstrap/js/popper.js"></script>
	<script src="loginAssets/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="loginAssets/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="loginAssets/vendor/tilt/tilt.jquery.min.js"></script>
	<script >
		$('.js-tilt').tilt({
			scale: 1.1
		})
	</script>
<!--===============================================================================================-->
	<script src="loginAssets/js/main.js"></script>


</body>
</html>