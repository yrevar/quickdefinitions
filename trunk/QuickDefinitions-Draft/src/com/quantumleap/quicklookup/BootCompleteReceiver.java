package com.quantumleap.quicklookup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {
	
	private static final String TAG = "QLBootCmpleRcvr";
	
	@Override
    public void onReceive(Context context, Intent intent) {
    	
    	Log.d(TAG, "On Boot Cmplt Received" + intent.getAction());
    	
        Intent service = new Intent(context, MainService.class);
        context.startService(service);
    }
}