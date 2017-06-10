package val.com.valparked.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import val.com.valparked.R;
import val.com.valparked.adapter.BaseAdapter;
import val.com.valparked.model.BaseResponseModel;
import val.com.valparked.model.Login;
import val.com.valparked.model.Parking;
import val.com.valparked.model.ParkingInfo;
import val.com.valparked.retrofit.RestApiCalls;
import val.com.valparked.utils.Constant;
import val.com.valparked.utils.Utils;
import val.com.valparked.viewholder.ParkingVewHolder;

public class MasterActivity extends BaseActivity  implements BaseAdapter.BindAdapterListener<ParkingVewHolder>{

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
        getLayoutInflater().inflate(R.layout.activity_master,frameLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        emptyView = (TextView) findViewById(R.id.empty_view);

        if (isConnected(this))
        {
            setLoadParking();
        }
    }
    public void setData() {
        Utils.recyclerView(recyclerView, this, true).setAdapter( new BaseAdapter<ParkingInfo,ParkingVewHolder>(this, getParking(), this, ParkingVewHolder.class, R.layout.parking_item));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitleMessage("Parking");
        if (getParking()!=null)
            setData();
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
            case android.R.id.home:
               onBackPressed();
                return true;
                default:
                    return  super.onOptionsItemSelected(item);
        }

    }
}