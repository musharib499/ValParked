package val.com.velparked.activity;

import android.os.Bundle;

import val.com.velparked.fragment.NfcRederCardFragment;

public class CardReaderActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       replaceFragment(NfcRederCardFragment.newInstance("",""));

    }



}
