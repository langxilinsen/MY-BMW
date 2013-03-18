package com.chonwhite.android.snippets.utils;

import java.util.ArrayList;
import java.util.ArrayList;

import java.util.ArrayList;

import java.util.ArrayList;

import java.util.ArrayList;

/**
 * 
 * @author Chonwhite 06/19/2011
 *
 */
public class DataLoader {
	int current = 0;
	int total = 74;
	//a marker to indicate weather the loading operation
	//is in progress,so we can guarantee that,the scrolling event would 
	//not trigger another loading operation when there is one operation 
	//in progress.
	private boolean isLoading = false;
	ArrayList<String> artistList = new ArrayList<String>();
	ArrayList<String> titleList = new ArrayList<String>();
	ArrayList<String> durationList= new ArrayList<String>();
	
	{
		//we gonna use these to produce random data set later;
		for(int i = 0; i < 10;i++){
			artistList.add("M.J.");
			artistList.add("Cara Dillon");
			artistList.add("Meav");
			artistList.add("WestLife");
			artistList.add("BackStreet");
			artistList.add("Linkin Park");
			artistList.add("Enya");
			artistList.add("Eagel");
			artistList.add("Brain Adams");
			artistList.add("Akon");
			
			titleList.add("Heal the world");
			titleList.add("Black is the color");
			titleList.add("One I Love");
			titleList.add("Seaons in the Sun");
			titleList.add("EveryBody");
			titleList.add("In the End");
			titleList.add("May it be");
			titleList.add("Hotel California");
			titleList.add("I will alway return");
			titleList.add("Mr.Lonely");
			
			durationList.add("04:43");
			durationList.add("05:32");
			durationList.add("06:03");
			durationList.add("02:56");
			durationList.add("04:03");
			durationList.add("03:36");
			durationList.add("04:03");
			durationList.add("03:48");
			durationList.add("04:23");
			durationList.add("04:13");
		}
	}
	
	public boolean isLoading(){
		return this.isLoading;
	}
	
	//so that we have a way to communicate with the caller
	ILoadDataResponse responseInterface;
	public DataLoader(ILoadDataResponse response){
		this.responseInterface = response;
	}
	public void loadNext(){
		new Thread(){

			@Override
			public void run() {
				super.run();
				isLoading = true;
				try {
					Thread.sleep(5000);//sleep fine seconds to simulate fetching data
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					isLoading = false;
				}
				if(Math.random() < 0.03){
					//retrieving data from server may fail sometime,
					//just to simulate this kind of occasion;
					responseInterface.onLoadDataError("somehow It failed");
				}else{
					if( current >= total){
						//you may want to handle this situation some other way
						responseInterface.onLoadDataError("no more data available");
						return;
					}
					
					//generate random fake data set,as if they were fetched from the server
					ArrayList<SongInfo> al = new ArrayList<SongInfo>();
					//we don't want to generate more data then the total number 
					for(int i =0; i < ((current + 10 < total)? 10:total - current);i ++){
						SongInfo song = new SongInfo();
						song.artist = artistList.get((int) (Math.random() * 10));
						song.title = titleList.get((int) (Math.random() * 10));
						song.duration = durationList.get((int) (Math.random() * 10));
						al.add(song);
					}
					//mark the number of the data we have generated,
					//this is all for demonstrating purpose
					//totally USELESS in real project.//TODO 
					current = ((current + 10) < total? current + 10: total);
					responseInterface.onLoadDataComplete(al, total, current);
					
				}
			}
			
		}.start();
	};
}
