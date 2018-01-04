<%@page import = "java.util.*" session="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/icons/calendarPageIcon.ico" />
    <title>Calendar</title>
    <link href="https://fonts.googleapis.com/css?family=Play" rel="stylesheet">
</head>
<body>
    <header>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/main.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap.min.css">
        <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/header.jsp"/>
    </header>
    <div class="table-responsive" style="font-family: 'Play', sans-serif">
        <c:if test="${not empty user}">
            <div class="panel panel-success">

                <table id="data_user_table" class="table table-striped table-bordered">
                    <div class="panel-heading">
                        <h4>My Reservations</h4>
                        <thead>
                            <tr class="active">
                                <th style="font-weight:600">Адрес</th>
                                <th style="font-weight:600">Велосипед</th>
                                <th style="font-weight:600">Начальное время</th>
                                <th style="font-weight:600">Конечное время</th>
                                <th style="font-weight:600">Стоимость (оплачено)</th>
                            </tr>
                        </thead>
                    </div>
                    <div class="panel-default" style="padding-top: 1%">
                        <tbody>
                            <c:forEach items="${data_user}" var="reserve">
                                <tr>
                                    <td>${reserve.bicycle.place.address}</td>
                                    <td>
                                            ${reserve.bicycle.maker} ${reserve.bicycle.model} ${reserve.bicycle.color} ${reserve.bicycle.size}
                                    </td>
                                    <td>${reserve.startTime}</td>
                                    <td>${reserve.endTime}</td>
                                    <td>${reserve.amount}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </div>
                </table>

            </div>
        </c:if>
        <div class="panel panel-primary">
            <table id="data_table" class="table table-striped table-bordered">
                <div class="panel-heading">
                    <h4>All Reservations</h4>
                    <thead class="info">
                        <th style="font-weight:600">Пункт проката</th>
                        <th style="font-weight:600">Адрес</th>
                        <th style="font-weight:600">Велосипед</th>
                        <th style="font-weight:600">Начальное время</th>
                        <th style="font-weight:600">Конечное время</th>
                    </thead>
                </div>
                <div class="panel-default" style="padding-top: 1%;">
                    <tbody >
                        <c:forEach items="${data}" var="reserve">
                            <tr>
                                <td>${reserve.bicycle.place.name}</td>
                                <td>${reserve.bicycle.place.address}</td>
                                <td>
                                    ${reserve.bicycle.maker} ${reserve.bicycle.model} ${reserve.bicycle.color} ${reserve.bicycle.size}
                                </td>
                                <td>${reserve.startTime}</td>
                                <td>${reserve.endTime}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </div>
            </table>
        </div>
    </div>
    <footer>
        <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/footer.jsp"/>
    </footer>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/calendar/calendar.js"></script>
</body>
</html>
