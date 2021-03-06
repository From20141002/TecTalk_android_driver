package com.tectalk.tectalk_driver;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DialogActivity extends Activity {
	
	private Button btnTimeOne;
	private Button btnTimeTwo;
	private Button btnTimeThree;
	private TextView txtviewSelect;

	private String driId; 
	private final String url = "http://182.162.90.100/TecTalk/GetPhoneId";
	private ArrayList<String> selectItemList = new ArrayList<String>();
	private ArrayList<String> selectCusList = new ArrayList<String>();

	private Intent intent;
	private boolean result = false;
	private String timeOne;
	private String timeTwo;
	private String timeThree;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.activity_dialog);
		
		SharedPreferences setting;
		setting = PreferenceManager.getDefaultSharedPreferences(this);

		btnTimeOne = (Button) findViewById(R.id.btnTimeOne);
		btnTimeTwo = (Button) findViewById(R.id.btnTimeTwo);
		btnTimeThree = (Button) findViewById(R.id.btnTimeThree);
		
		timeOne = setting.getString("timeOne", "15");
		timeTwo = setting.getString("timeTwo", "30");
		timeThree = setting.getString("timeThree", "60");
		
		btnTimeOne.setText(timeOne+"��");
		btnTimeTwo.setText(timeTwo+"��");
		btnTimeThree.setText(timeThree+"��");

		btnTimeOne.setOnClickListener(oneClickListener);
		btnTimeTwo.setOnClickListener(twoClickListener);
		btnTimeThree.setOnClickListener(threeClickListener);
		txtviewSelect = (TextView) findViewById(R.id.txtviewSelect);

		intent = getIntent();
		selectItemList = intent.getExtras().getStringArrayList("ITEMINFO");
		selectCusList = intent.getExtras().getStringArrayList("CUSINFO");
		driId = intent.getExtras().getString("DRIID");
		String result = "result : ";
		
		
		if (selectItemList != null) {
			for (int i = 0; i < selectItemList.size(); i++) {
				result += selectItemList.get(i);
				result += selectCusList.get(i) + "\n";
			}
			txtviewSelect.setText(result);
		}else{
			Log.d("test", "null error");
		}
	}

	
	private OnClickListener oneClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), timeOne ,
					Toast.LENGTH_SHORT).show();
			pushToCus(timeOne);
		}

	};
	private OnClickListener twoClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), timeTwo,
					Toast.LENGTH_SHORT).show();
			pushToCus(timeTwo);
		}
	};
	private OnClickListener threeClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), timeThree,
					Toast.LENGTH_SHORT).show();
			pushToCus(timeThree);
		}
	};
	
	public void pushToCus(String time){
		if (selectItemList != null) {
			for (int i = 0; i < selectItemList.size(); i++) {
				new ConnectServer().execute(time, selectCusList.get(i), driId, selectItemList.get(i));
			}
		} 
	}

	private class ConnectServer extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			values.add(new BasicNameValuePair("TIME", params[0]));
			values.add(new BasicNameValuePair("CUSID", params[1]));
			values.add(new BasicNameValuePair("DRIID", params[2]));
			values.add(new BasicNameValuePair("ITEMINFO", params[3]));
			
			Log.d("test" , "push : " + params[0] + params[1] + params[2] +params[3]);
			
			HttpParams param = client.getParams();
			HttpConnectionParams.setConnectionTimeout(param, 5000);
			HttpConnectionParams.setSoTimeout(param, 5000);
			
			try {
				
				URI uri = new URI(url);
				HttpPost httpPost = new HttpPost(uri);
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, "UTF-8");
				httpPost.setEntity(entity);
				HttpResponse response = client.execute(httpPost);
				String _result = EntityUtils.toString(response.getEntity());
				if(_result.contains("success")){
					result = true;
				} else result = false;
			} catch(Exception e){
				Log.d("aaa","error : " + e.toString());
				
			} return null;
		}
	}

}