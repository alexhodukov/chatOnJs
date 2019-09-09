const FROM_ID = 1;
setInitialSettings();
getUsers();
addHandlers();
getNewMessages();
var users = [];
var activeUser = null;


function setInitialSettings() {
    var elemRightColumn = document.getElementById("rightColumn");
    elemRightColumn.style.display = "none";
}
function getUsers() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "chat/users", true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            users = JSON.parse(xhr.responseText);
            showUsers(users);
        }
    }
    xhr.send();
}

function getCorrespondence() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "chat/messages/" + activeUser.id, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var arMsgs = JSON.parse(xhr.responseText);
            // console.log(arMsgs);
            showCorrespondence(arMsgs);
        }
    }
    xhr.send();
}

function getNewMessages() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "chat?id=" + FROM_ID, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var msg = JSON.parse(xhr.responseText);
            if (msg) {
                msg.date = new Date(msg.date);
                addMessage(msg);
                // console.log(msg);
            }
            getNewMessages();
        }
    }
    xhr.send();
}

function sendMessage(message) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "chat/sendMessage", true);
    xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            // console.log("Successfully");
        }
    }
    var json = JSON.stringify(message);
    xhr.send(json);
}

function addHandlers() {
    var elemListUsers = document.getElementById("listUsers");
    elemListUsers.addEventListener("change", function () {
        var id = Number(elemListUsers.options[elemListUsers.selectedIndex].value);
        activeUser = users.find(user => user.id === id);
        var elemActive = document.getElementById("active");

        elemActive.textContent = "Active: " + activeUser.name;
        var elemRightColumn = document.getElementById("rightColumn");
        elemRightColumn.style.display = "block";
        var elemCorrespondence = document.getElementById("correspondence");
        elemCorrespondence.value = "";
        getCorrespondence();
    });

    var elemClose = document.getElementById("close");
    elemClose.addEventListener("click", function () {
        var elemRightColumn = document.getElementById("rightColumn");
        elemRightColumn.style.display = "none";
        activeUser = null;
        var elemActive = document.getElementById("active");
        elemActive.textContent = "";
    })

    var elemSend = document.getElementById("send");
    elemSend.addEventListener("click", function() {
        if (inputMessage.value.trim() != "") {
            var message = new Message(inputMessage.value, new Date());
            addMessage(message);
            sendMessage(message);
        }
        inputMessage.value = "";
    });
}

function showUsers(users) {
    console.log(users);
    var elemListUsers = document.getElementById("listUsers");
    elemListUsers.innerHTML = "";
    for(var i = 0; i < users.length; i++) {
        var elemUser = document.createElement("option");
        elemUser.textContent = users[i].name;
        elemUser.value = users[i].id;
        elemListUsers.appendChild(elemUser);
    }
}

function showCorrespondence(arMsgs) {
    for(var i = 0; i < arMsgs.length; i++) {
        var msg = arMsgs[i];
        msg.date = new Date(msg.date);
        addMessage(msg);
    }
}

function addMessage(message) {
    function getFormatMessage() {
        return this.date.getHours() + ":" + this.date.getMinutes() + ":" + this.date.getSeconds() + " " +
               this.from + " : " + this.message + "\n";
    }
    var getFormatMessage = getFormatMessage.bind(message);
    var elemCorrespondence = document.getElementById("correspondence");
    elemCorrespondence.value += getFormatMessage();
    elemCorrespondence.scrollTop = elemCorrespondence.scrollHeight;
}

var elemSortBy = document.getElementById("sortBy");
elemSortBy.addEventListener("change", doSort);

function doSort() {
    var value = elemSortBy.options[elemSortBy.selectedIndex].value;
    showUsers(users.sort(function (a, b) {
        if(a[value] > b[value]) {
            return 1;
        }
        if(a[value] < b[value]) {
            return -1;
        }
        return 0;
    }));
}

function Message(message, date) {
    this.message = message;
    this.date = date;
    this.from = "Admin";
    this.fromId = FROM_ID;
    this.to = activeUser.id;
}