package com.running.together.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.activity.LoginActivity.LoginThread;
import com.running.together.app.MyApplication;
import com.running.together.model.UserInfo;
import com.running.together.myinterface.UploadHeaderInterface;
import com.running.together.util.HttpCallback;
import com.running.together.util.HttpCallback.MyCallback;
import com.running.together.util.MyDialog;
import com.running.together.util.MyToast;
import com.running.together.util.SharedPerferencesUtil;
import com.running.together.util.UploadHeader;
import com.running.together.util.fangfa;

public class WriteInfoActivity extends BaseActivity implements MyCallback,UploadHeaderInterface{
	private ImageView header;
	private EditText nackname;
	private EditText sex;
	private EditText age;
	private EditText school;
	private EditText hobby;
	private EditText signature;
	private Button submit;
	private final int RESULT_CODE = 0X111;
	private MyApplication myApplication;
	private SharedPerferencesUtil spUtil;
	private Dialog myDialog;
	private ImageView return_iv;
	private Button nexttime;
	private String picturePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_writeinfo);
		// 隐藏弹出来的输入法
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initView();
		initData();
	}

	private void initData() {
		myApplication = MyApplication.getInstance();
		spUtil = myApplication.getSpUtil();
		String header_url = spUtil.getLocalHeader();
		if (null != (header_url)) {
			header.setImageBitmap(BitmapFactory.decodeFile(header_url));
		}
	}

	private void initView() {
		nexttime = (Button) findViewById(R.id.next_time);
		header = (ImageView) findViewById(R.id.uploadHead);
		return_iv = (ImageView) findViewById(R.id.return_iv);
		signature = (EditText) findViewById(R.id.signature);
		nackname = (EditText) findViewById(R.id.et_nickname);
		age = (EditText) findViewById(R.id.et_age);
		sex = (EditText) findViewById(R.id.et_sex);
		school = (EditText) findViewById(R.id.et_school);
		hobby = (EditText) findViewById(R.id.et_hobby);
		submit = (Button) findViewById(R.id.register_submit);
		submit.setOnClickListener(new SubmitListener());
		header.setOnClickListener(new SubmitListener());
		return_iv.setOnClickListener(new SubmitListener());
		nexttime.setOnClickListener(new SubmitListener());
	}

	private class SubmitListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.uploadHead:
				// 上传头像信息
				uploadHeader();
				break;
			case R.id.register_submit:
				// 提交注册信息
				submit_info();
				break;
			case R.id.return_iv:
				finish();
				break;
			case R.id.next_time:
				Intent intent = new Intent(WriteInfoActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}

	}

	private void submit_info() {
		String userNickName = nackname.getText().toString();
		String userSex = sex.getText().toString();
		String userAge = age.getText().toString();
		String userSchool = school.getText().toString();
		String userHobby = hobby.getText().toString();
		String userSignture = signature.getText().toString();
		UserInfo info = new UserInfo(userNickName,userSex,userAge,userSchool,userHobby,userSignture);
		Gson gson = new Gson();
		String register_info = gson.toJson(info);
		//提交注册数据到服务器
		if(TextUtils.isEmpty(userNickName)||TextUtils.isEmpty(userSex)||TextUtils.isEmpty(userAge)||TextUtils.isEmpty(userSchool)){
			MyToast.centerToast(this, "带*的内容不能为空！");
		}else{
			new HttpCallback(AppConstant.Update+"?username="+spUtil.getUserID(), this, register_info).execute();
//			new HttpCallback(AppConstant.REQUEST_URL, this, register_info).execute();
			myDialog = MyDialog.LoginDialog(this,"请稍后.." );
			myDialog.show();
		}
	}

	private void uploadHeader() {
		// 调用系统相册
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		startActivityForResult(intent, RESULT_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_CODE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
			cursor.close();
			header.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			
			//把图片上传到服务器
			new UploadHeader(spUtil.getUserID(), AppConstant.BaseURL, picturePath, this).execute();
			

		}
	}
//	提交数据到服务器的回调接口
	@Override
	public void callback(String msg) {
		myDialog.dismiss();
		if("400".equals(msg)){
			MyToast.centerToast(this, "提交数据失败！");
			MyDialog.alertDialog(this, "提交数据失败");
		}else if("500".equals(msg)){
			MyToast.centerToast(this, "请求出现异常！");
			MyDialog.alertDialog(this, "请求出现异常！");
		}else if("200".equals(msg)){
			//服务器接受成功
			spUtil.setLocalHeader(picturePath);
			spUtil.setNickName(nackname.getText().toString());//更新昵称（如果完善资料不填写则昵称为注册时的用户名）
			Intent intent = new Intent(WriteInfoActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}else{
			MyDialog.alertDialog(this, "出现异常，请稍后再试！");
		}
	}
//上传头像的回调函数
@Override
public void completeUpload(String result) {
	if(null == result){
		Log.d(AppConstant.TAG,"返回为null");
	}else{
		Log.d(AppConstant.TAG,"上传成功");
	}
	
}
//class LoginThread implements Runnable{
//	String name,pass;
//	LoginThread(String name,String pass){
//		this.name=name;
//		this.pass=pass;
//	}
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//			fangfa.denglv(name, pass);
//			Log.d("LOgin", name+"和"+pass+"长链接");
//			Intent intent = new Intent(WriteInfoActivity.this, MainActivity.class);
//			startActivity(intent);
//			finish();
//	}
//}

}
