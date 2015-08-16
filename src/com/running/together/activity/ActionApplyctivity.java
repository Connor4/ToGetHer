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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.running.together.R;
import com.running.together.adapter.ActionApplyListViewAdapter;
import com.running.together.util.MyToast;

public class ActionApplyctivity extends BaseActivity implements OnClickListener{
	private Button topbar_left;
	private Button topbar_right;
	private TextView title_tv;
	private ImageView activity_header;
	private TextView activity_time;
	private TextView activity_address;
	private TextView activity_content;
	private Button apply_btn;
	private TextView apply_num;
	private RelativeLayout jiaruunliao;
	private ImageView apply_img0;
	private ImageView apply_img1;
	private ImageView apply_img2;
	private ImageView apply_img3;
	private ImageView apply_img4;
	private ImageView apply_img5;
	private RelativeLayout layout_to_comment;
	private ListView actionapply_listview;
	private EditText edit_msg;
	private Button send;
	private LinearLayout layout_edit;
	private List<Map<String,String>> list;
	private Map<String,String> map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_actionapply);
		initView();
		initData();
	}
	private void initData() {
		list = new ArrayList<Map<String,String>>();
		map = new HashMap<String,String>();
		//这里应该根据活动id找到该活动的所有信息
		layout_edit.setVisibility(View.GONE);
		/*******************创造静态的数据************************/
		Intent intent = getIntent();
		int position = intent.getIntExtra("activiy_id",0);
		if(position==0){
			title_tv.setText("夜跑来了"); //活动的主题
			
			map.put("header", "");
			map.put("userName", "这只熊");
			map.put("commentTime", "2015-6-1 18:50");
			map.put("commentContent", "不错哦，报名先说！");
			list.add(map);
			
			
		}else if(position==1){
			title_tv.setText("找小伙伴夜跑");
			map.put("header", "");
			map.put("userName", "这只狗");
			map.put("commentTime", "2015-6-2 20:30");
			map.put("commentContent", "来吧来吧，妹子都来吧！");
			list.add(map);
			
		}else if(position==2){
			title_tv.setText("寻找小伙伴");
			map.put("header", "");
			map.put("userName", "这只猫");
			map.put("commentTime", "2015-5-31 21:00");
			map.put("commentContent", "快点来吧，一起夜跑！");
			list.add(map);
			
		}
	
		actionapply_listview.setAdapter(new ActionApplyListViewAdapter(this, list));
		
		
	}
	private void initView() {
		
		layout_edit = (LinearLayout) findViewById(R.id.layout_edit);
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		apply_btn = (Button) findViewById(R.id.apply_btn);
		title_tv = (TextView) findViewById(R.id.bar_title);
		activity_time = (TextView) findViewById(R.id.activity_time);
		activity_header = (ImageView) findViewById(R.id.activity_header);
		activity_address = (TextView) findViewById(R.id.activity_address);
		activity_content = (TextView) findViewById(R.id.activity_content);
		apply_num = (TextView) findViewById(R.id.apply_num);
		jiaruunliao = (RelativeLayout) findViewById(R.id.relativelayout_jiaruqunnliao);
		apply_img0 = (ImageView) findViewById(R.id.apply_img0);
		apply_img1 = (ImageView) findViewById(R.id.apply_img1);
		apply_img2 = (ImageView) findViewById(R.id.apply_img2);
		apply_img3 = (ImageView) findViewById(R.id.apply_img3);
		apply_img4 = (ImageView) findViewById(R.id.apply_img4);
		apply_img5 = (ImageView) findViewById(R.id.apply_img5);
		layout_to_comment = (RelativeLayout) findViewById(R.id.layout_to_comment);
		actionapply_listview = (ListView) findViewById(R.id.actionapply_listview);
		edit_msg = (EditText) findViewById(R.id.edit_msg);
		send = (Button) findViewById(R.id.send);
		topbar_right.setVisibility(View.GONE);
		topbar_left.setOnClickListener(this);
		apply_btn.setOnClickListener(this);
		jiaruunliao.setOnClickListener(this);
		activity_header.setOnClickListener(this);
		apply_img0.setOnClickListener(this);
		apply_img1.setOnClickListener(this);
		apply_img2.setOnClickListener(this);
		apply_img3.setOnClickListener(this);
		apply_img4.setOnClickListener(this);
		apply_img5.setOnClickListener(this);
		layout_to_comment.setOnClickListener(this);
		send.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.topbar_left:
			finish();
			break;
		case R.id.activity_header:
			Intent intent = new Intent(ActionApplyctivity.this,OtherActionActivity.class);
			intent.putExtra("userId", "");//这里应该传递过去一个userId以便查找用户信息
			startActivity(intent);
			break;
		case R.id.apply_btn:
			MyToast.centerToast(this, "恭喜报名成功\n快去和小伙伴打个招呼吧！");
			break;
		case R.id.relativelayout_jiaruqunnliao:
//			Intent intent_chat = new Intent(ActionApplyctivity.this,ChattingActivity.class);
//			intent_chat.putExtra("userId", "");//这里应该传入本人userID
//			startActivity(intent_chat);
			break;
		case R.id.layout_to_comment:
//			edit_msg.getFocusables();
			layout_edit.setVisibility(View.VISIBLE);
			break;
		}
	}

}
