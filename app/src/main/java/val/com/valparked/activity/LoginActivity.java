package val.com.valparked.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import val.com.valparked.BuildConfig;
import val.com.valparked.R;

public class LoginActivity extends AppCompatActivity {
    private TextView tvVersion;
    private EditText edUserId;
    private EditText edUserPass;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
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
}
