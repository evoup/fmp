<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%-- <%@ taglib uri="http://www.springframework.org/spring-social/facebook/tags" prefix="facebook" %> --%>
<%@ page session="false" %>

<h3>Connect to Facebook</h3>

<form action="<c:url value="/connect/facebook" />" method="POST">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="hidden" name="scope" value="publish_stream,user_photos,offline_access" />
	<div class="formInfo">
		<p>You aren't connected to Facebook yet. Click the button to connect Spring Social Showcase with your Facebook account.</p>
	</div>
	<p><button type="submit"><img src="<c:url value="/resources/social/facebook/connect_light_medium_short.gif" />"/></button></p>
	<c:if test="${param.error_code > 0}">
			<div class="alert alert-danger">
				error_code: <c:out value="${param.error_code}"></c:out><br>
				error_message: <c:out value="${param.error_message}"></c:out>
			</div>
		</c:if>
	<label for="postToWall"><input id="postToWall" type="checkbox" name="postToWall" /> Tell your friends about Spring Social Showcase on your Facebook wall</label>
</form>
