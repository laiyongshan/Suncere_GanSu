package suncere.gansu.androidapp.adapter;

//import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import suncere.gansu.androidapp.model.entity.WarnBean;


/**
 * Created by HJO on 2017/5/14.
 */
public class ViewAdapterTwoView extends RecyclerView.Adapter<BindingViewHodle>{

    private final LayoutInflater mLayoutInflater;
    private int mlayout_id1,mlayout_id0;
    private int mBR;
    private int mBR1;

    private static final int PAGE0=0;
    private static final int PAGE1=1;

    int mPosition;
    private List<WarnBean>mlist;//数据源
    RecyclerViewOnItmeClickListener mrecyclerViewOnItmeClickListener;
    RecyclerViewOnBindItmeView mRecyclerViewOnBindItmeView;
    String lasttime="00000";  // 随意取的值

    public ViewAdapterTwoView(Context context, int layout_id0 , int layout_id1, int br0,int br1){
        mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlist=new ArrayList<>();
        mlayout_id0=layout_id0;
        mlayout_id1=layout_id1;
        mBR=br0;
        mBR1=br1;

    }

    public void setOnItmeClickListener(RecyclerViewOnItmeClickListener recyclerViewOnItmeClickListener){
        this.mrecyclerViewOnItmeClickListener=recyclerViewOnItmeClickListener;
    }

    public void setOnBindItmeView(RecyclerViewOnBindItmeView recyclerViewOnItmeClickListener){
        this.mRecyclerViewOnBindItmeView=recyclerViewOnItmeClickListener;
    }

    @Override
    public int getItemViewType(int position) {

        Log.e("warn"," ：mlist.get(position).getTimePoint()= "+ mlist.get(position).getTimePoint() + "   ：lasttime= "+lasttime);
            if (mlist.get(position).isheadView() ){ // 同一个时间段
                return PAGE1;
            }
            else{
                return PAGE0;
            }
    }

    @Override
    public BindingViewHodle onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType==PAGE0){
            binding=DataBindingUtil.inflate(mLayoutInflater, mlayout_id0,parent,false);
        }else{
            binding=DataBindingUtil.inflate(mLayoutInflater, mlayout_id1,parent,false);
        }
        return new BindingViewHodle(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHodle holder, final int position) {

        final WarnBean model=mlist.get(position);
        if (holder.getItemViewType()==PAGE0){
            holder.getBinding().setVariable(mBR,model);
        }else{
            holder.getBinding().setVariable(mBR1,model);
        }

        holder.getBinding().executePendingBindings();

        if (mRecyclerViewOnBindItmeView!=null){
            mRecyclerViewOnBindItmeView.OnBindItmeView( holder.getBinding().getRoot(),model,position,holder.getItemViewType());
        }

        if (mrecyclerViewOnItmeClickListener!=null) {
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mrecyclerViewOnItmeClickListener.OnItemClick(model,position);
                }
            });
        }

    }
    public void setData(List<WarnBean> list){
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public interface RecyclerViewOnItmeClickListener{
        void OnItemClick(Object obejct, int position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public interface RecyclerViewOnBindItmeView{ //对itme中的布局进行操作
        void OnBindItmeView(View view, Object obejct, int position, int ViewType);
    }
}
