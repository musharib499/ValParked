package val.com.valparked.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_issue_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editText=(EditText) view.findViewById(R.id.etCarNo);
        getFragmentAdapter().setFooter("NEXT",true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    getFragmentAdapter().addToBackStack(NfcRederCardFragment.newInstance(editText.getText().toString(), Constant.IssueCardFragment));
                }else {
                    editText.setError("Please enter car Number ");
                }
            }
        });

    }
}
