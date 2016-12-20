<%@tag description="Default Page template" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="title" required="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Stormpath Webapp Sample | <c:out value="${!empty title ? title : ''}"/></title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <%-- <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet" --%>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">

        <div class="header">

            <ul class="nav nav-pills pull-right">
                <c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}"/>
                <li<c:if test="${fn:endsWith(uri,'/')}"> class="active"</c:if>><a href="${pageContext.request.contextPath}/">Home</a></li>

                <%-- Change upper right context menu depending on if the user is logged in or not: --%>
                <c:choose>
                    <c:when test="${!empty account}">
                        <li<c:if test="${fn:endsWith(uri,'dashboard')}"> class="active"</c:if>><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                    </c:when>
                    <c:otherwise>
                        <li<c:if test="${fn:endsWith(uri,'login')}"> class="active"</c:if>><a href="${pageContext.request.contextPath}/login">Login</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>

            <h3 class="text-muted">Stormpath Webapp Sample</h3>

        </div>

        <jsp:doBody/>

    </div>
</body>
</html>