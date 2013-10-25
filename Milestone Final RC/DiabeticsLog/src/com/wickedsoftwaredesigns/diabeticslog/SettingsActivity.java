package com.wickedsoftwaredesigns.diabeticslog;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;

public class SettingsActivity extends Activity implements OnClickListener{

	
	Button manageReminders;
	Button editDocInfo;
	Button emailSupport;
	Button faqWeb;
	RadioGroup units;
	Switch reminderPowerSwitch;
	Boolean reminderPower;
	int unitButtonId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Settings");
		setContentView(R.layout.activity_settings);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		units = (RadioGroup)findViewById(R.id.unitGroup);
		reminderPowerSwitch = (Switch)findViewById(R.id.reminderSwitch);
		manageReminders = (Button)findViewById(R.id.manageRemindersButton);
		editDocInfo = (Button)findViewById(R.id.editDocInfoButton);
		emailSupport = (Button)findViewById(R.id.emailSupportButton);
		faqWeb = (Button)findViewById(R.id.faqSiteButton);
		manageReminders.setOnClickListener(this);
		editDocInfo.setOnClickListener(this);
		emailSupport.setOnClickListener(this);
		faqWeb.setOnClickListener(this);
		
		unitButtonId = getUnits();
		units.check(unitButtonId);
		
		
		reminderPowerSwitch.setChecked(reminderPower);
		
		
		
	}
	
	public void storePrefs(int buttonId, Boolean remindSwitch){
		
		SharedPreferences prefs = this.getSharedPreferences("userPrefs", 0);
		SharedPreferences.Editor editPrefs = prefs.edit();
		editPrefs.putBoolean("ReminderSwitch", remindSwitch);
		editPrefs.putInt("unitSelected", buttonId);
		editPrefs.commit();
		Log.i("Unit Saved", String.valueOf(buttonId));
	}
	
	public int getUnits(){
		int buttonId = 0;
		
		SharedPreferences prefs = this.getSharedPreferences("userPrefs", 0);
		buttonId = prefs.getInt("unitSelected", 0);
		reminderPower = prefs.getBoolean("ReminderSwitch", false);
		
		return buttonId;
	}
	
	/**
	 * Alert message.
	 * Funciton to build an alert dialog message from anywhere in the class
	 * @param message the message
	 */
	private void alertMessage(String message){
		AlertDialog.Builder alert = new AlertDialog.Builder(this).setMessage(message).setCancelable(true);
		alert.create().show();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == R.id.action_new_entry) {
			
			Intent intent = new Intent(this, NewLogEntry.class);
			startActivity(intent);
			
		}else if (item.getItemId() == R.id.action_settings) {
			
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		//building on click functions
		if (v.equals(this.manageReminders)) {
			
			//building intent for activity change
			Intent intent = new Intent(this, ReminderList.class);
			startActivity(intent);
			
		}else if (v.equals(this.editDocInfo)) {
			
			//building intent for activity change
			Intent intent = new Intent(this, EditDocInfo.class);
			startActivity(intent);
			
			
		}else if (v.equals(this.emailSupport)) {
			
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "mikesmithjr@fullsail.edu", null));
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Diabetic's Log Help");
			startActivity(Intent.createChooser(emailIntent, "Send email..."));
			
			
		}else if (v.equals(this.faqWeb)) {
			
			
			//save url to string
			String url = "http://www.fullsail.edu";
			//create intent to launch browser
			Intent intent = new Intent(Intent.ACTION_VIEW);
			//parse uri from string
			Uri uri = Uri.parse(url);
			//set uri string as data for intent
			intent.setData(uri);
			//start intent
			startActivity(intent);
			
		}
		
	}
	@Override
	protected void onPause(){
		super.onPause();
		int getRadioBtn = units.getCheckedRadioButtonId();
		Boolean reminder = reminderPowerSwitch.isChecked();
		storePrefs(getRadioBtn, reminder);
		Log.i("onPause", "Storing radio button " + String.valueOf(getRadioBtn));
		Log.i("onPause Reminder switch is ", String.valueOf(reminderPower));
	}
	@Override
	protected void onStop(){
		super.onStop();
		int getRadioBtn = units.getCheckedRadioButtonId();
		Boolean reminder = reminderPowerSwitch.isChecked();
		storePrefs(getRadioBtn, reminder);
		Log.i("onStop", "Storing radio button " + String.valueOf(getRadioBtn));
		Log.i("onStop Reminder switch is ", String.valueOf(reminderPower));
	}
}
