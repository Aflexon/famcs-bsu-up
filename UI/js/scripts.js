var messagesKey = "TypeWriter Messages";
var authorKey = "TypeWriter Author";

var author = "Guest";
var messageList = [];


function run(){
    document.getElementById("message-form").addEventListener("submit", function(e){
        e.preventDefault();
        send();
    });

    document.getElementById("message-form").addEventListener("keypress", keypressOnForm);

    document.getElementById("messages-container").addEventListener("click", delegateEvent);

    loadFromStorage();
    render();
}

function delegateEvent(evt){
    if(evt.target.classList.contains('edit')){
        edit(evt.target.parentNode.parentNode.parentNode.parentNode);
    } else if(evt.target.classList.contains('delete')){
        remove(evt.target.parentNode.parentNode.parentNode.parentNode);
    }
}

function edit(messageWrapper){
    var message = messageList[indexByElement(messageWrapper, messageList)];
	swal({
        title: "Edit your message!",
        text: "<textarea id='message-field' style='width:100%;resize:none;' rows='3'>" + message.message +"</textarea>",
        html: true,
        showCancelButton: true,
        closeOnConfirm: false,
        animation: "slide-from-top"},
        function(){
            var messageValue = document.getElementById('message-field').value;
            if (messageValue === false) return false;
            if (messageValue === "") {
                swal.showInputError("You need to write something!");
                return false;
            }
            if (messageValue === message.message){
                swal.close();
                return false;
            }
            message.message = htmlentities(messageValue);
            message.edit = formatDate(new Date());
            updateMessage(message);
            swal.close();
        }
    );
}

function remove(messageWrapper){
    var message = messageList[indexByElement(messageWrapper, messageList)];
    message.remove = true;
    updateMessage(message);
}

function indexByElement(element, messages){
    var id = element.attributes['data-id'].value;
    return messages.findIndex(function(item){
        return item.id == id;
    })
}

function keypressOnForm(e){
    var keyCode = (e.keyCode ? e.keyCode : e.which);
    if(!e.ctrlKey && keyCode == 13){
        e.preventDefault();
        send();
    }
    else if((keyCode == 10 || keyCode == 13) && e.ctrlKey){
        var field = e.target;
        field.value += "\n";
        field.scrollTop = field.scrollHeight;
    }
}

function getMessage(message){
    message = cloneObject(message);
    var messageTemplate = document.getElementById('message-template').innerHTML;
    message.ctrl = "";
    message.editInfo = "";
    message.class = "";
    if(message.isMy){
        message.ctrl = document.getElementById("ctrl-template").innerHTML;
        message.class = "my-message";
    }
    if(message.edit !== false){
        var editInfoTemplate = document.getElementById('edit-info-template').innerHTML;
        message.editInfo = editInfoTemplate.supplant({date : message.edit});
    }
    if(message.remove){
        message.class += " deleted";
        message.message = "This message has been removed.";
        message.ctrl = "";
        message.editInfo = "";
    }
    return messageTemplate.supplant(message);
}

function renderMessage(message){
    var messagesContainer = document.getElementById('messages-container');
    var messageWrapper = document.createElement('div');
    messageWrapper.classList.add("message-wrapper");
    messageWrapper.setAttribute('data-id', message.id);
    messageWrapper.innerHTML = getMessage(message);
    messagesContainer.appendChild(messageWrapper);
    messageWrapper.scrollIntoView();
}

function render(){
    for(var i = 0; i < messageList.length; i++){
        renderMessage(messageList[i]);
    }
    renderName(author);
}

function updateMessage(message){
    saveMessages(messageList);
    var messageWrapper = document.querySelector('[data-id="' + message.id + '"]');
    messageWrapper.innerHTML = getMessage(message);
}

function newMessage(messageText, messageAuthor){
    var my = false;
    if (messageAuthor === undefined){
        my = true;
        messageAuthor = author;
    }
    return {
        id: guid(),
        author: messageAuthor,
        date: formatDate(new Date()),
        message: htmlentities(messageText),
        edit: false,
        remove: false,
        isMy: my
    };
}

function send(){
    var messageField = document.getElementById('message');
    if(messageField.value.length == 0){
        swal(
            'Oops...',
            'Message can not be empty!',
            'error'
        );
        return;
    }
    var message = newMessage(messageField.value);
    messageField.value = "";
    messageList.push(message);
    saveMessages(messageList);
    renderMessage(message);
}

function changeName(){
    swal({
        title: "Change name!",
        text: "Write your name:",
        type: "input",
        showCancelButton: true,
        closeOnConfirm: false,
        animation: "slide-from-top",
        inputPlaceholder: "New name" },
        function(inputValue){
            if(inputValue === false) return false;
            if(inputValue === ""){
                swal.showInputError("You need to write something!");
                return false;
            }
            if(inputValue.length > 30){
                swal.showInputError("The name may not be greater than 30 characters.");
                return false;
            }
            author = htmlentities(inputValue);
            saveAuthor(author);
            renderName(author);
            swal.close();
        }
    );
}

function renderName(author){
    document.getElementById("me").innerHTML = author;
}

function toggleSidebar(){
    var sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle('hidden-mobile');
}

function checkStorage(){
    if(typeof(Storage) == "undefined"){
        alert('localStorage is not accessible');
    }
}

function saveMessages(messages){
    checkStorage();
    localStorage.setItem(messagesKey, JSON.stringify(messages));
}

function saveAuthor(author){
    checkStorage();
    localStorage.setItem(authorKey, author);
}

function loadFromStorage(){
    checkStorage();
    messageList = localStorage.getItem(messagesKey);
    if(!messageList){
        messageList = [newMessage("Welcome to our chat!", "System")]
    }
    else{
        messageList = JSON.parse(messageList)
    }
    author = localStorage.getItem(authorKey);
    if(!author){
        author = "Guest";
    }
}
