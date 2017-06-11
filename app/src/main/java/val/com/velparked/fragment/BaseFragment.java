package val.com.velparked.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import val.com.velparked.intarface.FragmentAdapter;
import val.com.velparked.utils.ProgressCommonDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    private FragmentAdapter fragmentAdapter;

    private ProgressDialog progressDialog;

    public FragmentAdapter getFragmentAdapter() {
        return fragmentAdapter;
    }

    public BaseFragment setFragmentAdapter(FragmentAdapter fragmentAdapter) {
        this.fragmentAdapter = fragmentAdapter;
        return this;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentAdapter) {
            fragmentAdapter = (FragmentAdapter) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    protected  void requestProgress(String title,String message){
        progressDialog = ProgressCommonDialog.showProgressDialog(getContext(),title,message);
    }
    protected void hideProgress(){
        if(progressDialog!=null && progressDialog.isShowing()){
            try{
                progressDialog.dismiss();
            }catch(Exception e){e.printStackTrace();}
        }
    }
    protected void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
    protected ProgressDialog showProgress(String msg){
        return progressDialog = ProgressCommonDialog.showProgressDialog(getActivity(),"",msg);
    }
    protected ProgressDialog showProgress(Context context, String msg){
        return progressDialog = ProgressCommonDialog.showProgressDialog(context,"",msg);
    }
    public boolean onBackPressed() {
        return false;
    }
}
