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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends Activity {
	public Button btnIdSend;
	public EditText editId;
	
	public String url = new String("http://182.162.90.100/TecTalk/SaveDriGCM");
	boolean result = false;
	
	public String driId;
	public String phoneId;
	
	public Toast toast;
	public OnClickListener clickIdSave;
	public Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		
		btnIdSend = (Button) findViewById(R.id.btnIdSend);
		editId = (EditText)findViewById(R.id.editId);
		intent = getIntent();
		phoneId = intent.getExtras().getString("phone_Id");
		
		clickIdSave = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SaveInfo();
				
			}
		};
		btnIdSend.setOnClickListener(clickIdSave);
	}

	private void SaveInfo(){
		
		Log.d("aaa", editId.getText().toString());
		Log.d("aaa", phoneId);
		
		if(editId.getText().toString().length() !=0){
			driId = editId.getText().toString();
			new ConnectServer().execute(null,null,null);
			
		}else{
			toast = Toast.makeText(getApplicationContext(), "����� Id�� �Է��ϼ���.", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	private class ConnectServer extends AsyncTask<Void,Void,Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			values.add(new BasicNameValuePair("DRI_ID", driId));
			values.add(new BasicNameValuePair("PHONE_ID", phoneId));

			
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
			
			if(result){
				
				toast = Toast.makeText(getApplicationContext(), "Id ��� ����", Toast.LENGTH_SHORT);
				toast.show();
				finish();
			} else{
				toast = Toast.makeText(getApplicationContext(), "���̵� �̹� �ֽ��ϴ�.", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		
	}
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu); //���� main
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
