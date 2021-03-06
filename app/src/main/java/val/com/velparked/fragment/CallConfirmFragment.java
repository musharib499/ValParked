package val.com.velparked.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import val.com.velparked.R;
import val.com.velparked.model.CallMyCar;
import val.com.velparked.model.ConfirmCallVehicle;
import val.com.velparked.model.ConfirmValidate;
import val.com.velparked.retrofit.RestApiCalls;
import val.com.velparked.utils.Constant;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallConfirmFragment extends BaseFragment {
    private static final String ARG_PARAM_NUMBER = "vehicleNumber";
    private static final String ARG_PARAM_NFC = "nfc";

    private String vehicleNumber;
    private String nfc;


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
    public static CallConfirmFragment newInstance(String nfc, String vehicleNumber) {
        CallConfirmFragment fragment = new CallConfirmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_NUMBER, vehicleNumber);
        args.putString(ARG_PARAM_NFC, nfc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vehicleNumber = getArguments().getString(ARG_PARAM_NUMBER);
            nfc = getArguments().getString(ARG_PARAM_NFC);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentAdapter().navigationLockShowBackArrow(true, "Confirm");
        getFragmentAdapter().setFooter("",false);
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

        TextView tvCarNo = (TextView) view.findViewById(R.id.tvCarNo);
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        if (!TextUtils.isEmpty(vehicleNumber))
            tvCarNo.setText(vehicleNumber);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(nfc)) {
                    if (getFragmentAdapter().isConnected(getActivity()))
                        setConfirmCallValid(nfc);
                } else
                    Toast.makeText(getActivity(), "Please go back again scan card  ", Toast.LENGTH_LONG).show();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    public void setConfirmCallValid(final String nfc) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.NCF_ID, nfc);
        showProgress(getActivity(), "Please wait....");
        RestApiCalls.getConfirmCallMyCar(params).enqueue(new Callback<ConfirmCallVehicle>() {
            @Override
            public void onResponse(Call<ConfirmCallVehicle> call, Response<ConfirmCallVehicle> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    ConfirmCallVehicle confirmCallVehicle = response.body();
                    Log.e("CallMyCar", response.toString());
                    if (confirmCallVehicle.getStatus()) {


                        if (confirmCallVehicle.getVehicalInfo() != null && !TextUtils.isEmpty(confirmCallVehicle.getVehicalInfo().getVehicalNumber()))
                            getFragmentAdapter().addToBackStack(ThanksFragment.newInstance(confirmCallVehicle.getVehicalInfo().getVehicalNumber()));


                    } else {
                        Toast.makeText(getActivity(), confirmCallVehicle.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(TAG, response.message());
                    Toast.makeText(getActivity(), "" + response.message().toString(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ConfirmCallVehicle> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), "" + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
