package cn.com.qc.ypresenter;

import java.util.List;

import cn.com.qc.bean.HomeBean;
import cn.com.qc.fragment.HomeFragment;

import cn.com.qc.help.NetUrl;
import cn.com.qc.help.ServerApi;
import cn.com.qc.ymodel.HomeModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/9/30.
 */

public class HomePresenter extends BasePresenter<HomeFragment, HomeModel> {

    @Override
    public HomeModel getModel() {
        return new HomeModel();
    }

    /**
     * 获取首页信息
     * @param url
     * @param city
     */
    public void getAllDatas(final String url, final String city){
        list.add(ServerApi.subscribe(new Observable.OnSubscribe<List<HomeBean>>() {
            @Override
            public void call(Subscriber<? super List<HomeBean>> subscriber) {
                m.getImgUrlHeader(NetUrl.ImgHeader,subscriber,url,city);
            }
        }, new Observer<List<HomeBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onErrorReturnAllData();
            }

            @Override
            public void onNext(List<HomeBean> HomeBean) {
                v.onSuccessReturnAllData(HomeBean);
            }
        }));
    }

}
