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
import com.running.together.adapter.MyTopicListViewAdapter;
import com.running.together.app.MyApplication;
import com.running.together.model.TopicInfo;
import com.running.together.myinterface.HttpStringInterface;
import com.running.together.util.MyToast;
import com.running.together.util.NetConnect;
import com.running.together.util.SharedPerferencesUtil;

public class MyTopicActivity extends BaseActivity implements
		HttpStringInterface ,OnItemClickListener{
	private TextView title_text;
	private Button title_left;
	private Button title_right;
	private ListView listView;
	private List<Map<String, String>> list;
	private Map<String, String> map;
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mytopic);
		initView();
		initData();
	}

	private void initData() {
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		String username = spUtil.getUserID();
		title_right.setVisibility(View.GONE);
		title_text.setText("我的话题");
		list = new ArrayList<Map<String, String>>();
		map = new HashMap<String, String>();
		/****************** 创造静态数据 ******************/
		map.put("topic_header",
				"http://img.blog.csdn.net/20150530183153907?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzEyMTA1NQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center");
		map.put("topic_name", "夜跑一族");
		map.put("topic_introduce", "一群志同道合的人一起探讨夜跑的奥秘");
		list.add(map);
		// 获取服务器数据并装载到Map中
		String request_url = AppConstant.BaseURL + "?username=" + username
				+ "&request_type=4";
		if (NetConnect.isNetConnected(this)) {
//			new HttpStringCallbcak(request_url, this);//对接之前发起请求会解析出错
		} else {
			MyToast.showShort(this, "没有网络！");
		}
		listView.setAdapter(new MyTopicListViewAdapter(this, list));
	}

	private void initView() {

		title_text = (TextView) findViewById(R.id.bar_title);
		title_left = (Button) findViewById(R.id.topbar_left);
		title_right = (Button) findViewById(R.id.topbar_right);
		listView = (ListView) findViewById(R.id.listview_topic);
		listView.setOnItemClickListener(this);
		// 返回键
		title_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void GetStringCallbcak(String info) {
		if (info != "") {
			Gson gson = new Gson();
			List<TopicInfo> topic_list = gson.fromJson(info,
					new TypeToken<List<TopicInfo>>() {
					}.getType());
			for (int i = 0; i < topic_list.size(); i++) {
				TopicInfo topic = topic_list.get(i);
				map.put("topic_header", topic.getTopic_header());
				map.put("topic_name", topic.getTopic_name());
				map.put("topic_introduce", topic.getTopic_introduce());
				list.add(map);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(MyTopicActivity.this,MyTopicDetail.class);
		intent.putExtra("topic_id", position);
		startActivity(intent);
		MyToast.showShort(this, position+"");
	}

}
