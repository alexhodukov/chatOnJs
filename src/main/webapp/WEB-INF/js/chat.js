var config = {};
var botName = {};
var userName = "You";

var chatContainer = document.createElement("fieldset");
chatContainer.style.width = "400px";
chatContainer.style.height = "520px";
chatContainer.style.display = "block";
chatContainer.style.border = "2px solid green";
chatContainer.style.margin = "5px";
chatContainer.style.padding = "1px";
chatContainer.style.position = "absolute";
chatContainer.style.bottom = "10px";

var legendForChatContainer = document.createElement("legend");
legendForChatContainer.textContent = "ChatDefault";

var correspondence = document.createElement("textArea");
correspondence.readOnly = "true";
correspondence.style.width = "350px";
correspondence.style.height = "320px";
correspondence.style.display = "block";
correspondence.style.border = "none";
correspondence.style.margin = "5px";
correspondence.style.marginRight = "2px";
correspondence.style.padding = "5px";
correspondence.style.float = "left";
correspondence.style.resize = "none";

var inputMessage = document.createElement("textArea");
inputMessage.style.width = "320px";
inputMessage.style.height = "138px";
inputMessage.style.display = "block";
inputMessage.style.border = "1px solid green";
inputMessage.style.margin = "5px";
inputMessage.style.marginRight = "2px";
inputMessage.style.padding = "5px";
inputMessage.style.float = "left";
inputMessage.style.resize = "none";

var btnSend = document.createElement("button");
btnSend.textContent = "Send";
btnSend.id = "btnSend"; 
btnSend.style.width = "50px";
btnSend.style.height = "150px";
btnSend.style.display = "block";
btnSend.style.border = "1px solid green";
btnSend.style.margin = "5px";
btnSend.style.marginLeft = "2px";
btnSend.style.float = "left";
btnSend.style.top = "0px";

var btnScrollMin = document.createElement("button");
btnScrollMin.id = "btnMin";
btnScrollMin.textContent = "-";
btnScrollMin.style.width = "20px";
btnScrollMin.style.height = "20px";
btnScrollMin.style.border = "1px solid green";
btnScrollMin.style.margin = "1px";
btnScrollMin.style.padding = "1px";
btnScrollMin.style.float = "right";

var containerMinimaize = document.createElement("fieldset");
containerMinimaize.style.width = "400px";
containerMinimaize.style.height = "45px";
containerMinimaize.style.display = "block";
containerMinimaize.style.border = "2px solid green";
containerMinimaize.style.margin = "5px";
containerMinimaize.style.position = "absolute";
containerMinimaize.style.bottom = "10px";

var legendForContainerMinimaize = document.createElement("legend");
legendForContainerMinimaize.textContent = "ChatDefault";

var btnScrollMax = document.createElement("button");
btnScrollMax.textContent = "[ ]";
btnScrollMax.style.width = "20px";
btnScrollMax.style.height = "20px";
btnScrollMax.style.border = "1px solid green";
btnScrollMax.style.margin = "1px";
btnScrollMax.style.padding = "1px";
btnScrollMax.style.float = "right";

var parentElem = document.body;
parentElem.style.cssText = "";
parentElem.appendChild(containerMinimaize);
parentElem.appendChild(chatContainer);

chatContainer.appendChild(legendForChatContainer);
chatContainer.appendChild(correspondence);
chatContainer.appendChild(btnScrollMin);
chatContainer.appendChild(inputMessage);
chatContainer.appendChild(btnSend);
containerMinimaize.appendChild(legendForContainerMinimaize);
containerMinimaize.appendChild(btnScrollMax);


var css = "button:active {transform: scale(0.98); background: rgb(219, 212, 212);}";
var style = document.createElement('style');
if (style.styleSheet) {
    style.styleSheet.cssText = css;
} else {
    style.appendChild(document.createTextNode(css));
}
document.getElementsByTagName('head')[0].appendChild(style);

getConfig();

function getConfig() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'chat/config', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            config = JSON.parse(xhr.responseText);
            console.log(config);
            legendForChatContainer.textContent = config.title;
            legendForContainerMinimaize.textContent = config.title;
            botName = config.botName;
            if (config.allowToDrag) {
                addDragAndDrop();
            }
            if (config.allowToMinimaize) {
                addEventListenerScroll();
            } else {
                chatMaximaize();
            }
            chatContainer["style"][config.position] = "10px";
            containerMinimaize["style"][config.position] = "10px";
            if (config.requireName) {
                askUserName();
            }
        }
    }
    xhr.send();
}

function addDragAndDrop() {
    var dragObject = {};

    legendForChatContainer.addEventListener("mousedown", function (e) {
        dragObject.elem = chatContainer;
        dragObject.downX = e.pageX;
        dragObject.downY = e.pageY;
    })

    legendForChatContainer.addEventListener("mousemove", function (e) {
        if (!dragObject.elem) {
            return;
        }

        if (!dragObject.startMoving) {
            var moveX = e.pageX - dragObject.downX;
            var moveY = e.pageY - dragObject.downY;
            if ( Math.abs(moveX) < 2 && Math.abs(moveY) < 2 ) {
                return;
            }

            var coords = getCoords(chatContainer);
            dragObject.shiftX = dragObject.downX - coords.left;
            dragObject.shiftY = dragObject.downY - coords.top;

            chatContainer.style.zIndex = 50;
            chatContainer.style.position = 'absolute';

            dragObject.startMoving = true;

            function getCoords(elem) {
                var box = elem.getBoundingClientRect();
                return {
                    top: box.top + pageYOffset,
                    left: box.left + pageXOffset
                };
            }
        }

        chatContainer.style.left = e.pageX - dragObject.shiftX + 'px';
        chatContainer.style.top = e.pageY - dragObject.shiftY + 'px';

        return false;
    });

    legendForChatContainer.addEventListener("mouseup", function () {
        console.log("up");
        dragObject = {};
    });

    chatContainer.addEventListener("dragstart", function() {
        return false;
    });
}

function addEventListenerScroll() {
    btnScrollMin.addEventListener("click", function() {
        console.log("btn scroll min");
        chatMinimaize();
        sessionStorage.setItem("chatMinimaize", true);
    });

    btnScrollMax.addEventListener("click", function() {
        console.log("btn scroll max");
        chatMaximaize();
        sessionStorage.setItem("chatMinimaize", false);
        console.log("storage " + sessionStorage.getItem("chatMinimaize"));
    });
}

function askUserName() {
    var name = prompt("Please, enter your name", "").trim();
	while (!name) {
        alert("Name must not be empty");
        name = prompt("Please, enter your name", "").trim();
    }
    userName = name;
    console.log(userName);
}

var isChatMinimaize = JSON.parse(sessionStorage.getItem("chatMinimaize"));
if (isChatMinimaize == null) {
    sessionStorage.setItem("chatMinimaize", true);
    chatMinimaize();
} else {
    if (isChatMinimaize) {
        chatMinimaize();
    } else {;
        chatMaximaize();
    }
}

function chatMinimaize() {
    chatContainer.style.display = "none";
    containerMinimaize.style.display = "block";
}

function chatMaximaize() {
	containerMinimaize.style.display = "none";
    chatContainer.style.display = "block";
}

btnSend.addEventListener("click", function() {
    if (inputMessage.value.trim() != "") {
        var message = createMessage(inputMessage.value, userName);
        console.log("userName " + userName);
        saveMessage(message);
        addMessage(message);
        
        var botMessage = "Answer to \"" + inputMessage.value.toUpperCase() + "\"";
        setTimeout(() => {
            var message = createMessage(botMessage, botName);
            saveMessage(message);
            addMessage(message);
        }, 2000);
    }
    inputMessage.value = "";
});

function createMessage(message, from) {
    if (config.showDateTime) {
        var date = new Date();
        return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() +
            " " + from + " : " + message + "\n";
    } else {
        return from + " : " + message + "\n";
    }
} 

function addMessage(message) {
    correspondence.value += message;
    correspondence.scrollTop = correspondence.scrollHeight;
}

function saveMessage(message) {
    var arMessage = [];
    var objAr = JSON.parse(sessionStorage.getItem("messages"));
    if (objAr) {
        arMessage = objAr["messages"];
        arMessage.push(message);
    } else {
        arMessage.push(message);
    }
    sessionStorage.setItem("messages", JSON.stringify({messages : arMessage}));
}




window.onload = function() {
    if (correspondence.value == "") {
        var objAr = JSON.parse(sessionStorage.getItem("messages"));
        if (objAr) {
            arMessage = objAr["messages"];
            for (i = 0; i < arMessage.length; i++) {
                addMessage(arMessage[i]);
            }
        }
    }
}