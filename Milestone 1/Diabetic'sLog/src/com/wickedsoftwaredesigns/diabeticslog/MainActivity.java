package com.wickedsoftwaredesigns.diabeticslog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button newLogEntry;
	Button logItemList;
	Button medList;
	Button viewDocInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//setting up buttons
		newLogEntry = (Button)findViewById(R.id.newLogEntryButton);
		logItemList = (Button)findViewById(R.id.logItemListButton);
		medList = (Button)findViewById(R.id.medListButton);
		viewDocInfo = (Button)findViewById(R.id.viewDocInfoButton);
		
		//creating on click listeners
		newLogEntry.setOnClickListener(this);
		logItemList.setOnClickListener(this);
		medList.setOnClickListener(this);
		viewDocInfo.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		//building on click functions
		if (v.equals(this.newLogEntry)) {
			
			//building intent for activity change
			Intent intent = new Intent(this, NewLogEntry.class);
			startActivity(intent);
			
		}else if (v.equals(this.logItemList)) {
			
			//building intent for activity change
			Intent intent = new Intent(this, LogItemList.class);
			startActivity(intent);
			
			
		}else if (v.equals(this.medList)) {
			
			//building intent for activity change
			Intent intent = new Intent(this, MedList.class);
			startActivity(intent);
			
			
		}else if (v.equals(this.viewDocInfo)) {
			
			//building intent for activity change
			Intent intent = new Intent(this, ViewDocInfo.class);
			startActivity(intent);
			
			
		}
		
	}

}
