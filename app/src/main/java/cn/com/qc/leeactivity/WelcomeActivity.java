package cn.com.qc.leeactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.com.qc.R;
import cn.com.qc.main.MainActivity;
import cn.com.qc.utils.Tools;

/**
 * 欢迎界面
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Tools.initSystemBarTint(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SharedPreferences sharedPreferences = getSharedPreferences("first", Context.MODE_PRIVATE);
                boolean isFirst = sharedPreferences.getBoolean("isFirst", true);
                if (!isFirst) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                    startActivity(intent);
                    sharedPreferences.edit().putBoolean("isFirst", false).commit();
                }
                finish();
            }
        }).start();
    }
}
