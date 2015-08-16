package com.running.together.model;

public class CommentInfo {
	private String header;
	private String userName;
	private String commentTime;
	private String commentContent;
	public CommentInfo(String header, String userName, String commentTime,
			String commentContent) {
		super();
		this.header = header;
		this.userName = userName;
		this.commentTime = commentTime;
		this.commentContent = commentContent;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	@Override
	public String toString() {
		return "CommentInfo [header=" + header + ", userName=" + userName
				+ ", commentTime=" + commentTime + ", commentContent="
				+ commentContent + "]";
	}
	
}