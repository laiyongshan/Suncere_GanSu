package suncere.gansu.androidapp.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import suncere.gansu.androidapp.R;


/**
 * Created by HJO on 2018/1/4.
 */
public class LoginProgressDialog extends AbstractBaseDialog {
    private View mRootView;
    private TextView login_progressBar_tv;
     String mText;

    public LoginProgressDialog(Context context) {
        super(context);
        setCenterOpen();
        setDialogWidth(0.5f);
        setCancelable(false);
    }

    @Override
    protected View getContentView() {
        if (mRootView == null) {
            mRootView = LayoutInflater.from(getContext()).inflate(R.layout.daqis_login_progress_dialog_layout, null);
            login_progressBar_tv=mRootView.findViewById(R.id.login_progressBar_tv);
        }
        return mRootView;
    }

    public void showDialog(){
        login_progressBar_tv.setText(mText);
        show();
    }

    public void setProgressText(String text){
        mText=text;
    }
}
