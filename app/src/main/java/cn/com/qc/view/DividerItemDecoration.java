package cn.com.qc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lenovo on 2017/9/25.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] attrs = new int[]{android.R.attr.listDivider};

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    Drawable mDivider;

    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation){
        TypedArray a = context.obtainStyledAttributes(attrs);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c,parent,state);
        if(mOrientation == HORIZONTAL_LIST){
            drawHorizontal(c,parent);
        }else if(mOrientation == VERTICAL_LIST){
            drawVertical(c,parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int count = parent.getChildCount();
        for(int i = 0;i < count;i++){
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            int top = view.getBottom() + params.bottomMargin;
            int bottom = view.getBottom() + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent){
        int top = parent.getTop() + parent.getPaddingTop();
        int bottom = parent.getBottom() - parent.getPaddingBottom();
        int count = parent.getChildCount();
        for(int i = 0;i < count;i++){
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            int left = view.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == HORIZONTAL_LIST){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else if(mOrientation == VERTICAL_LIST){
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
    }
}
