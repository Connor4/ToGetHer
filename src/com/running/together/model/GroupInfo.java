package com.running.together.model;

public class GroupInfo {
	private String group_name;
	private String online_num;
	private String group_introduce;
	private String group_header;

	public GroupInfo(String group_name, String online_num,
			String group_introduce, String group_header) {
		super();
		this.group_name = group_name;
		this.online_num = online_num;
		this.group_introduce = group_introduce;
		this.group_header = group_header;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getOnline_num() {
		return online_num;
	}

	public void setOnline_num(String online_num) {
		this.online_num = online_num;
	}

	public String getGroup_introduce() {
		return group_introduce;
	}

	public void setGroup_introduce(String group_introduce) {
		this.group_introduce = group_introduce;
	}

	public String getGroup_header() {
		return group_header;
	}

	public void setGroup_header(String group_header) {
		this.group_header = group_header;
	}

	@Override
	public String toString() {
		return "GroupInfo [group_name=" + group_name + ", online_num="
				+ online_num + ", group_introduce=" + group_introduce
				+ ", group_header=" + group_header + "]";
	}

}
