 /*
  * <QuickLookup: quickest way to find definitions, web results and images>
    Copyright (©) <2013>  <Soma Chakraborty, Yagnesh Revar>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.quantumleap.quicklookup;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class MainService extends Service{

	
	private static final String TAG = "QLFGService";
	
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
    	
    	Log.d(TAG, "On Service Bind... ");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
    	Log.d(TAG, "On Service Start... ");
        startForeground(1, createNotification());
                                ////// will do all my stuff here on in the method onStart() or onCreat()?

        return START_STICKY;    ///// which return is better to keep the service running untill explicitly killed. contrary to system kill.
                                ///// http://developer.android.com/reference/android/app/Service.html#START_FLAG_REDELIVERY

        //notes:-//  if you implement onStartCommand() to schedule work to be done asynchronously or in another thread, 
        //then you may want to use START_FLAG_REDELIVERY to have the system re-deliver an Intent for you so that it does not get lost if your service is killed while processing it
    }

    @Override
    public void onDestroy() {
    	
    	Log.d(TAG, "On Service Destroyed... ");
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
		Log.d(TAG, "On Service Created... ");
	}
	
	private Notification createNotification() {
		
		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.button1,pIntent);
		
		// Build notification
		// Actions are just fake
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.search)
		.setContentTitle(getResources().getString(R.string.app_name))
		.setContentText("Content")      
		.setAutoCancel(true)
		.setContent(remoteViews);
		
		return builder.getNotification();	
	}
}