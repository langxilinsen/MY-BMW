package com.krislq.sliding.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class HttpConnection implements Runnable{
//	private static String BaseUrl = "https://api.chexiaodi.com.cn/0_7_0/"; 
	private static String BaseUrl = "http://192.168.18.98:8080/server_api/index.php/0_7_0/"; 
	private static String UserAgent = "Android/0.1";
	public static final int DID_START = 0;
	public static final int DID_ERROR = 1;
	public static final int DID_SUCCEED = 2;
	
	private static final int GET = 0;
	private static final int POST = 1;
	
	private CallbackListener listener;
	private HttpClient httpClient;
	private String url;
	private int method;
	private List<NameValuePair> data;
	
	public void create(int method,String url ,List<NameValuePair> data,CallbackListener listener) {
		this.method = method;
		this.url = BaseUrl + url;
		Log.v("url", this.url);
		this.data = data;
		this.listener = listener;
		ConnectionManager.getInstance().push(this);
	}
	
	public void post(String url, List<NameValuePair> data, CallbackListener listener) {
		create(POST, url, data, listener);
	}
	
	public interface CallbackListener {  
        public void callBack(String result);  
    }  
	
	private static final Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HttpConnection.DID_START:
				Log.v("DID_START", "start");
				break;
			case HttpConnection.DID_SUCCEED:{
				Log.v("DID_SUCCEED", "succeed");
				CallbackListener listener = (CallbackListener)msg.obj;
				Object data = msg.getData();
				if(listener != null){
					if(data != null){
						Bundle bundle = (Bundle)data;
						String result = bundle.getString("callbackkey");
						Log.v("result_connection", result);
						listener.callBack(result);
					}
				}	
			}
				break;
			case HttpConnection.DID_ERROR:
				Log.v("DID_ERROR", "error");
				break;
			default:
				break;
			}
		}
	};
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		httpClient = getHttpCilent();
		try {
			HttpResponse httpReponse = null;
			switch (method) {
			case POST:
				HttpPost httpPost = new HttpPost(url);
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
				BasicNameValuePair pair1 = new BasicNameValuePair("UserAgent", UserAgent);//������Ϣ
				data.add(pair1);
				httpPost.setEntity(new UrlEncodedFormEntity(data,"UTF-8"));
				httpReponse = httpClient.execute(httpPost);
				if(isHttpSuccessExecuted(httpReponse)){
					String result = EntityUtils.toString(httpReponse.getEntity());
					this.sendMessage(result);
				}else{
					this.sendMessage("fail");
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			this.sendMessage("fail");
		}
		ConnectionManager.getInstance().didComplete(this);
	}
	
	private void sendMessage(String result){
		Message message = Message.obtain(handler, DID_SUCCEED,listener);
		Bundle data = new Bundle();
		data.putString("callbackkey", result);
		message.setData(data);
		handler.sendMessage(message);
	}
	
	
	public static DefaultHttpClient getHttpCilent() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 45000);
		HttpConnectionParams.setSoTimeout(httpParams, 45000);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}
	
	public static boolean isHttpSuccessExecuted(HttpResponse response) {
		int statusCode = response.getStatusLine().getStatusCode();
		return (statusCode > 199)&&(statusCode < 400);
	}
}
