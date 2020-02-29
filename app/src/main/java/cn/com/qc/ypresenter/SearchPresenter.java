package cn.com.qc.ypresenter;

import java.util.List;

import cn.com.qc.bean.SearchCompanyBean;
import cn.com.qc.bean.SearchConditionBean;
import cn.com.qc.bean.SearchPositionBean;
import cn.com.qc.help.ServerApi;
import cn.com.qc.main.SearchActivity;
import cn.com.qc.ymodel.SearchModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchPresenter extends BasePresenter<SearchActivity,SearchModel> {

    @Override
    public SearchModel getModel() {
        return new SearchModel();
    }

    public void getSearcheDatas( final String url, final String city,
                                final String searchType, final String searchBody
            , final int pageNumber, final int pageSize){
        list.add(ServerApi.subscribe(new Observable.OnSubscribe<List<SearchCompanyBean.SearchData>>() {
            @Override
            public void call(Subscriber<? super List<SearchCompanyBean.SearchData>> subscriber) {
                m.getSearcheDatas(subscriber,url,city,searchType,searchBody,pageNumber,pageSize);
            }
        }, new Observer<List<SearchCompanyBean.SearchData>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onError();
            }

            @Override
            public void onNext(List<SearchCompanyBean.SearchData> datas) {
                v.onSuccess(datas);
            }
        }));

    }

    public void getSearchPositionDatas( final String url, final String city,
                                        final String searchType, final String searchBody
            , final int pageNumber, final int pageSize){
        list.add(ServerApi.subscribe(new Observable.OnSubscribe<List<SearchPositionBean.SearchPositionData>>() {
            @Override
            public void call(Subscriber<? super List<SearchPositionBean.SearchPositionData>> subscriber) {
                m.getSearchPositionDatas(subscriber,url,city,searchType,searchBody,pageNumber,pageSize);
            }
        }, new Observer<List<SearchPositionBean.SearchPositionData>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onError();
            }

            @Override
            public void onNext(List<SearchPositionBean.SearchPositionData> datas) {
                v.onPosSuccess(datas);
            }
        }));
    }

    public void getSearchConditionDatas(final String url,
                                        final String enterpriseTypeId, final String industryId
            , final String jobEntityId, final String city, final long beginDate/*, final long endDate*/, final int pageSize, final int pageNumber){
        list.add(ServerApi.subscribe(new Observable.OnSubscribe<List<SearchConditionBean.SearchCoditionData>>() {
            @Override
            public void call(Subscriber<? super List<SearchConditionBean.SearchCoditionData>> subscriber) {
                m.getSearchConditionDatas(subscriber,url,enterpriseTypeId,industryId,jobEntityId,city,beginDate/*,endDate*/,pageSize,pageNumber);
            }
        }, new Observer<List<SearchConditionBean.SearchCoditionData>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onError();
            }

            @Override
            public void onNext(List<SearchConditionBean.SearchCoditionData> datas) {
                v.onConditionSuccess(datas);
            }
        }));
    }

}

