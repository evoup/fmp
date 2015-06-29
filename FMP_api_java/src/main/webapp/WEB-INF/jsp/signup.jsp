<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="resources/css/style.css" rel="stylesheet" type="text/css" /> 
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