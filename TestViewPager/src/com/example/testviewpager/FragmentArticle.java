package com.example.testviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentArticle extends Fragment{
	final static String ARG_POSITION = "position"; //Fragment类中就定义了这个
    int myCurrentPosition = -1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
		if(savedInstanceState != null)
			myCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		return inflater.inflate(R.layout.article_view	, null, false);
	}
	
	@Override
	public void onStart() {
		// During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
		super.onStart();
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
}
