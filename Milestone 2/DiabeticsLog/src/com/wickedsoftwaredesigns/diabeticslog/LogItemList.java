package com.wickedsoftwaredesigns.diabeticslog;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LogItemList extends Activity {
	
	ListView logItemList;
	String logItemsString;
	String reading;
	String date;
	String time;
	String reason;
	
	
	public ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
	
	public void updateUI(){
		//reading JSON string from local file
		logItemsString = FileManagement.readStringFile(this, "logList", false);
		
		JSONObject job = null;
		JSONArray logEntries = null;
		
		
		try {
			//creating the JSON Object
			job = new JSONObject(logItemsString);
			//pulling the movies array out of the object
			logEntries = job.getJSONArray("entry");
			//recording the length of the array
			int recordSize = logEntries.length();
			Log.i("JSONArray Size", "There are "+ String.valueOf(recordSize)+ " records in the file.");
			
					//looping over the array and pulling the data for each movie out
					for (int i = 0; i < recordSize; i++) {
						
						JSONObject entryObject = logEntries.getJSONObject(i);
						reading = entryObject.getString("reading");
						date = entryObject.getString("date");
						time = entryObject.getString("time");
						reason = entryObject.getString("reason");
						//storing the data into a hashmap into key value pairs
						HashMap<String, String> displayMap = new HashMap<String, String>();
						displayMap.put("reading", reading);
						displayMap.put("date", date);
						displayMap.put("time", time);
						
						myList.add(displayMap);
						Log.i("myList Hashmap", myList.toString());
					}
					//building a simple adapter to process the info into a listview
					SimpleAdapter adapter = new SimpleAdapter(this, myList, R.layout.listviewrow, 
							new String[] { "date", "time", "reading"}, 
							new int[]{R.id.mainLabel, R.id.subLabel, R.id.subLabel1});
					logItemList.setAdapter(adapter);
					
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Log List");
		setContentView(R.layout.activity_log_item_list);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    	    
	    logItemsString = FileManagement.readStringFile(this, "logList", false);
	    
	    logItemList = (ListView)findViewById(R.id.logItemList);
	    if (logItemsString != null) {
			updateUI();
		}else{
			alertMessage("No Log Entry Stored Please Press the '+' above to add one");
		}
		logItemList.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	              // When clicked, show a toast with the TextView text
	        		logItemList.getItemAtPosition(position);
	                Intent myIntent = new Intent(view.getContext(), ViewLogEntry.class);
	                myIntent.putExtra("Date", date);
	                myIntent.putExtra("Time", time);
	                myIntent.putExtra("Reading", reading);
	                myIntent.putExtra("Reason", reason);
	                startActivityForResult(myIntent, 0);

	            }
	          });
	    
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
			startActivityForResult(intent, 0);
			
		}else if (item.getItemId() == R.id.action_settings) {
			
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK)
		{
			myList.clear();
			updateUI();
		}
		
	
	}
}
