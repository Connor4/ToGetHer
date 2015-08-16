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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.running.together.R;
import com.running.together.adapter.MyGrideViewAdapter;
import com.running.together.adapter.MyGroupListViewAdapter;

public class MyGroupActivity extends BaseActivity implements OnItemClickListener{
	private Button title_left;
	private Button title_right;
	private TextView title_tv;
	// private ListView listView;
//	private List<Map<String, String>> list;
//	private Map<String, String> map;
	private TextView group_introduce;
	private TextView join_num;
	private GridView group_gridview;
	private List<String> list;
	private Button join;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mygroup);
		initView();
		initData();
	}

	private void initView() {
		join = (Button) findViewById(R.id.join);
		group_gridview = (GridView) findViewById(R.id.group_gridview);
		group_introduce = (TextView) findViewById(R.id.my_introduce);
		join_num = (TextView) findViewById(R.id.talknum);

		// listView = (ListView) findViewById(R.id.mygroup_listview);
		title_left = (Button) findViewById(R.id.topbar_left);
		title_right = (Button) findViewById(R.id.topbar_right);
		title_tv = (TextView) findViewById(R.id.bar_title);
		title_right.setVisibility(View.GONE);
//		list = new ArrayList<Map<String, String>>();
//		map = new HashMap<String, String>();
		list = new ArrayList<String>();
		join.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(MyGroupActivity.this,ChattingActivity.class);
			}
		});

		title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void initData() {
		/**************** 加载静态数据 ****************/
		Intent intent = getIntent();
		int position = intent.getIntExtra("GroupId", 0);
		if (position == 0) {
			title_tv.setText("越夜越跑");
			group_introduce.setText("千万个夜，只为等爱跑的你");
			list.add("http://img.my.csdn.net/uploads/201505/30/1432983671_6160.jpg-thumb.jpg");
		} else if (position == 1) {
			title_tv.setText("跑起来");
			group_introduce.setText("欢迎小伙伴的加入");
			list.add("http://img.my.csdn.net/uploads/201505/30/1432982657_8213.jpg-thumb.jpg");
		}
		group_gridview.setAdapter(new MyGrideViewAdapter(this, list));
		group_gridview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		Intent intent = new Intent(MyGroupActivity.this,OtherActionActivity.class);
		intent.putExtra("userId", position);//由于是静态数据，这里用position代替
		startActivity(intent);
	}
	

}
