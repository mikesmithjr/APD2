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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditDocInfo extends Activity implements OnClickListener{

	EditText docName;
	EditText docNumber;
	EditText docAddress;
	Button saveDocInfo;
	String doctorName;
	String doctorNumber;
	String doctorAddress;
	String docInfoString;
	
	public ArrayList<HashMap<String, String>> docInfo = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Edit Doc's Info");
		setContentView(R.layout.activity_edit_doc_info);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
		
		docName = (EditText)findViewById(R.id.drNameField);
		docNumber = (EditText)findViewById(R.id.drNumField);
		docAddress = (EditText)findViewById(R.id.docAddressField);
		saveDocInfo = (Button)findViewById(R.id.saveDocInfoButton);
		saveDocInfo.setOnClickListener(this);
		
	    
	    docInfoString = FileManagement.readStringFile(this, "doctorInfo", false);
	    Log.i("docInfo", docInfoString);
	    
	    if (docInfoString != null) {
			
	    	JSONObject job = null;
	    	JSONArray doctor = null;
	    	
	    	try {
				job = new JSONObject(docInfoString);
				doctor = job.getJSONArray("doctor");
				int recordSize = doctor.length();
				for (int i = 0; i < recordSize; i++) {
					
					JSONObject doctorObject = doctor.getJSONObject(i);
					String name = doctorObject.getString("name");
					String number = doctorObject.getString("number");
					String address = doctorObject.getString("address");
					Log.i("docName", name);
					Log.i("docNumber", number);
					Log.i("docAddress", address);
					docName.setText(name);
					docNumber.setText(number);
					docAddress.setText(address);
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
		}else{
			alertMessage("No Doctor Info Saved");
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
		
		doctorName = docName.getText().toString();
		doctorNumber = docNumber.getText().toString();
		doctorAddress = docAddress.getText().toString();
		
		if (doctorName != null) {
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			JSONObject docObj = new JSONObject();
			try {
				obj.put("name", doctorName);
				obj.put("number", doctorNumber);
				obj.put("address", doctorAddress);
				Log.i("output obj", obj.toString());
				array.put(obj);
				docObj.put("doctor", array);
				String output = docObj.toString();
				FileManagement.storeStringfile(this, "doctorInfo", output,
						false);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			alertMessage("Please Complete the Form!");
		}
		finish();
	}
}
