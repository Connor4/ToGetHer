package com.running.together.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.running.together.R;
import com.running.together.adapter.ViewPagerAdapter;

public class GuideActivity extends BaseActivity implements OnPageChangeListener{
	private ImageView[] images;
	private ViewPager mViewPager;
	private List<View> list;
	private ViewPagerAdapter mViewPagerAdapter;
	private int[] images_ids = {R.id.guideDot_one,R.id.guideDot_two,R.id.guideDot_three};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();
		initDots();
	}


	private void initView() {
		LayoutInflater inflater = LayoutInflater.from(this);
		list = new ArrayList<View>();
		list.add(inflater.inflate(R.layout.layout_guide_one, null));
		list.add(inflater.inflate(R.layout.layout_guide_two, null));
		list.add(inflater.inflate(R.layout.layout_guide_three, null));
		mViewPagerAdapter = new ViewPagerAdapter(list,null);
		mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
	}


	private void initDots() {
		images = new ImageView[list.size()];
		for(int i=0;i<list.size();i++){
			images[i] = (ImageView) findViewById(images_ids[i]);
		}
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageSelected(int arg0) {
		for(int i=0;i<list.size();i++){
			if(arg0 == i){
				images[i].setImageResource(R.drawable.guide_img_ratio_selected);
			}else{
				images[i].setImageResource(R.drawable.guide_img_ratio);
			}
		}
		
	}
	public void start(View v){
		Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
