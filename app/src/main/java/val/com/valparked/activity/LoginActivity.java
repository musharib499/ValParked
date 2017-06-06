package val.com.valparked.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import val.com.valparked.BuildConfig;
import val.com.valparked.R;
import val.com.valparked.application.ValApplication;
import val.com.valparked.model.Login;
import val.com.valparked.retrofit.RestApiCalls;
import val.com.valparked.utils.ComplexPreferences;
import val.com.valparked.utils.Constant;
import val.com.valparked.utils.Utils;

public class LoginActivity extends BaseActivity {
    private static final String TAG =LoginActivity.class.getName() ;
    private TextView tvVersion;
    private EditText edUserId;
    private EditText edUserPass;
    private Button btnLogin;
    private Context context=this;

    private ValApplication valApplication;
    private ComplexPreferences complexPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        valApplication=getValApplication();
        complexPreferences = getValApplication().getComplexPreference();

        onView();
    }

    public void onView()
    {
        tvVersion=(TextView) findViewById(R.id.tvVersion);
        edUserId=(EditText) findViewById(R.id.edUserId);
        edUserPass=(EditText) findViewById(R.id.edPassword);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        onClickView();
        setValue();
    }
    public void setValue()
    {
        tvVersion.setText("Version "+BuildConfig.VERSION_NAME);
    }
    void onClickView()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected(context))
                    buildDialog(context);
                    else setLogin(edUserId.getText().toString(),edUserId.getText().toString(),"0");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @SuppressLint("InlinedApi")
    private void show() {
        btnLogin.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    public void setLogin(String userName,String password,String userType)
    {
        HashMap<String,String> params = new HashMap<>();
        params.put(Constant.USERNAME,userName);
        params.put(Constant.PASSWORD,password);
        params.put(Constant.USER_TYPE, userType);
        params.put(Constant.DEVICE_ID, Utils.getDeviceId(this));

        requestProgress("Login","Please wait ...");
        RestApiCalls.getLogin(params).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                if (response.isSuccessful() && response.body()!=null)
                {
                    hideProgress();
                    Login login=response.body();
                    if (login.getStatus()&& login.getUserDetails()!=null)
                    {


                        valApplication.setLoginResponse(login);
                        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);


                    }
                    else {
                        Log.e(TAG,"status fails");
                    }
                }else {
                    Log.e(TAG,"No Record");

                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e(TAG,"fail");
            }
        });

    }


}
