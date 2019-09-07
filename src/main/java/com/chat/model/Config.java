package com.chat.model;

public class Config {
	private String title;
	private String botName;
	private String chatUrl;
	private String cssClass;
	private String position;
	private boolean isAllowToMinimaize;
	private boolean isAllowToDrag;
	private boolean isRequireName;
	private boolean isShowDateTime;
	private String network;
	
	
	
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getBotName() {
		return botName;
	}



	public void setBotName(String botName) {
		this.botName = botName;
	}



	public String getChatUrl() {
		return chatUrl;
	}



	public void setChatUrl(String chatUrl) {
		this.chatUrl = chatUrl;
	}



	public String getCssClass() {
		return cssClass;
	}



	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public boolean isAllowToMinimaize() {
		return isAllowToMinimaize;
	}



	public void setAllowToMinimaize(boolean isAllowToMinimaize) {
		this.isAllowToMinimaize = isAllowToMinimaize;
	}



	public boolean isAllowToDrag() {
		return isAllowToDrag;
	}



	public void setAllowToDrag(boolean isAllowToDrag) {
		this.isAllowToDrag = isAllowToDrag;
	}



	public boolean isRequireName() {
		return isRequireName;
	}



	public void setRequireName(boolean isRequireName) {
		this.isRequireName = isRequireName;
	}



	public boolean isShowDateTime() {
		return isShowDateTime;
	}



	public void setShowDateTime(boolean isShowDateTime) {
		this.isShowDateTime = isShowDateTime;
	}



	public String getNetwork() {
		return network;
	}



	public void setNetwork(String network) {
		this.network = network;
	}



	@Override
	public String toString() {
		return "Config [title=" + title + ", nameBot=" + botName + ", url=" + chatUrl + ", cssClass=" + cssClass
				+ ", position=" + position + ", isAllowToMinimaize=" + isAllowToMinimaize + ", isAllowDrag="
				+ isAllowToDrag + ", isRequireName=" + isRequireName + ", isShowDateTime=" + isShowDateTime + ", network="
				+ network + "]";
	}
	
	
}
