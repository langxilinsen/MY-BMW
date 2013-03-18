package com.example.zzzdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TestDialog extends Activity {

	Dialog dg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Log.v("create", "onCreate");
		setContentView(R.layout.dialog_show);
		// this.onPause();
		// this.onResume();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub

		super.onStart();
		

		Log.v("start", "onStart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		onPause();
		dg.cancel();
		super.onResume();
		
		Log.v("start", "onResume");
	}
	@Override
	protected void onPause(){
		super.onPause();
		Log.v("start", "onPause");
		dg = new AlertDialog.Builder(this).setTitle("车小弟正在努力中").create();
		dg.show();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onResume();
	}
}
