package suncere.gansu.androidapp.utils;

import java.util.Comparator;

import suncere.gansu.androidapp.model.entity.ListBean;

/**
 * Created by Hjo on 2017/6/6.
 */

public class ListSort implements Comparator<ListBean> {

    private boolean misSequence;//正序为true 倒序为false
    public ListSort(boolean isSequence){
        this.misSequence=isSequence;
    }

    @Override
    public int compare(ListBean object1, ListBean object2) {
        ListBean  before=misSequence? object1:object2;
        ListBean  after=misSequence? object2:object1;

//        if (before.getSortValue().equals("—"))  ;
        int flag=(Integer.valueOf(before.getSortID())).compareTo(Integer.valueOf(after.getSortID()));
        if(flag==0)
            return before.getCityName().toString().compareTo(after.getCityName().toString());

        return flag;
    }
}
