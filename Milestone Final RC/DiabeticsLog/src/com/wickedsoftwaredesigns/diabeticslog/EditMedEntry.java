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

public class EditMedEntry extends Activity implements OnClickListener{

	EditText medName;
	EditText qtyTime;
	String title;
	String qty;
	int _id;
	int idEntry;
	Button updateMed;
	String medItemsString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Edit Medication");
		setContentView(R.layout.activity_edit_med_entry);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    Bundle extras = getIntent().getExtras();
	    _id = extras.getInt("Id");
	    
		medName = (EditText)findViewById(R.id.medNameField);
		qtyTime = (EditText)findViewById(R.id.medQtyField);
		updateMed = (Button)findViewById(R.id.updateMedicationButton);
		updateMed.setOnClickListener(this);
		
		medItemsString = FileManagement.readStringFile(this, "medList", false);
		
		JSONObject job = null;
		JSONArray logEntries = null;
		
		try {
			//creating the JSON Object
			job = new JSONObject(medItemsString);
			//pulling the meds array out of the object
			logEntries = job.getJSONArray("entry");
			//recording the length of the array
			int recordSize = logEntries.length();
			Log.i("JSONArray Size", "There are "+ String.valueOf(recordSize)+ " records in the file.");
			
					//looping over the array and pulling the data for each movie out
					for (int i = 0; i < recordSize; i++) {
						
						JSONObject entryObject = logEntries.getJSONObject(i);
						idEntry = entryObject.getInt("id");
						title = entryObject.getString("title");
						qty = entryObject.getString("qty");
						Log.i("title", title);
						if (idEntry == _id) {
							
							medName.setText(title);
							qtyTime.setText(qty);
							
						}
						
					}
					
					
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("Bundle id", String.valueOf(_id));
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
		setResult(RESULT_OK);
		finish();
	}

}
