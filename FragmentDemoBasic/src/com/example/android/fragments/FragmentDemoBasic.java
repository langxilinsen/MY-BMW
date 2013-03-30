/*
 * Copyright (C) 2012 The Android Open Source Project
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FragmentDemoBasic extends FragmentActivity 
        implements FragmentHeadlines.OnHeadlineSelectedListener {
	private int two = 0;
	private int zero = 0;
	private int one = 0;
	private Button btn1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment,一个FrameLayout当的Container
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            FragmentHeadlines headFragment = new FragmentHeadlines();
  //          articleFragmentment firstFragment = new articleFragmentment();
            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            headFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout***这句代码要记住
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, headFragment).commit();
            
        }
    }
    private Bundle args1 = new Bundle();
    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
    	if(position==1){

    		Log.v("33333333333333","11111111111111111111111111111111111111111");
    		MyFragment myFragment = new MyFragment();
    		args1.putInt("times", one++);
    		myFragment.setArguments(args1);
    		Toast.makeText(this, "how much for second"+myFragment.getArguments().getInt("times"), 100).show();
    		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    		
    		transaction.replace(R.id.fragment_container_article,myFragment).commit();
 //   		transaction.addToBackStack(null);
    	}else{
    		Log.v("33333333333333","02020202202020202020202020202002020202020");
	        FragmentArticle articleFragment = (FragmentArticle)
	                getSupportFragmentManager().findFragmentById(R.id.article_fragment);
	
	        if (articleFragment != null) {
	            // If article frag is available, we're in two-pane layout...
	
	            // Call a method in the articleFragmentment to update its content
	            articleFragment.updateArticleView(position);
	            Log.v("33333333333333","articleFragment != null");
	
	        } else {
	            // If the frag is not available, we're in the one-pane layout and must swap frags...
	
	            // Create fragment and give it an argument for the selected article
	            FragmentArticle newFragment = new FragmentArticle();
	            Log.v("33333333333333","new FragmentArticle();");
	            Bundle args = new Bundle();
	            args.putInt(FragmentArticle.ARG_POSITION, position);
	            args.putInt("times", two++);
	            newFragment.setArguments(args);

	            Toast.makeText(this, "how much others"+newFragment.getArguments().getInt("times"), 100).show();
	            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	
	            // Replace whatever is in the fragment_container view with this fragment,
	            // and add the transaction to the back stack so the user can navigate back
	            transaction.replace(R.id.fragment_container_article, newFragment);
	            transaction.addToBackStack(null);
	
	            // Commit the transaction
	            transaction.commit();
	        }
    	}
    }
}