$(document).ready(function() {
    $(function () {
        $('#datetimepicker6').datetimepicker({
            defaultDate: new Date(),
            format:'DD/MM/YYYY HH:mm'
        });
        $('#datetimepicker7').datetimepicker({
            defaultDate: new Date(),
            format:'DD/MM/YYYY HH:mm',
            useCurrent: false, //Important! See issue #1075
        });
        $("#datetimepicker6").on("dp.change", function (e) {
            $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker7").on("dp.change", function (e) {
            $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
        });
    });
});

function setReserveFields(discount, id, bicycle, price, address){
    $("#hiddenIdBicycle").val(id);
    $("#rentPlace").val(address);
    $("#bicycleInfo").val(bicycle);
    var priceWithDiscount = (100 - discount)/100 * price;
    var amount = parseFloat(priceWithDiscount).toFixed(2);
    $("#hiddenCost").val(amount);
    $("#rentCost").val(amount);
}

function parseDate(date){
    var dateArray = date.split("/");
    var day = dateArray[0];
    var month = dateArray[1];
    var yearAndTime = dateArray[2].split(" ");
    var year = yearAndTime[0];
    var hoursAndMinutes = yearAndTime[1];
    hoursAndMinutes = hoursAndMinutes.split(":");
    return [day, month, year, hoursAndMinutes[0], hoursAndMinutes[1]];
}

function checkSum(){
    var dateArrayStart = parseDate($("#start_time").val());
    var sumHoursAndMinutesStart = dateArrayStart[3] + dateArrayStart[4];

    var dateArrayEnd = parseDate($("#end_time").val());
    var sumHoursAndMinutesEnd = dateArrayEnd[3] + dateArrayEnd[4];

    var dateSt = new Date(dateArrayStart[2], dateArrayStart[1], dateArrayStart[0], dateArrayStart[3], dateArrayStart[4], 0, 0);
    var dateEn = new Date(dateArrayEnd[2], dateArrayEnd[1], dateArrayEnd[0], dateArrayEnd[3], dateArrayEnd[4], 0, 0);
    if((dateEn.getTime() - dateSt.getTime()) >= 60 * 60 * 1000){
        $("#errorDate").hide("slow");
        var hours = (dateEn.getTime() - dateSt.getTime())/1000/60/60;
        var cost =  $("#hiddenCost").val();
        $("#rentCost").val(parseFloat(hours*cost).toFixed(2));
        return true;
    }
    else{
        $("#errorDate").show("slow");
        return false;
    }
}

function validateReservation(){
    if(checkSum() && (($("#bicycleInfo").val() !== "" && $("#rentCost").val() > 0))){ // Между датами минимум 1 час, выбран велосипед, цена больше 0
        var dateArrayStart = parseDate($("#start_time").val());
        var dateArrayEnd = parseDate($("#end_time").val());
        if(dateArrayStart[3] >= 9 && (dateArrayStart[3]+dateArrayStart[4]) <= 2100){ // Время выдачи с 9 до 21:00
            if(dateArrayEnd[3] >= 9 && (dateArrayEnd[3]+dateArrayEnd[4]) <= 2100){ // Время сдачи с 9 до 21:00
                return true;
            }
        }
    }
    return false;
}

function reservation(){
    if(validateReservation()){ // Двойная валидация данных: на клиенте и на сервере.
        $("#errorDate").hide("slow");
        var method = "POST";
        var action = "InvokerServlet";
        var command = "reservation";
        var data = "command="+ command +"&start=" + encodeURIComponent($("#start_time").val()) +"&end=" + encodeURIComponent($("#end_time").val()) +"&id=" + $("#hiddenIdBicycle").val() +"&amount=" + $("#rentCost").val();
        $.ajax({
            type: method,
            url:  action,
            data: data,
            success: function (data) {
                if(data === "true") {
                    $("#badReservation").hide("slow");
                    $("#successBlock").show("slow");
                    setTimeout(function(){
                        window.location.href='InvokerServlet?command=fill_calendar';
                    }, 4000);
                }
                else{
                    $("#badReservation").show("slow");
                }
            }
        });
    }else {
        $("#errorDate").show("slow");
    }
}