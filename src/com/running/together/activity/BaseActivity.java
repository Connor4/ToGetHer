package com.running.together.activity;

import com.running.together.util.ActivityControler;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ActivityControler.addActivity(this);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ActivityControler.removeActivity(this);
	}

}
