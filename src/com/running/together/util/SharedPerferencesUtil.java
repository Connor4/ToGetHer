package com.running.together.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPerferencesUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	public static final String LOGIN_USERID = "userID";
	public static final String PLATFORM_QQ = "QQ";
	public static final String PLATFORM_SINA = "SINA";

	public SharedPerferencesUtil(Context context, String fileName) {
		sp = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		editor = sp.edit();
	}
//保存用户ID
	public void setUserID(String user_id) {
		editor.putString("userID", user_id);
		editor.commit();
	}

	public String getUserID() {
		return sp.getString("userID", null);
	}
//保存用户密码--openfire长连接需要
		public void setUserPass(String user_id) {
			editor.putString("userPass", user_id);
			editor.commit();
		}

		public String getUserPass() {
			return sp.getString("userPass", null);
		}
//保存用户网络头像
	public void setUserIcon(String user_icon) {
		editor.putString("userIcon", user_icon);
		editor.commit();
	}

	public String getUserIcon() {
		return sp.getString("userIcon", null);
	}
//保存用户昵称
	public void setNickName(String nickname) {
		editor.putString("NickName", nickname);
		editor.commit();
	}

	public String getNickName() {
		return sp.getString("NickName", null);
	}
//保存是否第一次登陆的状态
	public void setIsFirst(boolean isFirst) {
		editor.putBoolean("isFirst", isFirst);
		editor.commit();
	}

	public boolean getIsFirst() {
		return sp.getBoolean("isFirst", true);
	}
//保存用户本地头像路径
	public void setLocalHeader(String pic_url) {
		editor.putString("pic_url", pic_url);
		editor.commit();
	}

	public String getLocalHeader() {
		return sp.getString("pic_url", null);
	}

	// 设置消息通知开关
	public void setToggleMessage(boolean isCheck) {
		editor.putBoolean("toggle_msg", isCheck);
		editor.commit();
	}

	public boolean getToggleMessage() {
		return sp.getBoolean("toggle_msg", true);
	}

	// 设置震动开关
	public void setToggleVibration(boolean isCheck) {
		editor.putBoolean("toggle_vibration", isCheck);
		editor.commit();
	}

	public boolean getToggleVibration() {
		return sp.getBoolean("toggle_vibration", true);
	}

	// 设置声音开关
	public void setToggleVoice(boolean isCheck) {
		editor.putBoolean("toggle_voice", isCheck);
		editor.commit();
	}

	public boolean getToggleVoice() {
		return sp.getBoolean("toggle_voice", true);
	}
	//完善用户信息
	public  void setCompleteInfo(boolean isAgreed){
		editor.putBoolean("complete", isAgreed);
		editor.commit();
	}
	public boolean getCompleteInfo(){
		return sp.getBoolean("complete", true);
	}
	//注销登录，清除账户信息
	public void clearUserInfo(){
		//清除userID、userIcon、NickName、pic_url信息
		editor.remove("userID");
		editor.remove("userPass");
		editor.remove("userIcon");
		editor.remove("NickName");
		editor.remove("pic_url");
		editor.remove("complete");
		editor.remove("platform");
		editor.commit();
	}
	//平台登录标记
	public void setLoginPlatform(int platform) {
		editor.putInt("platform", platform);
		editor.commit();
	}
	public int getLoginPlatform(){
		return sp.getInt("platform", 0);
	}
	public void setFriendID(String FID){
		editor.putString("FID", FID);
		editor.commit();
	}
	public String getFriendID(){
		return sp.getString("FID", null);
	}

}
