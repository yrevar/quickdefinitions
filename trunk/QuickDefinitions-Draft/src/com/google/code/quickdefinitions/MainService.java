package com.google.code.quickdefinitions;

import com.example.easydefinitions.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class MainService extends Service{

	
	private static final String TAG = "MainService";
	
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
    	
    	Log.i(TAG, "On Service Bind... ");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
    	Log.i(TAG, "On Service Start... ");
        startForeground(1, createNotification());
                                ////// will do all my stuff here on in the method onStart() or onCreat()?

        return START_STICKY;    ///// which return is better to keep the service running untill explicitly killed. contrary to system kill.
                                ///// http://developer.android.com/reference/android/app/Service.html#START_FLAG_REDELIVERY

        //notes:-//  if you implement onStartCommand() to schedule work to be done asynchronously or in another thread, 
        //then you may want to use START_FLAG_REDELIVERY to have the system re-deliver an Intent for you so that it does not get lost if your service is killed while processing it
    }

    @Override
    public void onDestroy() {
    	
    	Log.i(TAG, "On Service Destroyed... ");
        stop();
    }

    public void stop(){
        //if running
        // stop
        // make vars as false
        // do some stopping stuff
        stopForeground(true);
        /////// how to call stopSelf() here? or any where else? whats the best way?
    }

	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "On Service Created... ");
	}
	
	private Notification createNotification() {
		

		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.button1,pIntent);
		
		// Build notification
		// Actions are just fake
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
		.setContentTitle("Notification Testing")
		.setContentText("Content").setSmallIcon(R.drawable.ic_launcher)      
		.setAutoCancel(true)
		.setContent(remoteViews);
		
		return builder.getNotification();
		
		//NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager
		//notificationmanager.notify(0, builder.build());
	}
}