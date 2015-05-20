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
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.session.PlaybackState.CustomAction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DialogActivity extends ActionBarActivity {

	Intent intent;

	private Button m15Button;
	private Button m30Button;
	private Button m60Button;

	private String driId; 
	private boolean result = false;
	private final String url = "http://182.162.90.100/TecTalk/GetPhoneId";
	private TextView selectViewResult;
	private ArrayList<String> select_item_list = new ArrayList<String>();
	private ArrayList<String> select_cus_list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.activity_dialog);

		m15Button = (Button) findViewById(R.id.btn_15minute);
		m30Button = (Button) findViewById(R.id.btn_30minute);
		m60Button = (Button) findViewById(R.id.btn_60minute);

		m15Button.setOnClickListener(m15ClickListener);
		m30Button.setOnClickListener(m30ClickListener);
		m60Button.setOnClickListener(m60ClickListener);
		selectViewResult = (TextView) findViewById(R.id.selectViewResult);

		intent = getIntent();
		select_item_list = intent.getExtras().getStringArrayList("item_info");
		select_cus_list = intent.getExtras().getStringArrayList("cus_info");
		driId = intent.getExtras().getString("driver_id");
		String result = "result : ";
		if (select_item_list != null) {
			for (int i = 0; i < select_item_list.size(); i++) {
				result += select_item_list.get(i);
				result += select_cus_list.get(i) + "\n";
			}
			selectViewResult.setText(result);
		}else{
			Log.d("test", "null error");
		}
	}

	
	private OnClickListener m15ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "15minute",
					Toast.LENGTH_SHORT).show();
			pushToCus("15");
		}

	};
	private OnClickListener m30ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "30minute",
					Toast.LENGTH_SHORT).show();
			pushToCus("30");
		}
	};
	private OnClickListener m60ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "60minute",
					Toast.LENGTH_SHORT).show();
			pushToCus("60");
		}
	};
	
	public void pushToCus(String time){
		if (select_item_list != null) {
			for (int i = 0; i < select_item_list.size(); i++) {
				new ConnectServer().execute(time, select_cus_list.get(i), driId, select_item_list.get(i));
			}
		} 
	}

	private class ConnectServer extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			values.add(new BasicNameValuePair("msg", params[0]));
			values.add(new BasicNameValuePair("cusId", params[1]));
			values.add(new BasicNameValuePair("driId", params[2]));
			values.add(new BasicNameValuePair("itemInfo", params[3]));
			
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
		
		@Override
		protected void onPostExecute(Void res){
			super.onPostExecute(res);
			Toast toast;
			if(result){
				toast = Toast.makeText(getApplicationContext(), "Id 등록 성공", Toast.LENGTH_SHORT);
				toast.show();
				finish();
			} else{
				toast = Toast.makeText(getApplicationContext(), "아이디가 이미 있습니다.", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

}