var author = "Guest";
var messageField = $('#message');
var messageContainer = $('#messages-container');
var usersList = $('#users-list');
var messageForm = $('#message-form');

function htmlentities(s){
    var pre = document.createElement('pre');
    var text = document.createTextNode(s);
    pre.appendChild(text);
    return pre.innerHTML;
}

function send(){
    if(messageField.val().length == 0){
        sweetAlert(
            'Oops...',
            'Message can not be empty!',
            'error'
        );
        return;
    }
    var message = '<div class="message my-message">' + htmlentities(messageField.val()) + '</div></div>';
    var date = new Date();
    var controls = '<a href="#" class="edit" title="Edit message"><i class="fa fa-pencil"></i></a> ' +
        '<a href="#" class="delete" title="Remove message"><i class="fa fa-trash"></i></a>';
    var authorInfo = '<div class="author-info">' + formatDate(date) + ' ' + author + ' ' + controls + '</div>';
    var messageInfo = '<div class="message-info me">' + authorInfo + '</div>';
    messageField.val("");
    messageContainer.append(
      '<div class="message-wrapper">' + messageInfo + message + '</div>'
    );
    scrollDownMessages();
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
    usersList.toggle();
}

function scrollDownMessages(){
    messageContainer.scrollTop(messageContainer[0].scrollHeight);
}

$(function(){
    scrollDownMessages();

    messageForm.submit(function(e){
        e.preventDefault();
        send();
    });

    messageField.keypress(function(e){
        var keyCode = (e.keyCode ? e.keyCode : e.which);
        if (!e.ctrlKey && keyCode == 13) {
            e.preventDefault();
            messageForm.submit();
        }
        else if((keyCode == 10 || keyCode == 13) && e.ctrlKey){
            messageField.val(messageField.val() + "\n");
        }
    });

    $(document).on('click', '.delete', function(){
        var message =  $(this).parent().parent().next();
        $(this).prev().remove();
        $(this).remove();
        message.html("This message has been removed.");
        message.addClass("deleted");
    });

    $(document).on('click', '.edit', function(){
        var messageWrapper = $(this).parent().parent();
        var message = messageWrapper.next();
        var messageText = message.html()
        swal({
            title: "Edit your message!",
            text: "<textarea id='messageField' style='width:100%;resize:none;' rows='3'>" + messageText +"</textarea>",
            html: true,
            showCancelButton: true,
            closeOnConfirm: false,
            animation: "slide-from-top"},
            function(){
                var messageValue = $('#messageField').val();
                if (messageValue === false) return false;
                if (messageValue === "") {
                    swal.showInputError("You need to write something!");
                    return false;
                }
                if (messageValue === messageText){
                    swal.close();
                    return false;
                }
                message.html(htmlentities(messageValue));
                var messageInfo = messageWrapper.children();
                messageInfo.next().remove();
                var editInfo = "Edited  " + formatDate(new Date());
                messageInfo.parent().append('<div class="edit-info">' + editInfo + '</div>');
                swal.close();
            }
        );
    });

});