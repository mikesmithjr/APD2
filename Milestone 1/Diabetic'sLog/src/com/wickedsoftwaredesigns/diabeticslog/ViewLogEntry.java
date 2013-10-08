package com.wickedsoftwaredesigns.diabeticslog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewLogEntry extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_log_entry);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_log_entry, menu);
		return true;
	}

}
