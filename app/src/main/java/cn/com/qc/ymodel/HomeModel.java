package cn.com.qc.ymodel;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import cn.com.qc.adapter.HomeAdapter;
import cn.com.qc.app.App;
import cn.com.qc.bean.CompanyType;
import cn.com.qc.bean.HomeBean;
import cn.com.qc.bean.HomeCondition;
import cn.com.qc.bean.HomeHot;
import cn.com.qc.bean.HomePic;
import cn.com.qc.bean.HomeReturnDataBean;
import cn.com.qc.bean.ImageUrlHeaderBean;
import cn.com.qc.bean.Industry;
import cn.com.qc.bean.Information;
import cn.com.qc.bean.Job;
import cn.com.qc.help.JsonCallBack;
import cn.com.qc.help.NetUrl;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/9/30.
 */

public class HomeModel extends BaseAbtractModel {

    /**
     * 获取首页全部数据
     * @param subscriber
     * @param url
     * @param city
     */
    public void getAllDatas(final Subscriber subscriber, String url, String city, final List<HomeHot.HomeHotData> hots){
        OkGo.<HomeReturnDataBean>get(url)
                .tag(this)
                .headers("device","1")
                .params("city",city)
                .execute(new JsonCallBack<HomeReturnDataBean>() {
                    @Override
                    public void onSuccess(Response<HomeReturnDataBean> response) {
                        subscriber.onNext(change(response.body(),hots));
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<HomeReturnDataBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

    /**
     * 转换数据
     * @return
     */
    public List<HomeBean> change(HomeReturnDataBean dataBean,List<HomeHot.HomeHotData> hots){
        HomeReturnDataBean.ReturnData data = dataBean.getData();
        if(data == null){
            return null;
        }

        List<HomeBean> list = new ArrayList<>();
        HomeBean bean;
        List<HomePic> pics = data.getHomeImgs();
        bean = new HomeBean(HomeAdapter.Type.HomeImg,pics);
        list.add(bean);
        List<CompanyType> enterpriseTypes = data.getEnterpriseTypes();
        List<Industry> industries = data.getIndustryTypes();
        List<Job> jobs = data.getJobEntityes();
        HomeCondition homeCondition = new HomeCondition();
        homeCondition.setCompanyTypes(enterpriseTypes);
        homeCondition.setIndustrys(industries);
        homeCondition.setJobs(jobs);
        bean = new HomeBean(HomeAdapter.Type.Search,homeCondition);
        list.add(bean);
        List<HomeHot.HomeHotData> showActivite = hots;
        bean = new HomeBean(HomeAdapter.Type.Hot,showActivite);
        list.add(bean);
        List<Information> informations = data.getInformations();
        bean = new HomeBean(HomeAdapter.Type.Recommend,informations);
        list.add(bean);
        return list;
    }

    /**
     * 获取图片的前半部分URL
     *
     * @param imgUrlHeader
     * @param imgUrlHeader
     */
    public void getImgUrlHeader(String imgUrlHeader, final Subscriber subscriber, final String url, final String city) {
        OkGo.<ImageUrlHeaderBean>post(imgUrlHeader)
                .tag(this)
                .headers("device", "1")
                .execute(new JsonCallBack<ImageUrlHeaderBean>() {
                    @Override
                    public void onSuccess(Response<ImageUrlHeaderBean> response) {
                        ImageUrlHeaderBean bean = response.body();
                        if (bean != null) {
                            String path = bean.getBasePath();

                            App.isCompleteLoad = true;
                            App.imgHeader = path;
                        }
                        getHotDatas(NetUrl.DNS + NetUrl.FindHotInfo,subscriber,url,city);
                    }

                    @Override
                    public void onError(Response<ImageUrlHeaderBean> response) {
                        getHotDatas(NetUrl.DNS + NetUrl.FindHotInfo,subscriber,url,city);
                    }
                });
    }

    public void getHotDatas(String hotUrl, final Subscriber subscriber, final String url, final String city){
        OkGo.<HomeHot>post(hotUrl)
                .tag(this)
                .headers("device","1")
                .execute(new JsonCallBack<HomeHot>() {
                    @Override
                    public void onSuccess(Response<HomeHot> response) {
                        getAllDatas(subscriber,url,city,response.body().getData());
                    }

                    @Override
                    public void onError(Response<HomeHot> response) {
                        getAllDatas(subscriber,url,city,null);
                    }
                });
    }

}
