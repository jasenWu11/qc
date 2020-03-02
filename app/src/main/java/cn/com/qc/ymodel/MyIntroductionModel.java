package cn.com.qc.ymodel;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import cn.com.qc.bean.MyIntroductionBean;
import cn.com.qc.help.JsonCallBack;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/11/15.
 */

public class MyIntroductionModel extends BaseAbtractModel {

    public void getAllDatas(final Subscriber subscriber, String url, String phone, String token){
        OkGo.<MyIntroductionBean>post(url)
                .headers("device","1")
                .params("phone",phone)
                .params("token",token)
                .execute(new JsonCallBack<MyIntroductionBean>() {
                    @Override
                    public void onSuccess(Response<MyIntroductionBean> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<MyIntroductionBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

}
