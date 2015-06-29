<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" /> 
	<script src="http://connect.facebook.net/en_US/all.js"></script>
</head>
<div>
	<form id="signin-form" action="signin/authenticate" class="form-signin" role="form" method="POST">
		<h2 class="form-signin-heading">Please sign in</h2>
		<c:if test="${param.error == 'bad_credentials'}">
			<div class="alert alert-danger">
				Your sign in information was incorrect.Please try again or <a href="signup">sign up</a>.
			</div>
		</c:if>
		<input type="text" name="username" class="form-control" placeholder="Username" required autofocus>
		<input type="password" name="password" class="form-control" placeholder="Password" required>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br>
		<button type="submit" class="btn btn-lg btn-primary btn-block">Sign In</button>
	</form>
	
	<form id="linkedin-signin-form" action="connect/facebook" method="POST" class="form-signin" role="form">
		<h2 class="form-signin-heading">Or Connect by</h2>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<p><button type="submit"><img src="<c:url value="/resources/social/facebook/connect_light_medium_short.gif" />"/></button></p>
	</form>
</div>	