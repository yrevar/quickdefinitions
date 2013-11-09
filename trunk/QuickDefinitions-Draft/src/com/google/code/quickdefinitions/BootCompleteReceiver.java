package com.google.code.quickdefinitions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {
	
	private static final String TAG = "BootCmpleRcvr";
	
	@Override
    public void onReceive(Context context, Intent intent) {
    	
    	Log.i(TAG, "On Boot Cmplt Receive... starting main service..." + intent.getAction());
    	
        Intent service = new Intent(context, MainService.class);
        context.startService(service);
    }
}