<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<c:url value="/" var="root" />
<body>
	<h1>Password Check !</h1>
	<form:form action="${root}" method="POST" modelAttribute="form">
		<form:label path="username">Enter New Username : </form:label>
		<form:input path="username" />
		<form:label path="password">New Password : </form:label>
		<form:password path="password" />
		<input type="submit" value="Create User">
	</form:form>
	<hr>
	<form:form action="${root}login" method="POST" modelAttribute="login">
		<form:label path="username">Username : </form:label>
		<form:input path="username" />
		<form:label path="password">Password : </form:label>
		<form:password path="password" />
		<input type="submit" value="Check User">
	</form:form>
	<c:if test="${not empty message}"><c:out value="${message}" /></c:if>	
</body>
</html>
