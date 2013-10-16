package com.wickedsoftwaredesigns.diabeticslog;

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
	Button updateMed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Edit Medication");
		setContentView(R.layout.activity_edit_med_entry);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    Bundle extras = getIntent().getExtras();
		Log.i("bundle", extras.toString());
	    
		medName = (EditText)findViewById(R.id.medNameField);
		qtyTime = (EditText)findViewById(R.id.medQtyField);
		updateMed = (Button)findViewById(R.id.updateMedicationButton);
		updateMed.setOnClickListener(this);
		
		medName.setText(extras.getString("Title"));
		
		qtyTime.setText(extras.getString("Qty"));
		Log.i("Bundle title", medName.getText().toString());
		Log.i("Bundle Qty Time", qtyTime.getText().toString());
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
