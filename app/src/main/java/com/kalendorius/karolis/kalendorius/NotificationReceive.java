package com.kalendorius.karolis.kalendorius;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

/**
 * Created by Karolis on 15/11/05.
 */
public class NotificationReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle extra = intent.getExtras();
        if(extra == null) {
            return;
        }
        int eventId = extra.getInt("eventId");
        DBHandler db = new DBHandler(context, null, null, 1);

        Intent intent1 = new Intent(context, NavigationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("Kalendorius!")
                .setContentText("Įvykis: " + db.getEventName(eventId))
                .setSmallIcon(R.mipmap.ic_launcher);
        nBuilder.setContentIntent(pIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        mNotifyMgr.notify(m, nBuilder.build());

    }

    public static void cancelNotifications(Context context) {
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.cancelAll();
    }
}
