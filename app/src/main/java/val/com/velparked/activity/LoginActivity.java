package val.com.velparked.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import val.com.velparked.BuildConfig;
import val.com.velparked.R;
import val.com.velparked.application.ValApplication;
import val.com.velparked.model.Login;
import val.com.velparked.retrofit.RestApiCalls;
import val.com.velparked.utils.ComplexPreferences;
import val.com.velparked.utils.Constant;
import val.com.velparked.utils.Utils;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getName();
    private TextView tvVersion,tvError;
    private EditText edUserId;
    private EditText edUserPass;
    private Button btnLogin;
    private Context context = this;
    private RadioGroup radioGroup;
    private RadioButton rbValet, rbMaster;


    private ValApplication valApplication;
    private ComplexPreferences complexPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        valApplication = getValApplication();
        complexPreferences = getValApplication().getComplexPreference();

        onView();
    }

    public void onView() {
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvError = (TextView) findViewById(R.id.tvError);
        edUserId = (EditText) findViewById(R.id.edUserId);
        edUserPass = (EditText) findViewById(R.id.edPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbValet = (RadioButton) findViewById(R.id.radioVelet);
        rbMaster = (RadioButton) findViewById(R.id.radioMaster);


        onClickView();
        setValue();
    }

    public void setValue() {
        tvVersion.setText("Version " + BuildConfig.VERSION_NAME);
    }

    void onClickView() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!TextUtils.isEmpty(edUserId.getText().toString()) && !TextUtils.isEmpty(edUserPass.getText().toString())) {
                        if (isConnected(context)) {
                            tvError.setVisibility(View.GONE);
                            tvError.setText("");
                            String userType = "0";
                            if (radioGroup.getCheckedRadioButtonId() == rbValet.getId()) {
                                userType = "0";
                            } else if (radioGroup.getCheckedRadioButtonId() == rbMaster.getId()) {
                                userType = "1";
                            }

                            setLogin(edUserId.getText().toString(), edUserPass.getText().toString(), userType);
                        }
                    }else {
                        if (TextUtils.isEmpty(edUserId.getText().toString()))
                             edUserId.setError("Please enter username");

                        if (TextUtils.isEmpty(edUserPass.getText().toString()))
                            edUserPass.setError("Please enter password");
                    }


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

    public void setLogin(String userName, String password, final String userType) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.USERNAME, userName);
        params.put(Constant.PASSWORD, password);
        params.put(Constant.USER_TYPE, userType);
        params.put(Constant.DEVICE_ID, Utils.getDeviceId(this));
        Log.e(TAG,params.toString());
        requestProgress("Login", "Please wait ...");
        RestApiCalls.getLogin(params).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {

                    Login login = response.body();
                    if (login.getStatus() && login.getUserDetails() != null) {


                        valApplication.setLoginResponse(login);
                        if (userType.equalsIgnoreCase("0")) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (userType.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(LoginActivity.this, MasterActivity.class);
                            startActivity(intent);
                            finish();
                       }


                    } else {
                        Toast.makeText(context, login.getMessage(),LENGTH_SHORT).show();
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText(R.string.invalidup);

                    }
                } else {

                    Toast.makeText(context,response.message() ,LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                hideProgress();
                Toast.makeText(context, t.getMessage(),LENGTH_SHORT).show();
            }
        });

    }


}
