package cn.com.qc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by lenovo on 2017/9/26.
 */

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration{

    public static final int []attrs = {android.R.attr.listDivider};

    Drawable d;

    public GridDividerItemDecoration(Context context) {
        TypedArray t = context.obtainStyledAttributes(attrs);
        d = t.getDrawable(0);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c,parent);
        drawVertical(c,parent);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent){
        int childCount = parent.getChildCount();
        for(int i = 0;i < childCount;i++){
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            int left = view.getLeft() - params.leftMargin;
            int right = view.getRight() + params.rightMargin + d.getIntrinsicWidth();
            int top = view.getBottom() + params.bottomMargin;
            int bottom = top + d.getIntrinsicHeight();
            d.setBounds(left,top,right,bottom);
            d.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent){
        int childCount = parent.getChildCount();
        for(int i = 0;i < childCount;i++){
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            int left = view.getRight() + params.rightMargin;
            int right = left + d.getIntrinsicWidth();
            int top = view.getTop() - params.topMargin;
            int bottom = view.getBottom() + params.bottomMargin;
            d.setBounds(left,top,right,bottom);
            d.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int spanCount = getSpanCount(parent);
        boolean isLastLine = isLastLine(parent,
                ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition()
                ,spanCount,parent.getAdapter().getItemCount());

        boolean isLastList = isLastList(parent,
                ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition(),spanCount);

        if(isLastLine&&isLastList){
            outRect.set(0,0,0,0);
        }
        else if(isLastLine&&!isLastList){
            outRect.set(0,0,d.getIntrinsicWidth(),0);
        }
        else if(!isLastLine&&isLastList){
            outRect.set(0,0,0,d.getIntrinsicHeight());
        }
        else if(!isLastLine&&!isLastList)
        {
            outRect.set(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
        }
    }

    private int getSpanCount(RecyclerView parent){
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            return  ((GridLayoutManager)manager).getSpanCount();
        }else{
            return -1;
        }
    }

    /**
     * 是否最后一行
     * @return
     */

    private boolean isLastLine(RecyclerView parent,int pos,int spanCount,int itemCount){
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            int remainCount = itemCount%spanCount;
            if(remainCount == 0){
                itemCount = itemCount - spanCount;
            }else{
                itemCount = itemCount - remainCount;
            }
            //在最后一行
            if(pos >= itemCount){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否最后一列
     * @return
     */
    private boolean isLastList(RecyclerView parent,int pos,int spanCount){
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            if((pos + 1)%spanCount == 0){
                return true;
            }
        }
        return false;
    }

}
