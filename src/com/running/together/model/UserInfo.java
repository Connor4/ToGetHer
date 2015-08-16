package com.running.together.model;

/**
 *用户信息类
 */
public class UserInfo {
	private String userAccount;
	private String userPassword;
	private String userNickName;
	private String userSex;
	private String userAge;
	private String userSchool;
	private String hobby;
	private String singture;
	private String headpic;
	
	//两个参数的构造方法（注册账户的时候用）
	public UserInfo(String userAccount,String userPassword){
		this.userAccount = userAccount;
		this.userPassword = userPassword;
	}
	//QQ登录使用
		public UserInfo(String userAccount,String userPassword,String userNickName,String headurl){
			this.userAccount=userAccount;
			this.userPassword = userPassword;
			this.userNickName=userNickName;
			this.setHeadpic(headurl);
		}
	//五个参数的构造方法（完善个人信息的时候用）
	public UserInfo(String userNickName, String userSex, String userAge,
			String userSchool, String hobby,String signture) {
		super();
		this.userNickName = userNickName;
		this.userSex = userSex;
		this.userAge = userAge;
		this.userSchool = userSchool;
		this.hobby = hobby;
		this.singture = signture;
	}
	//六个参数的构造方法（接受服务器端数据时用）
		public UserInfo(String userNickName, String userSex, String userAge,
				String userSchool, String hobby,String signture,String headpic) {
			super();
			this.userNickName = userNickName;
			this.userSex = userSex;
			this.userAge = userAge;
			this.userSchool = userSchool;
			this.hobby = hobby;
			this.singture = signture;
			this.headpic = headpic;
		}
	
	
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserAge() {
		return userAge;
	}
	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}
	public String getUserSchool() {
		return userSchool;
	}
	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	public String getSingture() {
		return singture;
	}
	public void setSingture(String singture) {
		this.singture = singture;
	}
	
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	@Override
	public String toString() {
		return "UserInfo [userAccount=" + userAccount + ", userPassword="
				+ userPassword + ", userNickName=" + userNickName
				+ ", userSex=" + userSex + ", userAge=" + userAge
				+ ", userSchool=" + userSchool + ", hobby=" + hobby
				+ ", singture=" + singture + ", headpic=" + headpic + "]";
	}
	
	
	

}
