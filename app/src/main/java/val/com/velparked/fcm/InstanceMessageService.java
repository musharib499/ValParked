package val.com.velparked.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import val.com.velparked.activity.MasterActivity;
import val.com.velparked.intarface.NotificationInterface;
import val.com.velparked.utils.Constant;

/**
 * Created by User on 3/22/2017.
 */

public class InstanceMessageService extends FirebaseMessagingService {
    private static final String TAG = "InstanceMessageService";
    private NotificationInterface anInterface;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (anInterface instanceof NotificationInterface)
            anInterface=(NotificationInterface) getApplicationContext();
        //Log data to Log Cat
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().toString());
        //create notification
       /* if (anInterface!=null)
        anInterface.notify(remoteMessage.getData().toString());*/
        Intent intent = new Intent(Constant.FCM_MASTER);
        // add data
        intent.putExtra(Constant.NOTIFICATION, remoteMessage.getNotification().toString());

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        sendNotification(remoteMessage.getNotification().getBody());


    }

    @Override
    public void onCreate() {
        super.onCreate();
      /*  if (anInterface instanceof NotificationInterface)
            anInterface=(NotificationInterface) this;*/
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MasterActivity.class);
        intent.putExtra("msg",messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
