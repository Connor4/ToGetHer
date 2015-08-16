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
import com.running.together.adapter.MyActionListViewAdapter;
import com.running.together.app.MyApplication;
import com.running.together.model.MyActivityInfo;
import com.running.together.myinterface.HttpStringInterface;
import com.running.together.util.MyToast;
import com.running.together.util.NetConnect;
import com.running.together.util.SharedPerferencesUtil;

public class MyActivitysDetail extends BaseActivity implements
		HttpStringInterface {
	private Button topbar_left;
	private Button topbar_right;
	private TextView title_tv;
	private ListView listview_detail_activity;
	private List<Map<String, String>> list;
	private Map<String,String> map ;
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
		initView();
		initData();

	}

	private void initData() {
		// 请求数据
		String username = spUtil.getUserID();// 唯一标识
		String request_url = AppConstant.BaseURL + "?username=" + username+"&request_style=3";
		if(NetConnect.isNetConnected(this)){
//			new HttpStringCallbcak(request_url, this).execute();//对接之前发起请求会解析出错
		}else{
			MyToast.showShort(this, "没有网络！");
		}
		
		/*****************创造静态数据*******************/
		createData();
		
		
		listview_detail_activity.setAdapter(new MyActionListViewAdapter(this,
				list));
	}

	private void initView() {
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		list = new ArrayList<Map<String, String>>();
		map = new HashMap<String,String>();
		listview_detail_activity = (ListView) findViewById(R.id.listview_detail_activity);
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		title_tv = (TextView) findViewById(R.id.bar_title);
		title_tv.setText("Ta的活动");
		topbar_right.setVisibility(View.GONE);

		// 返回键
		topbar_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		listview_detail_activity.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
//				MyToast.showShort(MyActivitysDetail.this, arg2+"");
				Intent intent = new Intent(MyActivitysDetail.this,ActionApplyctivity.class);
				intent.putExtra("activiy_id", position);//这里还需要一个活动的id来区分各个活动
				startActivity(intent);
			}
		});
	}

	@Override
	public void GetStringCallbcak(String info) {
		if (info != "") {
			Gson gson = new Gson();
			List<MyActivityInfo> atyinfo_list = gson.fromJson(info,
					new TypeToken<List<MyActivityInfo>>() {
					}.getType());
			for(int i=0;i<atyinfo_list.size();i++){
				MyActivityInfo atyinfo = atyinfo_list.get(i);
				map.put("myaction_header", atyinfo.getMyaction_header());
				map.put("myaction_title", atyinfo.getMyaction_title());
				map.put("myaction_launcher", atyinfo.getMyaction_launcher());
				map.put("myaction_time", atyinfo.getMyaction_time());
				map.put("myaction_address", atyinfo.getMyaction_address());
				map.put("myaction_content", atyinfo.getMyaction_content());
				map.put("myaction_add", atyinfo.getMyaction_add());
				map.put("myaction_comment", atyinfo.getMyaction_comment());
				map.put("myaction_read", atyinfo.getMyaction_read());
				list.add(map);
			}

		} else {
			
		}

	}

	private void createData() {
		map.put("myaction_header","http://img.my.csdn.net/uploads/201505/30/1432983663_7635.jpg" );
		map.put("myaction_title", "夜跑来了");
		map.put("myaction_launcher", "这只熊");
		map.put("myaction_time","5月30日晚21:00");
		map.put("myaction_address", "大学城内环广工");
		map.put("myaction_content", "喜欢泡内环的小伙伴快加入我们吧");
		map.put("myaction_add", "5");
		map.put("myaction_comment", "3");
		map.put("myaction_read", "48");
		list.add(map);
		map = new HashMap<String,String>();
		map.put("myaction_header","" );
		map.put("myaction_title", "找小伙伴夜跑");
		map.put("myaction_launcher", "这只熊");
		map.put("myaction_time","5月31日晚21:00");
		map.put("myaction_address", "大学城中医院");
		map.put("myaction_content", "找一个女生一起跑内环");
		map.put("myaction_add", "8");
		map.put("myaction_comment", "13");
		map.put("myaction_read", "126");
		list.add(map);
		map = new HashMap<String,String>();
		map.put("myaction_header","http://img.my.csdn.net/uploads/201309/01/1378037192_8379.jpg" );
		map.put("myaction_title", "寻找小伙伴");
		map.put("myaction_launcher", "wacelike");
		map.put("myaction_time","今晚21:00");
		map.put("myaction_address", "南二路");
		map.put("myaction_content", "找一个小伙伴跑步，性别不限");
		map.put("myaction_add", "1");
		map.put("myaction_comment", "3");
		map.put("myaction_read", "11");
		list.add(map);
	}

}
