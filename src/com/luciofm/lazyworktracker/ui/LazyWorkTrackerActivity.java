package com.luciofm.lazyworktracker.ui;

import android.os.Bundle;

import com.luciofm.lazyworktracker.R;
import com.luciofm.lazyworktracker.actionbarcompat.ActionBarActivity;

public class LazyWorkTrackerActivity extends ActionBarActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
}