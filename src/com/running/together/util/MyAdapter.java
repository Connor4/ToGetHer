package com.running.together.util;

import java.util.List;
import java.util.zip.Inflater;

import com.running.together.R;
import com.running.together.model.Info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter
{
	private List<Info> infos;
	private LayoutInflater layoutInflater;

	public MyAdapter(Context context, List<Info> infos)
	{
		super();
		this.infos = infos;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount()
	{
		return infos.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	static class ViewHolder
	{
		ImageView head;
		ImageView sex;
		TextView activity;
		TextView time;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if (convertView == null)
		{
			convertView = layoutInflater.inflate(R.layout.personinfo, parent,
					false);
			viewHolder = new ViewHolder();
			viewHolder.head = (ImageView) convertView
					.findViewById(R.id.list_iv_head);
			viewHolder.sex = (ImageView) convertView
					.findViewById(R.id.list_sex);
			viewHolder.activity = (TextView) convertView
					.findViewById(R.id.list_activity);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.list_time);
			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.head.setImageResource(infos.get(position).getImgId());
		viewHolder.sex
				.setImageResource((infos.get(position).getSex()) == "male" ? R.drawable.man_icon
						: R.drawable.woman_icon);
		viewHolder.activity.setText(infos.get(position).getContent());
		viewHolder.time.setText(infos.get(position).getTime());
		return convertView;
	}

}
