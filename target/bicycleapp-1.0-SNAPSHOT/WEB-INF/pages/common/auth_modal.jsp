<%--
  Created by IntelliJ IDEA.
  User: Sizon
  Date: 15.12.2017
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register/style.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">

                <div class="container">
                    <section>
                        <div id="container_demo" >
                            <a class="hiddenanchor" id="toregister"></a>
                            <a class="hiddenanchor" id="tologin"></a>
                            <div id="wrapper">
                                <div id="login" class="animate form">
                                    <form id="loginForm" method="post" onsubmit="signIn(); return false;">
                                        <input type="hidden" name="command" value="LOG_IN">
                                        <h1>Log in</h1>
                                        <p>
                                            <label for="loginsignin" class="uname" > Login </label>
                                            <input id="loginsignin" required="required" type="text" placeholder="Username"/>
                                        </p>
                                        <p>
                                            <label for="passwordsignin" class="youpasswd"> Password </label>
                                            <input id="passwordsignin" required="required" type="password" placeholder="Password" />
                                        </p>
                                        <div class="alert alert-danger" style="display: none" id="signInError">
                                            <strong>Ошибка!</strong> Заполните данные правильно.
                                        </div>
                                        <input type="submit" class="btn btn-success" value="Log in"/>
                                        <p class="change_link">
                                            Not a member yet ?
                                            <a href="#toregister" class="to_register">Join us</a>
                                            <a href="#myModal" class="to_register" data-dismiss="modal">Close</a>
                                        </p>
                                    </form>
                                </div>

                                <div id="register" class="animate form">
                                    <form  name="registerForm" method = "post" id="registerForm" onsubmit="signUp(); return false;">
                                        <input type="hidden" name="command" value="REGISTER">
                                        <h1> Sign up </h1>
                                        <p>
                                            <label for="firstnamesignup" class="uname" >First name</label>
                                            <input id="firstnamesignup" name="firstname" required="required" type="text" />
                                        </p>
                                        <p>
                                            <label for="lastnamesignup" class="uname" >Last name</label>
                                            <input id="lastnamesignup" name="lastname" required="required" type="text"/>
                                        </p>
                                        <p>
                                            <label for="loginsignup" class="youmail"  >Login</label>
                                            <input id="loginsignup" name="login" required="required" type="text"/>
                                        </p>
                                        <p>
                                            <label for="passwordsignup" class="youmail"  >Password</label>
                                            <input id="passwordsignup" name="password" required="required" type="password"/>
                                        </p>
                                        <p>
                                            <label for="passwordsignup_confirm" class="youpasswd" >Please confirm your password </label>
                                            <input id="passwordsignup_confirm" name="password_confirm" required="required" type="password"/>
                                        </p>
                                        <p>
                                            <label for="emailsignup" class="youmail"  >Email</label>
                                            <input id="emailsignup" name="email" required="required" type="email"/>
                                        </p>
                                        <p>
                                            <label for="phonesignup" class="youpasswd" >Phone </label>
                                            <input id="phonesignup" name="phone" required="required" type="text" />
                                        </p>
                                        <div class="alert alert-danger" style="display: none" id="signUpError">
                                            <strong>Ошибка!</strong> Пользователь уже существует.
                                        </div>
                                        <div class="alert alert-success" style="display: none" id="signUpInfo">
                                            На указанную почту выслано письмо для подтверждения.
                                        </div>
                                        <input type="submit" class="btn btn-success" value="Sign up"/>
                                        <p class="change_link">
                                            Already a member ?
                                            <a href="#tologin" class="to_register"> Go and log in </a>
                                            <a href="#myModal" class="to_register" data-dismiss="modal">Close</a>
                                        </p>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </section>

        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/header.js"  type="text/javascript"></script>
</body>
</html>
