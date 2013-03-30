package com.krislq.sliding.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.krislq.sliding.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.krislq.sliding.activity.RegisterViewActivity;
import com.krislq.sliding.http.HttpConnection;


import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class LoginFragment extends Fragment {
	private TextView title;
	private Button loginButton;
	private Button weixinButton;
	private Button goRegButton;
	private EditText userNameEdit;
	private EditText passwordEdit;
	private static final String WeiXin_Id = "wx49bab52e4bc352bd";
	private IWXAPI api;

	public LoginFragment(){
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(getActivity(), WeiXin_Id, true);
		api.registerApp(WeiXin_Id);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.login_activity, null);
		loginButton = (Button) view.findViewById(R.id.login_login_button);
		userNameEdit = (EditText) view.findViewById(R.id.login_username_edit);
		passwordEdit = (EditText) view.findViewById(R.id.login_password_edit);

		loginButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				String phone = userNameEdit.getText().toString();
				String regEx = "^[1](3|5|8)[0-9]{9}$";
				// "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
				String password = passwordEdit.getText().toString();
				String regPx = "^[0-9A-Za-z]{6,32}$";
				boolean ru = Pattern.compile(regEx).matcher(phone).find();
				boolean rp = Pattern.compile(regPx).matcher(password).find();

				if (!ru) {
					Toast.makeText(getActivity(), "用户名或者密码格式错误",
							Toast.LENGTH_SHORT).show();
					userNameEdit.setSelected(true);
				} else if (!rp) {
					Toast.makeText(getActivity(), "用户名或者密码格式错误",
							Toast.LENGTH_SHORT).show();
				} else {

					// TODO Auto-generated method stub
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					BasicNameValuePair pair1 = new BasicNameValuePair(
							"UserName", phone);// 附加信息
					params.add(pair1);
					BasicNameValuePair pair2 = new BasicNameValuePair(
							"PassWord", password);// 附加信息
					params.add(pair2);

					new com.krislq.sliding.http.HttpConnection().post("logins", params,
							new HttpConnection.CallbackListener() {

								@Override
								public void callBack(String result) {
									// TODO Auto-generated method stub
									Log.v("result", result);
									Log.v("Chexiaodi", "123456");
									if (result != "fail") {

									} else {

									}
								}
							});
				}
			}
		});

		weixinButton = (Button) view.findViewById(R.id.weixin);
		weixinButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WXTextObject textObject = new WXTextObject();
				textObject.text = "Android微信接口测试";
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObject;
				msg.description = "车小弟";

				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = "text";
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				Log.v("weixin", "action");
				api.sendReq(req);
			}
		});
		// 窗口主动调整以适应输入法
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN-----SOFT_INPUT_ADJUST_RESIZE
		goRegButton = (Button) view.findViewById(R.id.login_goreg_button);
		goRegButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent();
				it.setClass(getActivity(), RegisterViewActivity.class);
				startActivity(it);
			}
		});

		return view;
	}

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// /*注册app_id到微信*/
	// api = WXAPIFactory.createWXAPI(this, WeiXin_Id, true);
	// api.registerApp(WeiXin_Id);
	// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
	// setContentView(R.layout.login_activity);
	// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
	// R.layout.title);//自定义布局赋值
	// title = (TextView)findViewById(R.id.title);
	// loginButton = (Button)findViewById(R.id.login_login_button);
	// title.setText("登陆");
	// userNameEdit = (EditText)findViewById(R.id.login_username_edit);
	// passwordEdit = (EditText)findViewById(R.id.login_password_edit);
	//
	// loginButton.setOnClickListener(new OnClickListener() {
	//
	// public void onClick(View v) {
	//
	// String phone = userNameEdit.getText().toString();
	// String regEx = "^[1](3|5|8)[0-9]{9}$";
	// //"^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
	// String password = passwordEdit.getText().toString();
	// String regPx = "^[0-9A-Za-z]{6,32}$";
	// boolean ru = Pattern.compile(regEx).matcher(phone).find();
	// boolean rp = Pattern.compile(regPx).matcher(password).find();
	//
	// if(!ru){
	// Toast.makeText(LoginFragment.this, "用户名或者密码格式错误",
	// Toast.LENGTH_SHORT).show();
	// userNameEdit.setSelected(true);
	// }else if(!rp){
	// Toast.makeText(LoginFragment.this, "用户名或者密码格式错误",
	// Toast.LENGTH_SHORT).show();
	// }else{
	//
	// // TODO Auto-generated method stub
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// BasicNameValuePair pair1 = new BasicNameValuePair("UserName",
	// phone);//附加信息
	// params.add(pair1);
	// BasicNameValuePair pair2 = new BasicNameValuePair("PassWord",
	// password);//附加信息
	// params.add(pair2);
	//
	// new HttpConnection().post("logins", params,new
	// HttpConnection.CallbackListener() {
	//
	// @Override
	// public void callBack(String result) {
	// // TODO Auto-generated method stub
	// Log.v("result", result);
	// Log.v("Chexiaodi", "123456");
	// if(result != "fail"){
	//
	// }else{
	//
	// }
	// }
	// } );
	// }
	// }
	// });
	//
	// weixinButton = (Button)findViewById(R.id.weixin);
	// weixinButton.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// WXTextObject textObject = new WXTextObject();
	// textObject.text = "Android微信接口测试";
	// WXMediaMessage msg = new WXMediaMessage();
	// msg.mediaObject = textObject;
	// msg.description = "车小弟";
	//
	// SendMessageToWX.Req req = new SendMessageToWX.Req();
	// req.transaction = "text";
	// req.message = msg;
	// req.scene = SendMessageToWX.Req.WXSceneTimeline ;
	// Log.v("weixin", "action");
	// api.sendReq(req);
	// }
	// });
	// //窗口主动调整以适应输入法
	// this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
	// );
	// //
	// WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN-----SOFT_INPUT_ADJUST_RESIZE
	// goRegButton = (Button) findViewById(R.id.login_goreg_button);
	// goRegButton.setOnClickListener(new OnClickListener(){
	// public void onClick(View v){
	// Intent it = new Intent();
	// it.setClass(LoginFragment.this, RegisterViewActivity.class);
	// startActivity(it);
	// }
	// });
	//
	//
	// }
	// private class OnTouchListenerImpl implements OnTouchListener{
	// @Override
	// public boolean onTouch(View v, MotionEvent event){
	//
	// // TODO Auto-generated method stub
	// String phone = userNameEdit.getText().toString();
	// String regEx = "^[1](3|5|8)[0-9]{9}$";
	// //"^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
	// boolean rs = Pattern.compile(regEx).matcher(phone).find();
	// if(!rs){
	// Toast.makeText(LoginViewActivity.this, "用户名格式错",
	// Toast.LENGTH_SHORT).show();
	// userNameEdit.setSelected(true);
	// }else{
	//
	// }
	// return false;
	// }
	//
	// }
	// public boolean onTouchEvent(android.view.MotionEvent event) {
	// InputMethodManager imm = (InputMethodManager)
	// getSystemService(Context.INPUT_METHOD_SERVICE);
	// return imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
	// .getWindowToken(), 0);
	//
	// }

}
