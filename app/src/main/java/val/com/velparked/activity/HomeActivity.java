package val.com.velparked.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

import val.com.velparked.R;
import val.com.velparked.fragment.HomeFragment;
import val.com.velparked.fragment.IssueCardFragment;
import val.com.velparked.fragment.NfcRederCardFragment;
import val.com.velparked.intarface.UpdateUIAdapter;
import val.com.velparked.nfcReader.NfcReader;
import val.com.velparked.utils.Constant;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private PendingIntent mPendingIntent;
    private NdefMessage mNdefPushMessage;
    private Context context = this;
    private DrawerLayout drawer;
    private UpdateUIAdapter uiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigation();
        replaceFragment(HomeFragment.newInstance());

    }

    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NfcRederCardFragment) {
            uiAdapter = (UpdateUIAdapter) fragment;
        }
       /* }else if (fragment instanceof IssueValidCardFragment)
        {
            uiAdapter=(UpdateUIAdapter) fragment;
        }else if (fragment instanceof CallMyCarFragment)
        {
            uiAdapter=(UpdateUIAdapter) fragment;
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(HomeFragment.newInstance());
        } else if (id == R.id.nav_validate_card) {
            replaceFragment(NfcRederCardFragment.newInstance("", Constant.CardValidFragment));
        } else if (id == R.id.nav_issue_card) {
            replaceFragment(IssueCardFragment.newInstance());
        } else if (id == R.id.nav_call_my_car) {
            replaceFragment(NfcRederCardFragment.newInstance("", Constant.CallValidFragment));
        } else if (id == R.id.nav_logout) {
            if (isConnected(this))
               setLogOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTag(String tag) {
        if (!TextUtils.isEmpty(tag))
            uiAdapter.setTagValue(tag);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setNavigation();
        setNavigationHeader(navigationView);

        //navigationView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //navigationView.setVisibility(View.GONE);

    }


    @Override
    public void setNfcPauseDisconnect(Activity context) {
        super.setNfcPauseDisconnect(context);
        if (NfcAdapter.getDefaultAdapter(context) != null) {
            NfcAdapter.getDefaultAdapter(context).disableForegroundDispatch(context);
            NfcAdapter.getDefaultAdapter(context).disableForegroundNdefPush(context);
        }

    }

    @Override
    public void setNfcResumeConnect(Activity context) {
        super.setNfcResumeConnect(context);
        if (NfcAdapter.getDefaultAdapter(context) != null) {
            if (!NfcAdapter.getDefaultAdapter(context).isEnabled()) {
                showWirelessSettingsDialog();
            }
            NfcAdapter.getDefaultAdapter(context).enableForegroundDispatch(context, mPendingIntent, null, null);
            NfcAdapter.getDefaultAdapter(context).enableForegroundNdefPush(context, mNdefPushMessage);
        }
    }

  /*  private void showMessage(int title, int message) {
        mDialog.setTitle(title);
        mDialog.setMessage(getText(message));
        mDialog.show();
    }*/

    private void showWirelessSettingsDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.nfc_disabled);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                startNfcSettingsActivity();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
        return;
    }

    protected void startNfcSettingsActivity() {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
        } else {
            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    @Override
    public void setStartNfc(Activity context) {
        super.setStartNfc(context);

        NfcReader.getInstance(this).resolveIntent(getIntent());
     AlertDialog   mDialog = new AlertDialog.Builder(context).setNeutralButton("Ok", null).create();

        if (NfcAdapter.getDefaultAdapter(context) == null) {
            mDialog.setTitle(R.string.error);
            mDialog.setMessage(getText(R.string.no_nfc));
            mDialog.show();
            //Toast.makeText(this, R.string.no_nfc, Toast.LENGTH_SHORT).show();
            //finish();
            return;
        }
        mPendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        mNdefPushMessage = new NdefMessage(new NdefRecord[]{NfcReader.getInstance(this).newTextRecord(
                "Message from NFC Reader :-)", Locale.ENGLISH, true)});
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        NfcReader.getInstance(this).resolveIntent(intent);
    }
}
