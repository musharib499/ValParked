package val.com.valparked.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import val.com.valparked.R;
import val.com.valparked.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssueCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssueCardFragment extends BaseFragment {
    public IssueCardFragment() {
        // Required empty public constructor
    }

    public static IssueCardFragment newInstance() {
        IssueCardFragment fragment = new IssueCardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentAdapter().setTitleMessage("Issue Card");
        getFragmentAdapter().setFooter("NEXT",true, Constant.IssueCardValidFrament);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_issue_card, container, false);
    }

}
