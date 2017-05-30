package val.com.valparked.activity;

import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import val.com.valparked.R;
import val.com.valparked.fragment.HomeFragment;
import val.com.valparked.fragment.IssueCardFragment;
import val.com.valparked.fragment.IssueCardValidFragment;
import val.com.valparked.utils.Constant;
import val.com.valparked.utils.FragmentAdapter;

import static val.com.valparked.utils.Constant.*;

public class BaseActivity extends AppCompatActivity implements FragmentAdapter ,NavigationView.OnNavigationItemSelectedListener{
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
         toolbar = (Toolbar) findViewById(R.id.toolbar);

        fragmentManager=getSupportFragmentManager();

    }






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
        toolbar.setTitle(message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public void setFooter(String message, boolean show, final String nextFragmentName) {

        FrameLayout flFooter=(FrameLayout)findViewById(R.id.flFooter);
        if (show) {
            flFooter.setVisibility(View.VISIBLE);
            TextView textFooter = (TextView) findViewById(R.id.tvFooter);
            textFooter.setText(message);
        }else {
            flFooter.setVisibility(View.GONE);
        }
        flFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSwitch(nextFragmentName);
            }
        });

    }

    @Override
    public void setFooter(String message, boolean show) {
        FrameLayout flFooter=(FrameLayout)findViewById(R.id.flFooter);
        if (show) {
            flFooter.setVisibility(View.VISIBLE);
            TextView textFooter = (TextView) findViewById(R.id.tvFooter);
            textFooter.setText(message);
        }else {
            flFooter.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNavigation() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void fragmentSwitch(String nextFragmentName) {
        switch (nextFragmentName) {
            case "IssueCardFragment":
                replaceFragment(IssueCardFragment.newInstance());
                break;
            case "IssueCardValidFragment":
                replaceFragment(IssueCardValidFragment.newInstance("Card Id"));
                break;
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
        super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(HomeFragment.newInstance());
        } else if (id == R.id.nav_validate_card) {

            replaceFragment(HomeFragment.newInstance());

        } else if (id == R.id.nav_issue_card) {

            replaceFragment(IssueCardFragment.newInstance());
        } else if (id == R.id.nav_call_my_car) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
