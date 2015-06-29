<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<link href="/fmp/resources/css/style.css" rel="stylesheet" type="text/css" />
<title>Create Ad Campaign</title>
</head>
<body>
<div>
	<form action="/fmp/facebook/campaign/createAdCampaign" method="POST" role="form" class="form-signin" >
		<input name="adAccountId" value="712344008863677" class="form-control"/>
		<input name="adCampaign.name" value="spring social created campaign" class="form-control"/>
		<input name="adCampaign.spendCap" value="PAUSED" class="form-control"/>
		<input name="adCampaign.objective" value="MOBILE_APP_INSTALLS" class="form-control"/>
		<input name="adCampaign.buyingType" value="FIXED_CPM" class="form-control"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br>
		<button type="submit">create</button>
	</form>
</div>
</body>
</html>