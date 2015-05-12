package com.tectalk.tectalk_driver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.session.PlaybackState.CustomAction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends Dialog {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.activity_dialog);

		setLayout();

		setClickListener(m5ClickListener, m10ClickListener, m15ClickListener);
	}

	public DialogActivity(Context context, String title, String content,
			View.OnClickListener m5Listener, View.OnClickListener m10Listener,
			View.OnClickListener m15Listener) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.mTitle = title;
		this.mContent = content;
		this.m5ClickListener = m5Listener;
		this.m10ClickListener = m10Listener;
		this.m15ClickListener = m15Listener;
	}

	/*
	 * private void setTitle(String title) { mTitleView.setText(title); }
	 * 
	 * private void setContent(String content) { mContentView.setText(content);
	 * }
	 */
	private void setClickListener(View.OnClickListener m5,
			View.OnClickListener m10, View.OnClickListener m15) {

		m5Button.setOnClickListener(m5);
		m10Button.setOnClickListener(m10);
		m15Button.setOnClickListener(m15);

	}

	// private TextView mTitleView;
	// private TextView mContentView;
	private Button m5Button;
	private Button m10Button;
	private Button m15Button;
	private String mTitle;
	private String mContent;

	private View.OnClickListener m5ClickListener;
	private View.OnClickListener m10ClickListener;
	private View.OnClickListener m15ClickListener;

	/*
	 * Layout
	 */
	private void setLayout() {
		// mTitleView = (TextView) findViewById(R.id.tv_title);
		// mContentView = (TextView) findViewById(R.id.tv_content);
		m5Button = (Button) findViewById(R.id.btn_15minute);
		m10Button = (Button) findViewById(R.id.btn_30minute);
		m15Button = (Button) findViewById(R.id.btn_60minute);
	}
}
