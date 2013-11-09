package com.google.code.quickdefinitions;

import com.example.easydefinitions.R;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i(TAG, "Easy Definition Main Activity Created...");
		
		//Start forground service if not already started at boot intent
		Intent service = new Intent(this, MainService.class);
		startService(service);
		
		//Handle search button click event
		ImageButton ib = (ImageButton) findViewById(R.id.imageButton1);
		
		ib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				//Start webview
				Intent webViewIntent = new Intent(v.getContext(), WebViewActivity.class);
				
				//Create bundle to be sent
				Bundle mBundle = new Bundle();
				mBundle.putString(WebViewActivity.URL_KEY, ((TextView)findViewById(R.id.word_to_lookup)).getText().toString());
				
				//attach bundle to intent
				webViewIntent.putExtras(mBundle);
				//Start acvitity
			    v.getContext().startActivity(webViewIntent);
			}
		});
		
		this.setFinishOnTouchOutside(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
