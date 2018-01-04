<%@page import = "java.util.*" session="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/icons/userPageIcon.ico" />
    <title>Profile</title>
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
                <h3>My Profile</h3>
            </div>
            <form class="form-horizontal" role="form" method="post" enctype="multipart/form-data" action="http://localhost:8080/UploadServlet">
                <input type="hidden" name="command" value="edit_profile">
                <div class="panel-body">
                    <hr>
                    <div class="row">
                        <!-- left column -->
                        <div class="col-md-3">
                            <div class="text-center">
                                <img src="data:image/jpeg;base64,${sessionScope.member.image}" alt="Profile image" style="width: 60%; border-radius: 10%" />
                                <h6>Upload a different photo...</h6>
                                <label class="btn btn-block btn-primary">
                                    Browse&hellip;  <input type="file" class="form-control" name="fileName" style="display: none" />
                                </label>
                                <div class="alert alert-info" style="margin-top: 1%">
                                    Максимальный размер 1МБ.
                                </div>
                            </div>
                        </div>

                        <!-- edit form column -->
                        <div class="col-md-9 personal-info">
                            <div class="alert alert-info alert-dismissable">
                                <a class="panel-close close" data-dismiss="alert">×</a>
                                <i class="fa fa-coffee"></i>
                                <strong>Статус: </strong>
                                ${sessionScope.member.blocked ? "Заблокирован" : "Подтвержден"}
                            </div>
                            <h3>Personal info</h3>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">First name:</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" type="text" value="${sessionScope.member.firstname}" disabled>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">Last name:</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" type="text" value="${sessionScope.member.lastname}" disabled>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">Email:</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" type="text" value="${sessionScope.member.email}" disabled>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">Phone:</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" type="text" name="phone" value="${sessionScope.member.phone}" pattern="\d{12}" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Username:</label>
                                    <div class="col-md-8">
                                        <input class="form-control" type="text" name="login" value="${sessionScope.member.login}" pattern="[A-Za-z0-9]+" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label"></label>
                                    <div class="col-md-8">
                                        <input type="submit" class="btn btn-primary" value="Save Changes">
                                        <span></span>
                                        <input type="reset" class="btn btn-default" value="Cancel">
                                    </div>
                                </div>
                                <c:if test="${not empty exception}">
                                    <div class="alert alert-danger" style="margin-top: 1%">
                                        Ошибка обновления данных!
                                    </div>
                                </c:if>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <footer>
        <c:import url="${pageContext.request.contextPath}/WEB-INF/pages/common/footer.jsp"/>
    </footer>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/profile/editProfile.js"></script>
</body>
</html>
