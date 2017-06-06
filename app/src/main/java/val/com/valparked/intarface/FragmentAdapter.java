package val.com.valparked.intarface;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import val.com.valparked.application.ValApplication;

/**
 * Created by Musharib on 27/05/17.
 */
public interface FragmentAdapter {
    void replaceFragment(Fragment fragment);
    void addToBackStack(Fragment fragment);
    void setTitleMessage(String message);
    void setFooter(String message,boolean show,String nextFragmentName);
    Button setFooter(String message, boolean show);
    void setNfcResumeConnect(Activity connect);
    void setNfcPauseDisconnect(Activity context);
    void setStartNfc(Activity context);
    boolean isConnected(Context context);
    void buildDialog(Context context);
    ValApplication getValApplication();
    void navigationLockShowBackArrow(boolean NotShow, String titleMessage);
}
