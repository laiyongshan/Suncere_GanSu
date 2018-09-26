package suncere.gansu.androidapp.utils;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import suncere.gansu.androidapp.api.ApiNetManager;
import suncere.gansu.androidapp.ui.MyApplication;

import static suncere.gansu.androidapp.ui.LoginActivity.isValidTagAndAlias;

/**
 * Created by Hjo on 2017/7/28.
 */

public class JPushSetAlias  {

    private static final String TAG = "JPushSetAlias";
   private static  JPushSetAlias jPushSetAlias;

    public static JPushSetAlias getInstence(){
        if (jPushSetAlias==null){
            synchronized (ApiNetManager.class){
                if (jPushSetAlias==null)
                    jPushSetAlias=new JPushSetAlias();
            }
        }
        return jPushSetAlias;
    }
    public  void setAlias(String alias) {

        if (TextUtils.isEmpty(alias)) {
           Log.e(TAG,"别名为空");
            return;
        }
        if (!isValidTagAndAlias(alias)) {
            Log.e(TAG,"别名格式不对");
            return;
        }

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.e(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.e(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };


    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(MyApplication.getMyApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}
