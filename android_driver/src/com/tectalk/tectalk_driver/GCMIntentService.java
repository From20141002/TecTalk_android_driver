package com.tectalk.tectalk_driver;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMBaseIntentService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GCMIntentService extends GCMBaseIntentService {
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
			Vibrator vibrator = (Vibrator) arg0
					.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(1000);
			SetNotification(arg0, test);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
	private void SetNotification(Context arg0, String test) {
		// TODO Auto-generated method stub
		NotificationManager notificationManager = null;
		Notification notification = null;
		try {
			notificationManager = (NotificationManager) arg0
					.getSystemService(arg0.NOTIFICATION_SERVICE);
			notification = new Notification(R.drawable.ic_launcher,test,System.currentTimeMillis());
			Intent intent = new Intent(arg0, NotificationActivity.class);
			PendingIntent pi = PendingIntent.getActivity(arg0, 0, intent, 0);
			notification.setLatestEventInfo(arg0,"alarmhihi", test, pi);
			notificationManager.notify(1,notification);

		} catch (Exception e) {
			Log.e("test", "[setNotification] Exception : " + e.getMessage());
		}

	}
	@Override
	protected void onRegistered(Context arg0, String phoneId) {
		// TODO Auto-generated method stub
		Log.d("test", "등록ID:" + phoneId);

		Context context = getApplicationContext();
		Intent intent = new Intent(context, Main2Activity.class);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("phone_Id", phoneId);// m
		context.startActivity(intent);// m

	}
	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.d("test", "해지ID:" + arg1);

	}
			
	public GCMIntentService() {
		super("619658958148");
		Log.d("test", "GCM 서비스 생성자 실행");
	}	
			
			
	
	
	
}
