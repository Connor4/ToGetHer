package com.running.together.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.running.together.R;
import com.running.together.activity.ChattingActivity;
import com.running.together.activity.Count;
import com.running.together.activity.ListInfoActivity;
import com.running.together.activity.YueActivity;
import com.running.together.app.MyApplication;
import com.running.together.model.Info;
import com.running.together.util.MyToast;
import com.running.together.util.SharedPerferencesUtil;

public class YueFragment extends Fragment
{
	// 定位相关
	public LocationClient mLocationClient;
	private LocationMode mCurrentMode;
	public BDLocationListener myListener;
	private double mLatitude;
	private double mLongtitude;
	BitmapDescriptor mCurrentMarker;
	BitmapDescriptor mMarker;
	MapView mMapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位
	private ImageView relocImageView;
	private ImageView yueImageView;
	private ImageView paoImageView;
	private ImageView listImageView;
	private String Addr;
	Callbacks mCallbacks;
	private MyApplication myApplication ;
	private SharedPerferencesUtil spUtil;

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException("activity没有实现callback接口");
		}
		mCallbacks = (Callbacks) activity;
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initView();
		initLocation();
		addOther(Info.infos);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.yue_layout, container, false);
		myApplication= MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		return v;
	}

	private void initView()
	{
		mMapView = (MapView) getActivity().findViewById(R.id.bmapView);
		relocImageView = (ImageView) getActivity().findViewById(
				R.id.yue_dingwei);
		yueImageView = (ImageView) getActivity().findViewById(R.id.yue_iv_yue);
		paoImageView = (ImageView) getActivity().findViewById(R.id.yue_iv_pao);
		listImageView = (ImageView) getActivity().findViewById(
				R.id.title_yue_iv_list);
		mBaiduMap = mMapView.getMap();
		mMapView.showZoomControls(false);
		// 设置地图初始化缩放级别为15
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));
		LatLng latLng = new LatLng(23.055687, 113.410272);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(msu);

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(Marker arg0)
			{
				Bundle extraInfo = arg0.getExtraInfo();
				Info info = (Info) extraInfo.getSerializable("info");
				View pop = getActivity().getLayoutInflater().inflate(
						R.layout.popwindow, null);
				final PopupWindow popupWindow = new PopupWindow(pop, 500, 800);
				View view = getActivity().getLayoutInflater().inflate(
						R.layout.activity_main, null);
				// 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
				popupWindow.setFocusable(true);
				// 设置允许在外点击消失
				popupWindow.setOutsideTouchable(true);
				// 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
				popupWindow.setBackgroundDrawable(new BitmapDrawable());
				// 设置菜单显示的位置
				popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
				ImageView head = (ImageView) pop
						.findViewById(R.id.yue_person_img);
				TextView name = (TextView) pop
						.findViewById(R.id.yue_person_name);
				TextView loc = (TextView) pop.findViewById(R.id.yue_person_loc);
				TextView content = (TextView) pop
						.findViewById(R.id.yue_person_content);
				Button yue = (Button) pop.findViewById(R.id.yue_person_yueta);
				head.setImageResource(info.getImgId());
				name.setText(info.getName());
				loc.setText(info.getLoc());
				content.setText(info.getContent());
				head.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						// Intent intent = new Intent();
					}
				});
				yue.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						//转到聊天界面
						if(spUtil.getUserID()==null){
							MyToast.showShort(getActivity(), "请先登录");
						}
						else{
							Intent chatting_intent=new Intent(getActivity(),ChattingActivity.class);
							chatting_intent.putExtra("friendname", "001");
							startActivity(chatting_intent);
						}
					}
				});

				// 监听菜单的关闭事件
				popupWindow.setOnDismissListener(new OnDismissListener()
				{
					@Override
					public void onDismiss()
					{
					}
				});

				// 监听触屏事件
				popupWindow.setTouchInterceptor(new OnTouchListener()
				{
					@Override
					public boolean onTouch(View view, MotionEvent event)
					{
						return false;
					}
				});
				return true;
			}
		});

		relocImageView.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				centerToMyLocation();
			}
		});

		yueImageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(), YueActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("addr", Addr);
				intent.putExtras(bundle);
				mCallbacks.OnIntentstart(intent);
			}
		});

		paoImageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(), Count.class);
				mCallbacks.OnIntentstart(intent);
			}
		});

		listImageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(),
						ListInfoActivity.class);
				mCallbacks.OnIntentstart(intent);
			}
		});
	}

	private void initLocation()
	{
		// 设置头像
		mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.yue_map_user);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, true, mCurrentMarker));
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mCurrentMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(getActivity()
				.getApplicationContext());
		myListener = new MyLocationListenner();
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	private void addOther(List<Info> infos)
	{
		LatLng latLng = null;
		Marker marker = null;
		OverlayOptions options;
		for (Info info : infos)
		{
			latLng = new LatLng(info.getLatitude(), info.getLongitude());
			mMarker = BitmapDescriptorFactory.fromResource(info.getImgId());
			options = new MarkerOptions().position(latLng).icon(mMarker);
			marker = (Marker) mBaiduMap.addOverlay(options);
			Bundle arg0 = new Bundle();
			arg0.putSerializable("info", info);
			marker.setExtraInfo(arg0);
		}
	}

	public class MyLocationListenner implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{ // map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()//
					.direction(0)// 此处设置开发者获取到的方向信息，顺时针0-360
					.latitude(location.getLatitude())//
					.longitude(location.getLongitude())//
					.accuracy(0)// 不要蓝色的那个精度圈
					.build();//
			mBaiduMap.setMyLocationData(locData);
			Addr = location.getAddrStr();
			// 更新经纬度
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();
			if (!isNetworkConnected(getActivity()))
			{
				Toast.makeText(getActivity(), "当前网络连接不可用，请检查网络",
						Toast.LENGTH_SHORT).show();
			}

			if (isFirstLoc)
			{
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}
	}

	public boolean isNetworkConnected(Context context)
	{
		if (context != null)
		{
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null)
			{
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	private void centerToMyLocation()
	{
		LatLng latLng = new LatLng(mLatitude, mLongtitude);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.animateMapStatus(msu);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted())
			mLocationClient.start();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onStop()
	{
		super.onStop();
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mLocationClient.stop();
		mCurrentMarker.recycle();
	}

	public interface Callbacks
	{
		public void OnIntentstart(Intent intent);
	}

}
