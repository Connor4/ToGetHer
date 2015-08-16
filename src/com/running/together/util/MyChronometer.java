package com.running.together.util;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

public class MyChronometer extends Chronometer
{
	private long timeWhenStopped = 0;

	public MyChronometer(Context context)
	{
		super(context);
	}

	public MyChronometer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MyChronometer(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public void start()
	{
		super.start();
		setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
	}

	@Override
	public void stop()
	{
		super.stop();
		timeWhenStopped = getBase() - SystemClock.elapsedRealtime();
	}

	public void reset()
	{
		stop();
		setBase(SystemClock.elapsedRealtime());
		timeWhenStopped = 0;
	}

	public long getCurrentTime()
	{
		return SystemClock.elapsedRealtime() - getBase();
	}

}
