package com.running.together.adapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.running.together.R;
import com.running.together.util.Download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionApplyListViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<Map<String, String>> list;
	LayoutInflater inflater;

	public ActionApplyListViewAdapter(Context mContext,
			List<Map<String, String>> list) {
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_actionapply, null);
			viewHolder = new ViewHolder();
			viewHolder.header = (ImageView) convertView
					.findViewById(R.id.actionapply_header);
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.userName);
			viewHolder.commentTime = (TextView) convertView
					.findViewById(R.id.comment_time);
			viewHolder.commentContent = (TextView) convertView
					.findViewById(R.id.comment_content);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.header.setImageBitmap(getBitmap(position));
		viewHolder.userName.setText(list.get(position).get("userName"));
		viewHolder.commentTime.setText(list.get(position).get("commentTime"));
		viewHolder.commentContent.setText(list.get(position).get("commentContent"));
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
			new Download(this,list.get(position).get("header")).start();
		}
		return bitmap;
	}

	class ViewHolder {
		public ImageView header;
		public TextView userName;
		public TextView commentTime;
		public TextView commentContent;

	}

}
