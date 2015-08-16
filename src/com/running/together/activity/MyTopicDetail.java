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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.running.together.R;
import com.running.together.adapter.MyTopicDetailAdapter;

public class MyTopicDetail extends BaseActivity {
	private Button topbar_left;
	private Button topbar_right;
	private TextView title_tv;
	private ListView listview_topic_detail;
	private List<Map<String, String>> list;
	private Map<String, String> map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_topic_detail);
		initView();
		initData();
	}

	private void initData() {
		Intent intent = getIntent();
		int position = intent.getIntExtra("topic_id", 0);
		if (position == 0) {
			title_tv.setText("夜跑一族");
			list = new ArrayList<Map<String, String>>();
			map = new HashMap<String, String>();
			map.put("topic_header", ""); // 暂时还没有设置（已设为默认的静态数据）
			map.put("topic_username", "这只熊");
			map.put("topic_time", "刚刚");
			map.put("topic_school", "广东工业大学");
			map.put("topic_content", "求问最佳跑步时间是晚上还是早晨");
			map.put("topic_image", "");// 暂时还没有设置（已设为默认的静态数据）
			map.put("topic_add", "1");
			map.put("topic_comment", "2");
			map.put("topic_good", "3");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("topic_header", ""); // 暂时还没有设置（已设为默认的静态数据）
			map.put("topic_username", "这只狗");
			map.put("topic_time", "1小时前");
			map.put("topic_school", "广东药学院");
			map.put("topic_content", "跑步怎样调整呼吸啊？各位。");
			map.put("topic_image", "");// 暂时还没有设置（已设为默认的静态数据）
			map.put("topic_add", "2");
			map.put("topic_comment", "3");
			map.put("topic_good", "4");
			list.add(map);
		}
		listview_topic_detail.setAdapter(new MyTopicDetailAdapter(this, list));
	}

	private void initView() {
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		title_tv = (TextView) findViewById(R.id.bar_title);
		topbar_right.setVisibility(View.GONE);
		listview_topic_detail = (ListView) findViewById(R.id.listview_topic_detail);
		topbar_right.setVisibility(View.GONE);
		topbar_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

}
