package com.running.together.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.running.together.R;
import com.running.together.activity.LoginActivity.LoginThread;
import com.running.together.app.MyApplication;
import com.running.together.util.SharedPerferencesUtil;
import com.running.together.util.fangfa;

public class WellcomeActivity extends BaseActivity{
	private SharedPerferencesUtil spUtil;
	private MyApplication myApplication;
	private final int TO_MAINACTIVITY = 0x123;
	private final int TO_LOGINACTIVITY = 0x456;
	private final int TO_GUIDEACTIVIYT = 0x789;
	private final int DELAY_TIME = 2000;//暂停2000毫秒
	private boolean isFirst = false;
	private boolean isLogin = false;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case TO_LOGINACTIVITY:
				ToLoginActivity();
				break;
			case TO_MAINACTIVITY:
				ToMainActivity();
				break;
			case TO_GUIDEACTIVIYT:
				ToGuideActivity();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wellcome);
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		isFirst = spUtil.getIsFirst();
		if(isFirst){
			mHandler.sendEmptyMessageDelayed(TO_GUIDEACTIVIYT, DELAY_TIME);
			spUtil.setIsFirst(false);
		}else{
			String userID = spUtil.getUserID();
			if(null == userID){
				Log.d("MyTAG","userID:"+userID);
				//如果SharedPerference没有userID即为还没登录过
				mHandler.sendEmptyMessageDelayed(TO_LOGINACTIVITY, DELAY_TIME);
			}else{
				mHandler.sendEmptyMessageDelayed(TO_MAINACTIVITY, DELAY_TIME);
			}
			
		}
	}
	private void ToLoginActivity(){
		Intent loginIntent = new Intent(WellcomeActivity.this,LoginActivity.class);
		startActivity(loginIntent);
		finish();
	}
	private void ToGuideActivity(){
		Intent guideItnent = new Intent(WellcomeActivity.this,GuideActivity.class);
		startActivity(guideItnent);
		finish();
	}
	private void ToMainActivity(){
//		Intent mainIntent = new Intent(WellcomeActivity.this,MainActivity.class);
//		startActivity(mainIntent);
		new Thread(new LoginThread(spUtil.getUserID(),spUtil.getUserPass())).start();
		this.finish();
		
	}
	//******************************************
	class LoginThread implements Runnable{
		String name,pass;
		LoginThread(String name,String pass){
			this.name=name;
			this.pass=pass;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
				fangfa.denglv(name, pass);
				Log.d("LOgin", name+"和"+pass+"长链接");
				Intent intent = new Intent(WellcomeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
		}
	}
	//******************************************
	
}
