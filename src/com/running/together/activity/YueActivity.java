package com.running.together.activity;

import com.running.together.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class YueActivity extends Activity
{
	private ImageView leftImageView;
	private ImageView rightImageView;
	private EditText contentEditText;
	private TextView loc;
	private Bundle arg0;
	private String addr;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yue_detail);
		leftImageView = (ImageView) findViewById(R.id.title_detail_back);
		rightImageView = (ImageView) findViewById(R.id.title_detail_fabu);
		contentEditText = (EditText) findViewById(R.id.yue_detail_edittext);
		loc = (TextView) findViewById(R.id.yue_detail_loctext);
		arg0 = this.getIntent().getExtras();
		addr = arg0.getString("addr");
		loc.setText(addr);

		leftImageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		rightImageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(getApplicationContext(), "undone", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

}
