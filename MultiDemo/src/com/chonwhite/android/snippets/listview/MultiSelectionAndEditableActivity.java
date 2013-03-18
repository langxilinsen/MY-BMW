package com.chonwhite.android.snippets.listview;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @author Chonwhite 06/19/2011
 *
 */
public class MultiSelectionAndEditableActivity extends Activity {
	private static final int NORMAL_MODE = 1025;
	private static final int EDIT_MODE = 1026;

	Activity mActivity;

	private int displayMode = NORMAL_MODE;

	private Button toEditModeButton;
	private ListView normalModeList;
	private ListView editModeList;
	private Button toNormalModeButton;
	private Button deleteButton;

	private EditModeAdapter editModeAdapter;
	private NormalModeAdapter normalModeAdapter;

	// define a class to store data
	class Song {
		Song(String title, String artist, String duration) {
			this.title = title;
			this.artist = artist;
			this.duration = duration;
		}

		String title;
		String artist;
		String duration;
		@Override
		public String toString() {
			return title + "(" + artist + ")";
		}
	}

	// simulated data
	Song[] mySongCollection = new Song[] {
			new Song("Bonny Bonny", "Cara Dillon", "03:45"),
			new Song("Dangerous", "Michael Jackson", "07:00"),
			new Song("Numb", "Linkin Park", "03:45"),
			new Song("My Love", "Westlife", "03:45"),
			new Song("Hero", "Mariah Carey", "04:23"),
			new Song("Hotel Carlifornia", "Eagle", "07:13"),
			new Song("One I Love", "Meav", "02:56"),
			new Song("Flying Without Wing", "Westlife", "03:45"),

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;// for further usage;
		initUI();
	};

	private void initUI() {
		if (displayMode == NORMAL_MODE) {
			toNormalMode();
		} else {
			toEditMode();
		}
	};

	private void toEditMode() {
		displayMode = EDIT_MODE;
		this.setContentView(R.layout.multiselection_edit);
		editModeList = (ListView) findViewById(R.id.editModeList);
		editModeAdapter = new EditModeAdapter();
		editModeList.setAdapter(editModeAdapter);
		editModeList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long id) {
				editModeAdapter.toggle(position);
			}
		});
		editModeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		toNormalModeButton = (Button) findViewById(R.id.toNormalModeButton);
		toNormalModeButton.setOnClickListener(new SwitchModeButtonListener());
		deleteButton = (Button) findViewById(R.id.editModeDeleteButton);
		deleteButton.setOnClickListener(new DeleteButtonListener());
	};

	private void toNormalMode() {
		displayMode = NORMAL_MODE;
		this.setContentView(R.layout.multiselection_normal);
		normalModeList = (ListView) findViewById(R.id.normalModeList);
		normalModeAdapter = new NormalModeAdapter();
		normalModeList.setAdapter(normalModeAdapter);
		toEditModeButton = (Button) findViewById(R.id.toEditModeButton);
		toEditModeButton.setOnClickListener(new SwitchModeButtonListener());
	}

	class SwitchModeButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (displayMode == NORMAL_MODE)
				toEditMode();
			else
				toNormalMode();
		}
	}

	class DeleteButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int[] selectedItemIndexes = editModeAdapter.getSelectedItemIndexes();
			int size = selectedItemIndexes.length;
			if (size == 0) {
				Toast.makeText(mActivity, "Nothing to delete",
						Toast.LENGTH_SHORT).show();
			} else {
				AlertDialog.Builder confirmDeletionDialog = new AlertDialog.Builder(
						mActivity);
				StringBuilder deletionList = new StringBuilder();
				for(int i = 0; i < size; i++){
					deletionList.append(mySongCollection[selectedItemIndexes[i]].toString() + " , ");
				};
				confirmDeletionDialog.setTitle("Delete");
				confirmDeletionDialog.setMessage("Are you sure you want to delete " + deletionList
						+ " ?");
				confirmDeletionDialog.setPositiveButton("Yes,I'm sure", null);
				confirmDeletionDialog.setNegativeButton("No,I regret", null);
				confirmDeletionDialog.create().show();
			}
		}
	}

	static class ViewHolderNormal {
		TextView titleText;
		TextView artistText;
		TextView durationText;
	}

	class NormalModeAdapter extends BaseAdapter {
		LayoutInflater inflater = null;

		@Override
		public int getCount() {
			return mySongCollection.length;
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

			ViewHolderNormal viewHolder;
			if (convertView == null) {
				if (inflater == null) {
					inflater = (LayoutInflater) mActivity
							.getSystemService(LAYOUT_INFLATER_SERVICE);
				}
				convertView = inflater.inflate(R.layout.item_normal, null);
				viewHolder = new ViewHolderNormal();
				viewHolder.artistText = (TextView) convertView
						.findViewById(R.id.artistTextNormal);
				viewHolder.titleText = (TextView) convertView
						.findViewById(R.id.titleTextNormal);
				viewHolder.durationText = (TextView) convertView
						.findViewById(R.id.durationTextNormal);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderNormal) convertView.getTag();
			}
			viewHolder.titleText.setText(mySongCollection[position].title);
			viewHolder.artistText.setText(mySongCollection[position].artist);
			viewHolder.durationText
					.setText(mySongCollection[position].duration);
			return convertView;
		}
	}

	static class ViewHolderEdit {
		TextView titleText;
		TextView artistText;
		TextView durationText;
		CheckBox checkBox;
	}

	class EditModeAdapter extends BaseAdapter {
		LayoutInflater inflater = null;
		boolean[] itemStatus;
		
		{
			itemStatus = new boolean[mySongCollection.length];
			
		}

		@Override
		public int getCount() {
			return mySongCollection.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		public void toggle(int position){
			if(itemStatus[position] == true){
				itemStatus[position] = false;
			}else{
				itemStatus[position] = true;
			}
			this.notifyDataSetChanged();//date changed and we should refresh the view
		}
		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		public int[] getSelectedItemIndexes() {

			if (itemStatus == null || itemStatus.length == 0) {
				return new int[0];
			} else {
				int size = itemStatus.length;
				int counter = 0;
				// TODO how can we skip this iteration?
				for (int i = 0; i < size; i++) {
					if (itemStatus[i] == true)
						++counter;
				}
				int[] selectedIndexes = new int[counter];
				int index = 0;
				for (int i = 0; i < size; i++) {
					if (itemStatus[i] == true)
						selectedIndexes[index++] = i;
				}
				return selectedIndexes;
			}
		};

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolderEdit viewHolder;
			if (convertView == null) {
				if (inflater == null) {
					inflater = (LayoutInflater) mActivity
							.getSystemService(LAYOUT_INFLATER_SERVICE);
				}
				convertView = inflater.inflate(R.layout.item_edit, null);
				viewHolder = new ViewHolderEdit();
				viewHolder.artistText = (TextView) convertView
						.findViewById(R.id.artistTextEdit);
				viewHolder.titleText = (TextView) convertView
						.findViewById(R.id.titleTextEdit);
				viewHolder.durationText = (TextView) convertView
						.findViewById(R.id.durationTextEdit);
				viewHolder.checkBox = (CheckBox) convertView
						.findViewById(R.id.checkBoxEdit);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderEdit) convertView.getTag();
			}
			viewHolder.titleText.setText(mySongCollection[position].title);
			viewHolder.artistText.setText(mySongCollection[position].artist);
			viewHolder.durationText
					.setText(mySongCollection[position].duration);
			viewHolder.checkBox
					.setOnCheckedChangeListener(new MyCheckBoxChangedListener(
							position));
			if (itemStatus[position] == true) {
				viewHolder.checkBox.setChecked(true);
			} else {
				viewHolder.checkBox.setChecked(false);
			}
			return convertView;
		}

		class MyCheckBoxChangedListener implements OnCheckedChangeListener {
			int position;

			MyCheckBoxChangedListener(int position) {
				this.position = position;
			}

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				System.out.println("" + position + "Checked?:" + isChecked);
				if (isChecked)
					itemStatus[position] = true;
				else
					itemStatus[position] = false;
			}

		}
	}
}
