package suncere.gansu.androidapp.ui.compare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidkun.xtablayout.XTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.utils.Tool;
import suncere.gansu.androidapp.utils.Tools;

/**
 * @author lys
 * @time 2018/9/21 10:54
 * @desc:
 */

public class CompareFragment extends Fragment{

    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;

    @BindView(R.id.selected_city_iv)
    ImageView selected_city_iv;

    @BindView(R.id.compare_segmentTabLayout)
    SegmentTabLayout compare_segmentTabLayout;

    @BindView(R.id.compare_viewPager)
    ViewPager compare_viewPager;

    AlertDialog.Builder cityAlert;
    List<CharSequence> titleCityName;
    String selectCityName;

    private String[] mTitles = {"同期对比", "年目标对比"};

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    Tools tools;

    private View view;



    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_compare,null);
        ButterKnife.bind(this,view);

        initView();
        initTabLayout();

        EventBus.getDefault().register(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initView(){

        tools=new Tools(getActivity());

        selectCityName = "甘肃14城市";
        tools.setCompareSelectedCity(selectCityName);

        titleCityName = new ArrayList<>();
        titleCityName = Arrays.asList(Tool.getCitys222());

        mFragments.add(new CompareFragment2());
        mFragments.add(new CompareFragment3());

        compare_segmentTabLayout.setTabData(mTitles);
        compare_segmentTabLayout.setCurrentTab(0);
        compare_segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                compare_viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        compare_viewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        compare_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                compare_segmentTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        compare_viewPager.setCurrentItem(0);
    }


    public void initTabLayout() {
        for (CharSequence cityName : titleCityName) {
            xTablayout.addTab(xTablayout.newTab().setText(cityName.toString()));
        }

        xTablayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                selectCityName = titleCityName.get(tab.getPosition()).toString();
                tools.setCompareSelectedCity(selectCityName);
                Bundle bundle=new Bundle();
                bundle.putString("selectCityName",selectCityName);
                EventBus.getDefault().post(bundle);
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

        selected_city_iv.setOnClickListener(on_title_click);


    }


    View.OnClickListener on_title_click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            selected_city_iv.setImageResource(R.mipmap.icon_shangla);
            // TODO Auto-generated method stub
            cityAlert = new AlertDialog.Builder(arg0.getContext()).setTitle(
                    "请选择城市").setItems((CharSequence[]) titleCityName.toArray(new CharSequence[titleCityName.size()]),
                    new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface sender, int index) {
                            // cityName.setText(cityArray[index]);
                            selectCityName = titleCityName.get(index).toString();
                            tools.setCompareSelectedCity(selectCityName);
                            xTablayout.getTabAt(index).select();
                            selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                        }
                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    selected_city_iv.setImageResource(R.mipmap.icon_xiala);
                }
            });

            cityAlert.show();
        }
    };


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //必须使用EventBus的订阅注解
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Bundle bundle) {

    }
}
