package com.running.together.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mob.tools.utils.UIHandler;
import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.app.MyApplication;
import com.running.together.model.UserInfo;
import com.running.together.util.HttpCallback;
import com.running.together.util.JsonFomat;
import com.running.together.util.MyDialog;
import com.running.together.util.MyToast;
import com.running.together.util.NetConnect;
import com.running.together.util.fangfa;
import com.running.together.util.HttpCallback.MyCallback;
import com.running.together.util.SharedPerferencesUtil;

public class LoginActivity extends BaseActivity implements OnClickListener,
		MyCallback, PlatformActionListener {
	Platform qq;
	// Platform qq ;
	// Platform sina ;
	/**
	 * 用户名
	 */
	private EditText user_name;
	/**
	 * 用户密码
	 */
	private EditText user_pass;
	/**
	 * 登录按钮
	 */
	private Button btn_login;
	/**
	 * 注册按钮
	 */
	private Button btn_register;
	/**
	 * 忘记密码
	 */
	private TextView tv_forget;
	/**
	 * qq登录
	 */
	private ImageView iv_qqlogin;
	/**
	 * 新浪微博登录
	 */
	private ImageView iv_sinalogin;
	// /**
	// * 微信登录
	// */
	// private ImageView iv_wechatlogin;
	// 对话框
	Dialog progressDialog;
	// SharedPerferencesUtil
	SharedPerferencesUtil spUtil;
	//
	MyApplication myApplication;
	//
	private TextView visitor_login;
	//
	String name;
	String pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initData();
		initView();
		ShareSDK.initSDK(this); // 初始化SDK
		qq = ShareSDK.getPlatform(getBaseContext(), QQ.NAME);
		// qq = ShareSDK.getPlatform(getBaseContext(), QQ.NAME);
		// sina = ShareSDK.getPlatform(getBaseContext(),SinaWeibo.NAME);
		isLogin();
	}

	/**
	 * 判断是否需要登录
	 */
	private void isLogin() {
		//*************************************
		String userId = spUtil.getUserID();
		if (null != userId) {
//			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//			intent.putExtra("userID", userId); // 将用户的id传递到MainActivity（作为用户的唯一标识）
//			startActivity(intent);
			//与openfire保持长连接
			new Thread(new LoginThread(spUtil.getUserID(),spUtil.getUserPass())).start();
			finish();
		}
		//*************************************
	}

	private void initData() {
		myApplication = MyApplication.getInstance();
		// spUtil = myApplication.getSpUtil();
		spUtil = myApplication.getSpUtil();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 关闭SDK
		ShareSDK.stopSDK(this);
		// qq.removeAccount();
		// sina.removeAccount();
	}

	private void initView() {
		user_name = (EditText) findViewById(R.id.input_user_name);
		user_pass = (EditText) findViewById(R.id.input_user_pass);
		tv_forget = (TextView) findViewById(R.id.forget_pass);
		btn_login = (Button) findViewById(R.id.login);
		btn_register = (Button) findViewById(R.id.register);
		iv_qqlogin = (ImageView) findViewById(R.id.iv_qq);
		iv_sinalogin = (ImageView) findViewById(R.id.iv_sina);
		visitor_login = (TextView) findViewById(R.id.visitor_login);
		// iv_wechatlogin = (ImageView) findViewById(R.id.iv_wechat);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		tv_forget.setOnClickListener(this);
		iv_qqlogin.setOnClickListener(this);
		iv_sinalogin.setOnClickListener(this);
		visitor_login.setOnClickListener(this);
		// iv_wechatlogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			Login();
			break;
		case R.id.register:
			Register();
			break;
		case R.id.forget_pass:
			Forget_pass();
			break;
		case R.id.iv_qq:
			QQLogin();
			break;
		case R.id.iv_sina:
			SinaLogin();
			break;
		case R.id.visitor_login:
			toMainActivity();
			break;
			// case R.id.iv_wechat:
			// WeChatLogin();
			// break;
		}

	}
//以游客身份登录就跳去主页（没有附带userID）
	private void toMainActivity() {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 正常登录
	 */
	private void Login() {
		if (NetConnect.isNetConnected(this)) {
			name = user_name.getText().toString().trim();
			pass = user_pass.getText().toString().trim();
			if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
				MyToast.showShort(this, "账号或密码不能为空");
				return;
			}
			progressDialog = MyDialog.LoginDialog(this, "正在登录");
			progressDialog.show();
			//*************************************
			Map<String, String> map = new HashMap<String, String>();
			map.put("userName", name);
			map.put("userPass", pass);
			Gson mygson = new Gson();
			String userMsg = mygson.toJson(map);
			String url2 = AppConstant.LOGIN;
			//发送异步请求
			Log.d("MyTag","Json:"+userMsg);
			new HttpCallback(url2, this, userMsg).execute();
			//*************************************
		} else {
			MyToast.showLong(this, "当前网络不可用");
		}

	}

	/**
	 * 注册
	 */
	private void Register() {
		Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
		startActivity(intent);
		finish();
		/*name = user_name.getText().toString().trim();
		pass = user_pass.getText().toString().trim();
		if (NetConnect.isNetConnected(this)) {
			if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
				MyToast.showShort(this, "注册的账号或密码不能为空！");
			}else{
				progressDialog = MyDialog.LoginDialog(this, "请稍后");
				progressDialog.show();
				//将注册的账号信息发送给后台
				Gson mygson = new Gson();
				UserInfo info = new UserInfo(name,pass);
				String userMsg = mygson.toJson(info);
				String url = AppConstant.REQUEST_URL;
				String url2 = AppConstant.test_url2;
				//发送异步请求
				new HttpCallback(url2, this, userMsg).execute();
//				Log.d("MyTag","Json:"+user);
//				System.out.println("打印："+user);
				
			}
		} else {
			MyToast.showLong(this, "当前网络不可用");
		}*/
	}

	/**
	 * 忘记密码
	 */
	private void Forget_pass() {

	}

	/**
	 * QQ登录
	 */
	private void QQLogin() {
		// qq = ShareSDK.getPlatform(getBaseContext(),QQ.NAME);
		qq.setPlatformActionListener(this);
		qq.SSOSetting(false);
		// qq.authorize();
		qq.showUser(null);
		spUtil.setLoginPlatform(AppConstant.QQ_LOGIN);//保存登录标记
	}

	/**
	 * 新浪微博登录
	 */
	private void SinaLogin() {
		Platform sina = ShareSDK.getPlatform(getBaseContext(), SinaWeibo.NAME);
		sina.setPlatformActionListener(this);
		sina.SSOSetting(true);
		sina.authorize();
		sina.showUser(null); // null默认为获取自己的资料
		spUtil.setLoginPlatform(AppConstant.SINA_LOGIN);//保存登录标记
	}

	// /**
	// * 微信登录
	// */
	// private void WeChatLogin() {
	// Platform wechat = ShareSDK.getPlatform(getBaseContext(),Wechat.NAME);
	// wechat.setPlatformActionListener(this);
	// wechat.authorize();
	// wechat.showUser(null);
	// }

	/**
	 * http请求回调的方法
	 */
	@Override
	public void callback(String msg) {
		progressDialog.dismiss();
		if("404".equals(msg)){
			MyDialog.alertDialog(this, "请求异常");
		}else if("500".equals(msg)){
			MyDialog.alertDialog(this, "提交数据失败");
//		}else{
//			MyDialog.alertDialog(this, msg);
//		}
//		if (null == msg) {
//			MyDialog.alertDialog(this, "请求出错，请稍后再试");
//			MyToast.showShort(this, "请求出错，请稍后再试");
		} else if ("FAIL".equals(msg)) {
			MyDialog.alertDialog(this, "账号或密码有误");
			MyToast.showShort(this, "账号或密码有误");
		} else{
			//*************************************
			//登陆成功，持久化id，昵称，头像
			Gson gson=new Gson();
			Map <String,String>map=gson.fromJson(msg, Map.class);
			Log.d("MYTAG", "userID:" + map.get("username"));
			Log.d("MYTAG", "userPass:" + map.get("password"));
			Log.d("MYTAG", "userIcon:" + map.get("usericon"));
			Log.d("MYTAG", "nickname:" + map.get("nickname"));
			spUtil.setUserID(map.get("username"));
			spUtil.setUserPass(map.get("password"));
			spUtil.setUserIcon(map.get("usericon"));
			spUtil.setNickName(map.get("nickname"));
			//与openfire保持长连接
			new Thread(new LoginThread(name,pass)).start();
			//*************************************
		}
	}

	@Override
	public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub
		Log.d("mytest", "cancel");

	}

	@Override
	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		PlatformDb platdb = plat.getDb(); // 获取平台数据库
		String userID = platdb.getUserId(); // 获取用户id
		String nickname = platdb.getUserName(); // 获取用户昵称
		String userIcon = platdb.getUserIcon(); // 获取用户头像连接
		// 将用户的头像连接、用户id、用户昵称保存到SharedPreference
		spUtil.setUserID(userID);
		spUtil.setUserIcon(userIcon);
		spUtil.setNickName(nickname);
		Log.d("MYTAG", "userID:" + userID);
		Log.d("MYTAG", "userIcon:" + userIcon);
		Log.d("MYTAG", "nickname:" + nickname);
		Intent intent = new Intent(LoginActivity.this, WriteInfoActivity.class);
		startActivity(intent);
		finish();

		// Log.d("TAG",userID+">>"+nickname+">>"+userIcon);

		// Log.d("mytest",arg2.toString());
		// String nickname = qq.getDb().get("nickname");
		// String userID = qq.getDb().getUserId();
		// Intent intent = new Intent(LoginActivity.this,MainActivity.class);
		// String msg = JsonFomat.format("",arg2);
		// intent.putExtra("msg", msg);
		// intent.putExtra("nickname", nickname);
		// intent.putExtra("userID", userID);
		// startActivity(intent);
		// finish();

	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		Log.d("mytest", "error");

	}
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
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
		}
	}

}
