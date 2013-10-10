package com.wickedsoftwaredesigns.diabeticslog;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
		
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
}
