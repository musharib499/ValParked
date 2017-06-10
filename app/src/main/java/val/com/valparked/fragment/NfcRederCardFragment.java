package val.com.valparked.fragment;

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
import val.com.valparked.R;
import val.com.valparked.intarface.UpdateUIAdapter;
import val.com.valparked.model.CallMyCar;
import val.com.valparked.model.Login;
import val.com.valparked.model.ValidCardInfo;
import val.com.valparked.retrofit.RestApiCalls;
import val.com.valparked.utils.Constant;

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
        tvShow.setText(getResources().getString(R.string.show_card));
        tvError = (TextView) view.findViewById(R.id.tvError);
    }

    private void callView(View view) {
        tvShow = (TextView) view.findViewById(R.id.tvShow);
        tvCarNo = (TextView) view.findViewById(R.id.tvCarNo);
        tvCarNo.setText(R.string.call_car);
        tvShow.setText(getResources().getString(R.string.show_valet_card));
        tvError = (TextView) view.findViewById(R.id.tvError);
    }

    private void validView(View view) {
        tvShow = (TextView) view.findViewById(R.id.tvShow);
        tvShow.setText(getResources().getString(R.string.show_card));
        tvError = (TextView) view.findViewById(R.id.tvError);

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
        LinearLayout llShowCard = (LinearLayout) view.findViewById(R.id.llShowCard);
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
            getFragmentAdapter().navigationLockShowBackArrow(true, "Validate Card");
        else if (type.equalsIgnoreCase(Constant.IssueCardFragment))
            getFragmentAdapter().navigationLockShowBackArrow(true, "Issue Card");
        else if (type.equalsIgnoreCase(Constant.CallValidFragment))
            getFragmentAdapter().navigationLockShowBackArrow(false, "Call My Car");


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

    public void setValid(String nfc) {
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

                        if (validCardInfo.cardInfo.getVehicalNumber() != null)
                            getFragmentAdapter().addToBackStack(ValidConfirmFragment.newInstance(validCardInfo.cardInfo.getVehicalNumber()));

                        tvError.setText("");
                    } else {
                        Toast.makeText(getActivity(), validCardInfo.getMessage(), Toast.LENGTH_LONG).show();
                        tvError.setText(R.string.card_not_found);
                    }
                } else {
                    Log.e(TAG, response.message());
                    tvError.setText(R.string.card_not_found);

                }

            }

            @Override
            public void onFailure(Call<ValidCardInfo> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
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


                        if (!TextUtils.isEmpty(callMyCar.vehicalInfo.vehicalNumber))
                            getFragmentAdapter().addToBackStack(CallConfirmFragment.newInstance(callMyCar.vehicalInfo.vehicalNumber));
                        tvError.setText("");


                    } else {
                        Toast.makeText(getActivity(), callMyCar.getMessage(), Toast.LENGTH_LONG).show();
                        tvError.setText(R.string.card_not_found);
                    }
                } else {
                    Log.e(TAG, response.message());
                    Toast.makeText(getActivity(), "" + response.message().toString(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CallMyCar> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), "" + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setIssueValid(String nfc, final String vehicle) {
        Login login = getFragmentAdapter().getValApplication().getLoginResponse();
        showProgress("Checking......");
        HashMap<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, login.getUserDetails().getUserid());
        params.put(Constant.VEHICAL_ID, vehicle);
        params.put(Constant.NCF_ID, nfc);
        params.put(Constant.DEVICE_FID, login.getUserDetails().getDeviceID().toString());
        Log.e("nfc", params.toString());

        RestApiCalls.getIssueCard(params).enqueue(new Callback<ValidCardInfo>() {
            @Override
            public void onResponse(Call<ValidCardInfo> call, Response<ValidCardInfo> response) {
                hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    ValidCardInfo validCardInfo = response.body();
                    Log.e("Issue", response.toString());
                    if (validCardInfo.getStatus()) {

                        //   if (validCardInfo.cardInfo.getVehicalNumber()!=null)
                        getFragmentAdapter().addToBackStack(IssueCardConfirmFragment.newInstance(vehicle));

                        tvError.setText(validCardInfo.getMessage());

                    } else {
                        Toast.makeText(getActivity(), validCardInfo.getMessage(), Toast.LENGTH_LONG).show();
                        tvError.setText(R.string.card_not_found);
                    }
                } else {
                    Log.e(TAG, response.message());
                    Toast.makeText(getActivity(), "" + response.message().toString(), Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<ValidCardInfo> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), "" + call.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
