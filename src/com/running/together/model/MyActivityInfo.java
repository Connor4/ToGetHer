package com.running.together.model;

public class MyActivityInfo {
	private String myaction_header;
	private String myaction_title;
	private String myaction_launcher;
	private String myaction_time;
	private String myaction_address;
	private String myaction_content;
	private String myaction_add;
	private String myaction_comment;
	private String myaction_read;

	public MyActivityInfo(String myaction_header, String myaction_title,
			String myaction_launcher, String myaction_time,
			String myaction_address, String myaction_content,
			String myaction_add, String myaction_comment, String myaction_read) {
		super();
		this.myaction_header = myaction_header;
		this.myaction_title = myaction_title;
		this.myaction_launcher = myaction_launcher;
		this.myaction_time = myaction_time;
		this.myaction_address = myaction_address;
		this.myaction_content = myaction_content;
		this.myaction_add = myaction_add;
		this.myaction_comment = myaction_comment;
		this.myaction_read = myaction_read;
	}

	public String getMyaction_header() {
		return myaction_header;
	}

	public void setMyaction_header(String myaction_header) {
		this.myaction_header = myaction_header;
	}

	public String getMyaction_title() {
		return myaction_title;
	}

	public void setMyaction_title(String myaction_title) {
		this.myaction_title = myaction_title;
	}

	public String getMyaction_launcher() {
		return myaction_launcher;
	}

	public void setMyaction_launcher(String myaction_launcher) {
		this.myaction_launcher = myaction_launcher;
	}

	public String getMyaction_time() {
		return myaction_time;
	}

	public void setMyaction_time(String myaction_time) {
		this.myaction_time = myaction_time;
	}

	public String getMyaction_address() {
		return myaction_address;
	}

	public void setMyaction_address(String myaction_address) {
		this.myaction_address = myaction_address;
	}

	public String getMyaction_content() {
		return myaction_content;
	}

	public void setMyaction_content(String myaction_content) {
		this.myaction_content = myaction_content;
	}

	public String getMyaction_add() {
		return myaction_add;
	}

	public void setMyaction_add(String myaction_add) {
		this.myaction_add = myaction_add;
	}

	public String getMyaction_comment() {
		return myaction_comment;
	}

	public void setMyaction_comment(String myaction_comment) {
		this.myaction_comment = myaction_comment;
	}

	public String getMyaction_read() {
		return myaction_read;
	}

	public void setMyaction_read(String myaction_read) {
		this.myaction_read = myaction_read;
	}

	@Override
	public String toString() {
		return "ActivityInfo [myaction_header=" + myaction_header
				+ ", myaction_title=" + myaction_title + ", myaction_launcher="
				+ myaction_launcher + ", myaction_time=" + myaction_time
				+ ", myaction_address=" + myaction_address
				+ ", myaction_content=" + myaction_content + ", myaction_add="
				+ myaction_add + ", myaction_comment=" + myaction_comment
				+ ", myaction_read=" + myaction_read + "]";
	}

}
