package cn.com.qc.ypresenter;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.qc.bean.CityBean;
import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.help.NetUrl;
import cn.com.qc.help.ServerApi;
import cn.com.qc.main.EditIntroductionActivity;
import cn.com.qc.ymodel.EditIntroductionModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2017/11/15.
 */

public class EditIntroductionPresenter extends BasePresenter<EditIntroductionActivity,EditIntroductionModel> {

    @Override
    public EditIntroductionModel getModel() {
        return new EditIntroductionModel();
    }

    public void save(final String url, final String name, final String sex, final String outYear, final String workTime
        , final String phone, final String email, final String city, final String school, final String major, final String beginDate, final String graduateDate, final String maxEdu1
        , final String company, final String position, final String enterDate, final String endDate, final String workContent, final String wantPos
        , final String workType, final String wantCity, final String wantMoney, final String introduct, final String studentId
        , final String path, final String fileName
        , final String studentEducationId, final String workId, final String hopeJobId
        ){

        if(path != null&&!path.equals("")&&fileName != null&&!fileName.equals("")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int width = v.getResources().getDisplayMetrics().widthPixels;
                    int height = v.getResources().getDisplayMetrics().heightPixels;
                    filePath = compressBitmapToLocal(decodeBitmapFromSDCard(path,width,height),fileName,v);
                    upImage(NetUrl.DNS + NetUrl.upImage,url,name,sex,outYear,workTime,phone,email,city,school,major,beginDate,graduateDate
                            ,maxEdu1,company,position,enterDate,endDate,workContent,wantPos,workType,wantCity,wantMoney
                            ,introduct,studentId,studentEducationId,workId,hopeJobId);
                }
            }).start();
        }else{
            updateIntro(url,name,sex,outYear,workTime,phone,email,city,school,major,beginDate,graduateDate
                    ,maxEdu1,company,position,enterDate,endDate,workContent,wantPos,workType,wantCity,wantMoney
                    ,introduct,studentId,"",studentEducationId,workId,hopeJobId);
        }
    }

    public void updateIntro(final String url, final String name, final String sex, final String outYear, final String workTime
            , final String phone, final String email, final String city, final String school, final String major, final String beginDate, final String graduateDate, final String maxEdu1
            , final String company, final String position, final String enterDate, final String endDate, final String workContent, final String wantPos
            , final String workType, final String wantCity, final String wantMoney, final String introduct, final String studentId
            , final String headImg
            , final String studentEducationId, final String workId, final String hopeJobId)
    {
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<EditIntroBean>() {
            @Override
            public void call(Subscriber<? super EditIntroBean> subscriber) {
                m.updateIntro(subscriber,url,name,sex,outYear,workTime,phone,email,city,school,major,beginDate,graduateDate
                        ,maxEdu1,company,position,enterDate,endDate,workContent,wantPos,workType,wantCity,wantMoney
                        ,introduct,studentId,headImg,studentEducationId,workId,hopeJobId);
            }
        }, new Observer<EditIntroBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onUpImageError();
            }

            @Override
            public void onNext(EditIntroBean s) {
                v.updateIntroSuccess(s.getStatus(),s.getMsg());
            }
        });
        list.add(subscription);
    }

    String filePath;

    public void upImage(final String url,final String netUrl, final String name, final String sex, final String outYear, final String workTime
            , final String phone, final String email, final String city, final String school, final String major, final String beginDate, final String graduateDate, final String maxEdu1
            , final String company, final String position, final String enterDate, final String endDate, final String workContent, final String wantPos
            , final String workType, final String wantCity, final String wantMoney, final String introduct, final String studentId
            , final String studentEducationId, final String workId, final String hopeJobId)
    {
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                m.upImage(subscriber,url,filePath);
            }
        }, new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onUpImageError();
            }

            @Override
            public void onNext(String s) {
                if(s == null){
                    s = "";
                }
                updateIntro(netUrl,name,sex,outYear,workTime,phone,email,city,school,major,beginDate,graduateDate
                        ,maxEdu1,company,position,enterDate,endDate,workContent,wantPos,workType,wantCity,wantMoney
                        ,introduct,studentId,s,studentEducationId,workId,hopeJobId);
            }
        });
        list.add(subscription);
    }

    public void getAllCity(final String url){
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<List<CityBean.Province>>() {
            @Override
            public void call(Subscriber<? super List<CityBean.Province>> subscriber) {
                m.getAllCity(subscriber,url);
            }
        }, new Observer<List<CityBean.Province>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onCityError();
            }

            @Override
            public void onNext(List<CityBean.Province> provinces) {
                v.onCitySuccess(change(provinces));
            }
        });
        list.add(subscription);
    }

    private List<String> change(List<CityBean.Province> provinces){
        List<String> list = new ArrayList<>();
        for(int i = 0;i < provinces.size();i++){
            CityBean.Province province = provinces.get(i);
            List<CityBean.City> cities = province.getDistricts();
            for(int j = 0;j < cities.size();j++){
                CityBean.City city = cities.get(j);
                String level = city.getLevel();
                String name = city.getName();
                if(level.equals("city")){
                    list.add(name);
                }
            }
        }
        return list;
    }

    @Override
    public void deAttach() {
        super.deAttach();
    //    delete(filePath);
    }
}
