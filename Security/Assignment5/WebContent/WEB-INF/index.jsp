<%@page import="edu.rit.swen755.models.BlogPost"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Blog Posts</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />
</head>
<body>
	<div class="logout">User: <%=session.getAttribute("username") %> <a href="${pageContext.request.contextPath}/logout">Logout</a></div>
	<table>
		<thead>
			<tr>
				<th>#</th>
				<th>Title</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (BlogPost post : (Iterable<BlogPost>) request.getAttribute("list")) {
			%>
			<tr>
				<td>
					<%=post.getId()%>
				</td>
				<td>
					<a href="${pageContext.request.contextPath}/post?id=<%=post.getId()%>">
						<%=post.getTitle()%>
					</a>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	
	<a href="${pageContext.request.contextPath}/post/add">New Blog Entry</a>
</body>
</html>