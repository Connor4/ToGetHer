package com.running.together.activity;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract.Helpers;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.app.MyApplication;
import com.running.together.myinterface.MyDialogInterface;
import com.running.together.util.ActivityControler;
import com.running.together.util.MyDialog;
import com.running.together.util.MyToast;
import com.running.together.util.SharedPerferencesUtil;
import com.running.together.util.XmppTool;

public class SettingActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener, MyDialogInterface {
	private ImageView return_iv;
	private TextView title_text;
	private RelativeLayout update;
	private RelativeLayout clearAhche;
	private Switch toggle_msg;
	private Switch toggle_voice;
	private Switch toggle_vibration;
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;
	private Button logout_btn;

	// private boolean switch_msg = true;
	// private boolean switch_voice = true;
	// private boolean switch_vibration = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		initView();
		initData();
	}

	private void initData() {
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		// switch_msg = spUtil.getToggleMessage();
		toggle_msg.setChecked(spUtil.getToggleMessage());
		toggle_vibration.setChecked(spUtil.getToggleVibration());
		toggle_voice.setChecked(spUtil.getToggleVoice());

	}

	private void initView() {
		logout_btn = (Button) findViewById(R.id.logout_btn);
		return_iv = (ImageView) findViewById(R.id.return_iv);
		title_text = (TextView) findViewById(R.id.bar_title);
		update = (RelativeLayout) findViewById(R.id.layout_update);
		clearAhche = (RelativeLayout) findViewById(R.id.layout_clearAhche);
		toggle_msg = (Switch) findViewById(R.id.toggle_msg);
		toggle_vibration = (Switch) findViewById(R.id.toggle_vibration);
		toggle_voice = (Switch) findViewById(R.id.toggle_voice);
		title_text.setText("设置");
		logout_btn.setOnClickListener(this);
		return_iv.setOnClickListener(this);
		update.setOnClickListener(this);
		clearAhche.setOnClickListener(this);
		toggle_msg.setOnCheckedChangeListener(this);
		toggle_vibration.setOnCheckedChangeListener(this);
		toggle_voice.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_iv:
			finish();
			break;
		case R.id.layout_update:
			version_update();
			break;
		case R.id.layout_clearAhche:
			clearAhche();
			break;
		case R.id.logout_btn:
			Logout();
			break;
		}
	}

	private void Logout() {
		if (null == spUtil.getUserID()) {
			MyDialog.alertDialog(this, "你还没有登录，不能注销");
		} else {

			MyDialog.commonDialog(SettingActivity.this, "注销登录将会清除你的所有账户信息，确定注销？", this);
		}

		/*
		 * Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
		 * startActivity(intent); ActivityControler.finishAll(); finish();
		 */
		// MyDialog.alertDialog(this, this.getPackageName());
		// Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
		// startActivity(intent);
		// finish();
	}

	/*
	 * private void cleanSharedperference(Context context) {
	 * deleteFilesByDirectory(new File( "/data/data/"+ context.getPackageName()
	 * + "/shared_prefs")); }
	 * 
	 * private void deleteFilesByDirectory(File file) { if (file != null &&
	 * file.exists() && file.isDirectory()) { for (File item : file.listFiles())
	 * { item.delete(); } }
	 * 
	 * }
	 */

	private void version_update() {
		MyToast.showShort(this, "正在检测版本...");
	}

	private void clearAhche() {
		MyToast.showShort(this, "正在清除缓存...");
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_msg:
			toggleMessageChange(isChecked);
			break;
		case R.id.toggle_vibration:
			toggleVibrationChange(isChecked);
			break;
		case R.id.toggle_voice:
			toggleVoiceChange(isChecked);
			break;
		}
	}

	private void toggleMessageChange(boolean isChecked) {
		spUtil.setToggleMessage(isChecked);
	}

	private void toggleVibrationChange(boolean isChecked) {
		spUtil.setToggleVibration(isChecked);
	}

	private void toggleVoiceChange(boolean isChecked) {
		spUtil.setToggleVoice(isChecked);
	}

	// 注销按钮对话框的回调
	@Override
	public void dialogCallback(int RETURN_CODE) {
		if (AppConstant.CONFRIM_CODE == RETURN_CODE) {
			// 点击对话框的确定按钮
			Intent intent = new Intent(SettingActivity.this,
					LoginActivity.class);
			MyApplication myApplication = MyApplication.getInstance();
			SharedPerferencesUtil spUtil = myApplication.getSpUtil();
			spUtil.clearUserInfo();// 清除账户信息
			XmppTool.closeConnection();
			ShareSDK.initSDK(this);
			Platform sina = ShareSDK.getPlatform(getBaseContext(), SinaWeibo.NAME);
			Platform qq = ShareSDK.getPlatform(getBaseContext(), QQ.NAME);
			if(qq.isValid()){
				qq.removeAccount();
				PlatformDb platdb = qq.getDb();
				platdb.removeAccount();
			}else if(sina.isValid()){
				sina.removeAccount();
				PlatformDb platdb = sina.getDb();
				platdb.removeAccount();
			}
			/*if(AppConstant.QQ_LOGIN == spUtil.getLoginPlatform()){
				if(qq.isValid()){
					qq.removeAccount();
				}
			}else{
				if(sina.isValid()){
					sina.removeAccount();
				}
			}*/
			startActivity(intent);
			ActivityControler.finishAll();

		} else if (AppConstant.CANCEL_CODE == RETURN_CODE) {
				//点击了取消按钮的操作
		}
	}

}
