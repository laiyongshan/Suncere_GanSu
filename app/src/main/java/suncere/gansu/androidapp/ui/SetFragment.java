package suncere.gansu.androidapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.customview.TipView;
import suncere.gansu.androidapp.utils.JPushSetAlias;
import suncere.gansu.androidapp.utils.Tools;

/**
 * Created by Hjo on 2017/5/12.
 */

public class SetFragment extends  BaseFragment {


    TipView mtip;
    Tools mtools;
    @BindView(R.id.set_button_exit)
    Button set_button_exit;

    boolean isLogin=false;
//    UpdateManager updateManager;
//    ProgressDialog proDialog;//载入对话框

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.set_frag,container,false);
        ButterKnife.bind(this,view);
        inits();
        return view;
    }

    public void inits(){
        mtip=new TipView(getActivity(),R.layout.tip_view);

       TextView set_app_version= (TextView) mtip.findViewById(R.id.set_app_version);
        set_app_version.setText("版本号："+MyApplication.getMyApplicationVersionName());
        mtools=new Tools(getActivity());
        isLogin=mtools.getishadLogin();
        if (isLogin){ //登陆状态
            set_button_exit.setText("注销");
        }else{
            set_button_exit.setText("登录");
        }
    }

    @OnClick({R.id.set_MenuItemAirDes,R.id.set_MenuItemSysDes,R.id.set_MenuItemOption,R.id.set_MenuItmeAbout,R.id.set_button_exit})//
    public void  onClick_MenuItme(View view){
        switch (view.getId()){
            case R.id.set_MenuItemAirDes:  //空气说明
                Intent  intent1=new Intent(getActivity(),AQIDesActivity.class);
                startActivity(intent1);
                break;
            case R.id.set_MenuItemSysDes:  //系统说明
                Intent  intent2=new Intent(getActivity(),SystemDesActivity.class);
                startActivity(intent2);
                break;
            case R.id.set_MenuItemOption:  //操作说明
//                intent=new Intent(getActivity(),AQIDesActivity.class);
//                startActivity(intent);
                break;
//            case R.id.set_MenuItemUpdate:  //检查更新
//                proDialog=ProgressDialog.show(getActivity()  , "检查更新", "请稍候",true);
//                proDialog.setCancelable(true);
//                new UpdateTask().execute();
//                break;
            case R.id.set_MenuItmeAbout:  //关于我们
                mtip.show();
                break;
            case  R.id.set_button_exit:  //登陆或者注销
                if (isLogin){  // 登录状态  点击时注销
                    mtools.setishadLogin(false);
                    JPushSetAlias.getInstence().setAlias("UnJPush");//名字随意取  只要不收到推送就行
                    Intent intent3=new Intent(getActivity(),MainActivity.class);
//                    intent3.putExtra("isJPush",true);
                    startActivity(intent3);
                }else{
                    Intent intent3=new Intent(getActivity(),LoginActivity.class);
//                    intent3.putExtra("isSet2Login","0");
                    startActivity(intent3);
                }

                break;
        }
    }


//    class UpdateTask extends AsyncTask<Void,Void,Boolean>
//    {
//        @Override
//        public Boolean doInBackground(Void... arg0) {
//            Log.e("UpdataAPK","版本更新地址："+ ApiNetManager.getInstence().BASE_URL+"Update/GetAndroidAPKUpdate?version="+updateManager.getVersionCode(getActivity()));
//            return updateManager.isUpdate( ApiNetManager.getInstence().BASE_URL+"/Update/GetAndroidAPKUpdate?version="+updateManager.getVersionCode(getActivity())  );
//        }
//
//        @SuppressLint("NewApi")
//        public void onPostExecute(Boolean hasUpdate)
//        {
//            proDialog.dismiss();
//            if(hasUpdate)
//                updateManager.showNoticeDialog();
//            else
//            {
//                new AlertDialog.Builder(getActivity()).setMessage("暂未发现新版本").setNegativeButton("确定", new AlertDialog.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        // TODO Auto-generated method stub
//                        arg0.dismiss();
//                    }}).create().show();
//            }
//
//        }
//    }
}
