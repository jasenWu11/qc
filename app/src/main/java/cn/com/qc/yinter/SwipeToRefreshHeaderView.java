package cn.com.qc.yinter;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.com.qc.R;


/**                                 Create an Simple RefreshHeaderView
 * Created by lenovo on 2017/3/31.
 */

public class SwipeToRefreshHeaderView extends FrameLayout implements SwipeRefreshTrigger,SwipeTrigger {

    ImageView header_down;//拉动时显示的图标

    ImageView header_success;//刷新成功显示的图标

    TextView header_text;//显示的文字

    ProgressBar header_progressBar;//刷新时显示的进度

    boolean rotated = false;//下上旋转动画是否已经执行了

    Animation upAnimation;//自下往上旋转

    Animation downAnimation;//自伤往下旋转

    int mHeight;//header的高度

    public SwipeToRefreshHeaderView(Context context) {
        super(context);
    }

    public SwipeToRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SwipeToRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        downAnimation = AnimationUtils.loadAnimation(context, R.anim.down_header_anim);
        upAnimation = AnimationUtils.loadAnimation(context,R.anim.up_header_anim);
        mHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        header_down = (ImageView) findViewById(R.id.header_down);
        header_success = (ImageView) findViewById(R.id.header_success);
        header_text = (TextView) findViewById(R.id.header_text);
        header_progressBar = (ProgressBar) findViewById(R.id.header_progressBar);
    }

    @Override
    public void onRefresh() {
        header_success.setVisibility(View.GONE);
        header_down.clearAnimation();
        header_down.setVisibility(View.GONE);
        header_progressBar.setVisibility(View.VISIBLE);
        header_text.setText("正在加载中...");
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if(!isComplete){
            header_progressBar.setVisibility(View.GONE);
            header_success.setVisibility(View.GONE);
            header_down.setVisibility(View.VISIBLE);
            if(yScrolled < mHeight){
                if(rotated){
                    header_down.clearAnimation();
                    header_down.startAnimation(downAnimation);
                    rotated = false;
                }
                header_text.setText("继续下拉，刷新页面...");
            }else{
                if(!rotated){
                    header_down.clearAnimation();
                    header_down.startAnimation(upAnimation);
                    rotated = true;
                }
                header_text.setText("释放，刷新页面...");
            }
        }
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        rotated = false;
        header_down.clearAnimation();
        header_down.setVisibility(View.GONE);
        header_success.setVisibility(View.VISIBLE);
        header_progressBar.setVisibility(View.GONE);
        header_text.setText("刷新成功!");
    }

    @Override
    public void onReset() {
        rotated = false;
        header_down.clearAnimation();
        header_down.setVisibility(View.GONE);
        header_progressBar.setVisibility(View.GONE);
        header_success.setVisibility(View.GONE);
    }

}
