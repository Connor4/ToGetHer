package com.running.together.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyGroupAdapter extends BaseAdapter {
	private Context mContext;
	private List<Map<String, String>> list;
	private LayoutInflater inflater;

	public MyGroupAdapter(Context mContext, List<Map<String, String>> list) {
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
	public View getView(int position, View converView, ViewGroup arg2) {
		
		return null;
	}
	class ViewHolder{
		public ImageView image;
	}

}
