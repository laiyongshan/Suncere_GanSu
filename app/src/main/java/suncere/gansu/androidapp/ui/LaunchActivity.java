package suncere.gansu.androidapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.utils.ToolUtils;

/**
 * Created by Hjo on 2017/5/27.
 */

public class LaunchActivity extends BaseActivity {


    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luanch_act);

        ToolUtils.setStatusBarFullTransparent(this);

        imageView=(ImageView)findViewById(R.id.luanch_imag) ;
        imageView.setImageResource(getLanchImage());
        intoMainAct();
    }
    private int getLanchImage(){
        return  R.mipmap.app_luach;
    }

    private void intoMainAct(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }




}
