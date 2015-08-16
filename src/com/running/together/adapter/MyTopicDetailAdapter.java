package com.running.together.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.running.together.R;

public class MyTopicDetailAdapter extends BaseAdapter{
	public Context mContext;
	private List<Map<String,String>> list;
	private LayoutInflater inflater;
	public MyTopicDetailAdapter(Context mContext,List<Map<String,String>> list){
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
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View converView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if(converView == null){
			converView = inflater.inflate(R.layout.activity_topic_listview, null);
			viewHolder = new ViewHolder();
			viewHolder.topic_header = (ImageView) converView.findViewById(R.id.topic_header);
			viewHolder.topic_username = (TextView) converView.findViewById(R.id.topic_username);
			viewHolder.topic_time = (TextView) converView.findViewById(R.id.topic_time);
			viewHolder.topic_school = (TextView) converView.findViewById(R.id.topic_school);
			viewHolder.topic_content = (TextView) converView.findViewById(R.id.topic_content);
			viewHolder.topic_image = (ImageView) converView.findViewById(R.id.topic_image);
			viewHolder.topic_add = (TextView) converView.findViewById(R.id.topic_add);
			viewHolder.topic_comment = (TextView) converView.findViewById(R.id.topic_comment);
			viewHolder.topic_good = (TextView) converView.findViewById(R.id.topic_good);
			converView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) converView.getTag();
		}
		viewHolder.topic_header.setImageResource(R.drawable.header7);
		viewHolder.topic_username.setText(list.get(position).get("topic_username"));
		viewHolder.topic_time.setText(list.get(position).get("topic_time"));
		viewHolder.topic_school.setText(list.get(position).get("topic_school"));
		viewHolder.topic_content.setText(list.get(position).get("topic_content"));
		viewHolder.topic_image.setImageResource(R.drawable.run);
		viewHolder.topic_add.setText(list.get(position).get("topic_add"));
		viewHolder.topic_comment.setText(list.get(position).get("topic_comment"));
		viewHolder.topic_good.setText(list.get(position).get("topic_good"));
		return converView;
	}
	class ViewHolder {
		public ImageView topic_header;
		public TextView topic_username;
		public TextView topic_time;
		public TextView topic_school;
		public TextView topic_content;
		public ImageView topic_image;
		public TextView topic_add;
		public TextView topic_comment;
		public TextView topic_good;
	}

}
