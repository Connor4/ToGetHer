package com.running.together.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.running.together.R;
import com.running.together.util.MyToast;

public class AboutActivity extends BaseActivity implements OnClickListener{
	private Button topbar_left;
	private Button topbar_right;
	private TextView title_tv;
	private LinearLayout evaluate;
	private LinearLayout function;
	private LinearLayout version;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		initView();
		initData();
	}

	private void initData() {
		topbar_right.setVisibility(View.GONE);
		title_tv.setText("关于");
	}

	private void initView() {
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		title_tv = (TextView) findViewById(R.id.bar_title);
		evaluate = (LinearLayout) findViewById(R.id.evaluate);
		function = (LinearLayout) findViewById(R.id.function);
		version = (LinearLayout) findViewById(R.id.version);
		topbar_left.setOnClickListener(this);
		evaluate.setOnClickListener(this);
		function.setOnClickListener(this);
		version.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.topbar_left:
			finish();
			break;
		case R.id.evaluate:
			pingjia();
			break;
		case R.id.function:
			gongneng();
			break;
		case R.id.version:
			banben();
			break;
		}
	}

	private void pingjia() {
		//评价
		MyToast.showShort(this, "评价");
	}

	private void gongneng() {
		// 功能介绍
		MyToast.showShort(this, "功能介绍");
		
	}

	private void banben() {
		//当前版本
		MyToast.showShort(this, "当前版本");
		
	}

}
