package cn.com.qc.ymodel;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import cn.com.qc.bean.SearchCompanyBean;
import cn.com.qc.bean.SearchConditionBean;
import cn.com.qc.bean.SearchPositionBean;
import cn.com.qc.help.JsonCallBack;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchModel extends BaseAbtractModel {

    public void getSearcheDatas(final Subscriber subscriber, final String url, final String city,
                                final String searchType, final String searchBody
            , final int pageNumber, final int pageSize){
        OkGo.<SearchCompanyBean>post(url)
                .headers("device","1")
                .params("city",city)
                .params("searchType",searchType)
                .params("searchBody",searchBody)
                .params("pageNumber",pageNumber)
                .params("pageSize",pageSize)
                .execute(new JsonCallBack<SearchCompanyBean>() {
                    @Override
                    public void onSuccess(Response<SearchCompanyBean> response) {
                        subscriber.onNext(response.body().getData().getList());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<SearchCompanyBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

    public void getSearchConditionDatas(final Subscriber subscriber, final String url,
                                final String enterpriseTypeId, final String industryId
            , final String jobEntityId, final String city, long beginDate/*,long endDate*/,int pageSize,int pageNumber){
        Log.i("TAG","enterpriseTypeId:" + enterpriseTypeId + ",industryId:" + industryId + ",jobEntityId:" + jobEntityId
                + ",beginDate:" + beginDate);
        PostRequest postRequest = OkGo.<SearchConditionBean>post(url)
                .headers("device","1");
        postRequest = params(postRequest,"enterpriseTypeId",enterpriseTypeId);
        postRequest = params(postRequest,"industryId",industryId);
        postRequest = params(postRequest,"jobEntityId",jobEntityId);
        postRequest = params(postRequest,"beginDate",beginDate);
        postRequest.params("city",city)
                .params("pageSize",pageSize)
                .params("pageNumber",pageNumber)
                .execute(new JsonCallBack<SearchConditionBean>() {
                    @Override
                    public void onSuccess(Response<SearchConditionBean> response) {
                        subscriber.onNext(response.body().getData().getList());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<SearchConditionBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }


    public PostRequest params(PostRequest postRequest,String key,String value){
        if(value != null){
            postRequest.params(key,value);
        }
        return postRequest;
    }

    public PostRequest params(PostRequest postRequest,String key,long value){
        if(value != 0){
            postRequest.params(key,value);
        }
        return postRequest;
    }

    public void getSearchPositionDatas(final Subscriber subscriber, final String url, final String city,
                                       final String searchType, final String searchBody
            , final int pageNumber, final int pageSize){
        OkGo.<SearchPositionBean>post(url)
                .headers("device","1")
                .params("city",city)
                .params("searchType",searchType)
                .params("searchBody",searchBody)
                .params("pageNumber",pageNumber)
                .params("pageSize",pageSize)
                .execute(new JsonCallBack<SearchPositionBean>() {
                    @Override
                    public void onSuccess(Response<SearchPositionBean> response) {
                        subscriber.onNext(response.body().getData().getList());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<SearchPositionBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

}
