package com.tectalk.tectalk_driver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private EditText edittxtID;
	private EditText edittxtPW;
	private Button btnLogin;
	private Button btnSignin;
	
	private Intent intent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		edittxtID = (EditText)findViewById(R.id.edittxtID);
		edittxtPW = (EditText)findViewById(R.id.edittxtPW);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnSignin = (Button)findViewById(R.id.btnSignin);
		
		btnLogin.setOnClickListener(mClickListener);
		btnSignin.setOnClickListener(mClickListener);
				
	}
	
	private OnClickListener mClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.btnLogin:
				
				break;
			case R.id.btnSignin:
				intent = new Intent(getApplicationContext(), SigninActivity.class);
				startActivity(intent);
				break;
			}
		}
	};

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
