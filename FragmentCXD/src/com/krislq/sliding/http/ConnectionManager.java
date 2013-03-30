package com.krislq.sliding.http;

import java.util.ArrayList;

public class ConnectionManager {
	public static final int MAX_CONNECTION = 5 ;//�̳߳�����
	private ArrayList<Runnable> active = new ArrayList<Runnable>();
	private ArrayList<Runnable> queue = new ArrayList<Runnable>();
	private static ConnectionManager instance;//ʵ��
	
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
			//ȡ�̣߳������߳�
			Thread thred = new Thread(next);
			thred.start();
		}
	}
	
	public void didComplete(Runnable runnable) {
		//ǰһ���߳�ִ����ϣ�����ִ����һ���߳�
		active.remove(runnable);
		startNext();
	}
}
