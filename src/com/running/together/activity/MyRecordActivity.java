package com.running.together.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.app.MyApplication;
import com.running.together.model.RecordInfo;
import com.running.together.myinterface.HttpStringInterface;
import com.running.together.util.HttpStringCallbcak;
import com.running.together.util.MyToast;
import com.running.together.util.NetConnect;
import com.running.together.util.SharedPerferencesUtil;

public class MyRecordActivity extends BaseActivity implements OnClickListener,
		HttpStringInterface {
	private TextView title_tv;
	private Button topbar_left;
	private Button topbar_right;
	private TextView user_name;
	private ImageView header;
	private TextView distance_sum;
	private TextView time_sum;
	private TextView calorie_sum;
	private TextView large_distance;
	private TextView large_time;
	private TextView large_calorie;
	private TextView large_speed;
	// 数据
//	private double allDistance;
//	private double allTime;
//	private double allCalorie;
//	private double largeDistance;
//	private double largeTime;
//	private double largeSpeed;
//	private double largeCalorie;
	//
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myrecord);
		initView();
		initData();

	}

	private void initView() {
		header = (ImageView) findViewById(R.id.myheader);
		user_name = (TextView) findViewById(R.id.myuser_name);
		title_tv = (TextView) findViewById(R.id.bar_title);
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		distance_sum = (TextView) findViewById(R.id.distance_sum);
		time_sum = (TextView) findViewById(R.id.time_sum);
		calorie_sum = (TextView) findViewById(R.id.calorie_sum);
		large_calorie = (TextView) findViewById(R.id.large_calorie);
		large_distance = (TextView) findViewById(R.id.large_distance);
		large_speed = (TextView) findViewById(R.id.large_speed);
		large_time = (TextView) findViewById(R.id.large_time);
		topbar_left.setOnClickListener(this);
	}

	private void initData() {
		title_tv.setText("个人记录");
		topbar_right.setVisibility(View.GONE);
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		if(spUtil.getNickName()!=null){
			user_name.setText(spUtil.getNickName());	
		}else{
			user_name.setText("游客");
		}
		if (null != spUtil.getLocalHeader()) {
			header.setImageBitmap(BitmapFactory.decodeFile(spUtil
					.getLocalHeader()));
		}else{
			header.setImageResource(R.drawable.header_default);
		}
		// 请求网络数据，更新界面
		/**************************创建静态数据*****************************/
		create();
		String username = spUtil.getUserID();
		String url = AppConstant.BaseURL + "?username=" + username
				+ "&request_style=2";
		if(!"".equals(username)){
			if(NetConnect.isNetConnected(this)){
//				new HttpStringCallbcak(url, this).execute();//对接之前发起请求会解析出错
			}else{
				MyToast.showShort(this, "没有网络！");
			}
		}
	

	}

	private void create() {
		distance_sum.setText("0.0");
		time_sum.setText("0.0");
		calorie_sum.setText("0.0");
		large_distance.setText("0.0");
		large_time.setText("0.0");
		large_calorie.setText("0.0");
		large_speed.setText("0.0");
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topbar_left:
			finish();
			break;
		}
	}

	@Override
	public void GetStringCallbcak(String info) {
		if (info != "") {
			Gson gson = new Gson();
			RecordInfo record = gson.fromJson(info, RecordInfo.class);
			String allDistance = record.getAllDistance();
			String allTime = record.getAllTime();
			String allCalorie = record.getAllCalorie();
			String largeDistance = record.getLargeCalorie();
			String largeTime = record.getLargeTime();
			String largeSpeed = record.getLargeSpeed();
			String largeCalorie = record.getLargeCalorie();
			distance_sum.setText(allDistance);
			time_sum.setText(allTime);
			calorie_sum.setText(allCalorie);
			large_distance.setText(largeDistance);
			large_time.setText(largeTime);
			large_calorie.setText(largeCalorie);
			large_speed.setText(largeSpeed);

		}else{
			

		}
	}

}
