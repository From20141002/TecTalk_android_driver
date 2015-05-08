package com.tectalk.tectalk_driver;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.media.session.PlaybackState.CustomAction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private DialogActivity mCustomDialog;// m

	private Intent intent;
	private String driver_id;
	private String url = "http://182.162.90.100/TecTalk/GetItemInfo";
	private String result;
	private JSONArray jArray;
	private JSONObject jObject;

	private ListView listViewResult;
	private String text = "";
	private ArrayAdapter<String> Adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.activity_main2);
		// Adapter = new
		// ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice);

		listViewResult = (ListView) findViewById(R.id.listViewResult);

		listViewResult.setAdapter(Adapter);

		// choice mode
		listViewResult.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		intent = getIntent();
		driver_id = intent.getExtras().getString("driver_id");

		new ConnectServer().execute(null, null, null);

	}

	// send_btn
	public void onClickView(View v) {
		switch (v.getId()) {
		case R.id.send_btn:
			mCustomDialog = new DialogActivity(this, "title", "gogogo",
					m5ClickListener, m10ClickListener, m15ClickListener);
			mCustomDialog.show();
			break;
		}
	}

	private View.OnClickListener m5ClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "5minute",
					Toast.LENGTH_SHORT).show();
		}

	};
	private View.OnClickListener m10ClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "10minute",
					Toast.LENGTH_SHORT).show();
		}
	};
	private View.OnClickListener m15ClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "15minute",
					Toast.LENGTH_SHORT).show();
		}
	};

	private class ConnectServer extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			HttpClient client = new DefaultHttpClient();
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			values.add(new BasicNameValuePair("DRIVER_ID", driver_id));

			HttpParams param = client.getParams();
			HttpConnectionParams.setConnectionTimeout(param, 5000);
			HttpConnectionParams.setSoTimeout(param, 5000);

			try {

				url = url + "?" + URLEncodedUtils.format(values, "UTF-8");
				HttpGet httpGet = new HttpGet(url);

				HttpResponse response = client.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());

			} catch (Exception e) {
				Log.d("aaa", "error : " + e.toString());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void res) {
			// TODO Auto-generated method stub
			super.onPostExecute(res);
			// JSONObject jObject = new JSONObject(result);
			try {
				jArray = new JSONArray(result);
				jObject = new JSONObject();
				Log.d("aaaa", "result = " + result);

				for (int i = 0; i < jArray.length(); i++) {
					text = "";
					jObject = jArray.getJSONObject(i);
					// text += jObject.getString("dri_id");
					text += jObject.getString("item_info") + " ";
					text += jObject.getString("cus_id") + " ";
					text += jObject.getString("item_address") + " ";
					text += jObject.getString("item_getbyhand") + " ";

					Adapter.add(text);

					Log.d("bbb", "text : " + text);
				}

			} catch (Exception e) {

			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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