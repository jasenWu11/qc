package cn.com.qc.ypresenter;

import android.os.Handler;
import android.os.Message;

import cn.com.qc.bean.LoginBean;
import cn.com.qc.bean.ThirdUserInfo;
import cn.com.qc.help.Helps;
import cn.com.qc.help.ServerApi;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.yinter.LoginThirdAuthorizeListener;
import cn.com.qc.ymodel.LoginModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2017/12/7.
 */

public class LoginPresenter extends BasePresenter<LoginActivity,LoginModel> {


    LoginThirdAuthorizeListener listener;

    @Override
    public LoginModel getModel() {
        return new LoginModel(handler);
    }

    /**
     * 授权
     */
    public void authorize(String platformName, LoginThirdAuthorizeListener listener){
        this.listener = listener;
        m.authorizeWX(platformName);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Helps.toast(v.getBaseContext(),"授权成功，正在登录，请稍后...");
                    listener.authorizeComplete((ThirdUserInfo) msg.obj);
                    break;
                case 2:
                    Helps.toast(v.getBaseContext(),"授权失败!");
                    listener.authorizeComplete(null);
                    break;
                case 3:
                    Helps.toast(v.getBaseContext(),"取消授权!");
                    listener.authorizeComplete(null);
                    break;
            }
        }
    };

    public void enter(final String url, final String phone, final String password){
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<LoginBean>() {
            @Override
            public void call(Subscriber<? super LoginBean> subscriber) {
                m.enter(subscriber,url,phone,password);
            }
        }, new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onLoginError();
            }

            @Override
            public void onNext(LoginBean e) {
                v.onLoginSuccess(e);
            }
        });
        list.add(subscription);
    }

}
