package cn.com.qc.ypresenter;

import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.help.ServerApi;
import cn.com.qc.main.ForgetActivity;
import cn.com.qc.ymodel.ForgetModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2017/12/20.
 */

public class ForgetPresenter extends BasePresenter<ForgetActivity,ForgetModel> {
    @Override
    public ForgetModel getModel() {
        return new ForgetModel();
    }

    public void sendCode(final String url,final String phone,final int dataType){//1
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<EditIntroBean>() {
            @Override
            public void call(Subscriber<? super EditIntroBean> subscriber) {
                m.sendCode(subscriber,url,phone,dataType);
            }
        }, new Observer<EditIntroBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onSendCodeError();
            }

            @Override
            public void onNext(EditIntroBean e) {
                v.onSendCodeSuccess(e.getStatus(),e.getMsg());
            }
        });
        list.add(subscription);
    }

    public void complete(final String url,final String phone,final String password,final String code){
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<EditIntroBean>() {
            @Override
            public void call(Subscriber<? super EditIntroBean> subscriber) {
                m.complete(subscriber,url,phone,password,code);
            }
        }, new Observer<EditIntroBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onCompleteError();
            }

            @Override
            public void onNext(EditIntroBean e) {
                v.onCompleteSuccess(e.getStatus(),e.getMsg());
            }
        });
        list.add(subscription);
    }

}
