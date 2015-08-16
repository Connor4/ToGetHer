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

public class MyGroupListViewAdapter extends BaseAdapter{
	public Context mContext;
	private List<Map<String,String>> list;
	LayoutInflater inflater;
	public MyGroupListViewAdapter(Context mContext,List<Map<String,String>> list){
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.mygroup_listview, null);
			viewHolder = new ViewHolder();
			viewHolder.online_num = (TextView) convertView.findViewById(R.id.online_num);
			viewHolder.group_name = (TextView) convertView.findViewById(R.id.group_name)	;
			viewHolder.group_introduce = (TextView) convertView.findViewById(R.id.group_introduce);
			viewHolder.group_header = (ImageView) convertView.findViewById(R.id.group_header);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.online_num.setText(list.get(position).get("online_num").toString()); 
		viewHolder.group_introduce.setText(list.get(position).get("group_introduce").toString());
		viewHolder.group_name.setText(list.get(position).get("group_name").toString());
		viewHolder.group_header.setImageBitmap(getBitmap(position));
		return convertView;
	}
	private Bitmap getBitmap(int position) {
		Bitmap bitmap = null;
		String SDCard = Environment.getExternalStorageDirectory().getPath() +"/"+list.get(position).hashCode();
		if(new File(SDCard).exists()){
			bitmap = BitmapFactory.decodeFile(SDCard);
		}else{
			bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.group_header2);
			/*bitmap = Download.getBitmap(list.get(position));*/
			new Download(this,list.get(position).get("group_header")).start();
		}
		return bitmap;
	}
	class ViewHolder {
		public TextView group_name;
		public TextView online_num;
		public TextView group_introduce;
		public ImageView group_header;
	}

}
