package com.wickedsoftwaredesigns.diabeticslog;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewDocInfo extends Activity {

	TextView viewDocName;
	TextView viewDocNumber;
	TextView viewDocAddress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("View Doc's Info");
		setContentView(R.layout.activity_view_doc_info);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		viewDocName = (TextView)findViewById(R.id.drNameFieldView);
		viewDocNumber = (TextView)findViewById(R.id.drNumFieldView);
		viewDocAddress = (TextView)findViewById(R.id.docAddressFieldView);
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
}
