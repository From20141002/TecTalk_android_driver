package com.tectalk.tectalk_driver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.session.PlaybackState.CustomAction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends Dialog {
	Intent intent;
	TextView txt_title;
	String txtview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		


		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.activity_dialog);
		
		txt_title.setText(txtview);

		setLayout();

		setClickListener(m15ClickListener, m30ClickListener, m60ClickListener);
	}

	public DialogActivity(Context context, String title, String content,
			View.OnClickListener m15Listener, View.OnClickListener m30Listener,
			View.OnClickListener m60Listener) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.mTitle = title;
		this.m15ClickListener = m15Listener;
		this.m30ClickListener = m30Listener;
		this.m60ClickListener = m60Listener;
		this.txtview = title;
		Log.d("aaaa", this.txtview);
	}

	/*
	 * private void setTitle(String title) { mTitleView.setText(title); }
	 * 
	 * private void setContent(String content) { mContentView.setText(content);
	 * }
	 */
	private void setClickListener(View.OnClickListener m15,
			View.OnClickListener m30, View.OnClickListener m60) {

		m15Button.setOnClickListener(m15);
		m30Button.setOnClickListener(m30);
		m60Button.setOnClickListener(m60);

	}

	// private TextView mTitleView;
	// private TextView mContentView;
	private Button m15Button;
	private Button m30Button;
	private Button m60Button;
	private String mTitle;
	private String mContent;

	private View.OnClickListener m15ClickListener;
	private View.OnClickListener m30ClickListener;
	private View.OnClickListener m60ClickListener;

	/*
	 * Layout
	 */
	private void setLayout() {
		// mTitleView = (TextView) findViewById(R.id.tv_title);
		// mContentView = (TextView) findViewById(R.id.tv_content);
		m15Button = (Button) findViewById(R.id.btn_15minute);
		m30Button = (Button) findViewById(R.id.btn_30minute);
		m60Button = (Button) findViewById(R.id.btn_60minute);
	}
}
