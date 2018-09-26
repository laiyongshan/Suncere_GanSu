package suncere.gansu.androidapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.customview.TabBar;
import suncere.gansu.androidapp.presenter.UpdataAppPresenter;
import suncere.gansu.androidapp.ui.calendar.CalendarAndCountFragment;
import suncere.gansu.androidapp.ui.compare.CompareFragment;
import suncere.gansu.androidapp.ui.iview.IView;
import suncere.gansu.androidapp.updataapp.UpdateManager;
import suncere.gansu.androidapp.utils.Tools;


public class MainActivity extends MvpActivity<UpdataAppPresenter> implements IView {

    UpdataAppPresenter mUpdataAppPresenter;
    TabBar mTabBar;
    int mFragmentPage = 0;
    Tools mtools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //设置修改状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        initView();
    }

    @Override
    protected UpdataAppPresenter createPresenter() {
        mUpdataAppPresenter = new UpdataAppPresenter(this);
        return mUpdataAppPresenter;
    }


    @Override
    protected void onStart() {
        super.onStart();

        mUpdataAppPresenter.updataAPP(MyApplication.getMyApplicationVersionName());
    }

    private void initView() {
        mtools = new Tools(this);
        Intent intent = getIntent();

//        if (null != intent) {
//            Bundle bundle = getIntent().getExtras();
//            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//        }
        boolean isJPush = intent.getBooleanExtra("isJPush", false);// 从消息栏进入APP
        if (isJPush) mFragmentPage = 4;//跳到预警界面

        mTabBar = (TabBar) findViewById(R.id.main_tabView);
        mTabBar.mSelectTextColor = Color.parseColor("#5EB9F2");
        mTabBar.mTabBarViewBackgroundColor=Color.parseColor("#DBE8F8");
//        if (mtools.getishadLogin()){ //  已经登录的情况下多显示预报预警界面
        mTabBar.mTextVTitleArray = new String[]{"首页", "对比", "地图", "排名", "预警", "日历", "文件"};
        mTabBar.mSelectImageVIconArray = new int[]{R.mipmap.icon_home_active, R.mipmap.icon_duibi_active, R.mipmap.icon_map_active, R.mipmap.icon_paiming_active, R.mipmap.icon_warn_active, R.mipmap.icon_calendar_active, R.mipmap.icon_file_active};
        mTabBar.mUnSelectImageVIconArray = new int[]{R.mipmap.icon_home, R.mipmap.icon_duibi, R.mipmap.icon_map, R.mipmap.icon_paiming, R.mipmap.icon_warn, R.mipmap.icon_calendar, R.mipmap.icon_file};
        mTabBar.mTabFragmentClassArray = new Class<?>[]{HomeFagment.class, CompareFragment.class, MapFragment.class, ListFragment.class, ForecastWaringFragment.class, CalendarAndCountFragment.class, FileManagerFragment.class};
//        }else{
//            JPushSetAlias.getInstence().setAlias("UnJPush");//名字随意取  只要不收到推送就行
//            mTabBar.mTextVTitleArray = new String[]{"首页","对比","地图","排名","设置"};
//            mTabBar.mSelectImageVIconArray = new int[]{R.drawable.icon_city_active,R.drawable.icon_duibi_active,R.drawable.icon_map_active,R.drawable.icon_paiming_active,R.drawable.icon_setting_active};
//            mTabBar.mUnSelectImageVIconArray = new int[]{R.drawable.icon_city,R.drawable.icon_duibi,R.drawable.icon_map,R.drawable.icon_paiming,R.drawable.icon_setting};
//            mTabBar.mTabFragmentClassArray = new Class<?>[]{HomeFagment.class,CompareFragment2.class,MapFragment.class,ListFragment.class,SetFragment.class };
//        }
        mTabBar.mDefaultSelectIndex = mFragmentPage;
        mTabBar.refreshTabBar();
        /* ,"对比"  ,CompareFragment2.class  ,R.drawable.icon_duibi_active  ,R.drawable.icon_duibi  */
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出时的时间
    private long mExitTime;
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2500) {
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            ShowExitAPP();
        }
    }


    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void getDataSuccess(Object response) {
        if (response != null) {
            String downloadUrl = response.toString();
            if (downloadUrl.startsWith("http") && downloadUrl.endsWith(".apk"))
                new UpdateManager(this).checkUpdate(downloadUrl);
        }
    }
}
