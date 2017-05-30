package val.com.valparked.activity;

import android.os.Bundle;

import val.com.valparked.R;
import val.com.valparked.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(HomeFragment.newInstance());
    }
}
