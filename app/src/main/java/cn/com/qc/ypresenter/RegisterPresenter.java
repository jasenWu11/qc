package cn.com.qc.ypresenter;

import android.util.Log;

import java.util.List;

import cn.com.qc.bean.CityBean;
import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.help.ServerApi;
import cn.com.qc.main.RegisterActivity;
import cn.com.qc.ymodel.RegisterModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2017/12/20.
 */

public class RegisterPresenter extends BasePresenter<RegisterActivity,RegisterModel>{
    @Override
    public RegisterModel getModel() {
        return new RegisterModel();
    }


    public void register(final String url,final String phone,final String password){
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<EditIntroBean>() {
            @Override
            public void call(Subscriber<? super EditIntroBean> subscriber) {
                m.register(subscriber,url,phone,password);
                Log.i("TAG","url:" + url + ",phone:" + phone + ",password:" + password);
            }
        }, new Observer<EditIntroBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onRegisterError();
            }

            @Override
            public void onNext(EditIntroBean e) {
                v.onRegisterSuccess(e.getStatus(),e.getMsg());
            }
        });
        list.add(subscription);
    }

}
