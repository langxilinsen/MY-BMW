package com.chonwhite.android.snippets.utils;

import java.util.ArrayList;
/***
 * 
 * @author Chonwhite 06/19/2011
 *
 */
public interface ILoadDataResponse {
	//invoke this method when everything goes right;
	public void onLoadDataComplete(ArrayList<SongInfo> nextSongInfo,int total,int current);
	//tell the caller that ,something wrong happened.
	public void onLoadDataError(String errorMsg);
}
