package com.example.myandroidfragment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myandroidfragment.R;

public class HeadlinesFragment extends ListFragment {
	OnHeadlineSelectedListener mCallback;

	public interface OnHeadlineSelectedListener {
		public void onArticleSelected(int position);
	}

//	public void onAttach(FragmentActivity fragmentActivity) {
//		super.onAttach(fragmentActivity);
//		try {
//			mCallback = (OnHeadlineSelectedListener) fragmentActivity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(fragmentActivity.toString()
//					+ "MUST IMPLEMENT OnHeadlineSelectedListener");
//		}
//	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.headlines_view, container, false);
	}
}
