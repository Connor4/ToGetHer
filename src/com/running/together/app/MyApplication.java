package com.running.together.app;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.running.together.application.MyApplication.MyLocationListener;
import com.running.together.util.SharedPerferencesUtil;

public class MyApplication extends Application{
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;

	public TextView mLocationResult, logMsg;
	public TextView trigger, exit;
	public Vibrator mVibrator;
	
	private static MyApplication myApplication;
	private SharedPerferencesUtil spUtil;
	public static String SP_NAME = "MSG_TOGETHER";
	//获取实例
	public synchronized static MyApplication getInstance() {
		return myApplication;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(this);
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());

		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
		myApplication = this;
		initData();
		
	}
	private void initData() {
		spUtil = new SharedPerferencesUtil(this, SP_NAME);
		

	}
	
	public synchronized SharedPerferencesUtil getSpUtil(){
		if(spUtil == null)
			spUtil = new SharedPerferencesUtil(this, SP_NAME);
		return spUtil;
	}
	
	public class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation location)
		{
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation)
			{
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
			{
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			logMsg(sb.toString());
			Log.i("BaiduLocationApiDem", sb.toString());
		}

	}

	public void logMsg(String str)
	{
		try
		{
			if (mLocationResult != null)
				mLocationResult.setText(str);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}	
