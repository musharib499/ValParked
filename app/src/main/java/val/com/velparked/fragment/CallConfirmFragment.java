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
import android.widget.Toast;

import val.com.velparked.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallConfirmFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "vehicleNumber";

    private String vehicleNumber;


    public CallConfirmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param vehicleNumber Parameter 1.

     * @return A new instance of fragment CallConfirmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallConfirmFragment newInstance(String vehicleNumber) {
        CallConfirmFragment fragment = new CallConfirmFragment();
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
        getFragmentAdapter().navigationLockShowBackArrow(true,"Confirm");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call_confirm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvCarNo= (TextView) view.findViewById(R.id.tvCarNo);
        Button btnConfirm= (Button) view.findViewById(R.id.btnConfirm);
        Button btnCancel= (Button) view.findViewById(R.id.btnCancel);

        if (!TextUtils.isEmpty(vehicleNumber))
        tvCarNo.setText(vehicleNumber);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(vehicleNumber))
                getFragmentAdapter().addToBackStack(ThanksFragment.newInstance(vehicleNumber));
                else Toast.makeText(getActivity(),"Not available Vehicle No ",Toast.LENGTH_LONG).show();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

}
