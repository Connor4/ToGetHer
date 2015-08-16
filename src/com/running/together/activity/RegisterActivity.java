package com.running.together.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.running.together.R;
import com.running.together.R.color;
import com.running.together.AppConstant.AppConstant;
import com.running.together.app.MyApplication;
import com.running.together.model.UserInfo;
import com.running.together.util.HttpCallback;
import com.running.together.util.HttpCallback.MyCallback;
import com.running.together.util.MyDialog;
import com.running.together.util.MyToast;
import com.running.together.util.NetConnect;
import com.running.together.util.SharedPerferencesUtil;
import com.running.together.util.fangfa;

public class RegisterActivity extends BaseActivity implements OnClickListener,
		android.widget.CompoundButton.OnCheckedChangeListener,MyCallback {
	private Button topbar_left;
	private Button topbar_right;
	private TextView title_tv;
	private Button register_submit;
	private EditText user_account;
	private EditText user_password;
	private EditText user_password_again;
	private CheckBox checkBox;
	private SharedPerferencesUtil spUtil;
	private MyApplication myApplication;
	private String user_name;
	private String user_pass;
	private String user_pass_again;
	private  Dialog progressDialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		initView();
		initData();
	}

	private void initView() {
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		title_tv = (TextView) findViewById(R.id.bar_title);
		register_submit = (Button) findViewById(R.id.register_submit);
		user_account = (EditText) findViewById(R.id.user_account);
		user_password = (EditText) findViewById(R.id.user_password);
		user_password_again = (EditText) findViewById(R.id.user_password_again);
		checkBox = (CheckBox) findViewById(R.id.agree_register);
		checkBox.setOnCheckedChangeListener(this);
		register_submit.setOnClickListener(this);
		topbar_left.setOnClickListener(this);

	}

	private void initData() {
		title_tv.setText("注册新用户");
		topbar_right.setVisibility(View.GONE);
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		

		/*
		 * boolean isAgreed = spUtil.getAgree(); if(isAgreed){
		 * register_submit.setClickable(true); }else{
		 * register_submit.setClickable(false); }
		 */

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topbar_left:
			finish();
			break;
		case R.id.register_submit:
			SubmitInfo();
			break;
		}
	}

	private void SubmitInfo() {
		if(NetConnect.isNetConnected(this)){
			user_name = user_account.getText().toString().trim();
			user_pass = user_password.getText().toString().trim();
			user_pass_again = user_password_again.getText().toString().trim();
			if (TextUtils.isEmpty(user_name) || TextUtils.isEmpty(user_pass)
					|| TextUtils.isEmpty(user_pass_again)) {
					MyToast.centerToast(this, "账号或密码不能为空");
			}else{
				//判断两次输入的密码是否一致
				if(user_pass.equals(user_pass_again)){
					//发送数据到服务器
					progressDialog = MyDialog.LoginDialog(this, "请稍后");
					progressDialog.show();
					//将注册的账号信息发送给后台
					Gson mygson = new Gson();
					Map<String,String> map=new HashMap<String, String>();
					map.put("username", user_name);
					map.put("password", user_pass);
//					UserInfo info = new UserInfo(user_name,user_pass);
					String userMsg = mygson.toJson(map);
					new HttpCallback(AppConstant.REGISTER, this, userMsg).execute();
					/*String url = AppConstant.REQUEST_URL;
					String url2 = AppConstant.test_url2;
					//发送异步请求
					new HttpCallback(url2, this, userMsg).execute();
//					Log.d("MyTag","Json:"+user);
//					System.out.println("打印："+user);
*/					
					
					
				}else{
					MyToast.centerToast(this, "两次输入密码不一致！");
				}
			}

		}else{
			MyToast.centerToast(this, "世界上最遥远的距离就是没网..");
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// spUtil.setAgree(isChecked);
		if (isChecked) {
			register_submit.setClickable(true);
			register_submit
					.setBackgroundResource(R.drawable.edittext_bg_selector);
		} else {
			register_submit.setClickable(false);
			register_submit.setBackgroundColor(getResources().getColor(
					R.color.btn_gray));
		}
	}
	@Override
	public void callback(String msg) {
		progressDialog.dismiss();
		if("500".equals(msg)){
			MyDialog.alertDialog(this, "请求出错");
		}else if("200".equals(msg)){
		//保存用户名
			spUtil.setUserID(user_account.getText().toString().trim());
			spUtil.setNickName(user_account.getText().toString().trim());
			spUtil.setUserPass(user_password.getText().toString().trim());
			boolean complete = spUtil.getCompleteInfo();
			if(complete){
				spUtil.setCompleteInfo(false);
				new Thread(new RegisterThread(spUtil.getUserID(), spUtil.getUserPass(), 1)).start();;
//				Intent intent = new Intent(RegisterActivity.this,WriteInfoActivity.class);
//				startActivity(intent);
//				finish();
			}else{
				new Thread(new RegisterThread(spUtil.getUserID(), spUtil.getUserPass(), 0)).start();
//				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
//				startActivity(intent);
//				finish();
			}
		}else if("NameIsOn".equals(msg)){
			MyDialog.alertDialog(this, "用户名已存在");
		}else{
			MyDialog.alertDialog(this, "请求异常");
		}
	}
	class RegisterThread implements Runnable{
		String name,pass;
		int state;
		RegisterThread(String name,String pass,int state){
			this.name=name;
			this.pass=pass;
			this.state=state;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
				fangfa.regist(name, pass);
				fangfa.denglv(name, pass);
				if(state==0){
					//QQ注册不用到完善页面，直接到主页
					Intent loginintent = new Intent(RegisterActivity.this, MainActivity.class);
					startActivity(loginintent);
					finish();
				}
				else{
					//默认跳到完善资料
					Intent loginintent = new Intent(RegisterActivity.this, WriteInfoActivity.class);
					startActivity(loginintent);
					finish();
				}

		}
	}
}
