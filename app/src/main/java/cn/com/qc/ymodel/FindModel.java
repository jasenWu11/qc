package cn.com.qc.ymodel;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import cn.com.qc.bean.FindHotCompany;
import cn.com.qc.bean.FindHotInfo;
import cn.com.qc.help.JsonCallBack;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/10/16.
 */

public class FindModel extends BaseAbtractModel {

    public void getHotCompany(final Subscriber subscriber,String url,String city){
        OkGo.<FindHotCompany>get(url)
                .tag(this)
                .headers("device","1")
                .params("city",city)
                .execute(new JsonCallBack<FindHotCompany>() {
                    @Override
                    public void onSuccess(Response<FindHotCompany> response) {
                        subscriber.onNext(response.body().getData());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<FindHotCompany> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

    public void getHotInfo(final Subscriber subscriber,String url){
        OkGo.<FindHotInfo>post(url)
                .tag(this)
                .execute(new JsonCallBack<FindHotInfo>() {
                    @Override
                    public void onSuccess(Response<FindHotInfo> response) {
                        subscriber.onNext(response.body().getData());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<FindHotInfo> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

}
