var chatContainer = document.createElement('fieldset');
chatContainer.innerHTML = "<legeng>KUKU</legend>";
chatContainer.style.width = "400px";
chatContainer.style.height = "500px";
chatContainer.style.display = "block";
chatContainer.style.border = "2px solid green";
chatContainer.style.margin = "5px";
// chatContainer.style.padding = "1px";
chatContainer.style.position = "absolute";
chatContainer.style.bottom = "10px";
chatContainer.style.right = "10px";

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

var btnSend = document.createElement('button');
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

var btnScrollMin = document.createElement('button');
btnScrollMin.id = "btnMin";
btnScrollMin.textContent = "-";
btnScrollMin.style.width = "20px";
btnScrollMin.style.height = "20px";
btnScrollMin.style.border = "1px solid green";
btnScrollMin.style.margin = "1px";
btnScrollMin.style.padding = "1px";
btnScrollMin.style.float = "right";

var divMinimaize = document.createElement('div');
divMinimaize.style.width = "400px";
divMinimaize.style.height = "30px";
divMinimaize.style.display = "block";
divMinimaize.style.border = "2px solid green";
divMinimaize.style.margin = "5px";
divMinimaize.style.position = "absolute";
divMinimaize.style.bottom = "10px";
divMinimaize.style.right = "10px";

var btnScrollMax = document.createElement('button');
btnScrollMax.textContent = "[ ]";
btnScrollMax.style.width = "20px";
btnScrollMax.style.height = "20px";
btnScrollMax.style.border = "1px solid green";
btnScrollMax.style.margin = "1px";
btnScrollMax.style.padding = "1px";
btnScrollMax.style.float = "right";

var parentElem = document.body;
parentElem.style.cssText = "";
parentElem.appendChild(divMinimaize);
parentElem.appendChild(chatContainer);

chatContainer.appendChild(correspondence);
chatContainer.appendChild(btnScrollMin);
chatContainer.appendChild(inputMessage);
chatContainer.appendChild(btnSend);
divMinimaize.appendChild(btnScrollMax);


var css = "button:active {transform: scale(0.98); background: rgb(219, 212, 212);}";
var style = document.createElement('style');
if (style.styleSheet) {
    style.styleSheet.cssText = css;
} else {
    style.appendChild(document.createTextNode(css));
}
document.getElementsByTagName('head')[0].appendChild(style);


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
    divMinimaize.style.display = "block";
}

function chatMaximaize() {
    divMinimaize.style.display = "none";
    chatContainer.style.display = "block";
}

btnSend.addEventListener("click", function() {
    if (inputMessage.value.trim() != "") {
        var message = createMessage(inputMessage.value, "You");
        saveMessage(message);
        addMessage(message);

        var xhr = new XMLHttpRequest();

        var json = JSON.stringify({
            message : createMessage(inputMessage.value, "You"),
            date : new Date(),
            from : "YOU"
        });
        
        xhr.open("POST", '/chat/message', true)
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        
        xhr.onreadystatechange = function() {
            alert( this.responseText );
        }
        
        // Отсылаем объект в формате JSON и с Content-Type application/json
        // Сервер должен уметь такой Content-Type принимать и раскодировать
        xhr.send(json);

        var msg = {
            
        }

    
        var botMessage = "Answer to \"" + inputMessage.value.toUpperCase() + "\"";
        setTimeout(() => {
            var message = createMessage(botMessage, "Bot");
            saveMessage(message);
            addMessage(message);
        }, 2000);
    }
    inputMessage.value = "";
});

function createMessage(message, from) {
    var date = new Date();
    var result;
    result = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() +
        " " + from + " : " + message + "\n";
    return result;
    
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




   

