$(document).ready(function() {
    $('#data_table').DataTable();
    $(".data_table").DataTable();
    $('#AmountNewTransaction').val($('#bicycles :selected').val());
});

function fillExpectedAmountNewTransaction(){
    $('#AmountNewTransaction').val($('#bicycles :selected').val());
};

$('#period').on('input',function(e){
    var amount =  $('#period').val() *  $('#bicycles :selected').val();
    $('#AmountNewTransaction').val(amount.toFixed(2));
});

function showTransactionBlock() {
    if(!$('#transactionBlock').is(':visible'))
    {
        $( "#transactionBlock" ).show("slow");
    }else{
        $( "#transactionBlock" ).hide("slow");
    }
}

function checkRealAmount(expectedAmount, startTime, endTime) {
    var startDate = new Date(startTime);
    var endDate = new Date(endTime);
    var currentDate = new Date();
    if(currentDate.getTime() < endDate.getTime()){
        return 0;
    }
    var oldPeriod = endDate.getTime() - startDate.getTime();
    var realPeriod = currentDate.getTime() - startDate.getTime();
    var realAmount = realPeriod * expectedAmount / oldPeriod;
    return (realAmount-expectedAmount).toFixed(2);
}

function fillModalWindowByNotFinishedTransaction(id){
    var method = "GET";
    var action = "InvokerServlet";
    var command = "find_not_finished_transaction";
    var data = "command="+ command +"&id=" + id;
    $.ajax({
        type: method,
        url:  action,
        data: data,
        success: function (data) {
            var transaction = JSON.parse(data);
            var bicycle = transaction.bicycle.maker + " " + transaction.bicycle.model + " " + transaction.bicycle.color + " " + transaction.bicycle.size;
            var client = transaction.member.firstname + " " + transaction.member.lastname;
            var document = transaction.document.doctype.name + " №" + transaction.document.number;
            $( "#transactionId" ).val(transaction.transactionId);
            $( "#modalBicycle" ).val(bicycle);
            $( "#modalClient" ).val(client);
            $( "#modalDocument" ).val(document);
            $( "#modalStartTime" ).val(transaction.startTime);
            $( "#modalEndTime" ).val(transaction.endTime);
            $( "#modalExpectedAmount" ).val(transaction.expectedAmount);
            var realAmount = checkRealAmount(transaction.expectedAmount, transaction.startTime, transaction.endTime);
            $( "#modalAmount" ).val(realAmount);
        }
    });
}

function fillModalWindowByNotFinishedReservation(id){
    var method = "GET";
    var action = "InvokerServlet";
    var command = "find_reservation";
    var data = "command="+ command +"&id=" + id;
    $.ajax({
        type: method,
        url:  action,
        data: data,
        success: function (data) {
            var reservation = JSON.parse(data);
            var bicycle = reservation.bicycle.maker + " " + reservation.bicycle.model + " " + reservation.bicycle.color + " " + reservation.bicycle.size;
            var client = reservation.member.firstname + " " + reservation.member.lastname;
            $( "#modalBicycleByReservation" ).val(bicycle);
            $( "#modalClientByReservation" ).val(client);
            $( "#modalStartTimeByReservation" ).val(reservation.startTime);
            $( "#modalEndTimeByReservation" ).val(reservation.endTime);
            $( "#modalExpectedAmountByReservation" ).val(reservation.amount);
            $( "#ModalmemberIdByReservation" ).val(reservation.member.memberId);
            $( "#ModalbicycleIdByReservation" ).val(reservation.bicycle.bicycleId);
        }
    });
}

function createTransactionByReservation() {
    var method = "POST";
    var action = "InvokerServlet";
    var command = "create_transaction_by_reservation";

    var docTypeId = $('#documentByReservation :selected').val(); // id docType
    var documentNumber =  $('#docNumber').val();
    var documentOther =  $('#docOther').val();

    var memberId = $('#ModalmemberIdByReservation').val();
    var bicycleId = $('#ModalbicycleIdByReservation').val();

    var startTime = $('#modalStartTimeByReservation').val();
    var endTime = $('#modalEndTimeByReservation').val();

    var expectedAmount = $('#modalExpectedAmountByReservation').val();

    var data = "command="+ command +"&id=" + id;
    $.ajax({
        type: method,
        url:  action,
        data: data,
        success: function (data) {
            var reservation = JSON.parse(data);
            var bicycle = reservation.bicycle.maker + " " + reservation.bicycle.model + " " + reservation.bicycle.color + " " + reservation.bicycle.size;
            var client = reservation.member.firstname + " " + reservation.member.lastname;
            $( "#modalBicycleByReservation" ).val(bicycle);
            $( "#modalClientByReservation" ).val(client);
            $( "#modalStartTimeByReservation" ).val(reservation.startTime);
            $( "#modalEndTimeByReservation" ).val(reservation.endTime);
            $( "#modalExpectedAmountByReservation" ).val(reservation.amount);
            $( "#memberId" ).val(reservation.member.memberId);
            $( "#bicycleId" ).val(reservation.bicycle.bicycleId);
        }
    });
}