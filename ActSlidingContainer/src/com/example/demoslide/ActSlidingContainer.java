package com.example.demoslide;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.example.demoslide.SlidingContainer.OnSlidingListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActSlidingContainer extends Activity implements OnClickListener, OnSlidingListener {
	private SlidingContainer slidingContainer;
	private SlidingIndicator slidingIndicator;
	private Button btnLeft, btnRight, btnMid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnLeft = (Button) findViewById(R.id.left);
		btnLeft.setOnClickListener(this);
		btnRight = (Button) findViewById(R.id.right);
		btnRight.setOnClickListener(this);
		btnMid = (Button) findViewById(R.id.mid);
		btnMid.setOnClickListener(this);

		slidingContainer = (SlidingContainer) findViewById(R.id.slidingContainer);
		slidingContainer.setOnSlidingListener(this);
		slidingIndicator = (SlidingIndicator) findViewById(R.id.slidingIndicator);
		slidingIndicator.setPageAmount(slidingContainer.getChildCount());
	}

	@Override
	public void onClick(View v) {
		if (v == btnLeft) {
			slidingContainer.scroll2page(slidingContainer.getCurrentPage() - 1);
		} else if (v == btnRight) {
			slidingContainer.scroll2page(slidingContainer.getCurrentPage() + 1);
		} else if (v == btnMid) {
			slidingContainer.scroll2page(slidingContainer.getChildCount() >> 1);
		}
	}

	@Override
	public void onSliding(int scrollX) {
		float scale = (float) (slidingContainer.getPageWidth() * slidingContainer.getChildCount())
				/ (float) slidingIndicator.getWidth();
		slidingIndicator.setPosition((int) (scrollX / scale));
	}

	@Override
	public void onSlidingEnd(int pageIdx, int scrollX) {
		slidingIndicator.setCurrentPage(pageIdx);
	}
}