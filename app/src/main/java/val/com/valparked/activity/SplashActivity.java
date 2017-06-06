package val.com.valparked.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        valApplication=getValApplication();

        complexPreferences=valApplication.getComplexPreference();

        mContentView = findViewById(R.id.fullscreen_content);

        Login login=valApplication.getLoginResponse();
        if (login!=null)
        {
            Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        show();
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }


}
