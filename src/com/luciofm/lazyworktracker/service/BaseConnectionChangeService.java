package com.luciofm.lazyworktracker.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public abstract class BaseConnectionChangeService extends IntentService {

	public BaseConnectionChangeService(String name) {
		super(name);
	}

	private static PowerManager.WakeLock mWakeLock;
	private static final String WAKELOCK_KEY = "LAZYWORKTRACKER";

	public abstract void handleConnectionChange(Context context, Intent intent);
	
	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			Context context = getApplicationContext();
			handleConnectionChange(context, intent);
		} finally {
			// Release the power lock, so phone can get back to sleep.
			// The lock is reference counted by default, so multiple
			// messages are ok.

			// If the onMessage() needs to spawn a thread or do something else,
			// it should use it's own lock.
			mWakeLock.release();
		}

	}

	public static void runIntentInService(Context context, Intent intent) {
		if (mWakeLock == null) {
			// This is called from BroadcastReceiver, there is no init.
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					WAKELOCK_KEY);
		}
		mWakeLock.acquire();

		// Use a naming convention, similar with how permissions and intents are
		// used. Alternatives are introspection or an ugly use of statics.
		String receiver = context.getPackageName() + ".service.ConnectionChangeService";
		intent.setClassName(context, receiver);

		context.startService(intent);

	}
}
