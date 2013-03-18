package com.example.testviewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class TestViewPager extends Activity {
	private ViewPager myViewPager;

	private MyPagerAdapter myAdapter; // 自定义的PagerAdapter子类

	private LayoutInflater mInflater;
	private List<View> mListView;
	private View layout1;
	private View layout2;
	private View layout3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);
		myAdapter = new MyPagerAdapter();
		myViewPager = (ViewPager) findViewById(R.id.viewpagerLayout);
		myViewPager.setAdapter(myAdapter);

		mListView = new ArrayList<View>();
		mInflater = getLayoutInflater(); // *********

		layout1 = mInflater.inflate(R.layout.layout1, null);
		layout2 = mInflater.inflate(R.layout.layout2, null);
		layout3 = mInflater.inflate(R.layout.layout3, null);

		mListView.add(layout1);
		mListView.add(layout2);
		mListView.add(layout3);

		// 初始化当前显示的view
		myViewPager.setCurrentItem(1);

		// 初始化第二个view的信息
		EditText v2EditText = (EditText) layout2.findViewById(R.id.editText1);
		v2EditText.setText("动态设置第二个view的值");

		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				Log.d("k", "onPageScrollStateChanged- " + arg0);
				// 状态有三个:0-空闲，1-正在滑行中，2-目标加载完毕

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// 从1到2滑动，在1滑动前调用此方法
				Log.d("k", "onPageScrolled- " + arg0);
			}

			@Override
			public void onPageSelected(int arg0) {
				// / activity从1到2滑动，2被加载后调用此方法
				View v = mListView.get(arg0);
				EditText editText = (EditText) v.findViewById(R.id.editText1);
				editText.setText("动态设置#" + arg0 + "editText控件的值");
			}

		});

	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			Log.d("k", "instantiateItem");
			((ViewPager) arg0).addView(mListView.get(arg1), 0);
			return mListView.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			Log.d("k", "destroyitem");
			((ViewPager) arg0).removeView(mListView.get(arg1));
		}

		@Override
		// abstract
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			Log.d("k", "isViewFromObject");
			return arg0 == arg1;
		}

		@Override
		// abstract
		public int getCount() {
			Log.d("k", "getCount");
			return mListView.size();

		}
		
		@Override
		public Parcelable saveState() {
			Log.d("k", "saveState");
			return null;

		}

		
		//后边是没用的
		@Override
		public void finishUpdate(View container) {
			// TODO Auto-generated method stub
			Log.d("k", "finishUpdate");
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			Log.d("k", "restoreState");
		}

		

		@Override
		public void startUpdate(View arg0) {
			Log.d("k", "startUpdate");
		}

	}

}
