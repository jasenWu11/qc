package cn.com.qc.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.help.Helps;
import cn.com.qc.view.SystemBarTintManager;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by lenovo on 2017/12/27.
 */

public class ConnectUsActivity extends Activity {

    @BindView(R.id.agentText)
    TextView agentText;
    @BindView(R.id.serviceText)
    TextView serviceText;
    @BindView(R.id.title)
    TextView title;

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_us);
        initSystemBarTint();
        unbinder = ButterKnife.bind(this);
        title.setText("联系我们");
    }

    String phone;

    @OnClick({R.id.agentR, R.id.serviceR, R.id.returns})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.agentR:
                phone = getPhone(agentText);
                call(phone);
                break;
            case R.id.serviceR:
                phone = getPhone(serviceText);
                call(phone);
                break;
            case R.id.returns:
                finish();
                break;
        }
    }

    private String getPhone(TextView textView) {
        String content = textView.getText().toString();
        int index = content.indexOf("(");
        if (index != -1) {
            content = content.substring(0, index);
        }
        return content;
    }

    public void call(String phone) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0x02);
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        //    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x02) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Helps.toast(this,"权限被禁止，无法拨打电话");
            }
        }
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
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
