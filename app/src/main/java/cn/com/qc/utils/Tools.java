package cn.com.qc.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.qc.R;
import cn.com.qc.view.SystemBarTintManager;

/**
 * 工具类集合
 */

public class Tools {
    // 吐司
    public static void toast(Activity activity, String string) {
        Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
    }

    public static void toastS(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    //设置状态栏颜色
    public static void initSystemBarTint(Activity activity) {
        Window window = activity.getWindow();
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor(activity));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor(activity));
        }
    }

    protected static int setStatusBarColor(Activity activity) {
        return activity.getResources().getColor(R.color.appcolor);
    }

    // 时间戳转字符串
    public static String getStrTime(String timeStamp) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//"yyyy年MM月dd日 hh:mm"
        long l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));
        return timeString;
    }
}
