package suncere.gansu.androidapp.customview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Created by liangfeizc on 7/31/15.
 */
public class ScreenUtils {

    private Context context;
    private WindowManager windowManager;

    public ScreenUtils(Context context){
        this.context = context;
        this.windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    }

    /** dp转化为px工具 */
    public int dp2px(float dp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public int getScreenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public int getScreenheight(){
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static void ViewWidthHeight(final View view, final ViewOnGlobalLayoutListener viewOnGlobalLayoutListener){
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                viewOnGlobalLayoutListener.onGlobalLayout(view, view.getWidth(),view.getHeight());
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public interface ViewOnGlobalLayoutListener{
        void onGlobalLayout(View view, int width, int Height);
    }

}
