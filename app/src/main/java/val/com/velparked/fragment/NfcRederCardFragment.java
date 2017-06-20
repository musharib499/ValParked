package val.com.velparked.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import val.com.velparked.R;
import val.com.velparked.intarface.UpdateUIAdapter;
import val.com.velparked.model.CallMyCar;
import val.com.velparked.model.Login;
import val.com.velparked.model.ValidCardInfo;
import val.com.velparked.retrofit.RestApiCalls;
import val.com.velparked.utils.Constant;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NfcRederCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NfcRederCardFragment extends BaseFragment implements UpdateUIAdapter {
    private static final String ARG_NUMBER = "number";
    private static final String ARG_Type = "type";
    private TextView tvCarNo, tvShow;
    private TextView tvError;
    private String type = "";
    private String number = "";
    private Button btnNext;

    public NfcRederCardFragment() {
        // Required empty public constructor
    }

    public static NfcRederCardFragment newInstance(String number, String type) {
        NfcRederCardFragment fragment = new NfcRederCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NUMBER, number);
        args.putString(ARG_Type, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentAdapter().setStartNfc(getActivity());
        if (getArguments() != null) {
            number = getArguments().getString(ARG_NUMBER);
            type = getArguments().getString(ARG_Type);
        }


    }


    private void issueView(View view) {
        tvShow = (TextView) view.findViewById(R.id.tvShow);
        tvCarNo = (TextView) view.findViewById(R.id.tvCarNo);
        tvCarNo.setText(number);
        tvShow.setText(getResources().getString(R.string.show_card_here));
        //tvShow.setTextSize(getResources().getDimension(R.dimen.text_size_small));
        tvShow.setTextColor(ContextCompat.getColor(getActivity(), R.color.error_color));
        tvError = (TextView) view.findViewById(R.id.tvError);
        btnNext = getFragmentAdapter().setFooter(getString(R.string.park_vehicle), false);
        // btnNext.setEnabled(false);

    }

    private void callView(View view) {
        tvShow = (TextView) view.findViewById(R.id.tvShow);
        tvCarNo = (TextView) view.findViewById(R.id.tvCarNo);
        tvCarNo.setText(R.string.call_car);
        tvShow.setText(getResources().getString(R.string.show_valet_card));
        tvError = (TextView) view.findViewById(R.id.tvError);
        btnNext = getFragmentAdapter().setFooter(getString(R.string.next), true);
        btnNext.setEnabled(false);
    }

    private void validView(View view) {
        tvShow = (TextView) view.findViewById(R.id.tvShow);
        tvCarNo = (TextView) view.findViewById(R.id.tvCarNo);
        tvCarNo.setVisibility(View.GONE);
        tvShow.setText(getResources().getString(R.string.show_card));
        tvError = (TextView) view.findViewById(R.id.tvError);
        btnNext = getFragmentAdapter().setFooter(getString(R.string.next), true);
        btnNext.setEnabled(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_card_valid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (type.equalsIgnoreCase(Constant.CardValidFragment))
            validView(view);
        else if (type.equalsIgnoreCase(Constant.IssueCardFragment))
            issueView(view);
        else if (type.equalsIgnoreCase(Constant.CallValidFragment))
            callView(view);


    }


    @Override
    public void onResume() {
        super.onResume();
        if (type.equalsIgnoreCase(Constant.CardValidFragment))
            getFragmentAdapter().navigationLockShowBackArrow(false, getString(R.string.validate));
        else if (type.equalsIgnoreCase(Constant.IssueCardFragment))
            getFragmentAdapter().navigationLockShowBackArrow(true, getString(R.string.issue_card));
        else if (type.equalsIgnoreCase(Constant.CallValidFragment))
            getFragmentAdapter().navigationLockShowBackArrow(false, getString(R.string.call_my_car));


        getFragmentAdapter().setNfcResumeConnect(getActivity());

    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentAdapter().setNfcPauseDisconnect(getActivity());

    }

    @Override
    public String getTagValue() {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (btnNext != null)
            btnNext.setEnabled(true);
    }

    @Override
    public void setTagValue(String tagValue) {
        if (getActivity() != null)
            if (tagValue != null) {
                Log.e(TAG, tagValue);
                if (!getFragmentAdapter().isConnected(getActivity())) {
                    getFragmentAdapter().buildDialog(getActivity());
                } else {
                    if (type.equalsIgnoreCase(Constant.CardValidFragment))
                        setValid(tagValue);
                    else if (type.equalsIgnoreCase(Constant.IssueCardFragment) && !TextUtils.isEmpty(number)) {
                        setIssueValid(tagValue, number);
                    } else if (type.equalsIgnoreCase(Constant.CallValidFragment)) {
                        setCallValid(tagValue);
                    }
                }

                // tvError.setText("");
            } else {
                tvError.setText(getResources().getString(R.string.card_not_found));
            }
    }

    public void setValid(final String nfc) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.NCF_ID, nfc);
        showProgress(getActivity(), "Please wait........");
        RestApiCalls.getValidCard(params).enqueue(new Callback<ValidCardInfo>() {
            @Override
            public void onResponse(Call<ValidCardInfo> call, Response<ValidCardInfo> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    ValidCardInfo validCardInfo = response.body();
                    Log.e("VALID", response.toString());

                    if (validCardInfo.getStatus() && validCardInfo.cardInfo != null) {

                        if (validCardInfo.cardInfo.getVehicalNumber() != null) {
                            btnNext.setEnabled(true);
                            final String vehicleNo = validCardInfo.cardInfo.getVehicalNumber();
                            btnNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getFragmentAdapter().addToBackStack(ValidateConfirmFragment.newInstance(nfc, vehicleNo));

                                }
                            });
                        }

                        tvError.setText("");
                    } else {
                        //  Toast.makeText(getActivity(), validCardInfo.getMessage(), Toast.LENGTH_LONG).show();
                        tvError.setText(R.string.card_not_found);
                    }
                } else {
                    Log.e(TAG, response.message());
                    tvError.setText(response.message());

                }

            }

            @Override
            public void onFailure(Call<ValidCardInfo> call, Throwable t) {
                hideProgress();
                tvError.setText("Something is wrong on server!");

            }
        });


    }

    public void setCallValid(final String nfc) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.NCF_ID, nfc);
        showProgress(getActivity(), "Please wait....");
        RestApiCalls.getCallVehicle(params).enqueue(new Callback<CallMyCar>() {
            @Override
            public void onResponse(Call<CallMyCar> call, Response<CallMyCar> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    CallMyCar callMyCar = response.body();
                    Log.e("CallMyCar", response.toString());
                    if (callMyCar.getStatus()) {


                        if (!TextUtils.isEmpty(callMyCar.vehicalInfo.vehicalNumber)) {
                            btnNext.setEnabled(true);
                            final String vehicleNo = callMyCar.vehicalInfo.vehicalNumber;
                            btnNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getFragmentAdapter().addToBackStack(CallConfirmFragment.newInstance(nfc, vehicleNo));

                                }
                            });

                        } else tvError.setText("");


                    } else {
                        tvError.setText(R.string.card_not_found);
                    }
                } else {
                    tvError.setText(response.message());

                }

            }

            @Override
            public void onFailure(Call<CallMyCar> call, Throwable t) {
                hideProgress();
                tvError.setText("Something is wrong on server!");
            }
        });


    }

    public void setIssueValid(String nfc, final String vehicle) {
        Login login = getFragmentAdapter().getValApplication().getLoginResponse();
        showProgress("Checking......");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, login.getUserDetails().getUserid());
        params.put(Constant.VEHICLE_ID, vehicle);
        params.put(Constant.NCF_ID, nfc);
        params.put(Constant.DEVICE_FID, login.getUserDetails().getDeviceID().toString());
        Log.e("nfc", params.toString());

        RestApiCalls.getIssueCard(params).enqueue(new Callback<ValidCardInfo>() {
            @Override
            public void onResponse(Call<ValidCardInfo> call, Response<ValidCardInfo> response) {
                hideProgress();
                ValidCardInfo validCardInfo = response.body();
                Log.e("Issue", response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    if (validCardInfo.getStatus()) {

                        //   if (validCardInfo.cardInfo.getVehicalNumber()!=null)
                     /*   btnNext.setEnabled(true);
                        btnNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getFragmentAdapter().addToBackStack(IssueCardConfirmFragment.newInstance(vehicle));
                            }
                        });*/
                        getFragmentAdapter().addToBackStack(IssueCardConfirmFragment.newInstance(vehicle));


                        tvError.setText(validCardInfo.getMessage());

                    } else {
                        tvError.setText(R.string.card_not_found);
                    }
                } else {
                    Log.e(TAG, response.message());
                    tvError.setText(response.message());


                }

            }

            @Override
            public void onFailure(Call<ValidCardInfo> call, Throwable t) {
                hideProgress();
                tvError.setText("Something is wrong on server!");
            }
        });


    }

}
