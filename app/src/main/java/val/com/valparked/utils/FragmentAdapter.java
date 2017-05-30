package val.com.valparked.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Musharib on 27/05/17.
 */
public interface FragmentAdapter {
    void replaceFragment(Fragment fragment);
    void addToBackStack(Fragment fragment);
    void setTitleMessage(String message);
    void setFooter(String message,boolean show,String nextFragmentName);
    void setFooter(String message,boolean show);
    void setNavigation();



}
