package val.com.velparked.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import val.com.velparked.R;

/**
 * Created by User on 6/7/2017.
 */

public class ParkingVewHolder extends RecyclerView.ViewHolder {

    public TextView tvCarNo;
    public TextView tvIn;
    public TextView tvOut;
    public TextView tvReq;
    public ImageView imIcon;
    public LinearLayout llPopupView;
    public Button btnOk;
    public ParkingVewHolder(View itemView) {
        super(itemView);
        tvCarNo=(TextView)itemView.findViewById(R.id.tvCarNo);
        tvIn=(TextView)itemView.findViewById(R.id.tvIn);
        tvReq=(TextView)itemView.findViewById(R.id.tvReq);
        tvOut=(TextView)itemView.findViewById(R.id.tvOut);
        imIcon=(ImageView)itemView.findViewById(R.id.imIcon);
        btnOk=(Button)itemView.findViewById(R.id.btnOk);
        llPopupView=(LinearLayout)itemView.findViewById(R.id.llPopupView);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPopupView.setVisibility(View.GONE);
            }
        });


    }
}
