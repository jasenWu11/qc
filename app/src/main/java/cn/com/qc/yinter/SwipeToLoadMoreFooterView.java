package cn.com.qc.yinter;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.com.qc.R;


/**
 * Created by lenovo on 2017/4/12.
 */

public class SwipeToLoadMoreFooterView extends FrameLayout implements SwipeTrigger, SwipeLoadMoreTrigger {


    ImageView footer_success;//加载成功显示图标

    TextView footer_text;//

    ProgressBar footer_progressBar;

    int mHeight;

    public SwipeToLoadMoreFooterView(@NonNull Context context) {
        super(context);
    }

    public SwipeToLoadMoreFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        mHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height);
    }

    public SwipeToLoadMoreFooterView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        footer_success = (ImageView) findViewById(R.id.footer_success);
        footer_text = (TextView) findViewById(R.id.footer_text);
        footer_progressBar = (ProgressBar) findViewById(R.id.footer_progressBar);
    }

    @Override
    public void onLoadMore() {
        footer_text.setText("正在加载中...");
        footer_success.setVisibility(View.GONE);
        footer_progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {
    //    Log.i("TAG","onMove   i:" + i);
        if(!b){
            footer_progressBar.setVisibility(View.GONE);
            footer_success.setVisibility(View.GONE);
            if(- i < mHeight){
                footer_text.setText("继续上拉,加载更多...");
            }else{
                footer_text.setText("释放,加载更多...");
            }
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        footer_progressBar.setVisibility(View.GONE);
        footer_success.setVisibility(View.VISIBLE);
        footer_text.setText("加载完成!");
    }

    @Override
    public void onReset() {
        footer_progressBar.setVisibility(View.GONE);
        footer_success.setVisibility(View.GONE);
    }

}
