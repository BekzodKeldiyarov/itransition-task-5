const url = "http://localhost:8080";
let stompClient;
let selectedUser;
let username;
let newMessageCounter = 0;

function connectToChat(username) {
    // console.log("connecting to chat");
    let socket = new SockJS(url + "/chat");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log("Connected to :" + frame);
        stompClient.subscribe("/topic/messages/" + username, function (response) {
            // console.log("subscribed");
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin) {
                // render(data.message, data.fromLogin);
            }
            var template = Handlebars.compile($("#toast").html());
            var context = {
                message: data.message, title: data.title, username: data.fromLogin
            };
            $('#toasts').html('');
            $('#toasts').append(template(context));

            $('.toast').toast('show');
            newMessageCounter = newMessageCounter + 1;
            $('#newMessageCounter').html('<span style="color: red">' + newMessageCounter + '</span>');

        })
    })

    $('#loggedUserName').html('You are logged as ' + username);
}


function sendMsg(from, title, text) {
    selectedUser = $('#messageTo').val();
    // console.log(selectedUser);
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from, title: title, message: text
    }))
    setTimeout(function () {
    }, 1000);
    getAllMessagesSendToUser(selectedUser);
}

function register() {
    username = document.getElementById("username").value;
    // console.log(username);
    $.get(url + "/register/" + username, function (response) {
    }).fail(function (error) {
        if (error.status === 400) {
        }
    })
    connectToChat(username);
    sendMessages();
}


function getAllUsers() {
    $('#receivedMailBlock').addClass('d-none');
    $('#sendMails').addClass('d-none');
    $('#sendNewMailBlock').removeClass('d-none');
    $.get(url + "/allUsers/", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<option value="' + users[i].login + '">' + users[i].login + '</option>'
        }
        $('#users').html(usersTemplateHTML);
    })
}

function selectUser(username) {
    selectedUser = username;
    // console.log("selecting user " + username);
    $('#selectedUser').html('');
    $('#selectedUser').append("Chat with " + username)
}


function receivedMessages() {
    $('#sendNewMailBlock').addClass('d-none');
    $('#sendMails').addClass('d-none');
    $('#receivedMailBlock').removeClass('d-none');

    newMessageCounter = 0;
    $('#newMessageCounter').html('');
    $.get(url + "/getMessages/" + username, function (response) {
        let messages = response;
        for (let i = 0; i < messages.length; i++) {
            var template = Handlebars.compile($("#received-message-template").html());
            var context = {
                message: messages[i].message, title: messages[i].title, sender: messages[i].fromLogin, id: i
            };
            $('#accordionReceivedMessage').append(template(context));
        }
    })
}

function sendMessages() {
    $('#sendNewMailBlock').addClass('d-none');
    $('#receivedMailBlock').addClass('d-none');
    $('#sendMails').removeClass('d-none');
    $('#accordionSendMails').html('');
    $.get(url + "/getMessagesSendFrom/" + username, function (response) {
        let messages = response;
        for (let i = 0; i < messages.length; i++) {
            var template = Handlebars.compile($("#send-message-template").html());
            var context = {
                message: messages[i].message, title: messages[i].title, receiver: messages[i].toLogin, id: i
            };
            $('#accordionSendMails').append(template(context));
        }
    });
}


let timer;
$('#messageTo').keypress(function () {
    clearTimeout(timer);
    timer = setTimeout(function () {
        let toUser = $('#messageTo').val();
        // console.log(toUser);
        getAllMessagesSendToUser(toUser);
    }, 3000); //Waits for 3 seconds after last keypress to execute the above lines of code
});

function getAllMessagesSendToUser(toUser) {

    $.get(url + "/getMessagesSendFrom/" + username + "/to/" + toUser, function (response) {
        let messages = response;
        for (let i = 0; i < messages.length; i++) {
            var template = Handlebars.compile($("#send-message-template").html());
            var context = {
                message: messages[i].message, title: messages[i].title, receiver: messages[i].toLogin, id: i
            };
            $('#accordionSendMessages').append(template(context));
        }
    });
}


