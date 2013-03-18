package com.chonwhite.android.snippets.listview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.chonwhite.android.snippets.utils.DataLoader;
import com.chonwhite.android.snippets.utils.ILoadDataResponse;
import com.chonwhite.android.snippets.utils.SongInfo;

/***
 * 
 * @author Chonwhite 06/19/2011
 *
 */
public class PageLoadActivity extends Activity {
	private static final int UPDATE_LISTVIEW = 1025;
	private static final int ERROR = 1026;
	
	Activity mActivity;
	TextView displayText;
	ListView pageLoadList;
	
	SongAdapter mAdapter;

	ArrayList<SongInfo> songList = new ArrayList<SongInfo>();
	
	DataLoader dataLoader;
	//a callback from DataLoader
	ILoadDataResponse dataResponse = new ILoadDataResponse(){
		@Override
		public void onLoadDataComplete(ArrayList<SongInfo> songInfos,
				int total, int current) {
			//TODO remove loadinView from listView
			songList.addAll(songInfos);
			System.out.println("tatal =" + total + "current =" + current);
			//use Handler.obtainMessage(),instead of msg = new Message();
			//because if there is already an Message object,that not be used by 
			//any one ,the system will hand use that object,so you don't have to 
			//create and object and allocate memory.
			//it  is also another example of object recycling and reusing in android.
			Message msg = mHandler.obtainMessage();
			msg.what = UPDATE_LISTVIEW;
			msg.obj = current + "/" + total + "songs";
			//this method is called from worker Thread,so we cannot update UI from here.
			msg.sendToTarget();
			
		}

		@Override
		public void onLoadDataError(String errorMsg) {
			//TODO remove loading View
			Message msg = mHandler.obtainMessage();
			msg.what = ERROR;
			msg.obj = errorMsg;
			msg.sendToTarget();
		}
	};
	
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case UPDATE_LISTVIEW:
				mAdapter.notifyDataSetChanged();
				pageLoadList.removeFooterView(loadingView);
				displayText.setText((String)msg.obj);
				break;
			case ERROR:
				Toast.makeText(mActivity, (String) msg.obj, Toast.LENGTH_SHORT).show();
				//we should remove the loading footer view in either case;
				pageLoadList.removeFooterView(loadingView);
				break;
			}
		}
		
	};
	private View loadingView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		dataLoader = new DataLoader(dataResponse);
		initUI();
		
	}

	private void initUI() {
		setContentView(R.layout.page_load);
		LayoutInflater inflater = (LayoutInflater) mActivity
			.getSystemService(LAYOUT_INFLATER_SERVICE);
		
		loadingView = inflater.inflate(R.layout.loading, null);
		displayText = (TextView) findViewById(R.id.pageLoadDisplayText);
		pageLoadList = (ListView) findViewById(R.id.pageLoadList);
		pageLoadList.setOnScrollListener(new MyScrollListener());
		mAdapter = new SongAdapter();
		pageLoadList.setAdapter(mAdapter);
		loadNext();
		
	}
	
	
	private void loadNext(){
		pageLoadList.addFooterView(loadingView);
		dataLoader.loadNext();
		//TODO show Loading view;
	};

	class MyScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			//we want to load the next chunk of data before the user reach the end 
			//of the list.
			if(firstVisibleItem + visibleItemCount < totalItemCount - 3)
				return;
			if(dataLoader.isLoading() == false){
				loadNext();
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}
	}
	
	// the following  piece of code was borrowed from MultiSelectionAndEditableActivity
	// and pasted in here,cause I'm just lazy to extract the class.but
	// we may want to extract the adapter class ,so that it would be more reusable.

	class ViewHolder{
		TextView titleText;
		TextView artistText;
		TextView durationText;
	}
	class SongAdapter extends BaseAdapter {
		LayoutInflater inflater = null;
		

		@Override
		public int getCount() {
			return songList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				if (inflater == null) {
					inflater = (LayoutInflater) mActivity
							.getSystemService(LAYOUT_INFLATER_SERVICE);
				}
				convertView = inflater.inflate(R.layout.item_normal, null);
				viewHolder = new ViewHolder();
				viewHolder.artistText = (TextView) convertView
						.findViewById(R.id.artistTextNormal);
				viewHolder.titleText = (TextView) convertView
						.findViewById(R.id.titleTextNormal);
				viewHolder.durationText = (TextView) convertView
						.findViewById(R.id.durationTextNormal);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.titleText.setText(songList.get(position).title);
			viewHolder.artistText.setText(songList.get(position).artist);
			viewHolder.durationText
					.setText(songList.get(position).duration);
			
			return convertView;
		}
	}
}
