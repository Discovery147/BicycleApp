function signIn(){
    var method = "POST";
    var action = "InvokerServlet";
    var command = "log_in";
    var data = "command="+ command +"&login=" + $('#loginsignin').val() +"&password=" + $('#passwordsignin').val();
    $.ajax({
        type: method,
        url:  action,
        data: data,
        success: function (data) {
            if(data === "true") {
                window.location.href='index.jsp';
            }
            else{
                $("#signInError").show("slow");
            }
        }
    });
}

function signUp(){
    var method = "POST";
    var action = "InvokerServlet";
    var command = "register";
    var data = "command="+ command +"&login=" + $('#loginsignup').val() +"&password=" + $('#passwordsignup').val()
                +"&firstname=" + $('#firstnamesignup').val() +"&lastname=" + $('#lastnamesignup').val()
                +"&email=" + $('#emailsignup').val() +"&phone=" + $('#phonesignup').val();
    $.ajax({
        type: method,
        url:  action,
        data: data,
        success: function (data) {
            if(data === "true") {
                $("#signUpError").hide();
                $("#signUpInfo").show("slow");
                setTimeout(function(){
                    window.location.href='InvokerServlet?command=fill_main';
                }, 5000);
            }
            else{
                $("#signUpError").show("slow");
            }
        }
    });
}
