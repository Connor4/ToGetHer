package com.running.together.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.myinterface.HttpStringInterface;
import com.running.together.util.HttpStringCallbcak;
import com.running.together.util.MyDialog;
import com.running.together.util.MyToast;

public class FeekBackActivity extends BaseActivity implements OnClickListener,
		HttpStringInterface {
	private Button topbar_left;
	private Button topbar_right;
	private TextView title_tv;
	private EditText edittext_advise;
	private Dialog mydialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feekback);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		initView();
		initData();
	}

	private void initData() {
		mydialog = MyDialog.LoginDialog(this, "正在提交..");
		// topbar_right.setBackgroundColor(getResources().getColor(R.color.orange));
		topbar_right.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.complete));
		title_tv.setText("意见反馈");
		// edittext_advise.requestFocus();
	}

	private void initView() {
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		title_tv = (TextView) findViewById(R.id.bar_title);
		edittext_advise = (EditText) findViewById(R.id.edittext_advise);
		topbar_left.setOnClickListener(this);
		topbar_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topbar_left:
			finish();
			break;
		case R.id.topbar_right:
			submit();
			break;
		}
	}

	private void submit() {
		String message = edittext_advise.getText().toString().trim();
		// 将Message发送到服务器
		new HttpStringCallbcak(AppConstant.BaseURL + "?feekbackmsg=" + message,
				this).execute();
		mydialog.show();

	}

	// 提交服务器返回数据
	@Override
	public void GetStringCallbcak(String info) {
		mydialog.dismiss();
		if (null != info && "200".equals(info)) {
			MyToast.centerToast(this, "提交成功，非常感谢您的宝贵意见！");
			finish();
		} else {
			MyToast.centerToast(this, "提交失败，请稍后再试！");
		}
		
	}

}
