package val.com.valparked.activity;

import android.os.Bundle;

import val.com.valparked.fragment.NfcRederCardFragment;

public class CardReaderActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       replaceFragment(NfcRederCardFragment.newInstance("",""));

    }



}
