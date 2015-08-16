package com.running.together.util;

import com.running.together.R;
import com.running.together.AppConstant.AppConstant;
import com.running.together.myinterface.MyDialogInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialog {
	//声明回调接口
	private MyDialogInterface myDialogInterface;
	
	public static Dialog LoginDialog(Context context, String msg) {
		
		View view = LayoutInflater.from(context).inflate(R.layout.progressdialog, null);
		TextView text = (TextView) view.findViewById(R.id.text_progressdialog);
		Dialog loadingDialog = new Dialog(context,R.style.loading_dialog);
		ImageView image = (ImageView) view.findViewById(R.id.imageview_progressdialog);
//		 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
// 		为ImageView添加动画
		image.startAnimation(hyperspaceJumpAnimation);
		loadingDialog.setContentView(view);
		loadingDialog.setCanceledOnTouchOutside(false);
		text.setText(msg);
		
		/*LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.progressdialog, null);
		LinearLayout linearlayout = (LinearLayout) view
				.findViewById(R.id.loading_layout);
		ImageView image = (ImageView) view
				.findViewById(R.id.imageview_progressdialog);
		TextView text = (TextView) view.findViewById(R.id.text_progressdialog);
//		// 加载动画
//		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
//				context, R.anim.loading_animation);
//		// 为ImageView添加动画
//		image.startAnimation(hyperspaceJumpAnimation);
		text.setText(msg);
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 自定义的Dialog
		loadingDialog.setCancelable(false);// 不可以用返回键取消
		loadingDialog.setContentView(linearlayout, new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));*/
		return loadingDialog;
	}
	
	//只有确定按钮的Dialog
	public static void alertDialog(Context context,String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("注意");
		builder.setIcon(R.drawable.jinggao);
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}
	//具有确定、取消按钮的Dialog
	public static void commonDialog( final Context context,String msg,final MyDialogInterface myDialogInterface){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("注意");
		builder.setIcon(R.drawable.jinggao);
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				context.startActivity(intent);
//				ActivityControler.finishAll();
//				context.finish();
				myDialogInterface.dialogCallback(AppConstant.CONFRIM_CODE);
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}
	
}
