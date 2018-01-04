<%@page import = "java.util.*" session="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/icons/mainPageIcon.ico" />
    <title>Bicycle rent</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
    <link href="https://fonts.googleapis.com/css?family=Play" rel="stylesheet">
</head>
<body>
<header>
    <c:import url="common/header.jsp"/>
</header>
<c:import url="common/carousel.jsp"/>
<c:import url="common/main_context.jsp"/>
<footer>
    <c:import url="common/footer.jsp"/>
</footer>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
