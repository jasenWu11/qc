package cn.com.qc.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by lenovo on 2017/9/27.
 */

public class DisableLinearLayoutManager extends LinearLayoutManager {
    public DisableLinearLayoutManager(Context context) {
        super(context);
    }

    public DisableLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public DisableLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return false&&super.canScrollVertically();
    }
}
