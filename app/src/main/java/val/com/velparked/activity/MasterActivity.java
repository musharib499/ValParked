package val.com.velparked.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import val.com.velparked.R;
import val.com.velparked.adapter.BaseAdapter;
import val.com.velparked.fragment.HomeFragment;
import val.com.velparked.fragment.IssueCardFragment;
import val.com.velparked.fragment.NfcRederCardFragment;
import val.com.velparked.intarface.NotificationInterface;
import val.com.velparked.model.Login;
import val.com.velparked.model.Parking;
import val.com.velparked.model.ParkingInfo;
import val.com.velparked.retrofit.RestApiCalls;
import val.com.velparked.utils.Constant;
import val.com.velparked.utils.Utils;
import val.com.velparked.viewholder.ParkingVewHolder;

public class MasterActivity extends BaseActivity  implements BaseAdapter.BindAdapterListener<ParkingVewHolder>,NotificationInterface{

    public List<ParkingInfo> getParking() {
        return parking;
    }

    public void setParking(List<ParkingInfo> parking) {
        this.parking = parking;
    }

    private List<ParkingInfo> parking=null;
    private RecyclerView recyclerView;
    private TextView emptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getValApplication().setUpFCM();
        getValApplication().initializeFcmToken();
        getLayoutInflater().inflate(R.layout.activity_master,frameLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        emptyView = (TextView) findViewById(R.id.empty_view);

        if (isConnected(this))
        {
            setLoadParking();
        }
        getValApplication().initializeFcmToken();
    }
    public void setData() {
        Utils.recyclerView(recyclerView, this, true).setAdapter( new BaseAdapter<ParkingInfo,ParkingVewHolder>(this, getParking(), this, ParkingVewHolder.class, R.layout.parking_item));
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTitleMessage("Parking");
        setNavigationHeader(navigationView);
        setNavigation();
        if (getParking()!=null)
            setData();


        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(Constant.FCM_MASTER));
    }



    @Override
    public void onBind(ParkingVewHolder holder, int position) {
       if (!TextUtils.isEmpty(getParking().get(position).getVehicalNumber()))
        holder.tvCarNo.setText(getParking().get(position).getVehicalNumber());

        if (!TextUtils.isEmpty(getParking().get(position).getStatus()) && getParking().get(position).getStatus().equalsIgnoreCase("Requested"))
            holder.tvCarNo.setTextColor(getResources().getColor(R.color.red));

        if (!TextUtils.isEmpty(getParking().get(position).getParkTime()))
        holder.tvIn.setText(setColorSpan(getString(R.string.in),(getParking().get(position).getParkTime()!=null? getParking().get(position).getParkTime() :"...")), TextView.BufferType.SPANNABLE);

        if (!TextUtils.isEmpty(getParking().get(position).getRequestTime()))
        holder.tvReq.setText(setColorSpan(getString(R.string.req),(getParking().get(position).getRequestTime()!=null? getParking().get(position).getRequestTime() :"...")), TextView.BufferType.SPANNABLE);

        if (!TextUtils.isEmpty(getParking().get(position).getPickedTime()))
         holder.tvOut.setText(setColorSpan(getString(R.string.out),(getParking().get(position).getPickedTime()!=null? getParking().get(position).getPickedTime() :"...")), TextView.BufferType.SPANNABLE);

        if (!TextUtils.isEmpty(getParking().get(position).getStatus())) {
            if (getParking().get(position).getStatus().equalsIgnoreCase("Parked"))
            holder.imIcon.setImageResource(R.drawable.ic_right);
            else if (getParking().get(position).getStatus().equalsIgnoreCase("Requested"))
                holder.imIcon.setImageResource(R.drawable.ic_right_request);
            else if (getParking().get(position).getStatus().equalsIgnoreCase("Process"))
                holder.imIcon.setImageResource(R.drawable.ic_process);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void setLoadParking()
    {
        Login login=getValApplication().getLoginResponse();
        showProgress("Parking Loading....");
        HashMap<String,String> params = new HashMap<>();
        params.put(Constant.USER_ID,"1"/*login.getUserDetails().getUserid()*/);
        RestApiCalls.getParking(params).enqueue(new Callback<Parking>() {
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                hideProgress();

                if (response.isSuccessful() && response.body()!=null)
                {
                    Parking parking=response.body();
                    if (parking.getStatus() && parking.getParkingInfo().size()!=0)
                    {
                       if (getParking()!=null && getParking().size()>0 )
                       {
                           getParking().clear();
                       }
                        setParking(parking.getParkingInfo());
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        setData();



                    }else {
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);

                    }

                }else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    Toast.makeText(MasterActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                hideProgress();
                Toast.makeText(MasterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Spanned getColoredSpanned(String text) {
        String input = "<font color=" + R.color.gray + ">" + text + "</font>";
        return Html.fromHtml( "<![CDATA[<font color='#145A14'> + text + </font>]]>");
    }
    public Spannable setColor(String s)
    {
        Spannable wordtoSpan = new SpannableString(s);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       return wordtoSpan;
    }

    private SpannableStringBuilder setColorSpan(String s,String s1)
    {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString redSpannable= new SpannableString(s);
        redSpannable.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        builder.append(redSpannable);
        SpannableString whiteSpannable= new SpannableString(s1);
        whiteSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s1.length(), 0);
        builder.append(whiteSpannable);
        return builder;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        navigationView.getMenu().setGroupVisible(R.id.group,false);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_logout:
                if (isConnected(this))
                    setLogOut();

                return true;
           /* case android.R.id.home:
               onBackPressed();
                return true;*/
                default:
                    return  super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

     /*   if (id == R.id.nav_home) {
            replaceFragment(HomeFragment.newInstance());
        } else if (id == R.id.nav_validate_card) {
            replaceFragment(NfcRederCardFragment.newInstance("", Constant.CardValidFragment));
        } else if (id == R.id.nav_issue_card) {
            replaceFragment(IssueCardFragment.newInstance());
        } else if (id == R.id.nav_call_my_car) {
            replaceFragment(NfcRederCardFragment.newInstance("", Constant.CallValidFragment));
        } else*/ if (id == R.id.nav_logout) {
            if (isConnected(this))
                setLogOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void notify(String s) {

        if (isConnected(this))
        {
            setLoadParking();
        }

    }


    public BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isConnected(MasterActivity.this))
            {
                setLoadParking();
            }
        }
    };

}
