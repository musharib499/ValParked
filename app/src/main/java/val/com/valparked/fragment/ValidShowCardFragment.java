package val.com.valparked.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import val.com.valparked.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ValidShowCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ValidShowCardFragment extends BaseFragment {

    public ValidShowCardFragment() {
        // Required empty public constructor
    }
    public static ValidShowCardFragment newInstance() {
        ValidShowCardFragment fragment = new ValidShowCardFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentAdapter().setTitleMessage("ValiDate Card");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vali_date_card, container, false);
    }

}
