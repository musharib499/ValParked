package val.com.valparked.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import val.com.valparked.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ValidConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ValidConfirmFragment extends BaseFragment implements View.OnClickListener {
    private String carNo="";
    private static final String ARG_PARAM1 ="param" ;

    public ValidConfirmFragment() {
        // Required empty public constructor
    }

    public static ValidConfirmFragment newInstance(String param1) {
        ValidConfirmFragment fragment = new ValidConfirmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
        {
            carNo=getArguments().getString(ARG_PARAM1);
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

        textView.setText(carNo);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.llCancel:
                getActivity().onBackPressed();
                break;
            case R.id.llConfirm:
               getFragmentAdapter().clearFragment();
                break;
            default:
                break;
        }

    }
}
