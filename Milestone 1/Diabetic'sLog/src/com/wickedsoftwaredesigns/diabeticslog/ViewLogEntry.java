package com.wickedsoftwaredesigns.diabeticslog;

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
import android.widget.TextView;

public class ViewLogEntry extends Activity implements OnClickListener{

	TextView date;
	TextView time;
	TextView reading;
	TextView reason;
	Button deleteLog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("View Log Entry");
		setContentView(R.layout.activity_view_log_entry);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		date = (TextView) findViewById(R.id.dateFieldView);
		time = (TextView) findViewById(R.id.timeFieldView);
		reading = (TextView) findViewById(R.id.sugarCountFieldView);
		reason = (TextView) findViewById(R.id.reasonFieldView);
		deleteLog = (Button) findViewById(R.id.deleteLogEntryButton);
		deleteLog.setOnClickListener(this);
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
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, LogItemList.class);
		startActivity(intent);
	}
}
