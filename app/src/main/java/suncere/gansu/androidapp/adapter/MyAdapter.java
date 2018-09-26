package suncere.gansu.androidapp.adapter;

//import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import suncere.gansu.androidapp.model.entity.BaseBean;


/**
 * Created by HJO on 2017/5/14.
 */

public class MyAdapter<T extends BaseBean> extends RecyclerView.Adapter<BindingViewHodle>{

    private final LayoutInflater mLayoutInflater;

    private int mlayout_id;
    private int mBR;

    private List<T>mlist;//数据源
    RecyclerViewOnItmeClickListener mrecyclerViewOnItmeClickListener;
    OnItmeViewBinding mOnItmeViewBinding;

    public MyAdapter(Context context,int layout_id ,int br){
        mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlist=new ArrayList<>();
        mlayout_id=layout_id;
        mBR=br;
    }

    public void setOnItmeClickListener(RecyclerViewOnItmeClickListener recyclerViewOnItmeClickListener){
        this.mrecyclerViewOnItmeClickListener=recyclerViewOnItmeClickListener;
    }
    public void setOnItmeViewBinding(OnItmeViewBinding onItmeViewBinding){
        mOnItmeViewBinding=onItmeViewBinding;
    }

    @Override
    public BindingViewHodle onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding=DataBindingUtil.inflate(mLayoutInflater, mlayout_id,parent,false);
        return new BindingViewHodle(binding);
    }


    @Override
    public void onBindViewHolder(BindingViewHodle holder, final int position) {
        final T model=mlist.get(position);
        if (mrecyclerViewOnItmeClickListener!=null) {
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mrecyclerViewOnItmeClickListener.OnItemClick(model,position);
                }
            });
        }
        if (mOnItmeViewBinding!=null){
            mOnItmeViewBinding.OnItmeView( holder.getBinding().getRoot(),model,position);
        }
        holder.getBinding().setVariable(mBR,model);
        holder.getBinding().executePendingBindings();
    }
    public void setData(List<T> list){
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public interface RecyclerViewOnItmeClickListener{
        void OnItemClick(Object obejct,int position);
    }

    public interface OnItmeViewBinding{
        void OnItmeView(View view,Object obejct,int position);
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
