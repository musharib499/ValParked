package val.com.velparked.application;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.firebase.FirebaseApp;

import val.com.velparked.R;
import val.com.velparked.fcm.FCMRegister;
import val.com.velparked.intarface.IGCMToken;
import val.com.velparked.model.Login;
import val.com.velparked.utils.ComplexPreferences;
import val.com.velparked.utils.Constant;


/**
 * Created by User on 6/4/2017.
 */

public class ValApplication extends Application {
    private ComplexPreferences complexPreferences = null;
    private Login login;
    private FCMRegister mFcmRegister;
    private IGCMToken igcmToken;


    @Override
    public void onCreate() {
        super.onCreate();
        setUpFCM();
        complexPreferences = ComplexPreferences.getComplexPreferences(getApplicationContext(), getString(R.string.app_name));


    }
    public Login getLoginResponse() {
        if (login == null ) {
            login = complexPreferences.getObject(Constant.LOGIN_KEY, Login.class);
        }
        return login!=null ?login :null ;
    }


    /**
     * @param mLoginResponse StateResponse
     */
    public void setLoginResponse(Login mLoginResponse) {
        if (mLoginResponse != null) {
            complexPreferences.putObject(Constant.LOGIN_KEY, mLoginResponse);
            complexPreferences.commit();
        }
        this.login = mLoginResponse;
    }
    public ComplexPreferences getComplexPreference() {
        if (complexPreferences != null) {
            return complexPreferences;
        }
        return null;
    }
    public ApplicationInfo getApplicationMetaData()
            throws PackageManager.NameNotFoundException {
        return getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
    }
    public void setUpFCM()
    {
        FirebaseApp.initializeApp(this.getApplicationContext());
       /* if(BuildConfig.VERSION_NAME.contains("pro")) {
            FirebaseMessaging.getInstance().subscribeToTopic(Constant.FCM_SUBSCRIPTION_LIVE);
        }else {
            FirebaseMessaging.getInstance().subscribeToTopic(Constant.FCM_SUBSCRIPTION_DEV);
        }*/

    }
    public void initializeFcmToken() {
//        String baseUrl = IWebServicesModel.getBaseUrl();
//        if (!TextUtils.isEmpty(baseUrl)) {
        mFcmRegister = new FCMRegister(this) {
            @Override
            protected void sendRegistrationIdToBackend(String regId) {
                Log.d("sendRegistrationIdTo", "" + regId);
                if (igcmToken != null) {
                    igcmToken.receivedToken();
                }
            }
        };
//        }
    }
}
