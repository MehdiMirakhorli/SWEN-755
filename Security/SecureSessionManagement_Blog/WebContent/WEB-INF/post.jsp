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
	<%
		BlogPost post = (BlogPost) request.getAttribute("post");
	%>
	<h1><%=post.getId() + " - " + post.getTitle()%></h1>
	<div>
		<%=post.getContent()%>
	</div>
</body>
</html>