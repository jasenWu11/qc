package cn.com.qc.view;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import cn.com.qc.R;
import cn.com.qc.help.Helps;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

/**
 * Created by lenovo on 2017/9/30.
 */

public class HomeScrollView extends ScrollView {

    public HomeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    View childView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() > 0){
            childView = getChildAt(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    //    Log.i("TAG","dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    Rect startRect;

    PointF fP;

    //只有第一次按下手指的时候调用了
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            startRect = new Rect(childView.getLeft(),childView.getTop(),childView.getRight(),childView.getBottom());
            float x = ev.getX();
            float y = ev.getY();
            fP = new PointF(x,y);
            enableMoving = false;
            lastFocusId = ev.getPointerId(ev.getActionIndex());
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    boolean enableMoving = false;

    boolean isMove = false;

    int moveY = 0;

    int lastFocusId = -1;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_MOVE:
                int nowFocusId = ev.getPointerId(ev.getActionIndex());;
                if(lastFocusId != nowFocusId){
                    fP.y = ev.getY();
                    lastFocusId = nowFocusId;
                    Log.i("TAG","lastFocusId:" + lastFocusId);
                }
                int scrollY = getScrollY();
                float y = ev.getY();
                float disY = y - fP.y;
                int maxHeight = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics());

                moveY += (int) (disY*0.3);
                moveY = moveY > maxHeight?maxHeight:moveY;
                //向下拉
                if(moveY > 0&&scrollY == 0){
                    childView.layout(startRect.left,startRect.top + moveY,startRect.right,startRect.bottom + moveY);
                    isMove = true;
                    enableMoving = true;
                    Log.i("TAG","moveY:" + moveY);
                }

                if(moveY < 0){
                    moveY = 0;
                }
                fP.y = y;
                break;
            case MotionEvent.ACTION_UP:
                if(isMove){
                    childView.layout(startRect.left,startRect.top,startRect.right,startRect.bottom);
                    TranslateAnimation trans = new TranslateAnimation(0,0,moveY,0);
                    trans.setDuration(300);
                    childView.startAnimation(trans);
                }
                isMove = false;
                enableMoving = false;
                moveY = -1;
                lastFocusId = -1;
                break;

        }
        return enableMoving||super.onTouchEvent(ev);
    }
}
