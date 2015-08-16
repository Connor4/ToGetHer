package com.running.together.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter{
	private List<View> list;
	public Context context;

	public ViewPagerAdapter(List<View> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0 == arg1;
	}

	

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewGroup) container).removeView(list.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewGroup) container).addView(list.get(position));
		return list.get(position);
	}
	

}
