package cn.com.qc.ymodel;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.help.JsonCallBack;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/12/20.
 */

public class ForgetModel extends BaseAbtractModel {

    public void sendCode(final Subscriber subscriber, String url, String phone, int dataType){
        OkGo.<EditIntroBean>get(url)
                .headers("device","1")
                .params("phone",phone)
                .params("dataType",dataType)
                .execute(new JsonCallBack<EditIntroBean>() {
                    @Override
                    public void onSuccess(Response<EditIntroBean> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<EditIntroBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

    public void complete(final Subscriber subscriber, String url, String phone, String password, String code){
        OkGo.<EditIntroBean>post(url)
                .headers("device","1")
                .params("phone",phone)
                .params("password",password)
                .params("password2",password)
                .params("code",code)
                .execute(new JsonCallBack<EditIntroBean>() {
                    @Override
                    public void onSuccess(Response<EditIntroBean> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<EditIntroBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

}
