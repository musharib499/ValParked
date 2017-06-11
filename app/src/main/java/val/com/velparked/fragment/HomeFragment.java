package val.com.velparked.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import val.com.velparked.R;
import val.com.velparked.utils.Constant;

public class HomeFragment extends BaseFragment {
    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // getFragmentAdapter().setFooter("Next",true,Constant.IssueCardFrament);
        if (getArguments() != null) {
          /*  mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentAdapter().navigationLockShowBackArrow(false,getString(R.string.home));
        getFragmentAdapter().setFooter("",false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout llVeliDate= (LinearLayout) view.findViewById(R.id.llValidate);
        LinearLayout llIssueDate= (LinearLayout) view.findViewById(R.id.llIssueCard);
        llVeliDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentAdapter().addToBackStack(NfcRederCardFragment.newInstance("", Constant.CardValidFragment));
            }
        });
        llIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentAdapter().addToBackStack(IssueCardFragment.newInstance());
            }
        });


    }
}
