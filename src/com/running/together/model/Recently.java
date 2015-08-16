package com.running.together.model;

public class Recently {
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Recently(String username,String nickname,String url,String time,String text,int state){
		this.url=url;
		this.nickname=nickname;
		this.text=text;
		this.time=time;
		this.username=username;
		this.setState(state);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	private String username;
	private String nickname;
	private String time;
	private String text;
	private String url;
	private int state;
}
