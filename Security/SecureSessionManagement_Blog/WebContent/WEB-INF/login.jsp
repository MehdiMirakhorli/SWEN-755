<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style.css" />
<style type="text/css">
p {
	text-align: center;
	color: red;
}
</style>
</head>
<body>
	<p>Log in to access blog</p>
	<p><%=request.getAttribute("error_msg")%></p>

	<form action="${pageContext.request.contextPath}/login" method="post">
		<label>Username: </label> <input type="text" name="username">
		<label>Password: </label> <input type="password" name="password">
		<input class="btn" type="submit" name="submit" value="Log in">
	</form>
</body>
</html>