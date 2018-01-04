<%--
  Created by IntelliJ IDEA.
  User: Sizon
  Date: 20.12.2017
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap.min.css">
</head>
<body>
        <nav id="scrollspy-nav" class="navbar navbar-default">
            <ul class="nav nav-pills">
                <c:forEach items="${data}" var="element">
                    <li class="nav-item"><a class="nav-link" href="#place${element.key.getPlaceId()}">${element.key.name}</a></li>
                </c:forEach>
            </ul>
        </nav>
        <article data-spy="scroll" data-target="#scrollspy-nav" data-offset="0" class="scrollspy-example" id="context" style="overflow-x:hidden; font-family: 'Play', sans-serif;">
            <c:forEach items="${data}" var="element">
                <h4 id="place${element.key.getPlaceId()}">${element.key.getName()}</h4>
                <h6>Адрес: ${element.key.getAddress()}</h6>
                <div class="table-responsive">
                    <table class="data_table table table-striped table-bordered">
                        <thead>
                            <tr class="info">
                                <th style="font-weight:600" >Производитель</th>
                                <th style="font-weight:600">Модель</th>
                                <th style="font-weight:600">Цена (руб./ч.)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${element.value}" var="bicycle">
                                <tr>
                                    <td>${bicycle.maker}</td>
                                    <td>${bicycle.model}</td>
                                    <td>${bicycle.price}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:forEach>
        </article>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/main/main.js"></script>
</body>
</html>
