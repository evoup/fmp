<html>
<head>
<title>Hello Facebook</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
</head>
<body>
	<h3>Connect to Facebook</h3>

	<form action="/connect/facebook" method="POST">
		<input type="hidden" name="scope" value="read_stream" />
		<div class="formInfo">
			<p>You aren't connected to Facebook yet. Click the button to
				connect this application with your Facebook account.</p>
		</div>
		<p>
			<button type="submit">Connect to Facebook</button>
		</p>
	</form>

	<div class="jumbotron">
		<h1>Your social network</h1>
		<p>View connections in your network</p>
		<p>
			<a class="btn btn-primary btn-lg" role="button" href="connections">View</a>
		</p>
	</div>

	<div>
		<form id="linkedin-signin-form" action="connect/facebook" method="POST" class="form-signin" role="form">
			<h2 class="form-signin-heading">Or Connect by</h2>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<button type="submit" class="btn btn-primary">facebook</button>
		</form>
	</div>
	<div>
	<form id="signin-form" action="signin/authenticate" class="form-signin" role="form" method="POST">
		<h2 class="form-signin-heading">Please sign in</h2>
		<c:if test="${param.error=='bad_credentials'}">
			<div class="alert alert-danger">
				Your sign in information was incorrect.Please try again or <a href="signup">sign up</a>.
			</div>
		</c:if>
		<input type="text" name="username" class="form-control" placeholder="Username" required autofocus>
		<input type="password" name="password" class="form-control" placeholder="Password" required>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<button type="submit" class="btn btn-lg btn-primary btn-block">Sign In</button>
	</form>
	<form id="linkedin-signin-form" action="signin/facebook" method="POST" class="form-signin" role="form">
		<h2 class="form-signin-heading">Or Connect by</h2>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<button type="submit" class="btn btn-primary">Facebook</button>
	</form>
</div>


<br>

<div class="container">
	<form:form commandName="signup" class="form-horizontal form-signup" role="form" action="signup" id="signup" method="POST">
		<h2 class="form-signin-heading">Sign Up</h2>
		<c:if test="${not empty message}">
        	<div class="alert alert-info">
        		<c:out value="${message}" />
        	</div>
        </c:if>
		<spring:hasBindErrors name="signup">
			<div class="alert alert-danger"><form:errors path="*" /></div>
		</spring:hasBindErrors>
		<div class="form-group">
			<form:label path="firstName" class="col-sm-3 control-label">First name</form:label>
			<div class="col-sm-9">
				<form:input path="firstName" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="lastName" class="col-sm-3 control-label">Last name</form:label>
			<div class="col-sm-9">
				<form:input path="lastName" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="username" class="col-sm-3 control-label">Username</form:label>
			<div class="col-sm-9">
				<form:input path="username" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="password" class="col-sm-3 control-label">Password</form:label>
			<div class="col-sm-9">
				<form:password path="password" class="form-control" />
			</div>
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<button type="submit" class="btn btn-lg btn-primary btn-block">Sign Up</button>
	</form:form>
</div>

</body>
</html>