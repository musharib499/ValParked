package val.com.valparked.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import val.com.valparked.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssueCardConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssueCardConfirmFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;


    public IssueCardConfirmFragment() {
        // Required empty public constructor
    }

    public static IssueCardConfirmFragment newInstance(String param1) {
        IssueCardConfirmFragment fragment = new IssueCardConfirmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentAdapter().navigationLockShowBackArrow(true, "Validate Card");
        getFragmentAdapter().setFooter("PARKING DONE", true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentAdapter().clearFragment();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issue_card_confirm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.tvCarNo);
        textView.setText(mParam1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentAdapter().clearFragment();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressed() {
        super.onBackPressed();
        return getFragmentAdapter().isConnected(getActivity());
    }
}
