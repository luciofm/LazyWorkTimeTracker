package com.luciofm.lazyworktracker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.luciofm.lazyworktracker.service.BaseConnectionChangeService;

public class ConnectivityChanged extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent date) {
		Log.d("LWT", "ConnectivityChanged receiver");
		BaseConnectionChangeService.runIntentInService(context, date);
	}

}
