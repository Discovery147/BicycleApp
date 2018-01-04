<%@page import = "java.util.*" session="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation/reservation.css">
<link href="https://fonts.googleapis.com/css?family=Play" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/reservation.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/icons/reservationPageIcon.ico" />
    <title>Бронирование</title>
</head>
<body>
    <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/header.jsp"/>
    <div id="background"></div>
    <div class="container" style="font-family: 'Play', sans-serif">
        <div class="row">
            <h2>Бронирование велосипеда</h2>
            <hr>
            <input type="hidden" name="bicycleId" value="">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Пункт проката: <input disabled type="text" id="rentPlace" name="address" style="color: black;" size="35" value="" />
                        Велосипед: <input disabled type="text" id="bicycleInfo" name="bicycle" style="color: black" size="35" value="" />
                        Стоимость (BYN): <input disabled type="text" id="rentCost" name="cost" style="color: black" size="10" value="0" />
                        <input type="text" id="hiddenCost" style="display: none">
                        <input type="text" id="hiddenIdBicycle" style="display: none">
                        <div class="dropdown" style="display: inline-block;">
                            <a id="dLabel" role="button" data-toggle="dropdown" class="btn btn-primary" style="border-color: #cecece" data-target="#" href="#">
                                Выберите велосипед <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">
                                <c:forEach items="${data}" var="element">
                                    <li class="dropdown-submenu pull-left">
                                        <a tabindex="-1" href="#">${element.key.address} </a>
                                        <ul class="dropdown-menu">
                                            <c:forEach items="${element.value}" var="bicycle">
                                                <li>
                                                    <a href="#" onclick="
                                                        setReserveFields('${sessionScope.member.discount}','${bicycle.bicycleId}','${bicycle.maker} ${bicycle.model} ${bicycle.color} ${bicycle.size}','${bicycle.price}','${element.key.address}')">
                                                        ${bicycle.maker} ${bicycle.model} ${bicycle.color} ${bicycle.size}
                                                    </a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class='col-md-5' style="width:35%">
                            <div class="form-group">
                                <div class='input-group date' id='datetimepicker6'>
                                    <input data-format="yyyy-MM-dd hh:mm:ss" id="start_time" type='text' class="form-control" />
                                    <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                                </div>
                            </div>
                        </div>
                        <div class='col-md-5' style="width:35%">
                            <div class="form-group">
                                <div class='input-group date' id='datetimepicker7'>
                                    <input data-format="yyyy-MM-dd hh:mm:ss" id="end_time" type='text' class="form-control" />
                                    <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                                </div>
                            </div>
                        </div>
                        <div  style="display: inline-block;">
                            <input type="button" class="btn btn-default" value="Посчитать стоимость" onclick="checkSum()"/>
                        </div>
                        <div  style="display: inline-block;">
                            <input type="button" class="btn btn-success" value="Забронировать" onclick="reservation(); return false;"/>
                        </div>
                        <div class="alert alert-success" style="margin-top: 2%; display: none" id="successBlock">
                            Велосипед успешно забронирован!
                            Cейчас вас перенаправит на календарь...
                        </div>
                        <div class="alert alert-info" style="margin-top: 2%;">
                            <strong>Важно!</strong>
                            <li>Ваша скидка: ${sessionScope.member.discount}%</li>
                            <li>Время проката минимум 1ч.</li>
                            <li>Бронирование осуществляется минимум за 1ч. до поездки.</li>
                            <li>Выдача и сдача инвентаря производится с 9:00 до 21:00 без выходных.</li>
                        </div>
                        <div class="alert alert-danger" style="display: none" id="errorDate">
                            <strong>Ошибка!</strong> Заполните данные правильно.
                        </div>
                        <div class="alert alert-danger" style="display: none" id="badReservation">
                            <strong>Отказ в бронировании</strong>
                            <br>Возможные причины:
                            <li>На балансе недостаточно средств.</li>
                            <li>Ваш аккаунт не подтвержден.</li>
                            <li>У вас отутствуют права на данную операцию.</li>
                            <li>Не выполнены условия бронирования.</li>
                        </div>
                    </div>
                </div>
        </div>
    </div>
<footer>
    <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/footer.jsp"/>
</footer>
</body>


</html>