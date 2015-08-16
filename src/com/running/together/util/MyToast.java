package com.running.together.util;

import com.running.together.R;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyToast {
	public static Toast toast;
	/**
	 * 展示短时间的Toast
	 * @param context 上下文
	 * @param msg 展示的信息
	 */
	public static void showShort(Context context,CharSequence msg){
		if(null == toast){
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}else{
			toast.setText(msg);
		}
		toast.show();
	}
	/**
	 * 展示长时间的Toast
	 * @param context 上下文
	 * @param msg	要显示的消息
	 */
	public static void showLong(Context context,CharSequence msg){
		if(null == toast){
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		}else{
			toast.setText(msg);
		}
		toast.show();
	}
	/**
	 * 自定义时间的Toast
	 * @param context 上下文
	 * @param msg 展示的信息
	 * @param Duration 展示的时间
	 */
	public static void showTime(Context context,CharSequence msg,int Duration){
		if(null == toast){
			toast = Toast.makeText(context, msg, Duration);
		}else{
			toast.setText(msg);
			toast.setDuration(Duration);
		}
		toast.show();
	}
	/**
	 * 取消toast(放在退出程序后toast还没消失)
	 */
	public static void hideToast(){
		if(null != toast){
			toast.cancel();
		}
	}
	/**
	 * 自定义居中的Toast
	 */
	public static void centerToast(Context context,CharSequence msg){
		Toast toast =Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		ImageView image = new ImageView(context);
		image.setImageResource(R.drawable.jinggao);
//		image.set
		LayoutParams mParams = new LayoutParams(40, 40);
		image.setLayoutParams(mParams);
		LinearLayout toastLayout = (LinearLayout) toast.getView();
		toastLayout.setOrientation(LinearLayout.HORIZONTAL);
		toastLayout.addView(image,0);
		toast.show();
	}
	
}
