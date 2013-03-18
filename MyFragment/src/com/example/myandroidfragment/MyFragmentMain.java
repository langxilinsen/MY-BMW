package com.example.myandroidfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.myandroidfragment.fragment.ArticleFragment;
import com.example.myandroidfragment.fragment.HeadlinesFragment;

public class MyFragmentMain extends FragmentActivity
		implements HeadlinesFragment.OnHeadlineSelectedListener{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);

//		if (findViewById(R.id.fragment_container) != null) {
//			if (savedInstanceState != null) {
//				return;
//			}
//			HeadlinesFragment firstFragment = new HeadlinesFragment();
//			// ArticleFragment firstFragment = new ArticleFragment();
//			firstFragment.setArguments(getIntent().getExtras());
//
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.fragment_container, firstFragment).commit();
//
//		}

//		// Create fragment and give it an argument specifying the article it
//		// should show
//		ArticleFragment newFragment = new ArticleFragment();
//		Bundle args = new Bundle();
//		args.putInt(ArticleFragment.ARG_POSITION, 1);
//		newFragment.setArguments(args);
//
//		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//		// Replace whatever is in the fragment_container view with this
//		// fragment,
//		// and add the transaction to the back stack so the user can navigate
//		// back
//		transaction.replace(R.id.fragment_container, newFragment);
//		transaction.addToBackStack(null);
//
//		// Commit the transaction
//		transaction.commit();
	}

	@Override
	public void onArticleSelected(int position) {
		// TODO Auto-generated method stub
		
	}
}