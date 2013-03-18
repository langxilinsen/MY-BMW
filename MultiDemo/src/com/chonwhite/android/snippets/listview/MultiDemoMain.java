package com.chonwhite.android.snippets.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/***
 * 
 * @author Chonwhite 06/19/2011
 * 
 */
public class MultiDemoMain extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
	}

	private void initUI() {
		setContentView(R.layout.main);
		findViewById(R.id.toCheckboxSnippetButton).setOnClickListener(
				new StartActivityButtonListener(
						MultiSelectionAndEditableActivity.class));
		findViewById(R.id.toProgressbarListSnippetButton).setOnClickListener(
				new StartActivityButtonListener(ProgressbarListActivity.class));
		findViewById(R.id.toPageLoadButton).setOnClickListener(
				new StartActivityButtonListener(PageLoadActivity.class));
	}

	class StartActivityButtonListener implements OnClickListener {
		Class<? extends Activity> targetActivity;

		StartActivityButtonListener(Class<? extends Activity> targetActivity) {
			this.targetActivity = targetActivity;
		}

		@Override
		public void onClick(View v) {
			startActivity(new Intent(MultiDemoMain.this, targetActivity));
		}

	}
}