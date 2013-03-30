package com.krislq.sliding.http;

import java.util.ArrayList;

public class ConnectionManager {
	public static final int MAX_CONNECTION = 5 ;//线程池容量
	private ArrayList<Runnable> active = new ArrayList<Runnable>();
	private ArrayList<Runnable> queue = new ArrayList<Runnable>();
	private static ConnectionManager instance;//实例
	
	public static ConnectionManager getInstance() {
		if(instance == null){
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	public void push(Runnable runnable) {
		queue.add(runnable);
		if(queue.size() < MAX_CONNECTION);
			startNext();
	}
	
	public void startNext() {
		if(!queue.isEmpty()){
			Runnable next = queue.get(0);
			queue.remove(0);
			active.add(next);
			//取线程，加载线程
			Thread thred = new Thread(next);
			thred.start();
		}
	}
	
	public void didComplete(Runnable runnable) {
		//前一个线程执行完毕，继续执行下一个线程
		active.remove(runnable);
		startNext();
	}
}
