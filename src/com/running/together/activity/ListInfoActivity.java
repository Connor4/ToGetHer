package com.running.together.activity;

import com.running.together.R;
import com.running.together.model.Info;
import com.running.together.util.MyAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

public class ListInfoActivity extends Activity implements
		SwipeRefreshLayout.OnRefreshListener
{
	private ListView listview;
	private MyAdapter myAdapter;
	private Context context;
	private static final int REFRESH_COMPLETE = 0X110;
	private ImageView tomap;
	private SwipeRefreshLayout mSwipeLayout;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case REFRESH_COMPLETE:
				Info.infos.add(0, new Info(23.055687, 113.410272,
						R.drawable.yue_map_user5, "female", "这只鸡", "广东工业大学",
						"我想去打乒乓球", "刚刚"));
				Info.infos.add(0, new Info(23.055687, 113.410272,
						R.drawable.yue_map_user6, "male", "这只猫", "广东工业大学",
						"我想去足球", "刚刚"));
				Info.infos.add(0, new Info(23.055687, 113.410272,
						R.drawable.yue_map_user7, "female", "这只鹅", "广东工业大学",
						"我想去游泳", "刚刚"));
				myAdapter.notifyDataSetChanged();
				mSwipeLayout.setRefreshing(false);
				break;
			}
		};
	};

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		tomap = (ImageView) findViewById(R.id.title_list_tomap);
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		init();
		listview.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				View pop = getLayoutInflater().inflate(
						R.layout.popwindow, null);
				final PopupWindow popupWindow = new PopupWindow(pop, 500, 800);
				View v = getLayoutInflater().inflate(
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
				head.setImageResource(Info.infos.get(position).getImgId());
				name.setText(Info.infos.get(position).getName());
				loc.setText(Info.infos.get(position).getLoc());
				content.setText(Info.infos.get(position).getContent());
				yue.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Toast.makeText(getApplicationContext(), "约她", Toast.LENGTH_SHORT)
								.show();
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
			}
		});

		tomap.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}

	private void init()
	{
		this.context = this;
		listview = (ListView) findViewById(R.id.listview);
		myAdapter = new MyAdapter(context, Info.infos);
		listview.setAdapter(myAdapter);
	}

	@Override
	public void onRefresh()
	{
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
	}
}
