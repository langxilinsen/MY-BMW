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

	private MyPagerAdapter myAdapter; // �Զ����PagerAdapter����

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

		// ��ʼ����ǰ��ʾ��view
		myViewPager.setCurrentItem(1);

		// ��ʼ���ڶ���view����Ϣ
		EditText v2EditText = (EditText) layout2.findViewById(R.id.editText1);
		v2EditText.setText("��̬���õڶ���view��ֵ");

		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				Log.d("k", "onPageScrollStateChanged- " + arg0);
				// ״̬������:0-���У�1-���ڻ����У�2-Ŀ��������

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// ��1��2��������1����ǰ���ô˷���
				Log.d("k", "onPageScrolled- " + arg0);
			}

			@Override
			public void onPageSelected(int arg0) {
				// / activity��1��2������2�����غ���ô˷���
				View v = mListView.get(arg0);
				EditText editText = (EditText) v.findViewById(R.id.editText1);
				editText.setText("��̬����#" + arg0 + "editText�ؼ���ֵ");
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

		
		//�����û�õ�
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
