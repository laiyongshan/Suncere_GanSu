package suncere.gansu.androidapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.customview.PollutantNameTextView;
import suncere.gansu.androidapp.model.entity.CompareBean;

/**
 * @author lys
 * @time 2018/8/28 17:37
 * @desc:
 */

public class CompareAdapter extends BaseQuickAdapter<CompareBean,BaseViewHolder> {

    public CompareAdapter( @Nullable List<CompareBean> data) {
        super(R.layout.compare_list_itme, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompareBean item) {
        ((PollutantNameTextView)helper.getView(R.id.com_PollutantName)).setText(item.getItemName()+"");
        ((TextView)helper.getView(R.id.com_CurrentData)).setText(item.getCurrentData());
        ((TextView)helper.getView(R.id.com_LastData)).setText(item.getLastData());
        ((TextView)helper.getView(R.id.com_DataChangePercent)).setText(item.getDataChangePercent());

        if(item.getDataChangePercent().contains("-")) {
            ((ImageView) helper.getView(R.id.com_image_down)).setBackgroundResource(R.drawable.data_down_0);
        }else{
            ((ImageView) helper.getView(R.id.com_image_down)).setBackgroundResource(R.drawable.data_up_1);
        }

        if(item.getDataChangePercent().equals("—%")){
            ((ImageView) helper.getView(R.id.com_image_down)).setVisibility(View.INVISIBLE);
        }
    }
}
