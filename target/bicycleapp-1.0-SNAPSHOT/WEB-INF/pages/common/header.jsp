<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.languageAttr}" scope="session" />
<fmt:setBundle basename="resource.localization.index.pagecontent" var="locale" />
<fmt:requestEncoding value = "UTF-8" />

<html>
<body>
    <nav class="navbar navbar-default" style="margin-bottom: 0">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
                    <img src="${pageContext.request.contextPath}/img/main/logo.png" height="35" class="d-inline-block align-top" alt="" />
                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class=""><a href="#" class="nav-link disabled">
                        <span class="glyphicon glyphicon-bookmark" aria-hidden="false"></span>
                        <fmt:message key="menu.about" bundle="${locale}" />
                        <span class="sr-only">(current)</span></a>
                    </li>
                    <li class=""><a href="#" class="nav-link disabled">
                        <span class="glyphicon glyphicon-fire" aria-hidden="false"></span>
                        Акции
                        <span class="sr-only">(current)</span></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/InvokerServlet?command=fill_calendar">
                            <span class="glyphicon glyphicon-calendar" aria-hidden="false"></span>
                            <fmt:message key="menu.opencalendar" bundle="${locale}" />
                        </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty user && empty cashier}">
                    <li>
                        <a href="#" data-toggle="modal" data-target="#myModal">
                            <span class="glyphicon glyphicon-log-in" aria-hidden="false"></span>
                            <fmt:message key="menu.auth" bundle="${locale}" />
                        </a>
                    </li>
                <jsp:include page="auth_modal.jsp" />
                </c:if>
                    <c:if test="${not empty user}">
                        <li class="" >
                            <a href="#">
                                <span class="glyphicon glyphicon-piggy-bank" aria-hidden="false"></span>
                                Баланс: ${sessionScope.member.amount} BYN
                                <span class="sr-only">(current)</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${not empty cashier}">
                        <li>
                            <a href="${pageContext.request.contextPath}/InvokerServlet?command=fill_cashier">
                                <span class="glyphicon glyphicon-briefcase" aria-hidden="false"></span>
                                Панель управления
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${not empty user || not empty cashier}">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-user" aria-hidden="false"></span>
                                <fmt:message key="menu.account" bundle="${locale}" />
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="${pageContext.request.contextPath}/pages/user/user.jsp">
                                        <span class="glyphicon glyphicon-eye-open" aria-hidden="false"></span>
                                        <fmt:message key="menu.viewprofile" bundle="${locale}" />
                                    </a>
                                </li>
                                <c:if test="${empty cashier}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/InvokerServlet?command=fill_reservation">
                                            <span class="glyphicon glyphicon-send" aria-hidden="false"></span>
                                            <fmt:message key="menu.addrequest" bundle="${locale}" />
                                        </a>
                                    </li>
                                </c:if>
                                <li role="separator" class="divider"></li>
                                <li class="" >
                                    <a href="${pageContext.request.contextPath}/InvokerServlet?command=log_out">
                                        <span class="glyphicon glyphicon-remove-sign" aria-hidden="false"></span>
                                        Exit
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:if>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            <span class="glyphicon glyphicon-globe" aria-hidden="false"></span>
                            <fmt:message key="menu.language" bundle="${locale}" /><span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="${pageContext.request.contextPath}/InvokerServlet?command=language&language=ru_RU">
                                    <fmt:message key="menu.language.rus" bundle="${locale}" />
                                </a>
                            </li>
                            <li>
                                <a  href="${pageContext.request.contextPath}/InvokerServlet?command=language&language=en_US">
                                    <fmt:message key="menu.language.eng" bundle="${locale}" />
                                </a>
                            </li>
                            <li>
                                <a  href="${pageContext.request.contextPath}/InvokerServlet?command=language&language=by_BY">
                                    <fmt:message key="menu.language.bel" bundle="${locale}" />
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</body>
</html>
