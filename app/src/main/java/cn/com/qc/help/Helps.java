package cn.com.qc.help;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.List;

import cn.com.qc.R;
import cn.com.qc.adapter.PopWindowsAdapter;
import cn.com.qc.yinter.HomeTypeChooseInter;

/**
 * Created by lenovo on 2017/9/25.
 */

public class Helps {

    public static final String TAG = "TAG";

    public static void log(String value,Object str){
        Log.i(TAG,value + ":" + str);
    }

    public static void log(String value){
        Log.i(TAG,value);
    }

    public static void toast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }

    public static <V> boolean isNull(V v){
        if(v == null){
            return true;
        }
        return false;
    }

    public static void showPopWindow(Context context, boolean isShow, final List<String> list, int width,
                                     final View v, final HomeTypeChooseInter t, @LayoutRes int resId,int marginTop){
        if(!isShow){
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_item,null);

        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
        params.width = width;
        listView.setLayoutParams(params);

        PopWindowsAdapter adapter = new PopWindowsAdapter(context,list,resId);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                t.onItemClick(v,list.get(position),position);
            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
        popupWindow.showAsDropDown(v,0,marginTop);
    }

    public static void cancelDialog(AlertDialog dialog){
        if(dialog != null){
            dialog.cancel();
            dialog = null;
        }
    }

    public static AlertDialog showDialog(Activity activity) {
        AlertDialog alertDialog = buildDialog(activity);
        alertDialog.show();
        View view = activity.getLayoutInflater().inflate(R.layout.custom_dialog_item, null);
        alertDialog.setContentView(view);
        //alertDialog尺寸大小，仅在show()之后，才能设定生效成功
        WindowManager.LayoutParams dialogParams = alertDialog.getWindow().getAttributes();
        int dimen = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80,activity.getResources().getDisplayMetrics());
        dialogParams.width = dimen;
        dialogParams.height = dimen;
        dialogParams.alpha = 0.8f;
        alertDialog.getWindow().setAttributes(dialogParams);

        return alertDialog;
    }

    public static AlertDialog buildDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity context, float bgAlpha)
    {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }

}
