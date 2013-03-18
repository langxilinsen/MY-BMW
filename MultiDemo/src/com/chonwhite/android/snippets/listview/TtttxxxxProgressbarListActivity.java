package com.chonwhite.android.snippets.listview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 
 * @author Chonwhite 06/19/2011
 * 
 */
public class ProgressbarListActivity extends Activity {
	private static final int UPDATE_PROGRESS = 1025;
	private static final int DOWNLOAD_COMPLETE = 1026;
	private static final int UPDATE_GAP = 1000;// 1 second

	private boolean DEBUG = true;

	Activity mActivity;

	ListView progressListView;
	DownloadAdapter mAdapter;
	long lastUpdatedTime = 0;
	boolean buttonInListClicked = false;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case UPDATE_PROGRESS:
				// 视野内的文件 的进度发生改时才会刷新，并且刷新间隔不大于UPDATE_GAP
				// 当ListView中的按钮被按下时刷新界面会出新一些问题，比如按下了第2个按钮，当界面刷新的时候有可能
				// 显示第4个按钮被按下了，所以先作个检查，当有ListView当中的按钮被按下时，不进行刷新
				if (msg.arg1 >= progressListView.getFirstVisiblePosition()
						&& msg.arg1 <= progressListView
								.getFirstVisiblePosition()
								+ progressListView.getChildCount()
						&& System.currentTimeMillis() - lastUpdatedTime > UPDATE_GAP
						&& buttonInListClicked == false) {
					mAdapter.notifyDataSetChanged();
					lastUpdatedTime = System.currentTimeMillis();
				}
				break;
			case DOWNLOAD_COMPLETE:
				Toast.makeText(mActivity,
						downloadInfos.get(msg.arg1).fileName + "下载完成",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};

	class DownloadInfo {
		DownloadInfo(String fileName) {
			this.fileName = fileName;

		}

		String fileName;
		int progress = 0;
	}

	ArrayList<DownloadInfo> downloadInfos = new ArrayList<DownloadInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		initUI();
	}

	private void initUI() {
		setContentView(R.layout.progressbar);
		progressListView = (ListView) findViewById(R.id.progressbarList);
		mAdapter = new DownloadAdapter();
		progressListView.setAdapter(mAdapter);
		for (int i = 0; i < 20; i++) {
			new DownloadThread(i).start();
			downloadInfos.add(new DownloadInfo("File " + i));
		}
		mAdapter.notifyDataSetChanged();
	}

	static class ViewHolder {
		TextView fileNameText;
		ProgressBar progress;
		Button button;
	}

	class DownloadAdapter extends BaseAdapter {

		LayoutInflater inflater = null;

		@Override
		public int getCount() {
			return downloadInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO DO observe this result in console ,and see if it works.
			if (DEBUG)
				System.out.println("refreshing" + position);
			ViewHolder viewHolder;
			if (convertView == null) {
				if (inflater == null) {
					inflater = (LayoutInflater) mActivity
							.getSystemService(LAYOUT_INFLATER_SERVICE);
				}
				convertView = inflater.inflate(R.layout.download_item, null);
				viewHolder = new ViewHolder();
				viewHolder.fileNameText = (TextView) convertView
						.findViewById(R.id.fileNameText);
				viewHolder.progress = (ProgressBar) convertView
						.findViewById(R.id.downloadProgress);
				viewHolder.button = (Button) convertView
						.findViewById(R.id.statusButton);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.fileNameText
					.setText(downloadInfos.get(position).fileName);
			viewHolder.progress
					.setProgress(downloadInfos.get(position).progress);
			viewHolder.button.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						buttonInListClicked = true;
					} else if(event.getAction() == MotionEvent.ACTION_UP){
						buttonInListClicked = false;
						mAdapter.notifyDataSetChanged();
					}else{
						buttonInListClicked = false;
					}
					return false;
				}
			});
			return convertView;
		}
	}

	// a thread class to simulate downloading  files from the Internet
	class DownloadThread extends Thread {
		int index;

		DownloadThread(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			while (downloadInfos.get(index).progress < 100) {
				downloadInfos.get(index).progress += Math.random()
						* (2 + this.index);
				// we cant't not update the UI from here,we have to ask the
				// Handler
				// to do the job for us,as it is running in the UI Thread.
				Message msg = mHandler.obtainMessage();
				msg.what = UPDATE_PROGRESS;
				msg.arg1 = this.index;
				msg.sendToTarget();
				try {
					Thread.sleep((this.index + 1) * 500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// download is complete and we should notify the user.
			Message msg = mHandler.obtainMessage();
			msg.what = DOWNLOAD_COMPLETE;
			msg.arg1 = this.index;
			msg.sendToTarget();

			// there is a chance that mHandler din't update our progressbar for
			// us
			// if this is the last to finish
			// just to make sure that the last finished file's progress get
			// updated
			Message finalUpdate = mHandler.obtainMessage();
			finalUpdate.what = UPDATE_PROGRESS;
			mHandler.sendMessageDelayed(finalUpdate, UPDATE_GAP + 100);
		}
	}
}
