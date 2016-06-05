package com.example.lucy.buddysystem;

import android.content.Intent;
import android.app.PendingIntent;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;
import android.graphics.Color;
import java.util.Date;

public class SundownNotifier extends AsyncTask<Date, Void, Void> {
    private Context ctx;
    PendingIntent contentIntent;

    public SundownNotifier(Context ctx){
        this.ctx = ctx;
    }

    protected void onPreExecute() {
        Intent notificationIntent = new Intent(ctx, MainActivity.class);
        contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected Void doInBackground(Date... sundown) {
        this.ctx = ctx.getApplicationContext();
        Date now = new Date();
        //if(now.equals(sundown)){
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(ctx)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("It's getting dark")
                            .setContentText("Find a buddy!")
                            .setLights(Color.CYAN, 1000, 1000)
                            .setAutoCancel(true);
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(8001, builder.build());
        //}
        return null;
    }
}
