package com.example.myviewtest;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
	private RadioGroup radioGroup;
	private MyMotionBar bar;
	private RadioButton but0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bar = (MyMotionBar)findViewById(R.id.my_framelayout);
		but0 = bar.getRadioButton(0);
		but0.setOnTouchListener(new OnTouchListener() {
	
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			Log.v("234","sdf");
			return false;
		}
		
	});
	}
	
}
//bar = (MyMotionBar)findViewById(R.id.my_framelayout);
//bar.setClickable(true);
//bar.setOnClickListener(new OnClickListener() {
//	@Override
//	public void onClick(View arg0) {
//		Log.v("234","sdf");
//		
//	}
//});
//but = (Button)findViewById(R.id.button1);
//
//but.setOnClickListener(new OnClickListener() {
//	@Override
//	public void onClick(View arg0) {
//		Log.v("234","sdf");
//		
//	}
//});