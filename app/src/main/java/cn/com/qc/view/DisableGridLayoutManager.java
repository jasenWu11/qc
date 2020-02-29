package cn.com.qc.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by lenovo on 2017/9/27.
 */

public class DisableGridLayoutManager extends GridLayoutManager{

    public DisableGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DisableGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public DisableGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false&&super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return false&&super.canScrollHorizontally();
    }
}
