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
import android.widget.EditText;

public class NewLogEntry extends Activity implements OnClickListener{

	EditText date;
	EditText time;
	EditText reading;
	EditText reason;
	Button saveNew;
	String logItemListData;
	JSONObject logList;
	JSONArray logListArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("New Log Entry");
		setContentView(R.layout.activity_new_log_entry);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		date = (EditText)findViewById(R.id.dateField);
		time = (EditText)findViewById(R.id.timeField);
		reading = (EditText)findViewById(R.id.sugarCountField);
		reason = (EditText)findViewById(R.id.reasonField);
		saveNew = (Button)findViewById(R.id.saveLogEntryButton);
		saveNew.setOnClickListener(this);
		
		logItemListData = FileManagement.readStringFile(this, "logList", false);
		
		try {
			logList = new JSONObject(logItemListData);
			logListArray = logList.getJSONArray("entry");
			Log.i("Checking Log Entries", logListArray.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		
		
		
		String getDate = date.getText().toString();
		String getTime = time.getText().toString();
		String getReading = reading.getText().toString();
		String getReason = reason.getText().toString();
		
		
		if (logListArray != null) {
			if (date != null && time != null && reading != null
					&& reason != null) {
				JSONObject obj = new JSONObject();
				
				JSONObject logObj = new JSONObject();
				try {
					obj.put("date", getDate);
					obj.put("time", getTime);
					obj.put("reading", getReading);
					obj.put("reason", getReason);
					Log.i("output obj", obj.toString());
					logListArray.put(obj);
					logObj.put("entry", logListArray);
					String output = logObj.toString();
					FileManagement.storeStringfile(this, "logList", output,
							false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				alertMessage("Please Complete the Form!");
			}
		}else{
			if (date != null && time != null && reading != null
					&& reason != null) {
				JSONObject obj = new JSONObject();
				JSONArray array = new JSONArray();
				JSONObject logObj = new JSONObject();
				try {
					obj.put("date", getDate);
					obj.put("time", getTime);
					obj.put("reading", getReading);
					obj.put("reason", getReason);
					Log.i("output obj", obj.toString());
					array.put(obj);
					logObj.put("entry", array);
					String output = logObj.toString();
					FileManagement.storeStringfile(this, "logList", output,
							false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				alertMessage("Please Complete the Form!");
			}
		}
		setResult(RESULT_OK);
		finish();
	}

}
