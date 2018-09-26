package suncere.gansu.androidapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import suncere.gansu.androidapp.R;

/**
 * Created by Hjo on 2017/6/1.
 */
public class PollutantsView extends LinearLayout {

    private String [] mPollutantsTexts; //污染物列表
    private String [] mPollutantsCodes;//污染物编码
    private int mpollutantstextSize;
    private int mSelectTextColor; //被选中的污染物字体颜色
    public int mSelectTextBackgroundColor;//被选中的污染物背景
    private int mUnSelectTextColor; //未被选中的污染物字体颜色
    public int mUnSelectTextBackgroundColor;//被选中的污染物背景
    private int mDefaultSelectIndex ;//默认选中的
    private Context mContext;





    private List<TextView> mlistView;



    private  SelceTextListener mSelceTextListener;

    public PollutantsView(Context context) {
        this(context,null);
    }

    public PollutantsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PollutantsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView(attrs, defStyleAttr);
    }

    public void setmSelceTextListener(SelceTextListener selceTextListener) {
        this.mSelceTextListener = selceTextListener;
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta= mContext.obtainStyledAttributes(attrs, R.styleable.PollutantsView);
        String _pollutantsTexts=ta.getString(R.styleable.PollutantsView_pollutantsTexts);
        String _pollutantsCodes=ta.getString(R.styleable.PollutantsView_pollutantsCodes);

        mSelectTextColor=ta.getColor(R.styleable.PollutantsView_selectPollutantTextColor,Color.BLACK);
        mSelectTextBackgroundColor=ta.getResourceId(R.styleable.PollutantsView_selectPollutantTextBackground,Color.TRANSPARENT);
        mUnSelectTextColor=ta.getColor(R.styleable.PollutantsView_unSelectPollutantTextColor,Color.WHITE);
        mUnSelectTextBackgroundColor=ta.getResourceId(R.styleable.PollutantsView_unSelectPollutantTextBackground,Color.TRANSPARENT);
        mDefaultSelectIndex=ta.getInteger(R.styleable.PollutantsView_defaultSelectPollutantIndex,0);
        mpollutantstextSize=ta.getInteger(R.styleable.PollutantsView_pollutantstextSize,14 );

        ta.recycle();
        mlistView=new ArrayList<>();
        if (_pollutantsTexts!=null){
            mPollutantsTexts=_pollutantsTexts.split(",");
        }
        if (_pollutantsCodes!=null){
            mPollutantsCodes=_pollutantsCodes.split(",");
        }
        addTextView();
    }


    /**
     * 添加textView
     */
    private void addTextView(){
        this.setOrientation(LinearLayout.HORIZONTAL);
        PollutantNameTextView textView;
        for (int i=0;i< mPollutantsTexts.length;i++){
            textView=new PollutantNameTextView(mContext);
            textView.setTag(i);
            this.addView(textView);
            LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            params.weight=1;
            textView.setPadding(0,dp2px(4f),0,dp2px(4f));
            params.gravity= Gravity.CENTER;
            textView.setText(mPollutantsTexts[i]);
            textView.setTextSize(mpollutantstextSize);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);
            mlistView.add(textView);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPollutantsCodes!=null){
                        mSelceTextListener.onSelect(mPollutantsTexts[(int) v.getTag()],mPollutantsCodes[(int) v.getTag()]);
                    }else{
                        mSelceTextListener.onSelect(mPollutantsTexts[(int) v.getTag()],null);
                    }
                    setTextViewColorAndBackground((int) v.getTag());
                }
            });
        }

        setTextViewColorAndBackground(mDefaultSelectIndex);
    }

    public List<TextView> getListView(){

        return mlistView;
    }

    /**
     *  修改选中textView的背景和颜色值
     * @param index  选中的View的下标
     */
    private void setTextViewColorAndBackground(int index){
        if (mlistView!=null && mlistView.size()>0){
            for (int i=0;i<mlistView.size();i++){
                if(i==index){
                    mlistView.get(i).setTextColor(mSelectTextColor);
                    mlistView.get(i).setBackgroundResource(mSelectTextBackgroundColor);
                }else{
                    mlistView.get(i).setTextColor(mUnSelectTextColor );
                    mlistView.get(i).setBackgroundResource(mUnSelectTextBackgroundColor );
                }
            }
        }
    }

    public interface  SelceTextListener {
        void onSelect(String pollutantName,String pollutantCode );
    }

    /** dp转化为px工具 */
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }
}
