package val.com.valparked.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import val.com.valparked.R;
import val.com.valparked.application.ValApplication;
import val.com.valparked.model.Login;
import val.com.valparked.utils.ComplexPreferences;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {
    private View mContentView;
    private ComplexPreferences complexPreferences;
    private ValApplication valApplication;
    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        show();
        setContentView(R.layout.activity_fullscreen);
        valApplication = getValApplication();

        complexPreferences = valApplication.getComplexPreference();

        mContentView = findViewById(R.id.fullscreen_content);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Login login = valApplication.getLoginResponse();
                if (login != null) {

                    if (login.getUserDetails().getUserType().equalsIgnoreCase("0")) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (login.getUserDetails().getUserType().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(SplashActivity.this, MasterActivity.class);
                        startActivity(intent);
                        finish();
                    }


                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);



        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("InlinedApi")
    private void show() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

    }


}
