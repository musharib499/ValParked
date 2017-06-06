package val.com.valparked.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import val.com.valparked.R;
import val.com.valparked.application.ValApplication;
import val.com.valparked.fragment.IssueCardFragment;
import val.com.valparked.fragment.NfcRederCardFragment;
import val.com.valparked.intarface.FragmentAdapter;
import val.com.valparked.utils.ProgressCommonDialog;

public class BaseActivity extends AppCompatActivity implements FragmentAdapter, NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawer;
    private ProgressDialog progressDialog;

    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fragmentManager = getSupportFragmentManager();


    }

    protected  void requestProgress(String title,String message){
        progressDialog = ProgressCommonDialog.showProgressDialog(this,title,message);
    }
    protected void hideProgress(){
        if(progressDialog!=null && progressDialog.isShowing()){
            try{
                progressDialog.dismiss();
            }catch(Exception e){e.printStackTrace();}
        }
    }
    protected void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    protected ProgressDialog showProgress(String msg){
        return progressDialog = ProgressCommonDialog.showProgressDialog(this,"",msg);
    }
    protected ProgressDialog showProgress(Context context, String msg){
        return progressDialog = ProgressCommonDialog.showProgressDialog(context,"",msg);
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
@Override
    public void buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

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
        //setNavigation();
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

        Fragment oldFragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());

        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit();
        }

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragment, fragment.getClass().getName())
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void setTitleMessage(String message) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(message);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() >0) {
            fragmentManager.popBackStack();
        }else
       {
           if (this instanceof HomeActivity)
               finish();
               else
           super.onBackPressed();

        }

    }

    @Override
    public void navigationLockShowBackArrow(boolean NotShow, String titleMessage) {
           setTitleMessage(titleMessage);
        if (NotShow) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.syncState();

        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.syncState();
        }


       // enableViews(NotShow);


    }




  /*  private void enableViews(boolean enable) {
        if (enable) {
            // Remove hamburger
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            mDrawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }else {
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