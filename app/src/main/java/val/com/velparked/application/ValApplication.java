package val.com.velparked.application;

import android.app.Application;

import val.com.velparked.R;
import val.com.velparked.model.Login;
import val.com.velparked.utils.ComplexPreferences;
import val.com.velparked.utils.Constant;

/**
 * Created by User on 6/4/2017.
 */

public class ValApplication extends Application {
    private ComplexPreferences complexPreferences = null;
    private Login login;
    @Override
    public void onCreate() {
        super.onCreate();
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
}
