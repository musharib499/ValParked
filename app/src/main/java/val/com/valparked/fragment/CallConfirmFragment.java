package val.com.valparked.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import val.com.valparked.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallConfirmFragment extends Fragment {
    private static final String ARG_PARAM1 = "vehicleNumber";

    private String vehicleNumber;


    public CallConfirmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallConfirmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallConfirmFragment newInstance(String param1, String param2) {
        CallConfirmFragment fragment = new CallConfirmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call_confirm, container, false);
    }

}
