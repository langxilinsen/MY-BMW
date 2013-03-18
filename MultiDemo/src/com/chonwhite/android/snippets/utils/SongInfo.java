package com.chonwhite.android.snippets.utils;

/**
 * 
 * @author Chonwhite 06/19/2011
 *
 */

//a bean to hold data without getter and setter method.
//there is no getter and setter because,accessing a method
//is more expensive than accessing a field.and there is no
//other extra operation required when setting the value.
public class SongInfo {
	public String artist;
	public String title;
	public String duration;
}