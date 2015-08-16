package com.running.together.model;

public class HeaderInfo {
	private String userId;
	private String userHeader;

	public HeaderInfo(String userId, String userHeader) {
		super();
		this.userId = userId;
		this.userHeader = userHeader;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserHeader() {
		return userHeader;
	}

	public void setUserHeader(String userHeader) {
		this.userHeader = userHeader;
	}

	@Override
	public String toString() {
		return "HeaderInfo [userId=" + userId + ", userHeader=" + userHeader
				+ "]";
	}

}
