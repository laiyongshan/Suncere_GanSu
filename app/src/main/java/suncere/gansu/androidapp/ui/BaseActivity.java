package suncere.gansu.androidapp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.zhy.autolayout.AutoLayoutActivity;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Hjo on 2017/5/12.
 */

public class BaseActivity extends AutoLayoutActivity {

    private  ExitAPPBroadcast mExitAPPBroadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getSupportActionBar().hide();//去掉标题
        IntentFilter filter = new IntentFilter();
        filter.addAction("suncere.androidAPP.exitApp");
        mExitAPPBroadcast=new ExitAPPBroadcast();
        registerReceiver(mExitAPPBroadcast, filter);
        setPermissions(this);
    }

    //申请权限
    public static void setPermissions(Activity context) {
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.SET_WALLPAPER,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(context, PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE);
    }

    public class ExitAPPBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("suncere.androidAPP.exitApp".equals(intent.getAction())) {
//                context.unregisterReceiver(this);
                finish();
            }
        }
    }


//    private BroadcastReceiver ExitAPPBroadcast = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("suncere.androidAPP.exitApp".equals(intent.getAction())) {
//                context.unregisterReceiver(this);
//                finish();
//            }
//        }
//    };

    public  void exitAPP(){
        Intent intent = new Intent("suncere.androidAPP.exitApp");
        MyApplication.getMyApplicationContext().sendBroadcast(intent);
    }



    public   void ShowExitAPP()
    {
//        new AlertDialog.Builder(this)
//                .setTitle("提示")
//                .setMessage("确定要退出？")
//                .setNeutralButton ("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int arg1) {
//                        dialog.dismiss();
//                        exitAPP();
//                    }
//                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int arg1) {
//                // TODO Auto-generated method stub
//                dialog.dismiss();
//            }
//        }).show();
        exitAPP();
    }




    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mExitAPPBroadcast!=null){
            unregisterReceiver(mExitAPPBroadcast);
        }

    }

}
