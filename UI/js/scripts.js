var author = "Jason Statham";

function send(){
    if($('#message').val().length == 0){
        sweetAlert(
            'Oops...',
            'Message can not be empty!',
            'error'
        );
        return;
    }
    var message = '<div class="message my-message">' + $('#message').val() + '</div></div>';
    var date = new Date();
    var controls = '<a href="#" title="Edit message"><i class="fa fa-pencil"></i></a> ' +
        '<a href="#" class="delete" title="Remove message"><i class="fa fa-trash"></i></a>';
    var authorInfo = '<div class="author-info">' + formatDate(date) + ' ' + author + ' ' + controls + '</div>';
    var messageInfo = '<div class="message-info me">' + authorInfo + '</div>';
    $('#message').val("");
    $('#messages-container').append(
      '<div class="message-wrapper">' + messageInfo + message + '</div>'
    );
    $("#messages-container").scrollTop($("#messages-container")[0].scrollHeight);
}

function changeName(){
    swal({
        title: "Change name!",
        text: "Write your new name:",
        type: "input",
        showCancelButton: true,
        closeOnConfirm: false,
        animation: "slide-from-top",
        inputPlaceholder: "New name" },
        function(inputValue){
            if (inputValue === false) return false;
            if (inputValue === "") {
                swal.showInputError("You need to write something!");
                return false;
            }
            if (inputValue.length > 30) {
                swal.showInputError("The name may not be greater than 30 characters.");
                return false;
            }
            author = inputValue;
            $('#me').html(author);
            swal.close();
        });
}

function formatDate(date){
    var minutes = date.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;

    var hh = date.getHours();
    if (hh < 10) hh = '0' + hh;

    var dd = date.getDate();
    if (dd < 10) dd = '0' + dd;

    var mm = date.getMonth() + 1;
    if (mm < 10) mm = '0' + mm;

    var yy = date.getFullYear();

    return dd + '.' + mm + '.' + yy + ' ' + hh + ':' + minutes;
}

function toggleUsers(){
    $(".right-column").toggle();
}

$(function(){
    $('#message-form').submit(function(e){
        e.preventDefault();
        send();
    });

    $('#message').keypress(function(e){
        var keyCode = (e.keyCode ? e.keyCode : e.which);
        if (!e.ctrlKey && keyCode == 13) {
            e.preventDefault();
            $('#message-form').submit();
        }
        else if((keyCode == 10 || keyCode == 13) && e.ctrlKey){
            $('#message').val($('#message').val() + "\n");
        }
    });

    $(document).on('click', '.delete', function(){
        var message =  $(this).parent().parent().next();
        $(this).prev().remove();
        $(this).remove();
        message.html("This message has been removed.");
        message.addClass("deleted");
    });

});