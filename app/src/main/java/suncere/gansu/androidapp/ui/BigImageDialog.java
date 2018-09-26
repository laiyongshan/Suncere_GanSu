package suncere.gansu.androidapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import suncere.gansu.androidapp.R;

/**
 * @author lys
 * @time 2018/8/17 14:50
 * @desc:
 */

public class BigImageDialog extends Dialog {

    ImageView big_img;
    String imgPath;
    Context context;

    public BigImageDialog(@NonNull Context context, int themeResId, String imgPath) {
        super(context, themeResId);
        this.imgPath=imgPath;
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_image);
        initView();
    }

    private void initView(){
        big_img=findViewById(R.id.big_img);
        Glide.with(context).load("http://61.178.102.124:7200/AppServer/"+imgPath).into(big_img);
        Log.i("lys","下载Url is:"+"http://61.178.102.124:7200/AppServer/"+imgPath);
    }
}
