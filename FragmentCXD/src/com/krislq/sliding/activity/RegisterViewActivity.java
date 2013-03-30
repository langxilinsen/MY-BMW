package com.krislq.sliding.activity;

import java.util.regex.Pattern;

import com.krislq.sliding.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterViewActivity extends Activity {
	private TextView title;
	private Button goLoginButton;
	private Button regYesButton;
	private EditText regUsernameEdit;
	private EditText regPasswordEdit1;
	private EditText regPasswordEdit2;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
		setContentView(R.layout.register_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);//自定义布局赋值 
		title = (TextView)findViewById(R.id.title);
		title.setText("登陆");
		goLoginButton = (Button)findViewById(R.id.reg_gologin_button);
		regYesButton = (Button)findViewById(R.id.reg_reg_button);
		regUsernameEdit = (EditText)findViewById(R.id.reg_name_edit);
		regPasswordEdit1 = (EditText)findViewById(R.id.reg_pass1_edit); 
		regPasswordEdit2 = (EditText)findViewById(R.id.reg_pass2_edit);
		goLoginButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				// Intent it = new Intent();
				// it.setClass(RegisterViewActivity.this,
				// LoginViewActivity.class);
				// startActivity(it);
				finish();
			}

		});
		regYesButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String phone = regUsernameEdit.getText().toString();
				String regEx = "^[1](3|5|8)[0-9]{9}$";  //"^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";  
				String password1 = regPasswordEdit1.getText().toString();
				String regPx1 = "^[0-9A-Za-z]{6,32}$";
				String password2 = regPasswordEdit1.getText().toString();
				String regPx2 = "^[0-9A-Za-z]{6,32}$";
				boolean ru = Pattern.compile(regEx).matcher(phone).find();		
				boolean rp1 = Pattern.compile(regPx1).matcher(password1).find();
				boolean rp2 = Pattern.compile(regPx2).matcher(password2).find();
				if(!ru){
					Toast.makeText(RegisterViewActivity.this, "用户名或者密码格式错误", Toast.LENGTH_SHORT).show();
					regUsernameEdit.setSelected(true);
				}else if(!rp1){
					Toast.makeText(RegisterViewActivity.this, "用户名或者密码格式错误", Toast.LENGTH_SHORT).show();
					regPasswordEdit1.setSelected(true);
				}else if(!rp2){
					Toast.makeText(RegisterViewActivity.this, "用户名或者密码格式错误", Toast.LENGTH_SHORT).show();
					regPasswordEdit2.setSelected(true);
				}else if(!(password1.equals(password2))){
					Toast.makeText(RegisterViewActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
				}else{
					
				}
			}	
		});
	}

	public boolean onTouchEvent(android.view.MotionEvent event) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		return imm.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), 0);

	}
}
