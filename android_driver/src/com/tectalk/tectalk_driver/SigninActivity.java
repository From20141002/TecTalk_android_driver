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

import android.support.v7.app.ActionBarActivity;
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

public class SigninActivity extends ActionBarActivity {

	private EditText edittxtID_signin;
	private EditText edittxtPW_signin;
	private EditText edittxtName_signin;
	private EditText edittxtPhone_signin;
	private EditText edittxtCompany_signin;
	private Button btnSubmit;
	
	private String driver_id;
	private String driver_pw;
	private String driver_name;
	private String driver_phone;
	private String driver_company;
	
	private Toast toast;
	private final String url = "http://182.162.90.100/TecTalk/SaveDriInfo";
	private boolean result = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		
		edittxtID_signin = (EditText)findViewById(R.id.edittxtID_signin);
		edittxtPW_signin = (EditText)findViewById(R.id.edittxtPW_signin);
		edittxtName_signin = (EditText)findViewById(R.id.edittxtName_signin);
		edittxtPhone_signin = (EditText)findViewById(R.id.edittxtPhone_signin);
		edittxtCompany_signin = (EditText)findViewById(R.id.edittxtCompany_signin);
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				SaveDriInfo();
			}
		});
	}

	private void SaveDriInfo(){
				
		if(edittxtID_signin.getText().toString().length()!=0){
			if(edittxtPW_signin.getText().toString().length()!=0){
				if(edittxtName_signin.getText().toString().length()!=0){
					if(edittxtPhone_signin.getText().toString().length()!=0){
						if(edittxtCompany_signin.getText().toString().length()!=0){
							
							driver_id = edittxtID_signin.getText().toString();
							driver_pw = edittxtPW_signin.getText().toString();
							driver_name = edittxtName_signin.getText().toString();
							driver_phone = edittxtPhone_signin.getText().toString();
							driver_company = edittxtCompany_signin.getText().toString();
							
							new ConnectServer().execute(null,null,null);
						}else{
							toast = Toast.makeText(getApplicationContext(), "회사명을 입력하세요.", Toast.LENGTH_SHORT);
							toast.show();
						}
					}else{
						toast = Toast.makeText(getApplicationContext(), "휴대폰 번호를 입력하세요", Toast.LENGTH_SHORT);
						toast.show();
					}
				}else{
					toast = Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_SHORT);
					toast.show();
				}
			}else{
				toast = Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT);
				toast.show();
			}
		}else{
			toast = Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	
	private class ConnectServer extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
		
			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			values.add(new BasicNameValuePair("DRIVER_ID", driver_id));
			values.add(new BasicNameValuePair("DRIVER_PW", driver_pw));
			values.add(new BasicNameValuePair("DRIVER_NAME", driver_name));
			values.add(new BasicNameValuePair("DRIVER_PHONE", driver_phone));
			values.add(new BasicNameValuePair("DRIVER_COMPANY", driver_company));
			
			HttpParams param = client.getParams();
			HttpConnectionParams.setConnectionTimeout(param, 5000);
			HttpConnectionParams.setSoTimeout(param, 5000);
			
			try{
				
				URI uri = new URI(url);
				HttpPost httpPost = new HttpPost(uri);
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, "UTF-8");
				httpPost.setEntity(entity);
				HttpResponse response = client.execute(httpPost);
				String _result = EntityUtils.toString(response.getEntity());
				Log.d("aaa", "result : " + result);

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
				toast = Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT);
				toast.show();
				finish();
			}else{
				toast = Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signin, menu);
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
