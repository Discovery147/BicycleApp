$('.list-group a').click(function(e) {
        e.preventDefault();
        $that = $(this);
        $that.parent().find('a').removeClass('active');
        $that.addClass('active');
    });

$(document).ready(function() {
    $('#data_table').DataTable();
    $(".data_table").DataTable();
} );

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
        return expectedAmount;
    }
    var oldPeriod = endDate.getTime() - startDate.getTime();
    var realPeriod = currentDate.getTime() - startDate.getTime();
    var realAmount = realPeriod * expectedAmount / oldPeriod;
    return realAmount.toFixed(2);
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