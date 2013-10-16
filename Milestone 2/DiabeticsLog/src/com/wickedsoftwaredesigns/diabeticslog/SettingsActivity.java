package com.wickedsoftwaredesigns.diabeticslog;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsActivity extends Activity implements OnClickListener{

	
	Button manageReminders;
	Button editDocInfo;
	Button emailSupport;
	Button faqWeb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Settings");
		setContentView(R.layout.activity_settings);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		manageReminders = (Button)findViewById(R.id.manageRemindersButton);
		editDocInfo = (Button)findViewById(R.id.editDocInfoButton);
		emailSupport = (Button)findViewById(R.id.emailSupportButton);
		faqWeb = (Button)findViewById(R.id.faqSiteButton);
		manageReminders.setOnClickListener(this);
		editDocInfo.setOnClickListener(this);
		emailSupport.setOnClickListener(this);
		faqWeb.setOnClickListener(this);
		
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
}
