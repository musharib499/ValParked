package val.com.velparked.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import val.com.velparked.BuildConfig;
import val.com.velparked.R;
import val.com.velparked.application.ValApplication;
import val.com.velparked.intarface.FragmentAdapter;
import val.com.velparked.model.BaseResponseModel;
import val.com.velparked.model.UserDetails;
import val.com.velparked.retrofit.RestApiCalls;
import val.com.velparked.utils.Constant;
import val.com.velparked.utils.ProgressCommonDialog;
import val.com.velparked.utils.TelephonyInfo;

public class BaseActivity extends AppCompatActivity implements FragmentAdapter, NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawer;
    private ProgressDialog progressDialog;
    protected FrameLayout frameLayout;
    protected NavigationView navigationView;
    private TextView tvPowerBy;
    private Button btnNext;

    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.fragment);
        tvPowerBy = (TextView) findViewById(R.id.tvPowerBy);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fragmentManager = getSupportFragmentManager();


    }

    protected void requestProgress(String title, String message) {
        progressDialog = ProgressCommonDialog.showProgressDialog(this, title, message);
    }

    protected void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
        }
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected ProgressDialog showProgress(String msg) {
        return progressDialog = ProgressCommonDialog.showProgressDialog(this, "", msg);
    }

    protected ProgressDialog showProgress(Context context, String msg) {
        return progressDialog = ProgressCommonDialog.showProgressDialog(context, "", msg);
    }

    /* public static boolean checkInternetConnection(Context context) {
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
     }*/
    @Override
    public boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            buildDialog(context);
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
        buildDialog(context);
        return false;
    }

    @Override
    public void buildDialog(final Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection");

        builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

               isConnected(c);
            }
        });
        builder.setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getValApplication().getLoginResponse()!=null && getValApplication().getLoginResponse().getUserDetails()!=null && !TextUtils.isEmpty(getValApplication().getLoginResponse().getUserDetails().getName()))
            tvPowerBy.setText(getValApplication().getLoginResponse().getUserDetails().getName());
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        Fragment oldFragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());

        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit();
        }

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragment, fragment.getClass().getName())
                .commit();

    }


    public ValApplication getValApplication() {
        if (null != getApplication() && getApplication() instanceof ValApplication)
            return (ValApplication) getApplication();
        else
            return null;
    }

    @Override
    public void addToBackStack(Fragment fragment) {

      //  Fragment oldFragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());

       /* if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit();
        }*/

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragment, fragment.getClass().getName())
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void setTitleMessage(String message) {
        toolbar.setTitle(message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public String getIMEI(Context context){
        StringBuilder builder=new StringBuilder();

            TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(this);

            String imsiSIM1 = telephonyInfo.getImsiSIM1();
            String imsiSIM2 = telephonyInfo.getImsiSIM2();


            if (!TextUtils.isEmpty(imsiSIM1)) {
                builder.append("IEMI : " + imsiSIM1);

            }
            if (!TextUtils.isEmpty(imsiSIM2)) {
                builder.append("\n").append("IEMI : " + imsiSIM2);

            }

        return builder.toString();

    }


    @Override
    public void setNfcResumeConnect(Activity context) {

    }

    @Override
    public void setNfcPauseDisconnect(Activity context) {

    }

    @Override
    public void setStartNfc(Activity context) {

    }


    @Override
    public void setFooter(String message, boolean show, final String nextFragmentName) {

        Button nextButton = (Button) findViewById(R.id.nextButton);
        if (show) {
            nextButton.setVisibility(View.VISIBLE);
            TextView textFooter = (TextView) findViewById(R.id.tvFooter);
            textFooter.setText(message);
        } else {
            nextButton.setVisibility(View.GONE);
        }
        btnNext=nextButton;
      /*  nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitch(nextFragmentName);
            }
        });*/

    }

    @Override
    public Button setFooter(String message, boolean show) {
        Button nextButton = (Button) findViewById(R.id.nextButton);
        if (show) {
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText(message);
        } else {
            nextButton.setVisibility(View.GONE);
        }
       btnNext= nextButton;
        return nextButton;
    }


    public void setNavigation() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeAsUpIndicator(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void setNavigationHeader(NavigationView navigationView)
    {
        View header=navigationView.getHeaderView(0);
        TextView tvHotelId=(TextView) header.findViewById(R.id.tvHotelId);
        TextView tvDeviceId=(TextView) header.findViewById(R.id.tvDeviceId);
        TextView tvName=(TextView) header.findViewById(R.id.tvName);
        TextView tvVersion=(TextView) findViewById(R.id.tvVersion);
        TextView tvIEMI=(TextView) findViewById(R.id.tvIEMI);
        if (isPhoneStatusPermissionGranted())
            tvIEMI.setText(getIMEI(this));

       /* }else {
            requestPermission();
        }*/

       // Button btnLogout=(Button) findViewById(R.id.btnLogout);
        ImageView imageView=(ImageView) header.findViewById(R.id.imgBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNavDrawerOpen())
                    closeNavDrawer();

            }
        });
      /*  btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected(getBaseContext()))
                {
                    setLogOut();
                }
            }
        });
*/

        UserDetails details=getValApplication().getLoginResponse().getUserDetails();
        if (details!=null)
        {
            tvHotelId.setText("Hotel Id : " + (details.getHotelid()!=null ? details.getHotelid():""));
            tvDeviceId.setText("Device Id : "+String.valueOf(details.getPhoneid()!=null ? details.getPhoneid():""));
            tvName.setText(details.getUserid()!=null ? details.getName():"");
            tvVersion.setText("Version " + BuildConfig.VERSION_NAME);

        }

    }

    private boolean isPhoneStatusPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted");
                return true;
            } else {

                // Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                return false;
            }

        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    protected void setLogOut()
    {
        HashMap<String,String> params = new HashMap<>();
        showProgress("Logout");
        params.put(Constant.USER_ID,getValApplication().getLoginResponse().getUserDetails().getUserid());
        params.put(Constant.DEVICE_FID,String.valueOf(getValApplication().getLoginResponse().getUserDetails().getDeviceID()));
        RestApiCalls.getLogOut(params).enqueue(new Callback<BaseResponseModel>() {
            @Override
            public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
                hideProgress();
                if (response.isSuccessful()&& response.body()!=null) {
                    BaseResponseModel responseModel = response.body();
                    if (responseModel.getStatus()) {
                        Toast.makeText(BaseActivity.this, ""+responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        getValApplication().getComplexPreference().removeObject(Constant.LOGIN_KEY);
                        startActivity(new Intent(BaseActivity.this,LoginActivity.class));
                        finish();
                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponseModel> call, Throwable t) {
              hideProgress();
                Toast.makeText(BaseActivity.this, ""+call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {

            fragmentManager.popBackStack();


        } else {
                super.onBackPressed();


        }

    }

  /*  @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (fragmentManager.getBackStackEntryCount().size() <= 1) {
           finish();
        } else {
            for (Fragment frag : fragmentManager.getF) {
                if (frag == null) {
                   finish();
                    return;
                }
                if (frag.isVisible()) {
                    FragmentManager childFm = frag.getChildFragmentManager();
                    if (childFm.getFragments() == null) {
                        super.onBackPressed();
                        return;
                    }
                    if (childFm.getBackStackEntryCount() > 0) {
                        childFm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        return;
                    } else {
                        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        return;
                    }
                }
            }
        }
    }*/



    @Override
    public void navigationLockShowBackArrow(boolean NotShow, String titleMessage) {
        setTitleMessage(titleMessage);
        if (NotShow) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            mDrawerToggle.syncState();

        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.syncState();
        }


        // enableViews(NotShow);


    }

    @Override
    public void clearFragment() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean isNavDrawerOpen() {
        return drawer != null && drawer.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}