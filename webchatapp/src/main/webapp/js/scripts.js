var Application = {
    mainUrl : 'http://localhost:8080/chat',
    token : 'TN11EN'
};

var messagesKey = "TypeWriter Messages";
var authorKey = "TypeWriter Author";

var author = "Guest";
var messageList = [];

function getCookie(name) {
    var cookie = " " + document.cookie;
    var search = " " + name + "=";
    var setStr = null;
    var offset = 0;
    var end = 0;
    if (cookie.length > 0) {
        offset = cookie.indexOf(search);
        if (offset != -1) {
            offset += search.length;
            end = cookie.indexOf(";", offset)
            if (end == -1) {
                end = cookie.length;
            }
            setStr = unescape(cookie.substring(offset, end));
        }
    }
    return(setStr);
}

function run(){
    document.getElementById("message-form").addEventListener("submit", function(e){
        e.preventDefault();
        send();
    });

    document.getElementById("message-form").addEventListener("keypress", keypressOnForm);

    document.getElementById("messages-container").addEventListener("click", delegateEvent);
    loadFromStorage();
    setInterval(function () {
        loadMessages();
    }, 500);
    renderName(author);
}
function isError(text) {
    if(text == "")
        return false;

    try {
        var obj = JSON.parse(text);
    } catch(ex) {
        return true;
    }

    return !!obj.error;
}

function output(value){
    var output = document.getElementById('output');

    output.innerText = JSON.stringify(value, null, 2);
}

function defaultErrorHandler(message) {
    console.error(message);
    output(message);
}

function ajax(method, url, data, continueWith, continueWithError) {
    var xhr = new XMLHttpRequest();

    continueWithError = continueWithError || defaultErrorHandler;
    xhr.open(method || 'GET', url, true);

    xhr.onload = function () {
        if (xhr.readyState !== 4)
            return;

        if(xhr.status != 200) {
            continueWithError('Error on the server side, response ' + xhr.status);
            return;
        }

        if(isError(xhr.responseText)) {
            continueWithError('Error on the server side, response ' + xhr.responseText);
            return;
        }

        continueWith(xhr.responseText);
    };

    xhr.ontimeout = function () {
        continueWithError('Server timed out !');
    };

    xhr.onerror = function (e) {
        var errMsg = 'Server connection error !\n'+
            '\n' +
            'Check if \n'+
            '- server is active\n'+
            '- server sends header "Access-Control-Allow-Origin:*"\n'+
            '- server sends header "Access-Control-Allow-Methods: PUT, DELETE, POST, GET, OPTIONS"\n';

        continueWithError(errMsg);
    };

    xhr.send(data);
}

window.onerror = function(err) {
    output(err.toString());
};

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
            message.text = htmlentities(messageValue);
            message.edit = formatDate(new Date());
            ajax('PUT', Application.mainUrl, JSON.stringify(message), function(responseText) {
                updateMessage(message);
            });
            swal.close();
        }
    );
}

function remove(messageWrapper){
    var message = messageList[indexByElement(messageWrapper, messageList)];
    message.remove = true;
    message.message = "";
    message.text = "";
    ajax('DELETE', Application.mainUrl + "?msgId=" + message.id, null, function(responseText) {
        updateMessage(message);
    });
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

function updateMessage(message){
    saveMessages(messageList);
    var messageWrapper = document.querySelector('[data-id="' + message.id + '"]');
    console.log(messageWrapper);
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
        timestamp: (new Date()).getTime(),
        message: htmlentities(messageText),
        text: htmlentities(messageText),
        edit: false,
        remove: false,
        isMy: my
    };
}

function addMessage(messageObject){
    var date = new Date();
    date.setTime(messageObject.timestamp);
    messageList.push({
        id: messageObject.id,
        author: messageObject.author,
        isMy: messageObject.author == author,
        timestamp: (new Date()).getTime(),
        remove: messageObject.text == "",
        edit: false,
        message: htmlentities(messageObject.text),
        text: htmlentities(messageObject.text),
        date: formatDate(date)
    });
}

function setOffline(){
    var statusLabel = document.getElementById("statusLabel");
    statusLabel.classList.remove("label-success");
    statusLabel.classList.add("label-danger");
    document.getElementById("status").innerText = "Offline";
}

function setOnline(){
    var statusLabel = document.getElementById("statusLabel");
    statusLabel.classList.remove("label-danger");
    statusLabel.classList.add("label-success");
    document.getElementById("status").innerText = "Online";

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
    ajax('POST', Application.mainUrl, JSON.stringify(message), function(responseText){
        messageField.value = "";
    });

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
function loadMessages(){
    var url = Application.mainUrl + '?token=' + Application.token;

    ajax('GET', url, null, function(responseText){
        var response = JSON.parse(responseText);
        setOnline();
        response.messages.forEach(function(item, i, arr){
            addMessage(item);
            renderMessage(messageList[messageList.length - 1]);
        });
        Application.token = response.token;
    }, function() {
        setOffline();
    });
}

function loadFromStorage(){
    checkStorage();
    /*messageList = localStorage.getItem(messagesKey);
    if(!messageList){
        messageList = [newMessage("Welcome to our chat!", "System")]
    }
    else{
        messageList = JSON.parse(messageList)
    }
    */
    author = getCookie("user_name");
    if(!author){
        author = "Guest";
    }
}
