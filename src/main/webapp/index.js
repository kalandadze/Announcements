const webSocket = new WebSocket('ws://localhost:8989/announcements/websocket');

webSocket.onerror = function (event) {
    onError(event)
};
webSocket.onopen = function (event) {
    onOpen(event)
};
webSocket.onmessage = function (event) {
    onMessage(event)
};

function onMessage(event) {
    console.log(event);
    addMessage(event);
}

function onOpen(event) {
    console.log("Welcome!");
}

function onError(event) {
    alert('An error occurred:' + event.data);
}

function send() {
    var input = document.getElementById("inpt");
    webSocket.send(input.value);
    input.value = "";
}

function addMessage(event){
    var div=document.getElementById("messages");
    var p=document.createElement("p");
    p.textContent=event.data;
    div.appendChild(p);
}