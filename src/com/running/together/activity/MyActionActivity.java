package com.running.together.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.running.together.R;
import com.running.together.adapter.MyActionListViewAdapter;

public class MyActionActivity extends BaseActivity{
	private ListView listView;
	private Button topbar_left;
	private Button topbar_right;
	private TextView Title_tv;
	private List<Map<String,String>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myaction);
		initView();
		initData();
	}
	private void initData() {
		list = new ArrayList<Map<String,String>>();
		//请求服务器数据并加载到Map中
		
		
//		listView.setAdapter(new MyActionListViewAdapter(this, list));
	}
	private void initView() {
		listView = (ListView) findViewById(R.id.listview_myaction);
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		Title_tv = (TextView) findViewById(R.id.bar_title);
		topbar_right.setVisibility(View.GONE);
		Title_tv.setText("我的活动");
		topbar_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
