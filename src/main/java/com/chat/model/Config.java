package com.chat.model;

public class Config {
	private String title;
	private String nameBot;
	private String url;
	private String cssClass;
	private String position;
	private boolean isAllowToMinimaize;
	private boolean isAllowDrag;
	private boolean isRequireName;
	private boolean isShowDateTime;
	private String network;
	
	
	
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getNameBot() {
		return nameBot;
	}



	public void setNameBot(String nameBot) {
		this.nameBot = nameBot;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
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



	public boolean isAllowDrag() {
		return isAllowDrag;
	}



	public void setAllowDrag(boolean isAllowDrag) {
		this.isAllowDrag = isAllowDrag;
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
		return "Config [title=" + title + ", nameBot=" + nameBot + ", url=" + url + ", cssClass=" + cssClass
				+ ", position=" + position + ", isAllowToMinimaize=" + isAllowToMinimaize + ", isAllowDrag="
				+ isAllowDrag + ", isRequireName=" + isRequireName + ", isShowDateTime=" + isShowDateTime + ", network="
				+ network + "]";
	}
	
	
}
