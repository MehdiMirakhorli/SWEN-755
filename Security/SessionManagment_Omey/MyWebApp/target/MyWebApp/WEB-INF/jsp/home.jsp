<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page>
    <jsp:attribute name="title">Welcome!</jsp:attribute>
    <jsp:body>
        <div class="jumbotron" id="welcome">

            <h1>Welcome to the Stormpath Webapp Sample Application!</h1>

            <p class="lead">
                <br/>
                <br/>
                Welcome to this <i>gloriously simple</i>
                <a href="https://docs.stormpath.com/java/servlet-plugin/">Stormpath Java Webapp</a> sample application!
                <ul>
                    <li>First, take a look through this very basic site.</li>
                    <li>Then, check out this project's source code
                        <a href="https://github.com/stormpath/stormpath-sdk-java/examples/servlet">on GitHub</a>.</li>
                    <li>Lastly, integrate Stormpath into your own sites!</li>
                </ul>
            </p>

            <br/>
            <br/>

            <h2>What This Sample App Demonstrates</h2>

            <br/>
            <br/>

            <p>This simple application demonstrates how easy it is to register, login, and securely authenticate
                users on your website using the Stormpath Servlet Plugin.</p>

            <p>Not a Stormpath user yet? <a href="https://stormpath.com">Go signup now!</a></p>

            <br/>
            <br/>

            <p class="bigbutton"><a class="bigbutton btn btn-lg btn-danger"
                                    href="${pageContext.request.contextPath}/register" role="button">Register</a></p>
        </div>
    </jsp:body>
</t:page>