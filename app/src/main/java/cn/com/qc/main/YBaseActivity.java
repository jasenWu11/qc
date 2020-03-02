package cn.com.qc.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.view.SystemBarTintManager;
import cn.com.qc.yinter.BaseInter;
import cn.com.qc.ypresenter.BasePresenter;

/**
 * Created by lenovo on 2017/9/25.
 */

public abstract class YBaseActivity<P extends BasePresenter,V extends BaseInter> extends AppCompatActivity implements BaseInter{

    P p;

    public abstract P getPresenter();

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        initSystemBarTint();
        unbinder = ButterKnife.bind(this);
        p = getPresenter();
        p.attach(this);
        init();
    }

    public abstract int getResId();

    public abstract void init();

    public void openActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    public <T> void openActivityForResult(Class<T> clazz,int code){
        Intent intent = new Intent(this,clazz);
        startActivityForResult(intent,code);
    }

    public <T> void openActivity(Class<T> clazz,String key,String value){
        Intent intent = new Intent(this,clazz);
        intent.putExtra(key,value);
        startActivity(intent);
    }

    public <T> void openActivity(Class<T> clazz,Bundle bundle){
        Intent intent = new Intent(this,clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public <T> void openActivity(Class<T> clazz,String... keyAndValue){
        Bundle bundle = new Bundle();
        int length = keyAndValue.length;
        for(int i = 0;i < length;i += 2){
            bundle.putString(keyAndValue[i],keyAndValue[i+1]);
        }
        Intent intent = new Intent(this,clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /** 设置状态栏颜色 */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (false) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getColorPrimary());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getColorPrimary());
        }
    }

    /** 获取主题色 */
    public int getColorPrimary() {
        return getResources().getColor(R.color.appcolor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(p != null){
            p.deAttach();
        }
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
