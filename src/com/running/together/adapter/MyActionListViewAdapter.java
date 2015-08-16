package com.running.together.adapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.running.together.R;
import com.running.together.util.Download;
import com.running.together.util.MyDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActionListViewAdapter extends BaseAdapter {
	public Context mContext;
	private List<Map<String, String>> list;
	private LayoutInflater inflater;

	public MyActionListViewAdapter(Context mContext,
			List<Map<String, String>> list) {
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_myaction_listview,
					null);
			viewHolder = new ViewHolder();
			viewHolder.myaction_header = (ImageView) convertView
					.findViewById(R.id.myaction_header);
			viewHolder.myaction_title = (TextView) convertView
					.findViewById(R.id.myaction_title);
			viewHolder.myaction_launcher = (TextView) convertView
					.findViewById(R.id.initiator);
			viewHolder.myaction_time = (TextView) convertView
					.findViewById(R.id.launch_time);
			viewHolder.myaction_address = (TextView) convertView
					.findViewById(R.id.launch_address);
			viewHolder.myaction_content = (TextView) convertView
					.findViewById(R.id.launch_content);
			viewHolder.myaction_add = (TextView) convertView
					.findViewById(R.id.action_add);
			viewHolder.myaction_comment = (TextView) convertView
					.findViewById(R.id.action_comment);
			viewHolder.myaction_read = (TextView) convertView
					.findViewById(R.id.action_read);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.myaction_title.setText(list.get(position)
				.get("myaction_title").toString());
		viewHolder.myaction_launcher.setText(list.get(position)
				.get("myaction_launcher").toString());
		viewHolder.myaction_time.setText(list.get(position)
				.get("myaction_time").toString());
		viewHolder.myaction_address.setText(list.get(position)
				.get("myaction_address").toString());
		viewHolder.myaction_content.setText(list.get(position)
				.get("myaction_content").toString());
		viewHolder.myaction_add.setText(list.get(position).get("myaction_add")
				.toString());
		viewHolder.myaction_comment.setText(list.get(position)
				.get("myaction_comment").toString());
		viewHolder.myaction_read.setText(list.get(position)
				.get("myaction_read").toString());

		viewHolder.myaction_header.setImageBitmap(getBitmap(position));
		return convertView;
	}

	private Bitmap getBitmap(int position) {
		Bitmap bitmap = null;
		String SDCard = Environment.getExternalStorageDirectory().getPath() +"/"+list.get(position).hashCode();
		if(new File(SDCard).exists()){
			bitmap = BitmapFactory.decodeFile(SDCard);
		}else{
			bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.group_header);
			/*bitmap = Download.getBitmap(list.get(position));*/
			new Download(this,list.get(position).get("myaction_header")).start();
		}
		Log.d("<<<<<<<<<<>>>>>>>>>>",list.get(position).get("myaction_header"));
		return bitmap;
	}

	class ViewHolder {
		public ImageView myaction_header;
		public TextView myaction_title;
		public TextView myaction_launcher;
		public TextView myaction_time;
		public TextView myaction_address;
		public TextView myaction_content;
		public TextView myaction_add;
		public TextView myaction_comment;
		public TextView myaction_read;

	}

}
