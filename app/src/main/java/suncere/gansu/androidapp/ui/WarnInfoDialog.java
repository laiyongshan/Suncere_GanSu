package suncere.gansu.androidapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.model.entity.WarnBean2;

/**
 * @author lys
 * @time 2018/9/3 17:30
 * @desc:
 */

public class WarnInfoDialog extends Dialog {

    WarnBean2 warnBean2;

    @BindView(R.id.fw_dialogViewTitle)
    TextView fw_dialogViewTitle;

    @BindView(R.id.fw_dialogView_content)
    TextView fw_dialogView_content;

    @BindView(R.id.fw_dialogView_colse)
    ImageView fw_dialogView_colse;

    public WarnInfoDialog(@NonNull Context context, int themeResId, WarnBean2 warnBean2) {
        super(context, themeResId);
        this.warnBean2=warnBean2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecastwarning_warn_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        fw_dialogView_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if(warnBean2.getLevel().equals("1"))
            fw_dialogViewTitle.setText("警报信息");

        fw_dialogView_content.setText(warnBean2.getExecptionInfo()+"");
    }
}
