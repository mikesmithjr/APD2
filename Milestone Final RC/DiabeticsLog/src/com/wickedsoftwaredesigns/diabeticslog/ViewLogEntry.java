package com.wickedsoftwaredesigns.diabeticslog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wickedsoftwaredesigns.libs.FileManagement;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
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
	String dateEntry;
	String timeEntry;
	String readingEntry;
	String reasonEntry;
	Button deleteLog;
	String logItemsString;
	JSONArray logEntries;
	int id;
	int idEntry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("View Log Entry");
		setContentView(R.layout.activity_view_log_entry);
		
		Bundle extras = getIntent().getExtras();
		id = extras.getInt("Id");
		Log.i("View Log Id", String.valueOf(id));
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		date = (TextView) findViewById(R.id.dateFieldView);
		time = (TextView) findViewById(R.id.timeFieldView);
		reading = (TextView) findViewById(R.id.sugarCountFieldView);
		reason = (TextView) findViewById(R.id.reasonFieldView);
		deleteLog = (Button) findViewById(R.id.deleteLogEntryButton);
		deleteLog.setOnClickListener(this);
		
		logItemsString = FileManagement.readStringFile(this, "logList", false);
		
		JSONObject job = null;
		logEntries = null;
		
		
		try {
			//creating the JSON Object
			job = new JSONObject(logItemsString);
			//pulling the entry array out of the object
			logEntries = job.getJSONArray("entry");
			//recording the length of the array
			int recordSize = logEntries.length();
			Log.i("JSONArray Size", "There are "+ String.valueOf(recordSize)+ " records in the file.");
			
					//looping over the array and pulling the data for each entry out
					for (int i = 0; i < recordSize; i++) {
						
						JSONObject entryObject = logEntries.getJSONObject(i);
						idEntry = Integer.parseInt(entryObject.getString("id"));
						readingEntry = entryObject.getString("reading");
						dateEntry = entryObject.getString("date");
						timeEntry = entryObject.getString("time");
						reasonEntry = entryObject.getString("reason");
						if (idEntry == id) {
							
							date.setText(dateEntry);
							time.setText(timeEntry);
							reading.setText(readingEntry);
							reason.setText(reasonEntry);
						}
						
					}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
		
		JSONObject job = null;
		logEntries = null;
		
		
		try {
			//creating the JSON Object
			job = new JSONObject(logItemsString);
			//pulling the entry array out of the object
			logEntries = job.getJSONArray("entry");
			//recording the length of the array
			int recordSize = logEntries.length();
			Log.i("JSONArray Size", "There are "+ String.valueOf(recordSize)+ " records in the file.");
			
					//looping over the array and pulling the data for each entry out
					for (int i = 0; i < recordSize; i++) {
						
						JSONObject entryObject = logEntries.getJSONObject(i);
						idEntry = Integer.parseInt(entryObject.getString("id"));
						if (idEntry == id) {
							
							Log.i("item to delete", String.valueOf(idEntry));
						}
						
					}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setResult(RESULT_OK);
		finish();
	}
}
