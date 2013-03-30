/*
 * * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentArticle extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;  //表示当前（或操作之前）正在显示的是哪个位置的fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	Log.v("33333333333333","FragmentArticle  onCreatView");
    	//***********************************重新创建activity的时候，通过判断savedInstanceState，来调用之前的article
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.article_view, container, false);
        
    }
    @Override
    public void onStart() {
    	Log.v("33333333333333","FragmentArticle  onStart");
        super.onStart();
        //Start()的时候，检查是还是有参数赋给Fragment。如果有则调用
        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = this.getArguments();  //获取的是当前Fragment对象的Arguments
        if (args != null) {
            // Set article based on argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
    	if(position==2){
    		FrameLayout myContainer = (FrameLayout)getActivity().findViewById(R.id.article_container);
    		LayoutInflater lI = getLayoutInflater(null);
    		myContainer.addView(lI.inflate(R.layout.article_other, null));
    		
    		TextView article = (TextView) getActivity().findViewById(R.id.article);
    		article.setText("我自己试一下哈");
    	}else{
    		TextView article = (TextView) getActivity().findViewById(R.id.article);
    		article.setText(Ipsum.Articles[position]);
    	}
        
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //此方法用来储存一个fragment对象的状态数据
        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
    @Override
    public void onResume() {
    	Log.v("33333333333333","FragmentArticle  onResume");
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    @Override
    public void onStop() {
    	Log.v("33333333333333","FragmentArticle  onStop");
    	// TODO Auto-generated method stub
    	super.onStop();
    }
    @Override
    public void onPause() {
    	Log.v("33333333333333","FragmentArticle  onPause");
    	// TODO Auto-generated method stub
    	super.onPause();
    }
    @Override
    public void onDestroy() {
    	Log.v("33333333333333","FragmentArticle  onDestroy");
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }
}