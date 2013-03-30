package com.example.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v("33333333333333","MyFragment  onCreate");
		return inflater.inflate(R.layout.article_other	, null,false);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onStart() {
		Log.v("33333333333333","MyFragment  onStart");
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
    public void onResume() {
    	Log.v("33333333333333","MyFragment  onResume");
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    @Override
    public void onStop() {
    	Log.v("33333333333333","MyFragment  onStop");
    	// TODO Auto-generated method stub
    	super.onStop();
    }
    @Override
    public void onPause() {
    	Log.v("33333333333333","MyFragment  onPause");
    	// TODO Auto-generated method stub
    	super.onPause();
    }
    @Override
    public void onDestroy() {
    	Log.v("33333333333333","MyFragment  onDestroy");
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }
}
