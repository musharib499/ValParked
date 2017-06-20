package val.com.velparked.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import val.com.velparked.R;
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
        Log.d(TAG, "From: " + remoteMessage.getData().toString());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().toString());

        JSONObject object = new JSONObject(remoteMessage.getData());
        Log.e("Notification",object.toString());

        try {
            sendNotification(object);
            Intent intent = new Intent(Constant.FCM_MASTER);
            // add data
            intent.putExtra(Constant.FCM, "1");
            intent.putExtra(Constant.NOTIFICATION, remoteMessage.getData().toString());

            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onCreate() {
        super.onCreate();
      /*  if (anInterface instanceof NotificationInterface)
            anInterface=(NotificationInterface) this;*/
    }

    private void sendNotification(JSONObject messageBody) throws JSONException {
        Intent intent = new Intent(this, MasterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(getNotificationIcon())
                .setContentTitle(messageBody.getString("title").toString())
                .setContentText(messageBody.getString("message"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_sedan_car_model : R.mipmap.ic_launcher;
    }

}
