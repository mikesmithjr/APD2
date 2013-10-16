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
import android.widget.RadioGroup;

public class Reminder extends Activity implements OnClickListener{

	EditText reminderTitle;
	EditText reminderTime;
	RadioGroup radioGroup;
	Button saveReminder;
	String reminderListData;
	JSONObject reminderList;
	JSONArray reminderArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Reminder");
		setContentView(R.layout.activity_reminder);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		
		reminderTitle = (EditText)findViewById(R.id.reminderTitleField);
		reminderTime = (EditText)findViewById(R.id.reminderTimeField);
		radioGroup = (RadioGroup) findViewById(R.id.rdo_group);
		saveReminder = (Button)findViewById(R.id.saveReminderButton);
		saveReminder.setOnClickListener(this);
				
		reminderListData = FileManagement.readStringFile(this, "reminderList", false);
		
		try {
			reminderList = new JSONObject(reminderListData);
			reminderArray = reminderList.getJSONArray("entry");
			Log.i("Checking Log Entries", reminderArray.toString());
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

		String getTitle = reminderTitle.getText().toString();
		String getTime = reminderTime.getText().toString();
		int getRadioBtn = radioGroup.getCheckedRadioButtonId();
		
		
		
		if (reminderArray != null) {
			if (reminderTitle != null && reminderTime != null) {
				JSONObject obj = new JSONObject();
				
				JSONObject logObj = new JSONObject();
				try {
					obj.put("title", getTitle);
					obj.put("time", getTime);
					obj.put("rdoButton", getRadioBtn);
					
					Log.i("output obj", obj.toString());
					reminderArray.put(obj);
					logObj.put("entry", reminderArray);
					String output = logObj.toString();
					FileManagement.storeStringfile(this, "reminderList", output,
							false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				alertMessage("Please Complete the Form!");
			}
		}else{
			if (reminderTitle != null && reminderTime != null) {
				JSONObject obj = new JSONObject();
				JSONArray array = new JSONArray();
				JSONObject logObj = new JSONObject();
				try {
					obj.put("title", getTitle);
					obj.put("time", getTime);
					obj.put("rdoButton", getRadioBtn);
					
					Log.i("output obj", obj.toString());
					array.put(obj);
					logObj.put("entry", array);
					String output = logObj.toString();
					FileManagement.storeStringfile(this, "reminderList", output,
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
