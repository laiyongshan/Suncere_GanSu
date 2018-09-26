package suncere.gansu.androidapp.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.model.entity.CompareBean3;
import suncere.gansu.androidapp.utils.ToolUtils;

/**
 * @author lys
 * @time 2018/9/21 15:43
 * @desc:
 */

public class CompareAdapter3 extends BaseQuickAdapter<CompareBean3,BaseViewHolder> {

    public CompareAdapter3(List<CompareBean3> data) {
        super(R.layout.item_compare3, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompareBean3 item) {
        ((TextView)helper.getView(R.id.com_CityName)).setText(item.getAreaName()+"");
        ((TextView)helper.getView(R.id.com_PM10Data)).setText(item.getComparePM10()+"");
        ((TextView)helper.getView(R.id.com_PM25Data)).setText(item.getComparePM25()+"");
        ((TextView)helper.getView(R.id.com_GoodDayData)).setText(item.getCompareGoodRate()+"");

        if(ToolUtils.isNum(item.getComparePM10())){
            if(Integer.valueOf(item.getComparePM10())>0){
                ((TextView)helper.getView(R.id.com_PM10Data)).setBackgroundResource(R.drawable.round_rect_aqi1g_pal);
            }else{
                ((TextView)helper.getView(R.id.com_PM10Data)).setBackgroundResource(R.drawable.round_rect_aqi3g_pal);
            }
        }

        if(ToolUtils.isNum(item.getComparePM25())){
            if(Integer.valueOf(item.getComparePM25())>0){
                ((TextView)helper.getView(R.id.com_PM25Data)).setBackgroundResource(R.drawable.round_rect_aqi1g_pal);
            }else{
                ((TextView)helper.getView(R.id.com_PM25Data)).setBackgroundResource(R.drawable.round_rect_aqi3g_pal);
            }
        }

        if(ToolUtils.isNum(item.getTarGetGoodRate())){
            if(Double.valueOf(item.getTarGetGoodRate())>0){
                ((TextView)helper.getView(R.id.com_GoodDayData)).setBackgroundResource(R.drawable.round_rect_aqi1g_pal);
            }else{
                ((TextView)helper.getView(R.id.com_GoodDayData)).setBackgroundResource(R.drawable.round_rect_aqi3g_pal);
            }
        }
    }
}
