package cn.com.qc.ymodel;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

import cn.com.qc.bean.LoginBean;
import cn.com.qc.bean.ThirdUserInfo;
import cn.com.qc.help.JsonCallBack;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/12/7.
 */

public class LoginModel extends BaseAbtractModel implements PlatformActionListener {

    Handler handler;

    public LoginModel(Handler handler){
        this.handler = handler;
    }

    public void enter(final Subscriber subscriber, String url, String phone, String password){
        OkGo.<LoginBean>post(url)
                .params("UserName",phone)
                .params("Password",password)
                .execute(new JsonCallBack<LoginBean>() {
                    @Override
                    public void onSuccess(Response<LoginBean> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<LoginBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

    /**
     * 授权
     */
    public void authorizeWX(String platformName){
        Platform weibo = ShareSDK.getPlatform(platformName);
        weibo.SSOSetting(false);  //设置false表示使用SSO授权方式

        weibo.setPlatformActionListener(this); // 设置分享事件回调
        weibo.authorize();//单独授权
        weibo.showUser(null);//授权并获取用户信息
    }

    /**
     * 授权
     */
    public void authorizeXL(String platformName){

    }

    /**
     * 授权
     */
    public void authorizeQQ(String platformName){

    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        PlatformDb db = platform.getDb();

        String data = db.exportData();
        Log.i("TAG","data:" + data);

        ThirdUserInfo userInfo = new ThirdUserInfo();
        send(userInfo,1);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        send(null,2);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        send(null,3);
    }

    private void send(ThirdUserInfo userInfo,int code){
        Message msg = Message.obtain();
        msg.what = code;
        msg.obj = userInfo;
        handler.sendMessage(msg);
    }

}
