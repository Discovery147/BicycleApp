<%@page import = "java.util.*" session="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/icons/userPageIcon.ico" />
    <title>Активация</title>
</head>
<body>
<header>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
    <link href="https://fonts.googleapis.com/css?family=Play" rel="stylesheet">
    <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/header.jsp"/>
</header>

<div class="container" style="font-family: 'Play', sans-serif">
    <div class="panel panel-primary" style="margin-top: 0.5%">
        <div class="panel-heading">
            <h3>Successful account activation</h3>
        </div>
        <div class="panel-body">
            <h5><strong>Login:</strong> ${login}</h5>
            Your email is confirmed.
            Your account has been activated.
        </div>
    </div>
</div>
<footer>
    <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/footer.jsp"/>
</footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
