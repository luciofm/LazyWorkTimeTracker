package com.luciofm.lazyworktracker.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionChangeService extends BaseConnectionChangeService {

	private static final String TAG = "LWTConnectionChangeService";

	private static final String LAST_CONNECTION_TYPE = "LAST_CONNECTION_TYPE";
	private static final String LAST_WIFI_CONNECTION = "LAST_WIFI_CONNECTION";

	public ConnectionChangeService() {
		super("LazyWorkTracker");
	}

	@Override
	public void handleConnectionChange(Context context, Intent intent) {
		Log.d(TAG, "handling Intent");
		/* FIXME - Move the shared preferences Name to some other place */
		SharedPreferences prefs = context.getSharedPreferences("LWTTPrefs",
				Context.MODE_PRIVATE);
		if (intent.getAction().contentEquals(
				ConnectivityManager.CONNECTIVITY_ACTION)) {
			boolean noConnectivity = intent.getBooleanExtra(
					ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

			Editor edit = prefs.edit();
			String lastConnType = prefs.getString(LAST_CONNECTION_TYPE, null);
			String lastWifiConn = prefs.getString(LAST_WIFI_CONNECTION, null);

			if (noConnectivity) {
				Log.d(TAG, "No connection at all [" + lastConnType + "] ["
						+ lastWifiConn + "]");
				if (lastConnType != null && lastConnType.contentEquals("WIFI"))
					Log.d(TAG, "Last connection was wifi...");
				edit.putString(LAST_CONNECTION_TYPE, null)
						.putString(LAST_WIFI_CONNECTION, null).commit();
				return;
			}

			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();

			Log.d(TAG, "Connected to: " + info.getTypeName() + " - "
					+ info.getState().name());
			Log.d(TAG, "Last connection to [" + lastConnType + "] ["
					+ lastWifiConn+ "]");
			if (info.getState() == NetworkInfo.State.CONNECTED)
				edit.putString(LAST_CONNECTION_TYPE, info.getTypeName())
						.putString(LAST_WIFI_CONNECTION, null).commit();
		}
	}

}
