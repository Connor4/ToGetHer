package com.running.together.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.adapter.MyGroupListViewAdapter;
import com.running.together.app.MyApplication;
import com.running.together.model.GroupInfo;
import com.running.together.myinterface.HttpStringInterface;
import com.running.together.util.HttpStringCallbcak;
import com.running.together.util.MyDialog;
import com.running.together.util.MyToast;
import com.running.together.util.NetConnect;
import com.running.together.util.SharedPerferencesUtil;

public class MyGroupDetail extends BaseActivity implements HttpStringInterface ,OnItemClickListener{
	private Button topbar_left;
	private Button topbar_right;
	private TextView title_tv;
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;
	private List<Map<String, String>> list;
	private Map<String, String> map;
	private ListView listview_mygroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mygroup_detail);
		initView();
		initData();
	}

	private void initData() {
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		// 请求服务器数据
		String username = spUtil.getUserID();
		String request_url = AppConstant.BaseURL + "?username=" + username
				+ "&reuqest_type=1";
		if(NetConnect.isNetConnected(this)){
//			new HttpStringCallbcak(request_url, this).execute();//对接之前发起请求会解析出错
		}else{
			MyToast.showShort(this, "没有网络！");
		}
		/****************创造静态数据******************/
		createData();
		listview_mygroup.setAdapter(new MyGroupListViewAdapter(this, list));

	}

	private void createData() {
		
		map.put("group_name","越夜越跑" );
		map.put("online_num", "121");
		map.put("group_introduce", "千万个夜，只为等爱跑的你");
		map.put("group_header", "http://img.my.csdn.net/uploads/201505/30/1432982240_3736.jpg-thumb.jpg");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("group_name","跑起来" );
		map.put("online_num", "203");
		map.put("group_introduce", "欢迎小伙伴的加入");
		map.put("group_header", "http://img.my.csdn.net/uploads/201505/30/1432982240_3736.jpg-thumb.jpg");
		list.add(map);
		
	}

	private void initView() {
		list = new ArrayList<Map<String, String>>();
		map = new HashMap<String, String>();
		listview_mygroup = (ListView) findViewById(R.id.listview_mygroup);
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		title_tv = (TextView) findViewById(R.id.bar_title);
		title_tv.setText("Ta的群组");
		topbar_right.setVisibility(View.GONE);
		listview_mygroup.setOnItemClickListener(this);
		topbar_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void GetStringCallbcak(String info) {
		MyDialog.alertDialog(this, info);
		if (info != "") {
			Gson gson = new Gson();
			List<GroupInfo> group_list = gson.fromJson(info,
					new TypeToken<List<GroupInfo>>() {
					}.getType());
			for(int i=0;i<group_list.size();i++){
				GroupInfo group = group_list.get(i);
				map.put("group_name",group.getGroup_name() );
				map.put("online_num", group.getOnline_num());
				map.put("group_introduce", group.getGroup_introduce());
				map.put("group_header", group.getGroup_header());
				list.add(map);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		Intent intent = new Intent(MyGroupDetail.this,MyGroupActivity.class);
		intent.putExtra("GroupId", position);
		startActivity(intent);
		MyToast.showShort(this, position+"");
	}

}
