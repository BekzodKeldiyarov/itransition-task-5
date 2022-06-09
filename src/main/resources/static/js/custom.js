let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;

function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $button = $('#messageSendBtn');
    $textarea = $('#messageBody');
    $title = $('#messageTitle');
}

// function render(message, username) {
//
//     var templateResponse = Handlebars.compile($("#message-response-template").html());
//     var contextResponse = {
//         response: message,
//         time: getCurrentTime(),
//         username: username
//     };
//
//     setTimeout(function () {
//         $chatHistoryList.append(templateResponse(contextResponse));
//         scrollToBottom();
//     }.bind(this), 1500);
// }

function sendMessage(message, title) {
    let username = $('#username').val();
    sendMsg(username, title, message);
    if (message.trim() !== '') {
        var template = Handlebars.compile($("#send-message-template").html());
        var context = {
            message: message, title: title, receiver: username, id: title
        };
        $('#accordionSendMessages').prepend(template(context));

        $title.val('');
        tinymce.get("messageBody").setContent('');
    }
}

function addMessage() {
    sendMessage(tinymce.get("messageBody").getContent(), $title.val());
}

function addMessageEnter(event) {
    if (event.keyCode === 13) {
        addMessage();
    }
}

init();


tinymce.init({
    selector: '#messageBody',
    plugins: ['advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview', 'anchor', 'searchreplace', 'visualblocks', 'fullscreen', 'insertdatetime', 'media', 'table', 'help', 'wordcount'],
    toolbar: 'undo redo | formatpainter casechange blocks | bold italic backcolor | ' + 'alignleft aligncenter alignright alignjustify | ' + 'bullist numlist checklist outdent indent | removeformat | a11ycheck code table help'
});

