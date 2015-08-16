package com.running.together.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.running.together.R;
import com.running.together.util.MyChronometer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Chronometer.OnChronometerTickListener;

public class Count extends Activity
{
	private MyChronometer mChronometer;
	private TextView HH;
	private TextView MM;
	private TextView SS;
	private TextView totalmeter;
	private TextView totaltime;
	private TextView speed;
	private TextView kcal;
	private Button startButton;
	private Button resetButton;
	private Boolean firFlag = false;
	private Boolean secFlag = false;
	private Boolean thiFlag = false;
	// 定位相关
	public LocationClient mLocationClient;
	public BDLocationListener myListener;
	private int dis = 0;
	private DistanceUtil distanceUtil;
	private LatLng lat1;
	private LatLng lat2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.count);
		initLocation();
		mChronometer = (MyChronometer) findViewById(R.id.chronometer);
		HH = (TextView) findViewById(R.id.count_hh);
		MM = (TextView) findViewById(R.id.count_mm);
		SS = (TextView) findViewById(R.id.count_ss);
		totalmeter = (TextView) findViewById(R.id.count_total_meter);
		totaltime = (TextView) findViewById(R.id.count_total_mm);
		speed = (TextView) findViewById(R.id.count_total_speed);
		kcal = (TextView) findViewById(R.id.count_total_calorie);
		startButton = (Button) findViewById(R.id.count_start);
		resetButton = (Button) findViewById(R.id.count_reset);

		startButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!secFlag)
				{
					setStop();
				} else
				{
					setStart();
				}
			}
		});

		resetButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mChronometer.reset();
				HH.setText("00");
				MM.setText("00");
				SS.setText("00");
				totalmeter.setText("0");
				totaltime.setText("0");
				speed.setText("0");
				kcal.setText("0");
				setStart();
			}
		});

		mChronometer
				.setOnChronometerTickListener(new OnChronometerTickListener()
				{
					@Override
					public void onChronometerTick(Chronometer cArg)
					{
						if (firFlag)
						{
							setInfos();
						}
					}
				});
	}

	private void initLocation()
	{
		mLocationClient = new LocationClient(this);
		myListener = new MyLocationListenner();
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(10000);// 设置发起定位请求的间隔时间为30000ms
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		mLocationClient.setLocOption(option);
	}

	public class MyLocationListenner implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{
			if (thiFlag)
			{
				lat1 = new LatLng(lat2.latitude, lat2.longitude);
			}
			thiFlag = true;
			lat2 = new LatLng(location.getLatitude(), location.getLongitude());
			dis += (int) distanceUtil.getDistance(lat1, lat2);
			if (dis == -1)
			{
				dis = 0;
			}
			totalmeter.setText(dis + "");
		}
	}

	private void setStart()
	{
		startButton.setText("开始");
		startButton.setBackgroundResource(R.drawable.start);
		resetButton.setTextColor(Color.BLACK);
		mChronometer.stop();
		mLocationClient.stop();
		firFlag = false;
		secFlag = false;
	}

	private void setStop()
	{
		startButton.setText("暂停");
		startButton.setBackgroundResource(R.drawable.pause);
		resetButton.setTextColor(Color.GRAY);
		mChronometer.start();
		mLocationClient.start();
		firFlag = true;
		secFlag = true;
	}

	private void setInfos()
	{
		long time = mChronometer.getCurrentTime();
		int h = (int) (time / 3600000);
		int m = (int) (time - h * 3600000) / 60000;
		int s = (int) (time - h * 3600000 - m * 60000) / 1000;
		String hh = h < 10 ? "0" + h : h + "";
		String mm = m < 10 ? "0" + m : m + "";
		String ss = s < 10 ? "0" + s : s + "";
		int h2m = Integer.parseInt(hh);
		int m2m = Integer.parseInt(mm);
		int tmin = h2m * 60 + m2m;
		HH.setText(hh);
		MM.setText(mm);
		SS.setText(ss);
		int speedInt = 0;
		int calorieInt = 0;
		if (dis > 0 && tmin > 0)
		{
			speedInt = dis / tmin;
			calorieInt = (int) (0.0518 * dis) ;
		}
		totaltime.setText(tmin + "");
		speed.setText(speedInt + "");
		kcal.setText(calorieInt+"");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		mLocationClient.stop();
		mChronometer.stop();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mLocationClient.stop();
	}

}
