<%@page import = "java.util.*" session="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/icons/reservationPageIcon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cashier/cashier.css">
    <link href="https://fonts.googleapis.com/css?family=Play" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
    <title>Рабочая область</title>
</head>
<body>
    <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/header.jsp"/>
    <div id="background"></div>
        <div class="container" style="font-family: 'Play', sans-serif">
            <div class="row">
                <h2>Рабочая область</h2>
                <hr color="red" />
                <div class="panel panel-success">
                    <div class="panel-heading" onclick="showTransactionBlock()">
                        Оформление поездки
                        <span class="glyphicon glyphicon-chevron-down" aria-hidden="false"></span>
                    </div>
                    <div class="panel-body" style="border-radius: 2px; display: none" id="transactionBlock">
                        <div class="container">
                            <form class="form-inline">
                                <div class="btn-group">
                                    <select class="selectpicker show-tick form-control" data-live-search="true" data-style="btn-primary" id="bicycles" onchange="fillExpectedAmountNewTransaction()">
                                        <c:forEach items="${bicycles}" var="bicycle">
                                            <option value="${bicycle.bicycleId}">${bicycle.maker} ${bicycle.model} ${bicycle.color} ${bicycle.size}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="period">Period </label>
                                    <input type="number" class="form-control" id="period" placeholder="Period" min="1" step="1">
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="AmountNewTransaction">Amount (in BYN)</label>
                                    <div class="input-group">
                                        <div class="input-group-addon">$</div>
                                        <input type="number" class="form-control" id="AmountNewTransaction" placeholder="Amount" min="0" step="1">
                                        <div class="input-group-addon">.00</div>
                                    </div>
                                </div>
                                <p>Document type</p>
                                <select  class="selectpicker" data-style="btn-danger" id="doctypes">
                                    <c:forEach items="${doctypes}" var="doctype">
                                        <option value="${doctype.name}">${doctype.name}</option>
                                    </c:forEach>
                                </select>

                                <div class="form-group">
                                    <label class="sr-only">Document number </label>
                                    <input type="text" class="form-control" placeholder="Number">
                                </div>
                                <div class="form-group">
                                    <label class="sr-only">Addition </label>
                                    <input type="text" class="form-control" placeholder="Other">
                                </div>
                                <button type="submit" class="btn btn-default">Добавить</button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="panel panel-info">
                    <div class="panel-heading">Занятость велосипедов</div>
                    <div class="panel-body" style="border-radius: 2px;">


                        <div class="table-responsive">
                            <table class="data_table table table-striped table-bordered">
                                <thead>
                                <tr class="default">
                                    <th style="font-weight:600" >Велосипед</th>
                                    <th style="font-weight:600">ФИО</th>
                                    <th style="font-weight:600">Документ</th>
                                    <th style="font-weight:600">Дата начала</th>
                                    <th style="font-weight:600">Дата окончания</th>
                                    <th style="font-weight:600">Предварительная сумма (BYN)</th>
                                    <th style="font-weight:600">Операции</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${transactions}" var="transaction">
                                    <tr>
                                        <td>${transaction.bicycle.maker} ${transaction.bicycle.model} ${transaction.bicycle.color} ${transaction.bicycle.size}</td>
                                        <td>${transaction.member.firstname} ${transaction.member.lastname}</td>
                                        <td>${transaction.document.doctype.name} ${transaction.document.number} ${transaction.document.other}</td>
                                        <td>${transaction.startTime}</td>
                                        <td>${transaction.endTime}</td>
                                        <td>${transaction.expectedAmount}</td>
                                        <td><button type="button" class="btn btn-success" data-toggle="modal" data-target="#notFinishedTransactionModal"
                                                    onclick="fillModalWindowByNotFinishedTransaction('${transaction.transactionId}')">Завершить</button></td>
                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>
                        </div>
                    </div>
                </div>

                <!-- Modal bicycle finish-->
                <div class="modal fade" id="notFinishedTransactionModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-body">
                                <form action="InvokerServlet" method="post">
                                    <input type="hidden" name="command" value="complete_transaction">
                                    <input type="hidden" name="transactionId" id="transactionId">
                                    <div class="panel panel-info">
                                        <div class="panel-heading">
                                            <div class="form-group">
                                                <label for="modalClient">Клиент</label>
                                                <input type="text" class="form-control" id="modalClient" disabled placeholder="Client">
                                            </div>
                                            <div class="form-group">
                                                <label for="modalDocument">Документ</label>
                                                <input type="text" class="form-control" id="modalDocument" disabled placeholder="Document">
                                            </div>
                                            <div class="form-group">
                                                <label for="modalBicycle">Велосипед</label>
                                                <input type="text" class="form-control" id="modalBicycle" disabled placeholder="Bicycle">
                                            </div>
                                        </div>
                                        <div class="panel-body">
                                            <div class="form-group">
                                                <label for="modalStartTime">Начальная дата</label>
                                                <input type="text" class="form-control" id="modalStartTime" disabled placeholder="Start time">
                                            </div>
                                            <div class="form-group">
                                                <label for="modalEndTime">Конечная дата</label>
                                                <input type="text" class="form-control disabled" id="modalEndTime" disabled placeholder="End time">
                                            </div>
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <div class="form-row">
                                                        <label for="modalExpectedAmount">Начальная цена</label>
                                                        <input type="number" class="form-control" id="modalExpectedAmount" disabled placeholder="Expected amount">
                                                    </div>
                                                    <div class="form-row">
                                                        <label for="modalAmount">Доплата</label>
                                                        <input type="number" step="0.01" min="0" class="form-control" id="modalAmount" name="amount" placeholder="Amount">
                                                    </div>
                                                </div>
                                            </div>
                                            <input type="submit" class="btn btn-primary" style="width: 100%" value="Завершить" />
                                        </div>
                                    </div>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal" style="width: 100%">Close</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="panel panel-danger">
                    <div class="panel-heading">Брони</div>
                    <div class="panel-body" style="border-radius: 2px;">


                        <div class="table-responsive">
                            <table class="data_table table table-striped table-bordered">
                                <thead>
                                <tr class="default">
                                    <th style="font-weight:600" >Велосипед</th>
                                    <th style="font-weight:600">ФИО</th>
                                    <th style="font-weight:600">Дата начала</th>
                                    <th style="font-weight:600">Дата окончания</th>
                                    <th style="font-weight:600">Предварительная сумма (BYN)</th>
                                    <th style="font-weight:600">Операции</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${reservations}" var="reservation">
                                    <tr>
                                        <td>${reservation.bicycle.maker} ${reservation.bicycle.model} ${reservation.bicycle.color} ${reservation.bicycle.size}</td>
                                        <td>${reservation.member.firstname} ${reservation.member.lastname}</td>
                                        <td>${reservation.startTime}</td>
                                        <td>${reservation.endTime}</td>
                                        <td>${reservation.amount}</td>
                                        <td><button type="button" class="btn btn-success" data-toggle="modal" data-target="#notFinishedTransactionModalByReservation"
                                                    onclick="fillModalWindowByNotFinishedReservation('${reservation.reservationId}')">Открыть поездку</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>


                    </div>
                </div>

                <!-- Modal open transaction by reservation-->
                <div class="modal fade" id="notFinishedTransactionModalByReservation" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-body">
                                <form action="InvokerServlet" method="post" onsubmit="createTransactionByReservation(); return false;">
                                    <input type="hidden" name="command" value="create_transaction_by_reservation">
                                    <input type="hidden" name="memberId" id="ModalmemberIdByReservation">
                                    <input type="hidden" name="bicycleId" id="ModalbicycleIdByReservation">
                                    <div class="panel panel-info">
                                        <div class="panel-heading">
                                            <div class="form-group">
                                                <label for="modalClient">Клиент</label>
                                                <input type="text" class="form-control" id="modalClientByReservation" disabled placeholder="Client">
                                            </div>
                                            <label for="documentByReservation">Document</label>
                                            <div class="form-group">
                                                <select  class="selectpicker" data-style="btn-danger" id="documentByReservation">
                                                    <c:forEach items="${doctypes}" var="doctype">
                                                        <option value="${doctype.doctypeId}">${doctype.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label class="sr-only">Document number </label>
                                                <input type="text" class="form-control"id="docNumber" placeholder="Number">
                                            </div>
                                            <div class="form-group">
                                                <label class="sr-only">Addition </label>
                                                <input type="text" class="form-control" id="docOther" placeholder="Other">
                                            </div>
                                            <div class="form-group">
                                                <label for="modalBicycle">Велосипед</label>
                                                <input type="text" class="form-control" id="modalBicycleByReservation" disabled placeholder="Bicycle">
                                            </div>
                                        </div>
                                        <div class="panel-body">
                                            <div class="form-group">
                                                <label for="modalStartTime">Начальная дата</label>
                                                <input type="text" class="form-control" id="modalStartTimeByReservation" disabled placeholder="Start time">
                                            </div>
                                            <div class="form-group">
                                                <label for="modalEndTime">Конечная дата</label>
                                                <input type="text" class="form-control disabled" id="modalEndTimeByReservation" disabled placeholder="End time">
                                            </div>
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <div class="form-row">
                                                        <label for="modalExpectedAmount">Начальная цена</label>
                                                        <input type="number" class="form-control" id="modalExpectedAmountByReservation" disabled placeholder="Expected amount">
                                                    </div>
                                                </div>
                                            </div>
                                            <input type="submit" class="btn btn-primary" style="width: 100%" value="Завершить" />
                                        </div>
                                    </div>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal" style="width: 100%">Close</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    <footer>
        <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/footer.jsp"/>
    </footer>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cashier.js"></script>
</body>


</html>