package suncere.gansu.androidapp.ui.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.presenter.BasePresenter;
import suncere.gansu.androidapp.ui.MvpFragment;

/**
 * @author lys
 * @time 2018/8/22 09:17
 * @desc:
 */

public class CalendarAndCountFragment extends MvpFragment{

    @BindView(R.id.fra_frame)
    FrameLayout fra_frame;

    DayCountFragment dayCountFragment;
    MoreCalendarFragment moreCalendarFragment;

    private View view;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_calendar_count,null);
        ButterKnife.bind(this,view);
        setTabSelection(0);
        return view;
    }


    public void setTabSelection(int index) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (index) {
            case 0:
                if (moreCalendarFragment == null) {
                    moreCalendarFragment = new MoreCalendarFragment(this);
                    ft.add(R.id.fra_frame, moreCalendarFragment);
                }
                ft.show(moreCalendarFragment);
                break;

            case 1:
                if (dayCountFragment == null) {
                    dayCountFragment = new DayCountFragment(this);
                    ft.add(R.id.fra_frame, dayCountFragment);
                }
                ft.show(dayCountFragment);
                break;
        }
        ft.commit();
    }

    //用于隐藏fragment
    private void hideFragment(FragmentTransaction ft) {
        if (moreCalendarFragment != null) {
            ft.hide(moreCalendarFragment);
        }
        if (dayCountFragment != null) {
            ft.hide(dayCountFragment);
        }
    }

}
