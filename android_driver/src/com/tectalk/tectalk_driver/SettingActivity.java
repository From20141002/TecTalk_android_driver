package com.tectalk.tectalk_driver;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SettingActivity extends PreferenceActivity {
	
	
	private Preference setSound;
	private Preference setVibrate;
	private Preference timeOne;
	private Preference timeTwo;
	private Preference timeThree;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.pref_setting);	
		
		setSound = (Preference)findPreference("setSound");
		setVibrate = (Preference)findPreference("setVibrate");
		timeOne = (Preference)findPreference("timeOne");
		timeTwo = (Preference)findPreference("timeTwo");
		timeThree = (Preference)findPreference("timeThree");
		
		SharedPreferences setting;
		setting = PreferenceManager.getDefaultSharedPreferences(this);
		
		timeOne.setSummary(setting.getString("timeOne", "15")+"분");
		timeTwo.setSummary(setting.getString("timeTwo", "30")+"분");
		timeThree.setSummary(setting.getString("timeThree", "60")+"분");
		
		setSound.setOnPreferenceChangeListener(onPreferenceChangeListner);
		setVibrate.setOnPreferenceChangeListener(onPreferenceChangeListner);
		timeOne.setOnPreferenceChangeListener(onPreferenceChangeListner);
		timeTwo.setOnPreferenceChangeListener(onPreferenceChangeListner);
		timeThree.setOnPreferenceChangeListener(onPreferenceChangeListner);
	}
	
	private Preference.OnPreferenceChangeListener onPreferenceChangeListner = new OnPreferenceChangeListener() {
		
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// TODO Auto-generated method stub
			
			String key = preference.getKey();
			switch(key){
			case "setSound":
				break;
			case "setVibrate":
				break;
			case "timeOne":
				timeOne.setDefaultValue(newValue.toString());
				timeOne.setSummary(newValue.toString()+"분");
				break;
			case "timeTwo":
				timeTwo.setSummary(newValue.toString()+"분");
				timeTwo.setDefaultValue(newValue.toString());
				break;
			case "timeThree":
				timeTwo.setSummary(newValue.toString()+"분");
				timeThree.setDefaultValue(newValue.toString());
				break;
			}
			return true;
		}
	};
}
