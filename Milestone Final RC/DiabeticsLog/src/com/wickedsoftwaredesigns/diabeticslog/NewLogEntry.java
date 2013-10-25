package com.wickedsoftwaredesigns.diabeticslog;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wickedsoftwaredesigns.libs.FileManagement;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class NewLogEntry extends Activity implements OnClickListener{

	EditText date;
	Button changeDate;
	EditText time;
	Button changeTime;
	EditText reading;
	EditText reason;
	Button saveNew;
	String logItemListData;
	JSONObject logList;
	JSONArray logListArray;
	int id;
	Calendar cal;
	int month;
	int day;
	int yearInt;
	int hour;
	int min;
	static final int TIME_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID = 1;
	Boolean fileStatus;
	Random rand;
	String units;
	
	public boolean doesFileExist(String filename){
		File file = getBaseContext().getFileStreamPath(filename);
		return file.exists();
	}
	
	public int getUnits(){
		int buttonId = 0;
		SharedPreferences prefs = this.getSharedPreferences("userPrefs", 0);
		buttonId = prefs.getInt("unitSelected", 0);
		if (buttonId == 2131296288) {
			
			units = " mg/dL";
			
		}else if (buttonId == 2131296289) {
			
			units = " mmol/L";
			
		}
		return buttonId;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("New Log Entry");
		setContentView(R.layout.activity_new_log_entry);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		date = (EditText)findViewById(R.id.dateField);
		changeDate = (Button)findViewById(R.id.changeDateButton);
		changeDate.setOnClickListener(this);
		time = (EditText)findViewById(R.id.timeField);
		changeTime = (Button)findViewById(R.id.changeTimeButton);
		changeTime.setOnClickListener(this);
		reading = (EditText)findViewById(R.id.sugarCountField);
		reason = (EditText)findViewById(R.id.reasonField);
		saveNew = (Button)findViewById(R.id.saveLogEntryButton);
		saveNew.setOnClickListener(this);
		
		getUnits();
		
		cal = Calendar.getInstance();
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		yearInt = cal.get(Calendar.YEAR);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		min = cal.get(Calendar.MINUTE);
		
		
		date.setText(String.valueOf(month + 1 ) + "/" + String.valueOf(day) + "/" + String.valueOf(yearInt));
		
		StringBuilder sbTime = new StringBuilder();
		if(hour>=12){
			sbTime.append(padding_str(hour-12)).append(":").append(padding_str(min)).append(" PM");
		}else{
			sbTime.append(padding_str(hour)).append(":").append(padding_str(min)).append(" AM");
		}
		time.setText(sbTime);
		
		fileStatus = doesFileExist("logList");
		
		if (fileStatus == true) {
			logItemListData = FileManagement.readStringFile(this, "logList", false);
			rand = new Random();
			id = rand.nextInt(100000-10);
			
			Log.i("Log id",String.valueOf(id));
			try {
				logList = new JSONObject(logItemListData);
				logListArray = logList.getJSONArray("entry");
				Log.i("Checking Log Entries", logListArray.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			id = 1;
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
	protected Dialog onCreateDialog(int id){
		switch (id){
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, hour, min,false);
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, yearInt,month,day);
		}
		return null;
	}
	
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			hour = hourOfDay;
			min = minute;
			
			StringBuilder sbTime = new StringBuilder();
			if(hour>=12){
				sbTime.append(padding_str(hour-12)).append(":").append(padding_str(min)).append(" PM");
			}else{
				sbTime.append(padding_str(hour)).append(":").append(padding_str(min)).append(" AM");
			}
			time.setText(sbTime);
		}
	};
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			month = monthOfYear;
			day = dayOfMonth;
			yearInt = year;
			
			date.setText(String.valueOf(month + 1 ) + "/" + String.valueOf(day) + "/" + String.valueOf(yearInt));
			
		}
	};
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

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		
		if(v.equals(this.saveNew)){
		
		String getDate = date.getText().toString();
		String getTime = time.getText().toString();
		String getReading = reading.getText().toString();
		String getReason = reason.getText().toString();
		
		
		if (fileStatus == true) {
			if (date != null && time != null && reading != null
					&& reason != null) {
				
				JSONObject obj = new JSONObject();
				
				JSONObject logObj = new JSONObject();
				try {
					obj.put("id", id);
					obj.put("date", getDate);
					obj.put("time", getTime);
					obj.put("reading", getReading + units);
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
					obj.put("id", id);
					obj.put("date", getDate);
					obj.put("time", getTime);
					obj.put("reading", getReading + units);
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
		}else if(v.equals(this.changeTime)){
			
			showDialog(TIME_DIALOG_ID);
			
		}else if(v.equals(this.changeDate)) {
			showDialog(DATE_DIALOG_ID);
		}
	}
	

}
