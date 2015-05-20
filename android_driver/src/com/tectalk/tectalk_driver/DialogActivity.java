package com.tectalk.tectalk_driver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.session.PlaybackState.CustomAction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DialogActivity extends ActionBarActivity {



	Intent intent;
//	TextView txt_title;
//	String txtview;
	
	private Button m15Button;
	private Button m30Button;
	private Button m60Button;


//	private OnClickListener m15ClickListener;
//	private OnClickListener m30ClickListener;
//	private OnClickListener m60ClickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.activity_dialog);
		
//		txt_title = (TextView)findViewById(R.id.selectViewResult);
//		txt_title.setAd
//		txt_title.setText(txtview);
		
		m15Button = (Button) findViewById(R.id.btn_15minute);
		m30Button = (Button) findViewById(R.id.btn_30minute);
		m60Button = (Button) findViewById(R.id.btn_60minute);

//		setClickListener(m15ClickListener, m30ClickListener, m60ClickListener);
	}
/*
	public DialogActivity(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		//this.mTitle = title;
		//this.m15ClickListener = m15Listener;
		//this.m30ClickListener = m30Listener;
		//this.m60ClickListener = m60Listener;
//		this.txtview = title;
//		Log.d("aaaa", this.txtview);
	}
	*/
	
	

	/*
	 * private void setTitle(String title) { mTitleView.setText(title); }
	 * 
	 * private void setContent(String content) { mContentView.setText(content);
	 * }
	 *//*
	private void setClickListener(View.OnClickListener m15,
			View.OnClickListener m30, View.OnClickListener m60) {

		m15Button.setOnClickListener(m15);
		m30Button.setOnClickListener(m30);
		m60Button.setOnClickListener(m60);

	}*/
	/*
	private OnClickListener m15ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "15minute",
					Toast.LENGTH_SHORT).show();
//			mCustomDialog.dismiss(); // exit dialog
		}

	};
	private OnClickListener m30ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "30minute",
					Toast.LENGTH_SHORT).show();
//			mCustomDialog.dismiss();
		}
	};
	private OnClickListener m60ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "60minute",
					Toast.LENGTH_SHORT).show();
//			mCustomDialog.dismiss();
		}
	};
	*/

	// private TextView mTitleView;
	// private TextView mContentView;
	

	/*
	 * Layout
	 */
	/*

	protected Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}*/
}