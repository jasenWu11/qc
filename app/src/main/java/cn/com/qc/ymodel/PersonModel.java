package cn.com.qc.ymodel;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.help.JsonCallBack;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/12/22.
 */

public class PersonModel extends BaseAbtractModel {

    public void logout(final Subscriber subscriber, String url){
        OkGo.<EditIntroBean>post(url)
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
