package com.running.together.model;

public class RecordInfo {
	private String allDistance;
	private String allTime;
	private String allCalorie;
	private String largeDistance;
	private String largeTime;
	private String largeSpeed;
	private String largeCalorie;
	public RecordInfo(String allDistance, String allTime, String allCalorie,
			String largeDistance, String largeTime, String largeSpeed,
			String largeCalorie) {
		super();
		this.allDistance = allDistance;
		this.allTime = allTime;
		this.allCalorie = allCalorie;
		this.largeDistance = largeDistance;
		this.largeTime = largeTime;
		this.largeSpeed = largeSpeed;
		this.largeCalorie = largeCalorie;
	}
	public String getAllDistance() {
		return allDistance;
	}
	public void setAllDistance(String allDistance) {
		this.allDistance = allDistance;
	}
	public String getAllTime() {
		return allTime;
	}
	public void setAllTime(String allTime) {
		this.allTime = allTime;
	}
	public String getAllCalorie() {
		return allCalorie;
	}
	public void setAllCalorie(String allCalorie) {
		this.allCalorie = allCalorie;
	}
	public String getLargeDistance() {
		return largeDistance;
	}
	public void setLargeDistance(String largeDistance) {
		this.largeDistance = largeDistance;
	}
	public String getLargeTime() {
		return largeTime;
	}
	public void setLargeTime(String largeTime) {
		this.largeTime = largeTime;
	}
	public String getLargeSpeed() {
		return largeSpeed;
	}
	public void setLargeSpeed(String largeSpeed) {
		this.largeSpeed = largeSpeed;
	}
	public String getLargeCalorie() {
		return largeCalorie;
	}
	public void setLargeCalorie(String largeCalorie) {
		this.largeCalorie = largeCalorie;
	}
	@Override
	public String toString() {
		return "RecordInfo [allDistance=" + allDistance + ", allTime="
				+ allTime + ", allCalorie=" + allCalorie + ", largeDistance="
				+ largeDistance + ", largeTime=" + largeTime + ", largeSpeed="
				+ largeSpeed + ", largeCalorie=" + largeCalorie + "]";
	}
	
}
