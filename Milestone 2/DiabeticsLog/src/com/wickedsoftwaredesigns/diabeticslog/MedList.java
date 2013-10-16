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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MedList extends Activity {

	ListView medListView;
	String medItemsString;
	String title;
	String qty;
	SimpleAdapter adapter;
	public ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
	
	public void updateUI(){
		//reading JSON string from local file
		medItemsString = FileManagement.readStringFile(this, "medList", false);
		Log.i("medItemString", medItemsString);
		JSONObject job = null;
		JSONArray logEntries = null;
		medListView = (ListView)findViewById(R.id.medList);
		
		
		try {
			//creating the JSON Object
			job = new JSONObject(medItemsString);
			//pulling the movies array out of the object
			logEntries = job.getJSONArray("entry");
			//recording the length of the array
			int recordSize = logEntries.length();
			Log.i("JSONArray Size", "There are "+ String.valueOf(recordSize)+ " records in the file.");
			
					//looping over the array and pulling the data for each movie out
					for (int i = 0; i < recordSize; i++) {
						
						JSONObject entryObject = logEntries.getJSONObject(i);
						title = entryObject.getString("title");
						qty = entryObject.getString("qty");
						Log.i("title", title);
						//storing the data into a hashmap into key value pairs
						HashMap<String, String> displayMap = new HashMap<String, String>();
						displayMap.put("title", title);
						displayMap.put("qty", qty);
						
						
						myList.add(displayMap);
						Log.i("myList Hashmap", myList.toString());
					}
					//building a simple adapter to process the info into a listview
					adapter = new SimpleAdapter(this, myList, R.layout.listviewrow, 
							new String[] { "title", "qty"}, 
							new int[]{R.id.mainLabel, R.id.subLabel});
					
					medListView.setAdapter(adapter);
					
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Medication List");
		setContentView(R.layout.activity_med_list);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    medItemsString = FileManagement.readStringFile(this, "medList", false);
	    
	    medListView = (ListView)findViewById(R.id.medList);
	    if (medItemsString != null) {
			updateUI();
			
		}else{
			alertMessage("No Medication Stored Please Press the '+' above to add one");
		}
		medListView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	              	medListView.getItemAtPosition(position);
	                Intent myIntent = new Intent(view.getContext(), EditMedEntry.class);
	                Log.i("Put Extra Title", title);
	                Log.i("Put Extra", qty);
	                myIntent.putExtra("Title", title);
	                myIntent.putExtra("Qty", qty);
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
			
			Intent intent = new Intent(this, NewMedEntry.class);
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
