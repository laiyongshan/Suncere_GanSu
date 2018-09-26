    package suncere.gansu.androidapp.ui;

    import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.model.entity.UserBean;
import suncere.gansu.androidapp.presenter.LoginPresenter;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.utils.AESEncryptor;
import suncere.gansu.androidapp.utils.JPushSetAlias;
import suncere.gansu.androidapp.utils.Tools;

    /**
     * Created by Hjo on 2017/5/27.
     */
    public class LoginActivity extends  MvpActivity<LoginPresenter>implements IView {
        private static final String TAG = "LoginActivityJPush";
        @BindView(R.id.login_user)
        EditText user_ed;

        @BindView(R.id.login_psw)
        EditText password_ed;

        @BindView(R.id.login_go)
        Button login_submit;


        @BindView(R.id.login_progress_rela)
        RelativeLayout login_progress;

        Tools mTools;
        LoginPresenter mLoginPresenter;
        String mUserName;
        String mPassword;

        @Override
        protected LoginPresenter createPresenter() {
            mLoginPresenter=new LoginPresenter(this);
            return mLoginPresenter;
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_act);
            ButterKnife.bind(this);
            initView ();
        }

        private void  initView (){
            mTools=new Tools(this);
            String[] userNameAndPsw=mTools.getUserNameAndPsw();
            if ( !"admin".equals(userNameAndPsw[0]) ) user_ed.setText(userNameAndPsw[0]);
            if(!"123".equals( userNameAndPsw[1])) password_ed.setText(userNameAndPsw[1]);

//              Intent intent=getIntent();
//              String   isSet2Logig=intent.getStringExtra("isSet2Login");
//                if (isSet2Logig==null && !"123".equals( userNameAndPsw[1]) && !"admin".equals(userNameAndPsw[0])  ){
//                    login();
//                }

        }

        @OnClick(R.id.login_go)
        public void on_Click_listener(View view){
            login();
        }

        public void login(){
            login_submit.setClickable(false);
            login_submit.setBackgroundResource(R.drawable.bg_login_button_n);

            mUserName=user_ed.getText().toString().trim();
            mPassword=password_ed.getText().toString().trim();


            String aespas=null;
            AESEncryptor aESEncryptor  =  AESEncryptor.getInstance();
           // 加密
            try {
                aespas=aESEncryptor.encrypt(mPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String pass=null;
            try {
                pass=aESEncryptor.decrypt(aespas);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("login","解密  mPassword="+pass );
            mLoginPresenter.login(mUserName,aespas);
        }

        @Override
        public void getDataSuccess(Object response) {
            UserBean userBean= (UserBean) response;
            if (userBean !=null){
                if ("1".equals(userBean.getResult())){
                    mTools.setSaveUserNameAndPsw(mUserName,mPassword);
                    mTools.setUserBean(userBean);
//                    setAlias(mUserName); //设置别名
                    JPushSetAlias.getInstence().setAlias(mUserName);
                    Intent intent=new Intent(this,MainActivity.class);
                    intent.putExtra("User",userBean);
                    startActivity(intent);
                }else { //登录失败
                    Toast.makeText(this,"用户名或者密码错误！",Toast.LENGTH_SHORT).show();
                    loginAfter();
                }
            }
        }
        @Override
        public void getDataFail(String msg) {
            Toast.makeText(this,"无法连接！",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showRefresh() {
            login_progress.setVisibility(View.VISIBLE);
        }

        @Override
        public void finishRefresh() {
            loginAfter();
        }

        private void loginAfter(){
            login_submit.setClickable(true);
            login_submit.setBackgroundResource(R.drawable.bg_login_button);
            login_progress.setVisibility(View.GONE);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
               exitAPP();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }



        /*       以下是极光推送 设置别名   */
        public static boolean isValidTagAndAlias(String s) {  // 校验Tag Alias 只能是数字,英文字母和中文
            Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
            Matcher m = p.matcher(s);
            return m.matches();
        }
//        private void setAlias(String alias) {
//
//            if (TextUtils.isEmpty(alias)) {
//                Toast.makeText(this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (!isValidTagAndAlias(alias)) {
//                Toast.makeText(this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // 调用 Handler 来异步设置别名
//            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
//        }

//        private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//            @Override
//            public void gotResult(int code, String alias, Set<String> tags) {
//                String logs ;
//                switch (code) {
//                    case 0:
//                        logs = "Set tag and alias success";
//                        Log.e(TAG, logs);
//                        // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                        break;
//                    case 6002:
//                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                        Log.e(TAG, logs);
//                        // 延迟 60 秒来调用 Handler 设置别名
//                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//                        break;
//                    default:
//                        logs = "Failed with errorCode = " + code;
//                        Log.e(TAG, logs);
//                }
//
//            }
//        };


//        private static final int MSG_SET_ALIAS = 1001;
//        private final Handler mHandler = new Handler() {
//            @Override
//            public void handleMessage(android.os.Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what) {
//                    case MSG_SET_ALIAS:
//                        Log.d(TAG, "Set alias in handler.");
//                        // 调用 JPush 接口来设置别名。
//                        JPushInterface.setAliasAndTags(getApplicationContext(),
//                                (String) msg.obj,
//                                null,
//                                mAliasCallback);
//                        break;
//                    default:
//                        Log.i(TAG, "Unhandled msg - " + msg.what);
//                }
//            }
//        };


    }
