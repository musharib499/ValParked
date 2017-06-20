package val.com.velparked.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

import val.com.velparked.R;


/**
 * Created by Mushareb Ali on 7/6/16.
 */
public class ProgressCommonDialog {


    public static ProgressDialog showProgressDialog(Context context, String title, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        //  progressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)));
        // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ProgressBar progressBar = (ProgressBar) progressDialog.findViewById(R.id.progressBar);

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);

        return progressDialog;


    }

    public static ProgressDialog progressDialog(Context context, String title, String message)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        //  progressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)));
        // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ProgressBar progressBar = (ProgressBar) progressDialog.findViewById(R.id.progressBar);

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);

        return progressDialog;
    }


}
