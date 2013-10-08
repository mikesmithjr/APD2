package com.wickedsoftwaredesigns.diabeticslog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditMedEntry extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_med_entry);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_med_entry, menu);
		return true;
	}

}
