package com.example.myviewtest;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

public class MyMotionBar extends FrameLayout {
//	private RadioButton radioBut0;
//	private RadioButton radioBut1;
//	private RadioButton radioBut2;
//	private RadioButton radioBut3;
	private FrameLayout myFrame;
	final int SUM = 4;
	RadioButton[] radioButs;
	ImageView[] imageViews;
	int preId = 0;
	int currId = 0;

	public MyMotionBar(Context context) {
		super(context);
	}

	public MyMotionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myFrame = (FrameLayout) inflater.inflate(R.layout.my_motion_bar, null);
		addView(myFrame);
		// radioBut0.setOnClickListener(new OnClickListenerImpl());
		//
		// radioBut1.setOnClickListener(new OnClickListenerImpl());
		//
		// radioBut2.setOnClickListener(new OnClickListenerImpl());
		//
		// radioBut3.setOnClickListener(new OnClickListenerImpl());
		initView();
	}

	


	

	public void initView() {
		radioButs = new RadioButton[SUM];
		radioButs[0] = (RadioButton) this.findViewById(R.id.labor_radio0);
		radioButs[1] = (RadioButton) this.findViewById(R.id.labor_radio1);
		radioButs[2] = (RadioButton) this.findViewById(R.id.labor_radio2);
		radioButs[3] = (RadioButton) this.findViewById(R.id.labor_radio3);
		imageViews = new ImageView[SUM];
		imageViews[0] = (ImageView) this.findViewById(R.id.image0);
		imageViews[1] = (ImageView) this.findViewById(R.id.image1);
		imageViews[2] = (ImageView) this.findViewById(R.id.image2);
		imageViews[3] = (ImageView) this.findViewById(R.id.image3);
		for (int i = 0; i < SUM; i++) {
			radioButs[i].setOnClickListener(clickListener);
			imageViews[i].setVisibility(View.INVISIBLE);
		}
		imageViews[0].setVisibility(View.VISIBLE);
		radioButs[0].setEnabled(false);
		preId = 0;
	}

	private void updataCurView(int curClickID) {
		if (0 <= curClickID && SUM > curClickID) {
			radioButs[preId].setEnabled(true);
			radioButs[curClickID].setEnabled(false);
			imageViews[preId].setVisibility(View.INVISIBLE);
			imageViews[curClickID].setVisibility(View.VISIBLE);
			preId = curClickID;
		}
	}

	private void startSlip(View v) {
		Animation animation = new TranslateAnimation(0.0f, v.getLeft() - radioButs[preId].getLeft(), 0.0f, 0.0f);
		animation.setDuration(300);
		animation.setFillAfter(false);
		animation.setFillBefore(true);
		for (int i = 0; i < SUM; i++) {
			if (radioButs[i] == v) {
				currId = i;
				break;
			}
		}
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				updataCurView(currId);
			}

			public void onAnimationRepeat(Animation animation) {
			}
		});
		imageViews[preId].startAnimation(animation);
	}

	private OnClickListener clickListener = new OnClickListener() {
		public void onClick(final View v) {
			Log.v("log_234234234", "sfasfdasdfa");
			startSlip(v);
		}
	};
	
	public class OnClickListenerImpl implements OnClickListener {
		@Override
		public void onClick(View v) {
			log(v.getId());
		}
	}
	public RadioButton getRadioButton(int i) {
		return radioButs[i];

	}
	public void log(int i) {
		Log.v("log_linsen", String.valueOf(i));
	}

	public void log(String i) {
		Log.v("log_linsen", i);
	}

}
