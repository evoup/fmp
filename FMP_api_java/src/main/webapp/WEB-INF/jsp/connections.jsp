<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- <ul class="media-list"> -->
<%-- 	<c:forEach items="${connections}" var="connection"> --%>
<!-- 		<li class="media"> -->
<!-- 			<a href="" class="pull-left"> -->
<%-- 				<img src="graph.facebook.com/v2.3/${connection.profilePictureUrl}/picture" alt="" class="media-object"> --%>
<!-- 			</a> -->
<!-- 			<div class="media-body"> -->
<%-- 				<h4 class="media-heading">${connection.firstName} ${connection.lastName}</h4> --%>
<%-- 				<p><c:out value="${connection.headline}" /></p> --%>
<!-- 			</div> -->
<!-- 		</li> -->
<%-- 	</c:forEach> --%>
<!-- </ul> -->

<div><img src="//graph.facebook.com/v2.3/${connection.id}/picture" alt="${connection.firstName} ${connection.lastName}" title="${connection.firstName} ${connection.lastName}"/>
</div>
<div class="media-body">
	<h4 class="media-heading">Hello ${connection.firstName} ${connection.lastName}</h4>
	<p>
		<a href="${connection.link}">return to your facebook page.</a>
	</p>
</div>
