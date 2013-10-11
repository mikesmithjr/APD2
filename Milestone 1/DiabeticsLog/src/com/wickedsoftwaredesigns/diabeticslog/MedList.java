package com.wickedsoftwaredesigns.diabeticslog;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MedList extends Activity {

	ListView medListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Medication List");
		setContentView(R.layout.activity_med_list);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    medListView = (ListView)findViewById(R.id.medList);
	    
	    String[] medTitleList = getResources().getStringArray(R.array.med_item_title);
	    
	    medListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, medTitleList));
	    medListView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	              // When clicked, show a toast with the TextView text
	                 Intent myIntent = new Intent(view.getContext(), EditMedEntry.class);
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
			startActivity(intent);
			
		}else if (item.getItemId() == R.id.action_settings) {
			
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			
		}
		
		return super.onOptionsItemSelected(item);
	}
}
