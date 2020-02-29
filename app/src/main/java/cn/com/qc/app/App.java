package cn.com.qc.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.mob.MobApplication;
import com.tandong.sa.tools.AssistTool;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by lenovo on 2017/10/9.
 */

public class App extends MobApplication {

    public static final int ConnectionTime = 2000;
    public static final int ReadTimeOutTime = 2000;
    public static final int WriteTimeOutTime = 2000;
    private static final String TAG = "Init";

    public String city = "";

    public String id;

    public String token="";

    public String headImg;

    public String userName;

    public String phone;

    public static String imgHeader = "";

    public static boolean isCompleteLoad = false;
    private AssistTool assistTool;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        System.out.println("传过来"+token);
        this.token = token;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initCloudChannel(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(ConnectionTime, TimeUnit.MILLISECONDS);
        builder.readTimeout(ReadTimeOutTime, TimeUnit.MILLISECONDS);
        builder.writeTimeout(WriteTimeOutTime, TimeUnit.MILLISECONDS);
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        assistTool = new AssistTool(this);
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {

            }
        });
        String deviceId = pushService.getDeviceId();
        System.out.println("deviceId：" + deviceId);
        assistTool.savePreferenceString("deviceId", deviceId);
    }
}
