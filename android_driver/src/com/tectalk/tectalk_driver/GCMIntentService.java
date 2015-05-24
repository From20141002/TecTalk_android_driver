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
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMBaseIntentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class GCMIntentService extends GCMBaseIntentService {
	
	boolean result = false;
	public String url = new String("http://182.162.90.100/TecTalk/SaveDriGCM");
	Intent intent;
	Intent intent1;// m
	

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
  
	}
	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		Log.d("test", "msg: 왔다.");
		try {
			JSONObject jObject = new JSONObject(arg1.getExtras().getString(
					"test"));
			Log.d("test", jObject.toString());

			String test = jObject.toString();
			SetNotification(test);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
	private void SetNotification(String test) {
		// TODO Auto-generated method stub
		NotificationManager notificationManager = null;
		Notification notification = null;
		SharedPreferences setting;
		setting = PreferenceManager.getDefaultSharedPreferences(this);
	
		try {
			notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			notification = new Notification(R.drawable.ic_launcher, test,System.currentTimeMillis());
			if(setting.getBoolean("setSound", true)){
				notification.defaults |= Notification.DEFAULT_SOUND;
			}
			if(setting.getBoolean("setVibrate", true)){
				notification.defaults |= Notification.DEFAULT_VIBRATE;
			}
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
					
			Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
			PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
			notification.setLatestEventInfo(getApplicationContext(),"alarmhihi", test, pi);
			notificationManager.notify(1,notification);

		} catch (Exception e) {
			Log.e("test", "[setNotification] Exception : " + e.getMessage());
		}
 
	}
	@Override
	protected void onRegistered(Context arg0, String phoneId) {
		// TODO Auto-generated method stub
		Log.d("test", "등록ID:" + phoneId);
		new ConnectServer().execute(phoneId);
	}
	
	@Override
	protected void onUnregistered(Context arg0, final String phoneId) {
		// TODO Auto-generated method stub
		Log.d("test", "해지ID:" + phoneId);
		

	}
			
	public GCMIntentService() {
		super("619658958148");
		Log.d("test", "GCM 서비스 생성자 실행");
	}	
	
	private class ConnectServer extends AsyncTask<String,Void,Void>{

		@Override
		protected Void doInBackground(String... params) {
			
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			values.add(new BasicNameValuePair("DRIID", MainActivity.driver_id));
			values.add(new BasicNameValuePair("PHONEID", params[0]));

			
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
			} else{
				toast = Toast.makeText(getApplicationContext(), "아이디가 이미 있습니다.", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
}