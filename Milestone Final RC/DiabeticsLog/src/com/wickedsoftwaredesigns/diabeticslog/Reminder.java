package com.wickedsoftwaredesigns.diabeticslog;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wickedsoftwaredesigns.libs.FileManagement;
import com.wickedsoftwaredesigns.libs.ScheduleClient;
import com.wickedsoftwaredesigns.libs.ToastFactory;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

public class Reminder extends Activity implements OnClickListener{

	EditText reminderTitle;
	EditText reminderTime;
	Button changeTime;
	RadioGroup radioGroup;
	Button saveReminder;
	String reminderListData;
	JSONObject reminderList;
	JSONArray reminderArray;
	Calendar cal;
	int hour;
	int min;
	Boolean fileStatus;
	Random rand;
	int _id;
	int idEntry;
	int rdoButtonEntry;
	String titleEntry;
	String timeEntry;
	
	static final int TIME_DIALOG_ID = 999;
	
	public boolean doesFileExist(String filename){
		File file = getBaseContext().getFileStreamPath(filename);
		return file.exists();
	}
	
	public boolean idDoesMatch(int id1, int id2){
		Log.i("id match", "starting match");
		if (id1 == id2) {
			Log.i("id match", "match true");
			return true;
		}
		Log.i("id match", "match false");
		return false;
	}
	
	private ScheduleClient scheduleClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Reminder");
		setContentView(R.layout.activity_reminder);
		
		scheduleClient = new ScheduleClient(this);
		scheduleClient.doBindService();
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		reminderTitle = (EditText)findViewById(R.id.reminderTitleField);
		reminderTime = (EditText)findViewById(R.id.reminderTimeField);
		radioGroup = (RadioGroup) findViewById(R.id.rdo_group);
		changeTime = (Button)findViewById(R.id.changeTimeButton);
		changeTime.setOnClickListener(this);
		saveReminder = (Button)findViewById(R.id.saveReminderButton);
		saveReminder.setOnClickListener(this);
		
		fileStatus = doesFileExist("reminderList");
		
		if (fileStatus == true) {
			Bundle extras = getIntent().getExtras();
			
			reminderListData = FileManagement.readStringFile(this, "reminderList", false);
			
			try {
				reminderList = new JSONObject(reminderListData);
				reminderArray = reminderList.getJSONArray("entry");
				Log.i("Checking Log Entries", reminderArray.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (extras != null) {
				_id = extras.getInt("Id");
				Log.i("id", String.valueOf(_id));
				
				if (_id >= 10) {
					
					
					
					try {
						
						//recording the length of the array
						int recordSize = reminderArray.length();
						Log.i("JSONArray Size", "There are "+ String.valueOf(recordSize)+ " records in the file.");
						
								//looping over the array and pulling the data for each movie out
								for (int i = 0; i < recordSize; i++) {
									
									JSONObject entryObject = reminderArray.getJSONObject(i);
									idEntry = entryObject.getInt("id");
									titleEntry = entryObject.getString("title");
									timeEntry = entryObject.getString("time");
									rdoButtonEntry = entryObject.getInt("rdoButton");
									Log.i("title", titleEntry);
									Log.i("_id value", String.valueOf(_id));
									Log.i("idEntry Value", String.valueOf(idEntry));
									Boolean idMatch = idDoesMatch(idEntry, _id);
									if (idMatch == true) {
										
										reminderTitle.setText(titleEntry);
										reminderTime.setText(timeEntry);
										radioGroup.check(rdoButtonEntry);
										
									}
									
								}
								
								
								
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			} else {
				rand= new Random();
				_id = rand.nextInt(100000-10);
				cal = Calendar.getInstance();
				hour = cal.get(Calendar.HOUR_OF_DAY);
				min = cal.get(Calendar.MINUTE);
				StringBuilder sb = new StringBuilder();
				if (hour >= 12) {
					sb.append(padding_str(hour - 12)).append(":")
							.append(padding_str(min)).append(" PM");
				} else {
					sb.append(padding_str(hour)).append(":")
							.append(padding_str(min)).append(" AM");
				}
				reminderTime.setText(sb);
			}
			
			
		}else{
				rand = new Random();
				_id = rand.nextInt(100000-10);
				cal = Calendar.getInstance();
				hour = cal.get(Calendar.HOUR_OF_DAY);
				min = cal.get(Calendar.MINUTE);
				StringBuilder sb = new StringBuilder();
				if(hour>=12){
					sb.append(padding_str(hour-12)).append(":").append(padding_str(min)).append(" PM");
				}else{
					sb.append(padding_str(hour)).append(":").append(padding_str(min)).append(" AM");
				}
				reminderTime.setText(sb);
			
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
	
	private static String padding_str(int c) {
		 if (c >= 10){
		     return String.valueOf(c);
		 }else{
			   return "0" + String.valueOf(c);
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
	protected Dialog onCreateDialog(int id){
		switch (id){
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, hour, min,false);
		}
		return null;
	}
	
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			hour = hourOfDay;
			min = minute;
			
			StringBuilder sb = new StringBuilder();
			if(hour>=12){
				sb.append(hour-12).append(":").append(min).append(" PM");
			}else{
				sb.append(hour).append(":").append(min).append(" AM");
			}
			reminderTime.setText(sb);
		}
	};
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {

		if (v.equals(this.changeTime)) {
			
			showDialog(TIME_DIALOG_ID);
			
		}else if(v.equals(this.saveReminder)){
		String getTitle = reminderTitle.getText().toString();
		String getTime = reminderTime.getText().toString();
		int getRadioBtn = radioGroup.getCheckedRadioButtonId();
		
		
		
		if (fileStatus == true && _id >= 10) {
			if (reminderTitle != null && reminderTime != null) {
				JSONObject obj = new JSONObject();
				
				JSONObject logObj = new JSONObject();
				try {
					obj.put("id", _id);
					obj.put("title", getTitle);
					obj.put("time", getTime);
					obj.put("rdoButton", getRadioBtn);
					
					Log.i("output obj", obj.toString());
					reminderArray.put(obj);
					logObj.put("entry", reminderArray);
					String output = logObj.toString();
					FileManagement.storeStringfile(this, "reminderList", output,
							false);
					Calendar c = Calendar.getInstance();
					c.set(Calendar.HOUR_OF_DAY, hour);
					c.set(Calendar.MINUTE, min);
					c.set(Calendar.SECOND, 0);
					scheduleClient.setAlarmForNotification(c);
					StringBuilder sb = new StringBuilder();
					if(hour>=12){
						sb.append(hour-12).append(":").append(min).append(" PM");
					}else{
						sb.append(hour).append(":").append(min).append(" AM");
					}
					ToastFactory.shortToast(this, "Notification set for: " + sb);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				alertMessage("Please Complete the Form!");
			}
		}else if (fileStatus == true && _id <= 10){
			if (reminderTitle != null && reminderTime != null) {
				JSONObject obj = new JSONObject();
				JSONArray array = new JSONArray();
				JSONObject logObj = new JSONObject();
				try {
					obj.put("id", _id);
					obj.put("title", getTitle);
					obj.put("time", getTime);
					obj.put("rdoButton", getRadioBtn);
					
					Log.i("output obj", obj.toString());
					array.put(obj);
					logObj.put("entry", array);
					String output = logObj.toString();
					FileManagement.storeStringfile(this, "reminderList", output,
							false);
					Calendar c = Calendar.getInstance();
					c.set(Calendar.HOUR_OF_DAY, hour);
					c.set(Calendar.MINUTE, min);
					c.set(Calendar.SECOND, 0);
					scheduleClient.setAlarmForNotification(c);
					StringBuilder sb = new StringBuilder();
					if(hour>=12){
						sb.append(hour-12).append(":").append(min).append(" PM");
					}else{
						sb.append(hour).append(":").append(min).append(" AM");
					}
					ToastFactory.shortToast(this, "Notification set for: " + sb);
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
					obj.put("id", _id);
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
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, hour);
				c.set(Calendar.MINUTE, min);
				c.set(Calendar.SECOND, 0);
				scheduleClient.setAlarmForNotification(c);
				StringBuilder sb = new StringBuilder();
				if(hour>=12){
					sb.append(hour-12).append(":").append(min).append(" PM");
				}else{
					sb.append(hour).append(":").append(min).append(" AM");
				}
				ToastFactory.shortToast(this, "Notification set for: " + sb);
			} else {
				alertMessage("Please Complete the Form!");
			}
		}
		setResult(RESULT_OK);
		finish();
	}
	}
	
	@Override
	protected void onStop(){
		if(scheduleClient != null){
			scheduleClient.doUnbindService();
			super.onStop();
		}
	}
}
