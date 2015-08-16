package com.running.together.activity;

import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.running.together.R;
import com.running.together.app.MyApplication;
import com.running.together.model.MyInfo;
import com.running.together.myinterface.HttpStringInterface;
import com.running.together.util.ActivityControler;
import com.running.together.util.HttpImageCallback;
import com.running.together.util.HttpImageCallback.IconCallback;
import com.running.together.util.SaveBitmap;
import com.running.together.util.SharedPerferencesUtil;

public class OtherActionActivity extends BaseActivity implements
		OnClickListener, IconCallback, HttpStringInterface {
	private Button topbar_left;
	private TextView title_tv;
	private Button topbar_right;
	private ImageView header;
	private TextView user_name;
	private LinearLayout layout_activity;
	private LinearLayout layout_group;
	private LinearLayout layout_fans;
	private LinearLayout layout_attention;
	private TextView activity_num;
	private TextView group_num;
	private TextView fans_num;
	private TextView care_num;
	private TextView my_sex;
	private TextView my_age;
	private TextView my_school;
	private TextView my_hobby;
	private TextView my_signature;
	private Button attention;
	private Button talk;
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myinfo);
		initView();
		initData();
	}

	private void initData() {
		title_tv.setText("个人主页");
		title_tv.setTypeface(Typeface.DEFAULT_BOLD);
		title_tv.setTextColor(Color.BLACK);
		// 初始化SharedPreference
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		//从服务器获取好友的信息并更新
		
		
		
		
		
		/*if (null != spUtil.getNickName()) {
			user_name.setText(spUtil.getNickName());
		} else {
			user_name.setText("未知");
		}
		if (null != spUtil.getLocalHeader()) {
			header.setImageBitmap(BitmapFactory.decodeFile(spUtil
					.getLocalHeader()));
		} else if (null != spUtil.getUserIcon()) {// 如果有用户头像
			new HttpImageCallback(spUtil.getUserIcon(), this).execute();
		}*/
		/*String userId = spUtil.getUserID();
		if (null != userId) {
			// 请求服务器个人信息
			// String encode_content = URLEncoder.encode("明天北京飞拉萨的飞机");
			// String test_url =
			// "http://www.tuling123.com/openapi/api?key=f6825cb333a2fc8a0205de98f79a724a&info="+encode_content;
			// MyDialog.alertDialog(this,url);
			String url = AppConstant.BaseURL + "GetActivity"
					+ "GetUser?username=" + userId + "&request_style=5";
			new HttpStringCallbcak(url, this).execute();

		}*/

	}

	private void initView() {
		topbar_left = (Button) findViewById(R.id.topbar_left);
		topbar_right = (Button) findViewById(R.id.topbar_right);
		title_tv = (TextView) findViewById(R.id.bar_title);
		header = (ImageView) findViewById(R.id.user_header);
		user_name = (TextView) findViewById(R.id.user_name);
		layout_activity = (LinearLayout) findViewById(R.id.activityNum);
		layout_attention = (LinearLayout) findViewById(R.id.careNum);
		layout_fans = (LinearLayout) findViewById(R.id.fansNum);
		layout_group = (LinearLayout) findViewById(R.id.groupNum);
		activity_num = (TextView) findViewById(R.id.activity_num);
		fans_num = (TextView) findViewById(R.id.fans_num);
		group_num = (TextView) findViewById(R.id.group_num);
		care_num = (TextView) findViewById(R.id.care_num);
		my_age = (TextView) findViewById(R.id.my_age);
		my_sex = (TextView) findViewById(R.id.my_sex);
		my_school = (TextView) findViewById(R.id.my_school);
		my_hobby = (TextView) findViewById(R.id.my_hobby);
		my_signature = (TextView) findViewById(R.id.my_signature);
		attention = (Button) findViewById(R.id.btn_attention);
		talk = (Button) findViewById(R.id.btn_talk);
		topbar_right.setVisibility(View.GONE);
		topbar_left.setOnClickListener(this);
		topbar_right.setOnClickListener(this);
		header.setOnClickListener(this);
		layout_activity.setOnClickListener(this);
		layout_attention.setOnClickListener(this);
		layout_group.setOnClickListener(this);
		layout_fans.setOnClickListener(this);
		attention.setOnClickListener(this);
		talk.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topbar_left:
			finish();
			break;
		/*case R.id.topbar_right:
			Edit();
			break;*/
		case R.id.user_header:
			ChangeHeader();
			break;
		case R.id.activityNum:
			Activitys();
			break;
		case R.id.careNum:
			MyCare();
			break;
		case R.id.fansNum:
			MyFans();
			break;
		case R.id.groupNum:
			MyGroup();
			break;
		case R.id.btn_attention:
			Attention();
			break;
		case R.id.btn_talk:
			Talk();
			break;

		}
	}

	/*private void Edit() {
		Intent intent = new Intent(OtherActionActivity.this,
				WriteInfoActivity.class);
		startActivity(intent);
		finish();
	}*/

	private void ChangeHeader() {
		if (null == spUtil.getUserID()) {
			Intent intent = new Intent(OtherActionActivity.this,
					LoginActivity.class);
			startActivity(intent);
			ActivityControler.finishAll();
			finish();
		}

	}

	private void Activitys() {
		Intent intent = new Intent(OtherActionActivity.this,
				MyActivitysDetail.class);
		startActivity(intent);

	}

	private void MyCare() {
		Intent intent = new Intent(OtherActionActivity.this,
				CareDetailActivity.class);
		startActivity(intent);

	}

	private void MyFans() {
		Intent intent = new Intent(OtherActionActivity.this,
				FansDetailActivity.class);
		startActivity(intent);

	}

	private void MyGroup() {
		Intent intent = new Intent(OtherActionActivity.this,
				MyGroupDetail.class);
		startActivity(intent);

	}

	private void Attention() {
		// TODO Auto-generated method stub

	}

	private void Talk() {
		// TODO Auto-generated method stub

	}

	// 获取头像的回调
	@Override
	public void getIcon(Bitmap bitmap) {
		if (bitmap != null) {
			header.setImageBitmap(bitmap);
			saveBitmap(bitmap);
			// MyDialog.alertDialog(this, "回调成功");
		}
		// else{
		// MyDialog.alertDialog(this, "bitmap为空");
		// }
	}

	/**
	 * 将头像缓存在SD卡上
	 * 
	 * @param bitmap
	 */
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

	@Override
	public void GetStringCallbcak(String info) {
		// MyDialog.alertDialog(this, info);
		if ("" != info) {
			if ("200".equals(info)) {
				// 收到服务器返回正确码，解析json数据
				Gson gson = new Gson();
				MyInfo myInfo = gson.fromJson(info, MyInfo.class);
				String activitynum = myInfo.getActivity_num();
				String groupnum = myInfo.getGroup_num();
				String fansnum = myInfo.getFans_num();
				String carenum = myInfo.getCare_num();
				String userName = myInfo.getUsername();
				String userNickName = myInfo.getNickname();
				String userSex = myInfo.getSex();
				String userAge = myInfo.getAge();
				String userSchool = myInfo.getSchool();
				String userSingture = myInfo.getPersonal();
				String userHobby = myInfo.getHobby();
				String userHeadpic = myInfo.getHeadpic();

				activity_num.setText(activitynum);
				group_num.setText(groupnum);
				fans_num.setText(fansnum);
				care_num.setText(carenum);
				user_name.setText(userNickName);
				my_sex.setText(userSex);
				my_age.setText(userAge);
				my_school.setText(userSchool);
				my_hobby.setText(userHobby);
				my_signature.setText(userSingture);
				// 如果本地或者第三方的头像没有加载，则加载服务器上的头像
				if (null == userHeadpic && null == spUtil.getUserIcon()
						&& null == spUtil.getLocalHeader()) {
					new HttpImageCallback(userHeadpic, this).execute();
				}

			}
		} else {
			Log.d("TAG", "暂无数据");
			activity_num.setText("3");
			group_num.setText("2");
			fans_num.setText("2");
			care_num.setText("2");
			// user_name.setText("游客");
			my_sex.setText("男");
			my_age.setText("20");
			my_school.setText("广东工业大学");
			my_hobby.setText("跑步、篮球");
			my_signature.setText("老板，我是买醋的，不是打酱油的，打酱油的是后面那位");
		}
	}

}
