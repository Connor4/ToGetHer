package com.running.together.fragment;

import com.running.together.R;
import com.running.together.activity.MyActivitysDetail;
import com.running.together.activity.MyTopicActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class FaxianFragment extends Fragment
{
	private TextView events;
	private TextView topic;
	private TextView groupchat;
	FaxianCallback MyCallback;

	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			MyCallback = (FaxianCallback) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
					+ "必须实现FragmentCallback接口");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		events=(TextView) getActivity().findViewById(R.id.faxian_hotevents);
		topic=(TextView) getActivity().findViewById(R.id.faxian_tv_topic);
		groupchat = (TextView) getActivity().findViewById(R.id.faxian_grounp_createchat);
		events.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(),MyActivitysDetail.class);
				MyCallback.FaxianCallback(intent);
			}
		});
		
		topic.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(),MyTopicActivity.class);
				MyCallback.FaxianCallback(intent);
			}
		});
		
		groupchat.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
		//		Intent intent = new Intent(getActivity(),MyActivitysDetail.class);
		//		MyCallback.fragmentCallback(intent);
			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View faxianLayout = inflater.inflate(R.layout.faxian_layout, container,
				false);
		return faxianLayout;
	}

	public interface FaxianCallback
	{
		void FaxianCallback(Intent intent);
	}

}
