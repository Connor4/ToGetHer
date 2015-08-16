package com.running.together.adapter;

import java.io.File;
import java.util.List;

import com.running.together.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyGrideViewAdapter extends BaseAdapter{
	private Context mContext;
	private List<String> list;//放置头像路径的list
	LayoutInflater inflater;
	public MyGrideViewAdapter(Context mContext,List<String> list){
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
		if(null == convertView){
			convertView = inflater.inflate(R.layout.layout_grideview, null);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image_gridview);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.image.setImageBitmap(getBitmap(position));
		return convertView;
	}
	private Bitmap getBitmap(int position) {
		Bitmap bitmap = null;
		String SDCard = Environment.getExternalStorageDirectory().getPath() +"/"+list.get(position).hashCode();
		if(new File(SDCard).exists()){
			bitmap = BitmapFactory.decodeFile(SDCard);
		}else{
			bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_launcher);
			/*bitmap = Download.getBitmap(list.get(position));*/
			new com.running.together.util.Download(this,list.get(position)).start();
		}
		return bitmap;
	}
	class ViewHolder{
		public ImageView image;
		
	}

}
