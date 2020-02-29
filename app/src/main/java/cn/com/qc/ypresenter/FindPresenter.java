package cn.com.qc.ypresenter;

import java.util.List;

import cn.com.qc.bean.FindHotCompany;
import cn.com.qc.bean.FindHotInfo;
import cn.com.qc.fragment.FindFragment;
import cn.com.qc.help.NetUrl;
import cn.com.qc.help.ServerApi;
import cn.com.qc.ymodel.FindModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/10/16.
 */

public class FindPresenter extends BasePresenter<FindFragment,FindModel> {

    @Override
    public FindModel getModel() {
        return new FindModel();
    }

    public void getHotCompany(final String url,final String city){
        list.add(ServerApi.subscribe(new Observable.OnSubscribe<FindHotCompany.FindData>() {
            @Override
            public void call(Subscriber<? super FindHotCompany.FindData> subscriber) {
                m.getHotCompany(subscriber,url,city);
            }
        }, new Observer<FindHotCompany.FindData>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                v.onError();
            }

            @Override
            public void onNext(FindHotCompany.FindData data) {
                v.onSuccessHotCompany(data);
            }
        }));
    }



}
