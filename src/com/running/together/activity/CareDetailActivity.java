package com.running.together.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.adapter.MyGrideViewAdapter;
import com.running.together.app.MyApplication;
import com.running.together.model.HeaderInfo;
import com.running.together.myinterface.HttpStringInterface;
import com.running.together.util.HttpStringCallbcak;
import com.running.together.util.MyToast;
import com.running.together.util.NetConnect;
import com.running.together.util.SharedPerferencesUtil;

public class CareDetailActivity extends BaseActivity implements
		OnItemClickListener, HttpStringInterface, OnClickListener {
	private GridView gridView;
	private List<String> list;
	private List<String> user_list;
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;
	private TextView title_tv;
	private Button topbar_left;
	private Button topbar_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mycaredetail);
		initView();
		initData();
	}

	private void initData() {
		// String encode_content = URLEncoder.encode("明天北京飞拉萨的飞机");
		// String test_url =
		// "http://www.tuling123.com/openapi/api?key=f6825cb333a2fc8a0205de98f79a724a&info="
		// + encode_content;
		// Get请求发送用户userID，请求服务器返回用户关注的人头像
		String request_url = AppConstant.BaseURL + "?username="
				+ spUtil.getUserID();
		/*********************** 创建静态数据 *************************/
		list.add("http://img.my.csdn.net/uploads/201505/30/1432982240_3736.jpg-thumb.jpg");
		list.add("http://img.my.csdn.net/uploads/201505/30/1432982339_7124.jpg-thumb.jpg");
		if(NetConnect.isNetConnected(this)){
			new HttpStringCallbcak(request_url, this).execute();
		}else{
			MyToast.showShort(this, "没有网络！");
		}
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
		// list.add("http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg");
		gridView.setAdapter(new MyGrideViewAdapter(this, list));
	}

	private void initView() {
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		list = new ArrayList<String>();
		user_list = new ArrayList<String>();
		gridView = (GridView) findViewById(R.id.gridview);
		title_tv = (TextView) findViewById(R.id.bar_title);
		title_tv.setText("关注");
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		topbar_left.setOnClickListener(this);
		topbar_right.setVisibility(View.GONE);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		MyToast.hideToast();
//		MyToast.centerToast(CareDetailActivity.this, list.get(position));
		Intent intent = new Intent(CareDetailActivity.this,
				OtherActionActivity.class);
		if (0 != user_list.size()) {
			intent.putExtra("userId", user_list.get(position));//把用户的userID传到详情页（作为主键查找用户信息）
		}
		startActivity(intent);

	}

	// http回调
	@Override
	public void GetStringCallbcak(String info) {
		// 返回的应该是个gson
		// MyDialog.alertDialog(this, info);
		System.out.println("<<" + info);
		if (info != "") {
			Gson gson = new Gson();
			List<HeaderInfo> headerList = gson.fromJson(info,
					new TypeToken<List<HeaderInfo>>() {
					}.getType());
			if (0 != headerList.size()) {
				for (int i = 0; i < headerList.size(); i++) {
					HeaderInfo headerInfo = headerList.get(i);
					list.add(headerInfo.getUserHeader());
					user_list.add(headerInfo.getUserId());
				}
			}
			// Gson gson = new Gson();
			// List<HeaderInfo> headerList = gson.fromJson(info,
			// new TypeToken<List<HeaderInfo>>() {
			// }.getType());
			//
			// System.out.println("<<进来了");
			/*
			 * for (int i = 0; i < headerList.size(); i++) { HeaderInfo
			 * headerInfo = headerList.get(i);
			 * list.add(headerInfo.getUserHeader());
			 * user_list.add(headerInfo.getUserId()); }
			 */

		} else {
			// MyToast.showShort(this, "数据更新失败");
			System.out.println("<<返回为空");
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.topbar_left:
			finish();
			break;
		}
	}

}
