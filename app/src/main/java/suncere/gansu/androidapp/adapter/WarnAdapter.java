package suncere.gansu.androidapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.model.entity.WarnBean2;
import suncere.gansu.androidapp.ui.WarnInfoDialog;
import suncere.gansu.androidapp.utils.ToolUtils;

/**
 * @author lys
 * @time 2018/9/3 17:07
 * @desc:
 */

public class WarnAdapter extends BaseQuickAdapter<WarnBean2,BaseViewHolder> {

    Context context;

    public WarnAdapter( @Nullable List<WarnBean2> data,Context context) {
        super(R.layout.forecastwraning_list_item, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final WarnBean2 item) {
        ((TextView)helper.getView(R.id.fw_StationName)).setText(item.getCityName()+item.getStationName()+"");
        ((TextView)helper.getView(R.id.fw_timePoint_item)).setText(ToolUtils.stringToData(item.getTimePoint(),"yyyy-MM-dd HH:mm:ss","MM-dd HH:mm")+"");
        ((TextView)helper.getView(R.id.fw_monValue)).setText(item.getMonValue()+"");
        ((TextView)helper.getView(R.id.fw_preMonValue)).setText(item.getPreMonValue()+"");
        if(item.getLevel().equals("0")) {
            ((TextView) helper.getView(R.id.fw_Leven)).setText("预警");
            ((TextView) helper.getView(R.id.fw_Leven)).setBackgroundResource(R.drawable.forecastwarning_list_item_leven_f);
        }else{
            ((TextView)helper.getView(R.id.fw_Leven)).setText("警报");
            ((TextView) helper.getView(R.id.fw_Leven)).setBackgroundResource(R.drawable.forecastwarning_list_item_leven_a);
        }

        ((TextView)helper.getView(R.id.fw_Leven)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarnInfoDialog warnInfoDialog=new WarnInfoDialog(context,R.style.dialog,item);
                warnInfoDialog.show();
            }
        });
    }
}
