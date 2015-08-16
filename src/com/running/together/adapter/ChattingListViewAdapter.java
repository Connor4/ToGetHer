package com.running.together.adapter;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.running.together.R;
import com.running.together.activity.ChattingActivity;
import com.running.together.model.Recently;
import com.running.together.util.AsyncImageLoader;
import com.running.together.util.AsyncImageLoader.ImageCallback;

public class ChattingListViewAdapter extends BaseAdapter {
	private ViewHolder holder;
	private Activity activity;
	private Context context;
	public Cursor cursor;
	private MyOnClickListener on;
	public ChattingListViewAdapter(Activity activity,Cursor cursor) throws JSONException {
		this.activity = activity;
		this.context=activity.getApplicationContext();
		this.cursor=cursor;
	}
	public void resetViewHolder(ViewHolder viewHolder){
		viewHolder.nickname.setText(null);
		viewHolder.text.setText(null);
		viewHolder.time.setText(null);
//		viewHolder.username="";
		if(viewHolder.header!=null){
			viewHolder.header.setImageBitmap(null);
		}
	}
	public int getCount(){
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Recently getItem(int position) {
		// TODO Auto-generated method stub
		cursor.moveToPosition(position);
		Recently recently=new Recently(
				cursor.getString(cursor.getColumnIndex("Fname")), 
	            cursor.getString(cursor.getColumnIndex("Fnickname")), 
	    	    cursor.getString(cursor.getColumnIndex("Furl")),
	    	    cursor.getString(cursor.getColumnIndex("Ftime")),
	    	    cursor.getString(cursor.getColumnIndex("Ftext")),
	    	    cursor.getInt(cursor.getColumnIndex("State")));
		return recently;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.chatting_list_item, null);
			holder=new ViewHolder();
			holder.header=(ImageView) convertView.findViewById(R.id.header);
			holder.nickname=(TextView) convertView.findViewById(R.id.nickname);
			holder.time=(TextView) convertView.findViewById(R.id.time);
			holder.text=(TextView) convertView.findViewById(R.id.text);
//			holder.item=(RelativeLayout) convertView.findViewById(R.id.item);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag(); 
			resetViewHolder(holder);
		}
		Recently recently=getItem(position);
		holder.nickname.setText(recently.getNickname());
		holder.time.setText(recently.getTime());
		if(recently.getState()==1){
			//有新消息到达，加小红点
			holder.text.setText(recently.getText()+"(修改)");
		}
		else{
			holder.text.setText(recently.getText());
		}
		Bitmap head_image = AsyncImageLoader.loadBitmap(0,
				recently.getUrl(), holder.header, position,
				new ImageCallback() {
					public void imageSet(Bitmap bitmap, ImageView iv) {
						iv.setImageBitmap(bitmap);
					}
				});
		if (head_image != null) {
			holder.header.setImageBitmap(head_image);
		}
		on=new MyOnClickListener(recently.getUsername());
		holder.header.setOnClickListener(on);
		convertView.setOnClickListener(on);
		return convertView;
	}
	public static class ViewHolder {
		private ImageView header;
		private TextView nickname,time,text;
		private RelativeLayout item;
//		private String name;
	}
	private class MyOnClickListener implements OnClickListener{
		private String name;
		public MyOnClickListener(String name) {
			// TODO Auto-generated constructor stub
			this.name=name;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
//			case R.id.item:

//				break;
			case R.id.header:
				//点击头像打开个人信息
				
				break;
			default:
				//点击item打开聊天界面
				Intent chatIntent =new Intent(activity, ChattingActivity.class);
//				Log.d("dianji",v.getId()+"");
				chatIntent.putExtra("friendname",name);
				activity.startActivity(chatIntent);
				break;
			}
		}
		
	}

}
