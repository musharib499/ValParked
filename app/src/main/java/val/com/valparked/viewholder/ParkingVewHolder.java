package val.com.valparked.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import val.com.valparked.R;

/**
 * Created by User on 6/7/2017.
 */

public class ParkingVewHolder extends RecyclerView.ViewHolder {

    public TextView tvCarNo;
    public TextView tvIn;
    public TextView tvOut;
    public TextView tvReq;
    public ImageView imIcon;
    public ParkingVewHolder(View itemView) {
        super(itemView);
        tvCarNo=(TextView)itemView.findViewById(R.id.tvCarNo);
        tvIn=(TextView)itemView.findViewById(R.id.tvIn);
        tvReq=(TextView)itemView.findViewById(R.id.tvReq);
        tvOut=(TextView)itemView.findViewById(R.id.tvOut);
        imIcon=(ImageView)itemView.findViewById(R.id.imIcon);


    }
}
