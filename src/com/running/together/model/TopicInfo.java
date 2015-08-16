package com.running.together.model;

public class TopicInfo {
	private String topic_header;
	private String topic_name;
	private String topic_introduce;

	public TopicInfo(String topic_header, String topic_name,
			String topic_introduce) {
		super();
		this.topic_header = topic_header;
		this.topic_name = topic_name;
		this.topic_introduce = topic_introduce;
	}

	public String getTopic_header() {
		return topic_header;
	}

	public void setTopic_header(String topic_header) {
		this.topic_header = topic_header;
	}

	public String getTopic_name() {
		return topic_name;
	}

	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}

	public String getTopic_introduce() {
		return topic_introduce;
	}

	public void setTopic_introduce(String topic_introduce) {
		this.topic_introduce = topic_introduce;
	}

	@Override
	public String toString() {
		return "TopicInfo [topic_header=" + topic_header + ", topic_name="
				+ topic_name + ", topic_introduce=" + topic_introduce + "]";
	}

}
