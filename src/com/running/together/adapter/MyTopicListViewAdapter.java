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

public class MyTopicListViewAdapter extends BaseAdapter {

	public Context mContext;
	private List<Map<String, String>> list;
	LayoutInflater inflater;

	public MyTopicListViewAdapter(Context mContext,
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_topic, null);
			viewHolder = new ViewHolder();
			viewHolder.topic_header = (ImageView) convertView
					.findViewById(R.id.topic_header);
			viewHolder.topic_name = (TextView) convertView
					.findViewById(R.id.topic_name);
			viewHolder.topic_introduce = (TextView) convertView
					.findViewById(R.id.topic_introduce);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
//		viewHolder.topic_header.setImageBitmap(BitmapFactory.decodeFile(list
//				.get(position).get("topic_header").toString()));
		viewHolder.topic_introduce.setText(list.get(position)
				.get("topic_introduce").toString());
		viewHolder.topic_name.setText(list.get(position).get("topic_name").toString());
		viewHolder.topic_header.setImageBitmap(getBitmap(position));
		return convertView;
	}
	private Bitmap getBitmap(int position) {
		Bitmap bitmap = null;
		String SDCard = Environment.getExternalStorageDirectory().getPath() +"/"+list.get(position).hashCode();
		if(new File(SDCard).exists()){
			bitmap = BitmapFactory.decodeFile(SDCard);
		}else{
			bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.header7);
			/*bitmap = Download.getBitmap(list.get(position));*/
			new Download(this,list.get(position).get("topic_header")).start();
		}
		return bitmap;
	}

	class ViewHolder {
		public TextView topic_name;
		public TextView topic_introduce;
		public ImageView topic_header;
	}

}
