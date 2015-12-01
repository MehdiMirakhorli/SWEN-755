<%@page import="edu.rit.swen755.models.BlogPost"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Blog Posts</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style.css" />
</head>
<body>
	<form action="${pageContext.request.contextPath}/post/add"
		method="post">
		<label>Title: </label> <input type="text" name="title"> <label>Content:
		</label>
		<textarea rows="8" name="content"></textarea>
		<input class="btn" type="submit" name="submit">
	</form>
</body>
</html>