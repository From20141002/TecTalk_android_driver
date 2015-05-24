package com.tectalk.tectalk_driver;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText edittxtID;
	private EditText edittxtPW;
	private Button btnLogin;
	private Button btnSignin;
	
	private String driver_id; 
	private String driver_pw;
	
	private String url = "http://182.162.90.100/TecTalk/LoginDri";
	private Boolean result;
	
	private Intent intent = null;
	private Toast toast;
	//아래부터 자동로그인기능에 필요한것들
	public CheckBox cb_autologin = null;
	SharedPreferences setting;
	SharedPreferences.Editor editor;
	private boolean autoLogin = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		setting = PreferenceManager.getDefaultSharedPreferences(this);
		editor = setting.edit();
		
		
		if(setting.contains("ID")){
			intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.putExtra("DRIID", setting.getString("ID", "NULL"));
			startActivity(intent);
			finish();
		}
		editor.remove("setSound");
		editor.remove("setVibrate");
		editor.remove("timeOne");
		editor.remove("timeTwo");
		editor.remove("timeThree");
		editor.commit();
		edittxtID = (EditText)findViewById(R.id.edittxtID);
		edittxtPW = (EditText)findViewById(R.id.edittxtPW);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnSignin = (Button)findViewById(R.id.btnSignin);
		
		btnLogin.setOnClickListener(mClickListener);
		btnSignin.setOnClickListener(mClickListener);
		//자동로그인 기능 추가
		
		
		cb_autologin =(CheckBox)findViewById(R.id.cbAutologin);
	
		cb_autologin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if(isChecked){
					autoLogin = true;
					
				}else{
					autoLogin = false;
				}
				
			}
		});
	}
	
	
	private OnClickListener mClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.btnLogin:
				getDriInfo();
				break;
			case R.id.btnSignin:
				intent = new Intent(getApplicationContext(), SigninActivity.class);
				startActivity(intent);
				break;
			}
		}
	};

	private void getDriInfo(){
		if(edittxtID.getText().toString().length()!=0){
			if(edittxtPW.getText().toString().length()!=0){
				
				driver_id = edittxtID.getText().toString();
				driver_pw = edittxtPW.getText().toString();
				new ConnectServer().execute(null,null,null);
				
			}else{
				toast = Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT);
				toast.show();
			}
		}else{
			toast = Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	private class ConnectServer extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
		
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			values.clear();
			values.add(new BasicNameValuePair("DRIID", driver_id));
			values.add(new BasicNameValuePair("DRIPW", driver_pw));
			HttpParams param = client.getParams();
			HttpConnectionParams.setConnectionTimeout(param, 5000);
			HttpConnectionParams.setSoTimeout(param, 5000);
			
			try{
				String _url = url + "?" + URLEncodedUtils.format(values,"UTF-8");
				HttpGet httpGet = new HttpGet(_url);
				
				HttpResponse response = client.execute(httpGet);
				String _result = EntityUtils.toString(response.getEntity());
				
				if(_result.contains("success")){
					result = true;
				}else{
					result = false;
				}

			}catch(Exception e){
				Log.d("aaa","error : " + e.toString());
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void res) {
			// TODO Auto-generated method stub
			super.onPostExecute(res);
			if(result){
				toast = Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT);
				toast.show();
				if(autoLogin){
					driver_id = edittxtID.getText().toString();
					driver_pw = edittxtPW.getText().toString();		
					editor.putString("ID", driver_id);
					editor.commit();
				}
				intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.putExtra("DRIID", driver_id);
				startActivity(intent);
				finish();
			}else{
				toast = Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
}
