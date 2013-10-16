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
import android.widget.TextView;

public class ViewDocInfo extends Activity implements OnClickListener{

	TextView viewDocName;
	TextView viewDocNumber;
	TextView viewDocAddress;
	Button editDocInfoButton;
	String docInfoString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("View Doc's Info");
		setContentView(R.layout.activity_view_doc_info);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		viewDocName = (TextView)findViewById(R.id.drNameFieldView);
		viewDocNumber = (TextView)findViewById(R.id.drNumFieldView);
		viewDocAddress = (TextView)findViewById(R.id.docAddressFieldView);
		editDocInfoButton = (Button)findViewById(R.id.editDocInfoFromViewButton);
		editDocInfoButton.setOnClickListener(this);
		
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
					viewDocName.setText(name);
					viewDocNumber.setText(number);
					viewDocAddress.setText(address);
					
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
		
		Intent intent = new Intent(this, EditDocInfo.class);
		startActivity(intent);
		
	}
}
