package  suncere.gansu.androidapp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.OverScroller;
import android.widget.ScrollView;

import java.lang.reflect.Method;

public class VerticalViewPager  extends  ScrollView {

    /**
     * 内容总高度
     */
    private int mContentTotalHeight=0;
    /**
     * 控件实际高度
     */
    private int mViewHeight=-1;
    /**
     * 屏幕的高度
     */
    private int mScreenHeight;
    /**
     * 手指按下时的getScrollY
     */
    private int mScrollStart;
    /**
     * 手指抬起时的getScrollY
     */
    private int mScrollEnd;
    /**
     * 记录移动时的Y
     */
    private int mLastY;
    /**
     * 滚动的辅助类
     */
    private OverScroller _mscroller;
    /**
     * 是否正在滚动
     */
    private boolean isScrolling;
    /**
     * 加速度检测
     */
    private VelocityTracker mVelocityTracker;
    /**
     * 记录当前页
     */
    private int currentPage = 0;

    /**
     * 上次onMeasure的时候的MeasureHeight
     */
    int preMeasureHeight=9999;

//    private boolean needResetPageHeight=true;

    private OnPageChangeListener mOnPageChangeListener;


    public void setmOnPageChangeListener(OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }

    private OverScroller getmSroller()
    {

        if(_mscroller==null)
            _mscroller=new OverScroller(this.getContext());
        return _mscroller;
    }


    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
//		super.setOnTouchListener(on_scrollView_Touch);
        InitView();
    }

    public VerticalViewPager(Context context) {
        super(context);
//		super.setOnTouchListener(on_scrollView_Touch);
        InitView();
    }

    private void InitView()
    {
//		this.setOrientation(LinearLayout.VERTICAL);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        super.onAttachedToWindow();
        super.onFinishInflate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureHeight=this.getMeasuredHeight()+getVirtualBarHeigh();
        int count =((ViewGroup) getChildAt(0)). getChildCount();

        if(this.getMeasuredHeight()>preMeasureHeight)return;
        preMeasureHeight=this.getMeasuredHeight();
        mViewHeight=-1;
//        Log.e("hjooo","虚拟按键高度getVirtualBarHeigh()="+ getVirtualBarHeigh() );
        for (int i = 0; i < count; ++i)
        {
            View childView =((ViewGroup) getChildAt(0)). getChildAt(i);
            if(this.mViewHeight==-1)
                childView.getLayoutParams().height=measureHeight;
            mContentTotalHeight+=measureHeight;
        }


        if(this.mViewHeight==-1){
            this.mViewHeight=measureHeight;
            this.mScreenHeight=this.mViewHeight;
        }

    }

    /**
     * 华为手机等 有’不可隐藏‘的虚拟按键高度
     * @return
     */
    public int getVirtualBarHeigh() {
        int vh = 0;
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    protected void onScrollChanged(int l,int t,int oldl,int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
//		super.computeScroll();
    }


    private float xDistance, yDistance, xLast, yLast;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {  // 处理滑动冲突
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = event.getX();
                yLast = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = event.getX();
                final float curY = event.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;
                }
        }
        return    super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        return this.innernalOnTouchEvent(event);
    }

    protected boolean innernalOnTouchEvent(MotionEvent event)
    {
        boolean result=super.onTouchEvent(event);
        // 如果当前正在滚动，调用父类的onTouchEvent
        if (isScrolling)
            return super.onTouchEvent(event);

        int action = event.getAction();
        int y = (int) event.getY();

        obtainVelocity(event);
//        Log.e("OnTouchEvent" ,"y="+ y +"   mLastY= "+mLastY);
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                mScrollStart = getScrollY();
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (! getmSroller() .isFinished()) {
                    getmSroller().abortAnimation();
                }
                int dy = mLastY - y;
                // 边界值检查
                int scrollY = getScrollY();
                Log.e("OnTouchEvent" ,"MotionEvent.ACTION_MOVE    y="+ y +"   mLastY= " +mLastY+ "   dy="+ dy +"   scrollY= "+scrollY);
                // 已经到达顶端，下拉多少，就往上滚动多少
                if (dy < 0 && scrollY + dy < 0) {
                    dy = -scrollY;
                    getParent().requestDisallowInterceptTouchEvent(false);
                }  else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(dy>0&&scrollY+dy>mContentTotalHeight-mScreenHeight) {
                    dy=mContentTotalHeight-mScreenHeight-scrollY;
                }
//                scrollBy(0, dy);
                hjoscrollBy(dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mScrollEnd = getScrollY();
                int dScrollY = mScrollEnd - mScrollStart;
                if (wantScrollToNext()){// 往上滑动
                    if (shouldScrollToNext()) {
                        getmSroller().startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    } else {
                        getmSroller().startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }
                if (wantScrollToPre()){// 往下滑动
                    if (shouldScrollToPre()) {
                        getmSroller().startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);
                    } else {
                        getmSroller().startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }
                isScrolling = true;
                postInvalidate();
                recycleVelocity();
                break;
        }

        return result;
    }

    private void hjoscrollBy(int dy){
        Log.e("OnTouchEvent" ,"滑动 ： "+ dy);
        scrollBy(0, dy);
    }


    /**
     * 根据滚动距离判断是否能够滚动到下一页
     * @return
     */
    private boolean shouldScrollToNext()
    {
        return mScrollEnd - mScrollStart > mScreenHeight / 2 || Math.abs(getVelocity()) > 600;
    }

    /**
     * 根据用户滑动，判断用户的意图是否是滚动到下一页
     * @return
     */
    private boolean wantScrollToNext()
    {
        return mScrollEnd > mScrollStart;
    }

    /**
     * 根据滚动距离判断是否能够滚动到上一页
     *
     * @return
     */
    private boolean shouldScrollToPre()
    {
        return -mScrollEnd + mScrollStart > mScreenHeight / 2 || Math.abs(getVelocity()) > 600;
    }

    /**
     * 根据用户滑动，判断用户的意图是否是滚动到上一页
     *
     * @return
     */
    private boolean wantScrollToPre()
    {
        return mScrollEnd < mScrollStart;
    }

    @Override
    public void computeScroll()
    {
        if (getmSroller().computeScrollOffset())

        {
            scrollTo(0, getmSroller().getCurrY());
            postInvalidate();
        } else
        {

            int position = (int) ((double)getScrollY() / (double)mScreenHeight+0.5);  //hjo  加0.5为参数校准  目的为防止只是滑动一小段距离而触发回调接口

            if (position != currentPage)
            {
                if (mOnPageChangeListener != null)
                {
                    currentPage = position;
                    mOnPageChangeListener.onPageChange(currentPage);
                }
            }

            isScrolling = false;
        }

    }

    /**
     * 获取y方向的加速度
     *
     * @return
     */
    private int getVelocity()
    {
        mVelocityTracker.computeCurrentVelocity(1000);
        return (int) mVelocityTracker.getYVelocity();
    }

    /**
     * 释放资源
     */
    private void recycleVelocity()
    {
        if (mVelocityTracker != null)
        {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 初始化加速度检测器
     *
     * @param event
     */
    private void obtainVelocity(MotionEvent event)
    {
        if (mVelocityTracker == null)
        {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    public interface OnPageChangeListener
    {
        void onPageChange(int pageIndex);
    }

}
