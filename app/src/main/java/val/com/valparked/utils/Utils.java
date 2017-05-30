package val.com.valparked.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import val.com.valparked.R;

/**
 * Created by Musharib Ali on 5/26/2017.
 */

public class Utils {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void showNoConnectionDialog(final Activity activity) {
        showNoConnectionDialog(activity,activity.getString(R.string.no_connection_title),activity.getString(R.string.no_connection),null);
    }
    public static void showNoConnectionDialog(final Activity activity,String title,String message,DialogInterface.OnClickListener onClickListener) {

        AlertDialog.Builder builder;// = new AlertDialog.Builder(activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }

        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setPositiveButton("Ok",onClickListener);


        builder.show();
    }
    public static String getDeviceId(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if(TextUtils.isEmpty(deviceId)){
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
        }

        return deviceId;
    }
}
