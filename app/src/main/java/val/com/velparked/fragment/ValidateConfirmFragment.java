package val.com.velparked.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import val.com.velparked.R;
import val.com.velparked.model.ConfirmValidate;
import val.com.velparked.retrofit.RestApiCalls;
import val.com.velparked.utils.Constant;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ValidateConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ValidateConfirmFragment extends BaseFragment implements View.OnClickListener {
    private String carNo="";
    private static final String ARG_PARAM_NUMBER = "vehicleNumber";
    private static final String ARG_PARAM_NFC = "nfc";

    private String vehicleNumber;
    private String nfc;
    public ValidateConfirmFragment() {
        // Required empty public constructor
    }

    public static ValidateConfirmFragment newInstance(String nfc , String vehicleNumber) {
        ValidateConfirmFragment fragment = new ValidateConfirmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_NUMBER, vehicleNumber);
        args.putString(ARG_PARAM_NFC, nfc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
        {
            vehicleNumber = getArguments().getString(ARG_PARAM_NUMBER);
            nfc = getArguments().getString(ARG_PARAM_NFC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_valid_confirm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView=(TextView) view.findViewById(R.id.tvCarNo);
        ((LinearLayout) view.findViewById(R.id.llCancel)).setOnClickListener(this);
        ((LinearLayout) view.findViewById(R.id.llConfirm)).setOnClickListener(this);

        textView.setText(vehicleNumber);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.llCancel:
                getActivity().onBackPressed();
                break;
            case R.id.llConfirm:
              if (getFragmentAdapter().isConnected(getActivity()))
                  setConfirmValidate(nfc);
                break;
            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentAdapter().setFooter("",false);
    }

    public void setConfirmValidate(final String nfc) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.NCF_ID, nfc);
        showProgress(getActivity(), "Please wait....");
        RestApiCalls.getConfirmValidate(params).enqueue(new Callback<ConfirmValidate>() {
            @Override
            public void onResponse(Call<ConfirmValidate> call, Response<ConfirmValidate> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    ConfirmValidate confirmCallVehicle = response.body();
                    Log.e("CallMyCar", response.toString());
                    if (confirmCallVehicle.getStatus()) {


                        if ( confirmCallVehicle.getCardInfo()!=null && !TextUtils.isEmpty(confirmCallVehicle.getCardInfo().getVehicalNumber()))
                            getFragmentAdapter().clearFragment();

                    } else {
                        Toast.makeText(getActivity(), confirmCallVehicle.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(TAG, response.message());
                    Toast.makeText(getActivity(), "" + response.message().toString(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ConfirmValidate> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), "" + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
