package com.running.together.activity;

import com.running.together.R;
import com.running.together.fragment.FaxianFragment;
import com.running.together.fragment.MessagesFragment;
import com.running.together.fragment.MyFragment;
import com.running.together.fragment.YueFragment;
import com.running.together.fragment.FaxianFragment.FaxianCallback;
import com.running.together.fragment.MyFragment.FragmentCallback;
import com.running.together.fragment.YueFragment.Callbacks;
import com.running.together.util.MyToast;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener,
		Callbacks,FaxianCallback,FragmentCallback
{
	// 用于展示的Fragment
	private YueFragment yueFragment;
	private FaxianFragment faxianFragment;
	private MessagesFragment messagesFragment;
	private MyFragment myFragment;
	// 界面布局
	private View yueLayout;
	private View faxianLayout;
	private View messagesLayout;
	private View myLayout;
	// 在Tab布局上显示图标的控件
	private ImageView yueImage;
	private ImageView faxianImage;
	private ImageView messagesImage;
	private ImageView myImage;
	// 在Tab布局上显示标题的控件
	private TextView yueText;
	private TextView faxianText;
	private TextView messagesText;
	private TextView myText;
	// 用于对Fragment进行管理
	private FragmentManager fragmentManager;
	String userID = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		userInfo();
		setContentView(R.layout.activity_main);
		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	private void userInfo()
	{
		Intent intent = getIntent();
		userID = intent.getStringExtra("userID");
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews()
	{
		yueLayout = findViewById(R.id.yue_layout);
		faxianLayout = findViewById(R.id.faxian_layout);
		messagesLayout = findViewById(R.id.messages_layout);
		myLayout = findViewById(R.id.my_layout);
		yueImage = (ImageView) findViewById(R.id.yue_image);
		faxianImage = (ImageView) findViewById(R.id.faxian_image);
		messagesImage = (ImageView) findViewById(R.id.messages_image);
		myImage = (ImageView) findViewById(R.id.my_image);
		yueText = (TextView) findViewById(R.id.yue_text);
		faxianText = (TextView) findViewById(R.id.faxian_text);
		messagesText = (TextView) findViewById(R.id.messages_text);
		myText = (TextView) findViewById(R.id.my_text);
		yueLayout.setOnClickListener(this);
		faxianLayout.setOnClickListener(this);
		messagesLayout.setOnClickListener(this);
		myLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.yue_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.faxian_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.messages_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.my_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index)
	{
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index)
		{
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			yueImage.setImageResource(R.drawable.fragment_yue_1);
			yueText.setTextColor(Color.parseColor("#dc143c"));
			if (yueFragment == null)
			{
				// 如果MessageFragment为空，则创建一个并添加到界面上
				yueFragment = new YueFragment();
				transaction.add(R.id.content, yueFragment);
			} else
			{
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(yueFragment);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			faxianImage.setImageResource(R.drawable.fragment_faxian_1);
			faxianText.setTextColor(Color.parseColor("#dc143c"));
			if (faxianFragment == null)
			{
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				faxianFragment = new FaxianFragment();
				transaction.add(R.id.content, faxianFragment);
			} else
			{
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(faxianFragment);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			messagesImage.setImageResource(R.drawable.fragment_message_1);
			messagesText.setTextColor(Color.parseColor("#dc143c"));
			if (messagesFragment == null)
			{
				// 如果NewsFragment为空，则创建一个并添加到界面上
				messagesFragment = new MessagesFragment();
				transaction.add(R.id.content, messagesFragment);
			} else
			{
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(messagesFragment);
			}
			break;
		case 3:
		default:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			myImage.setImageResource(R.drawable.fragment_my_1);
			myText.setTextColor(Color.parseColor("#dc143c"));
			if (myFragment == null)
			{
				// 如果SettingFragment为空，则创建一个并添加到界面上
				myFragment = new MyFragment();
				transaction.add(R.id.content, myFragment);
			} else
			{
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(myFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection()
	{
		yueImage.setImageResource(R.drawable.fragment_yue_0);
		yueText.setTextColor(Color.parseColor("#7a7a7a"));
		faxianImage.setImageResource(R.drawable.fragment_faxian_0);
		faxianText.setTextColor(Color.parseColor("#7a7a7a"));
		messagesImage.setImageResource(R.drawable.fragment_message_0);
		messagesText.setTextColor(Color.parseColor("#7a7a7a"));
		myImage.setImageResource(R.drawable.fragment_my_0);
		myText.setTextColor(Color.parseColor("#7a7a7a"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction)
	{
		if (yueFragment != null)
		{
			transaction.hide(yueFragment);
		}
		if (faxianFragment != null)
		{
			transaction.hide(faxianFragment);
		}
		if (messagesFragment != null)
		{
			transaction.hide(messagesFragment);
		}
		if (myFragment != null)
		{
			transaction.hide(myFragment);
		}
	}

	@Override
	public void OnIntentstart(Intent intent)
	{
		startActivity(intent);
	}
	
	
	
	
	public void closeMainActivity()
	{
		finish();
	}

	double exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// System.out.println(">>>>"+System.currentTimeMillis());
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if ((System.currentTimeMillis() - exitTime) > 2000)
			{
				MyToast.showShort(this, "再按一次返回键退出程序！");
				exitTime = System.currentTimeMillis();
			} else
			{
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void fragmentCallback(Intent intent)
	{
		startActivity(intent);
	}

	@Override
	public void FaxianCallback(Intent intent)
	{
		startActivity(intent);
	}

}
