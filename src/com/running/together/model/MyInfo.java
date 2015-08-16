package com.running.together.model;

/**
 * 用户信息类
 */
public class MyInfo {
	private String username;
	private String nickname;
	private String sex;
	private String age;
	private String school;
	private String personal;
	private String hobby;
	private String headpic;
	private String activity_num;
	private String group_num;
	private String fans_num;
	private String care_num;

	public MyInfo(String username, String nickname, String sex, String age,
			String school, String personal, String hobby, String headpic,
			String activity_num, String group_num, String fans_num,
			String care_num) {
		super();
		this.username = username;
		this.nickname = nickname;
		this.sex = sex;
		this.age = age;
		this.school = school;
		this.personal = personal;
		this.hobby = hobby;
		this.headpic = headpic;
		this.activity_num = activity_num;
		this.group_num = group_num;
		this.fans_num = fans_num;
		this.care_num = care_num;
	}

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public String getActivity_num() {
		return activity_num;
	}

	public void setActivity_num(String activity_num) {
		this.activity_num = activity_num;
	}

	public String getGroup_num() {
		return group_num;
	}

	public void setGroup_num(String group_num) {
		this.group_num = group_num;
	}

	public String getFans_num() {
		return fans_num;
	}

	public void setFans_num(String fans_num) {
		this.fans_num = fans_num;
	}

	public String getCare_num() {
		return care_num;
	}

	public void setCare_num(String care_num) {
		this.care_num = care_num;
	}

	@Override
	public String toString() {
		return "MyInfo [username=" + username + ", nickname=" + nickname
				+ ", sex=" + sex + ", age=" + age + ", school=" + school
				+ ", personal=" + personal + ", hobby=" + hobby + ", headpic="
				+ headpic + ", activity_num=" + activity_num + ", group_num="
				+ group_num + ", fans_num=" + fans_num + ", care_num="
				+ care_num + "]";
	}

}
