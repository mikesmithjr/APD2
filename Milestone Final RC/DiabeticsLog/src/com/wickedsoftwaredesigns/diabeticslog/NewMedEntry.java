package com.wickedsoftwaredesigns.diabeticslog;

import java.io.File;
import java.util.Random;

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

public class NewMedEntry extends Activity implements OnClickListener{

	EditText newMedName;
	EditText newQtyTime;
	Button saveNewMed;
	String medItemListData;
	JSONObject medList;
	JSONArray medListArray;
	int id;
	Boolean fileStatus;
	Random rand;
	
	public boolean doesFileExist(String filename){
		File file = getBaseContext().getFileStreamPath(filename);
		return file.exists();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("New Medication");
		setContentView(R.layout.activity_new_med_entry);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		newMedName = (EditText)findViewById(R.id.medNameFieldNew);
		newQtyTime = (EditText)findViewById(R.id.medQtyFieldNew);
		saveNewMed = (Button)findViewById(R.id.saveNewMedicationButton);
		saveNewMed.setOnClickListener(this);
		
		fileStatus = doesFileExist("medList");
		
		if (fileStatus == true) {
			medItemListData = FileManagement.readStringFile(this, "medList",
					false);
			rand = new Random();
			id = rand.nextInt(100000-10);
			try {
				medList = new JSONObject(medItemListData);
				medListArray = medList.getJSONArray("entry");
				Log.i("Checking Log Entries", medListArray.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			id = 10;
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
		
		String getNewMedName = newMedName.getText().toString();
		String getQtyTime = newQtyTime.getText().toString();
		
		if (fileStatus == true) {
			if (newMedName != null && newQtyTime != null) {
				JSONObject obj = new JSONObject();
				
				JSONObject medObj = new JSONObject();
				
				
				try {
					obj.put("id", id);
					obj.put("title", getNewMedName);
					obj.put("qty", getQtyTime);
					Log.i("output obj", obj.toString());
					medListArray.put(obj);
					medObj.put("entry", medListArray);
					String output = medObj.toString();
					FileManagement.storeStringfile(this, "medList", output,
							false);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				alertMessage("Please Complete the Form!");
			}
		}else{
			if (newMedName != null && newQtyTime != null ) {
				JSONObject obj = new JSONObject();
				JSONArray array = new JSONArray();
				JSONObject medObj = new JSONObject();
			
				try {
					obj.put("id", id);
					obj.put("title", getNewMedName);
					obj.put("qty", getQtyTime);
					Log.i("output obj", obj.toString());
					array.put(obj);
					medObj.put("entry", array);
					String output = medObj.toString();
					FileManagement.storeStringfile(this, "medList", output,
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
