package com.running.together.fragment;

import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.running.together.R;
import com.running.together.activity.AboutActivity;
import com.running.together.activity.FeekBackActivity;
import com.running.together.activity.LoginActivity;
import com.running.together.activity.MyActivitysDetail;
import com.running.together.activity.MyGroupDetail;
import com.running.together.activity.MyInfoActivity;
import com.running.together.activity.MyRecordActivity;
import com.running.together.activity.MyTopicActivity;
import com.running.together.activity.SettingActivity;
import com.running.together.app.MyApplication;
import com.running.together.util.ActivityControler;
import com.running.together.util.HttpImageCallback;
import com.running.together.util.HttpImageCallback.IconCallback;
import com.running.together.util.MyToast;
import com.running.together.util.SaveBitmap;
import com.running.together.util.SharedPerferencesUtil;

public class MyFragment extends Fragment implements IconCallback {
	private ImageView title_faxian_iv_wo;
	private LinearLayout my_info;
	private LinearLayout my_record;
	private LinearLayout my_group;
	private LinearLayout my_topic;
	private LinearLayout my_acivities;
	private LinearLayout about_me;
	private LinearLayout feekback;
	private ImageView header;
	private TextView my_userName;
	private TextView fans_num;
	private TextView noticed_num;
	// 声明回调接口
	FragmentCallback MyCallback;
	//
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;

	// 传递接口对象
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			MyCallback = (FragmentCallback) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "必须实现FragmentCallback接口");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View settingLayout = inflater.inflate(R.layout.my_layout, container,
				false);
		return settingLayout;
	}

	// Activity建立的时候调用这个方法，View的获取可以在这个方法进行
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		if (null != spUtil.getNickName()) {
			my_userName.setText(spUtil.getNickName());
		} else {
			my_userName.setText("游客，请登录");
		}

		if (null != spUtil.getLocalHeader()) {
			header.setImageBitmap(BitmapFactory.decodeFile(spUtil
					.getLocalHeader()));

		} else if (null != spUtil.getUserIcon()) {// 如果有用户头像
			// Log.d("TAG",spUtil.getUserIcon());
			// new HttpCallback(spUtil.getUserIcon(),this,1);
			// my_userName.setText(spUtil.getNickName());
			new HttpImageCallback(spUtil.getUserIcon(), this).execute();
		}
		/************************以下是静态数据**************************************/
		if(null == spUtil.getUserID()){
			fans_num.setText("粉丝0");
			noticed_num.setText("关注0");
		}else{
			fans_num.setText("粉丝2");
			noticed_num.setText("关注2");
		}
		
		
		
	}

	private void initView() {
		fans_num = (TextView) getActivity().findViewById(R.id.fans_num);
		noticed_num = (TextView) getActivity().findViewById(R.id.noticed_num);
		my_userName = (TextView) getActivity().findViewById(R.id.my_userName);
		header = (ImageView) getActivity().findViewById(R.id.header);
		title_faxian_iv_wo = (ImageView) getActivity().findViewById(
				R.id.title_faxian_iv_wo);
		my_info = (LinearLayout) getActivity().findViewById(R.id.my_info);
		my_record = (LinearLayout) getActivity().findViewById(R.id.my_record);
		my_group = (LinearLayout) getActivity().findViewById(R.id.my_group);
		my_topic = (LinearLayout) getActivity().findViewById(R.id.my_topic);
		my_acivities = (LinearLayout) getActivity().findViewById(
				R.id.my_acivities);
		about_me = (LinearLayout) getActivity().findViewById(R.id.about_me);
		feekback = (LinearLayout) getActivity().findViewById(R.id.feekback);
		title_faxian_iv_wo.setOnClickListener(new MyClickListener());
		my_record.setOnClickListener(new MyClickListener());
		my_info.setOnClickListener(new MyClickListener());
		my_group.setOnClickListener(new MyClickListener());
		my_topic.setOnClickListener(new MyClickListener());
		my_acivities.setOnClickListener(new MyClickListener());
		about_me.setOnClickListener(new MyClickListener());
		feekback.setOnClickListener(new MyClickListener());

	}

	private class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_faxian_iv_wo:
				// 只是跳转，不销毁本页
				Intent setting = new Intent(getActivity(),
						SettingActivity.class);
				MyCallback.fragmentCallback(setting);
				break;
			case R.id.my_info:
				if (null == spUtil.getUserID()) {
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivity(intent);
					ActivityControler.finishAll();
					return;
				}
				Intent info = new Intent(getActivity(), MyInfoActivity.class);
				MyCallback.fragmentCallback(info);
				break;
			case R.id.my_group:
				if(null == spUtil.getUserID()){
					MyToast.showShort(getActivity(), "您还没有登录！");
				}else{
					Intent group = new Intent(getActivity(), MyGroupDetail.class);
					MyCallback.fragmentCallback(group);
				}
				break;
			case R.id.my_record:
				if(null == spUtil.getUserID()){
					MyToast.showShort(getActivity(), "您还没有登录！");
				}else{
				Intent record = new Intent(getActivity(),
						MyRecordActivity.class);
				MyCallback.fragmentCallback(record);
				}
				break;
			case R.id.my_topic:
				if(null == spUtil.getUserID()){
					MyToast.showShort(getActivity(), "您还没有登录！");
				}else{
				Intent topic = new Intent(getActivity(), MyTopicActivity.class);
				MyCallback.fragmentCallback(topic);
				}
				break;
			case R.id.my_acivities:
				if(null == spUtil.getUserID()){
					MyToast.showShort(getActivity(), "您还没有登录！");
				}else{
				Intent activities = new Intent(getActivity(),
						MyActivitysDetail.class);
				MyCallback.fragmentCallback(activities);
				}
				break;
			case R.id.about_me:
				Intent aboutMe = new Intent(getActivity(), AboutActivity.class);
				MyCallback.fragmentCallback(aboutMe);
				break;
			case R.id.feekback:
				Intent feek = new Intent(getActivity(), FeekBackActivity.class);
				MyCallback.fragmentCallback(feek);
				break;

			}
		}

	}

	public interface FragmentCallback {
		void fragmentCallback(Intent intent);
	}

	@Override
	public void getIcon(Bitmap bitmap) {
		if (bitmap != null) {
			header.setImageBitmap(bitmap);
			saveBitmap(bitmap);

		}

	}

	private void saveBitmap(Bitmap bitmap) {
		String icon_url = spUtil.getUserIcon();
		try {
			String dir = Environment.getExternalStorageDirectory()
					.getCanonicalPath() + "/" + "ToGetHer" + "/";
			String path = dir + icon_url.hashCode() + ".png";
			spUtil.setLocalHeader(path);// 将路径放到sharedpreference的本地头像路径中
			/*
			 * File filedir = new File(dir); if(!filedir.exists()){
			 * filedir.mkdir();//路径不存在就创建 } File file = new File(path);
			 * FileOutputStream fos = null; fos = new FileOutputStream(file);
			 * bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			 * fos.flush(); fos.close();
			 */
			SaveBitmap.save(bitmap, dir, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
