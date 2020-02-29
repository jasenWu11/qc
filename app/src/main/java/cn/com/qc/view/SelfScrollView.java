package cn.com.qc.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import cn.com.qc.R;
import cn.com.qc.help.Helps;
import cn.com.qc.main.MyIntroductionActivity;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

/**
 * Created by lenovo on 2017/11/15.
 */

public class SelfScrollView extends ScrollView {

    public SelfScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_MOVE){
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int scrollY = getScrollY();
        if(action == MotionEvent.ACTION_MOVE){
            int height = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP,45,getResources().getDisplayMetrics());
            if(scrollY >= headHeight - height){
                Context context = getContext();
                if(context instanceof MyIntroductionActivity){
                    ((MyIntroductionActivity)context).bar.setBackgroundColor(getResources().getColor(R.color.blue_1e));
                }
            }else{
                Context context = getContext();
                if(context instanceof MyIntroductionActivity){
                    ((MyIntroductionActivity)context).bar.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    View childView;

    View headLinear;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() > 0){
            childView = getChildAt(0);
            headLinear = childView.findViewById(R.id.headLinear);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int headHeight;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        headHeight = headLinear.getMeasuredHeight();
    }
}
