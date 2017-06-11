package val.com.velparked.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import val.com.velparked.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThanksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThanksFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "vehicleNumber";
    private String vehicleNumber;

    public ThanksFragment() {
        // Required empty public constructor
    }

    public static ThanksFragment newInstance(String vehicleNumber) {
        ThanksFragment fragment = new ThanksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, vehicleNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vehicleNumber = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentAdapter().navigationLockShowBackArrow(true,"Thanks");
        getFragmentAdapter().setFooter("",false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thanks, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvCarNo= (TextView) view.findViewById(R.id.tvCarNo);
        ((Button)view.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentAdapter().clearFragment();
            }
        });
        if (!TextUtils.isEmpty(vehicleNumber))
            tvCarNo.setText(vehicleNumber);
    }
}
