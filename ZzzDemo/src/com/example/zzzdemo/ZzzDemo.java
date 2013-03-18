package com.example.zzzdemo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class ZzzDemo extends Activity {
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button)findViewById(R.id.button1);
	
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 
				
				Intent it = new Intent(ZzzDemo.this,TestDialog.class);
				startActivity(it);
				
			}
		});
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		
		// dg = new
		// AlertDialog.Builder(TestDialog.this).setTitle("车小弟正在努力中").create();
		// dg.show();
		super.onResume();
		
		Log.v("start", "onPause");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
