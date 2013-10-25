package com.wickedsoftwaredesigns.diabeticslog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public class ReminderList extends Activity {
	
	ListView reminderListView;
	String reminderItemsString;
	String title;
	String time;
	int _id;
	Boolean fileStatus;
	
	public ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();

	public void updateUI(){
		//reading JSON string from local file
		reminderItemsString = FileManagement.readStringFile(this, "reminderList", false);
		
		JSONObject job = null;
		JSONArray logEntries = null;
		
		
		try {
			//creating the JSON Object
			job = new JSONObject(reminderItemsString);
			//pulling the Reminders array out of the object
			logEntries = job.getJSONArray("entry");
			//recording the length of the array
			int recordSize = logEntries.length();
			Log.i("JSONArray Size", "There are "+ String.valueOf(recordSize)+ " records in the file.");
			
					//looping over the array and pulling the data for each reminder out
					for (int i = 0; i < recordSize; i++) {
						
						JSONObject entryObject = logEntries.getJSONObject(i);
						_id = entryObject.getInt("id");
						title = entryObject.getString("title");
						time = entryObject.getString("time");
						//storing the data into a hashmap into key value pairs
						HashMap<String, String> displayMap = new HashMap<String, String>();
						displayMap.put("title", title);
						displayMap.put("time", time);
						displayMap.put("id", String.valueOf(_id));
						
						myList.add(displayMap);
						Log.i("myList Hashmap", myList.toString());
					}
					//building a simple adapter to process the info into a listview
					SimpleAdapter adapter = new SimpleAdapter(this, myList, R.layout.listviewrow, 
							new String[] { "title", "time",}, 
							new int[]{R.id.mainLabel, R.id.subLabel});
					reminderListView.setAdapter(adapter);
					
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public boolean doesFileExist(String filename){
		File file = getBaseContext().getFileStreamPath(filename);
		return file.exists();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Reminders List");
		setContentView(R.layout.activity_reminder_list);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    reminderListView = (ListView)findViewById(R.id.reminderList);
	    
	    fileStatus = doesFileExist("reminderList");
	    
	    if (fileStatus == true) {
			updateUI();
		}else{
			alertMessage("No Reminders Stored Please Press teh '+' above to add one");
		}
		reminderListView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	              // When clicked, show a toast with the TextView text
	        	
	        		Object item = reminderListView.getItemAtPosition(position);
	        		String listcontent = item.toString();
	        		Log.i("string object", listcontent);
	        		@SuppressWarnings("unchecked")
					Map<String, String> itemInfo = (Map<String, String>) item;
	                Intent myIntent = new Intent(view.getContext(), Reminder.class);
	                String itemId = itemInfo.get("id");
	                myIntent.putExtra("Id", Integer.parseInt(itemId));
	                
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
			
			Intent intent = new Intent(this, Reminder.class);
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
			Log.i("result", "refresh list");
			myList.clear();
			updateUI();
		}
		
	
	}
}
