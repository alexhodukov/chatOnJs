var config = {};

window.onload = function() {
	getConfig();
}

function setInputTextValue(id) {
	var elem = document.getElementById(id);
	elem.value = config[id];
	elem.addEventListener("change", function() {
		config[id] = elem.value;
		updateConfig();
	})
}

function setSelectValue(id) {
	var elem = document.getElementById(id);
	elem.value = config[id];
	elem.addEventListener("change", function() {
		config[id] = elem.options[elem.selectedIndex].value;
		updateConfig();
	})
}

function setInputCheckboxValue(id) {
	var elem = document.getElementById(id);
	elem.checked = config[id];
	elem.addEventListener("change", function() {
		config[id] = elem.checked;
		updateConfig();
	})
}

function updateConfig() {
	console.log(config);
	showConfig(config);

	var xhr = new XMLHttpRequest();
	xhr.open("POST", "chat/config", true);
	xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			console.log(xhr);
		}
	}
	var json = JSON.stringify(config);
	console.log("json " + json);
	xhr.send(json);
}

function getConfig() {
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'chat/config', true);
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			config = JSON.parse(xhr.responseText);
			console.log(config);
			showConfig(config);

			setInputTextValue("title");
			setInputTextValue("botName");
			setInputTextValue("chatUrl");
			setInputTextValue("cssClass");
			setSelectValue("position");
			setInputCheckboxValue("allowToMinimaize");
			setInputCheckboxValue("allowToDrag");
			setInputCheckboxValue("requireName");
			setInputCheckboxValue("showDateTime");
		}
	}
	xhr.send();
}

function showConfig(conf) {
	var str = "<script>" + "\n" + "\u00A0" + "(function() {" + "\n" + "\u00A0"
			+ "new TsChat({" + "\n" + "\u00A0" + "\u00A0" + "title: "
			+ conf.title
			+ "\n"
			+ "\u00A0"
			+ "\u00A0"
			+ "botName:"
			+ conf.botName
			+ "\n"
			+ "\u00A0"
			+ "\u00A0"
			+ "chatUrl:"
			+ conf.chatUrl
			+ "\n"
			+ "\u00A0"
			+ "\u00A0"
			+ "cssClass:"
			+ conf.cssClass
			+ "\n"
			+ "\u00A0"
			+ "\u00A0"
			+ "position:"
			+ conf.position
			+ "\n"
			+ "\u00A0"
			+ "\u00A0"
			+ "isAllowToMinimaize:"
			+ conf.allowToMinimaize
			+ "\n"
			+ "\u00A0"
			+ "\u00A0"
			+ "isAllowToDrag:"
			+ conf.allowToDrag
			+ "\n"
			+ "\u00A0"
			+ "\u00A0"
			+ "isRequireName:"
			+ conf.requireName
			+ "\n"
			+ "\u00A0"
			+ "\u00A0"
			+ "isShowDateTime:"
			+ conf.showDateTime
			+ "\n"
			+ "\u00A0" + "});" + "\n" + "})();" + "\n" + "</script>";
	document.getElementById("label").innerText = str;
}