package cn.com.qc.ymodel;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.io.File;

import cn.com.qc.bean.CityBean;
import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.bean.UpImageBean;
import cn.com.qc.help.JsonCallBack;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/11/15.
 */

public class EditIntroductionModel extends BaseAbtractModel {

    public void upImage(final Subscriber subscriber, String url , String path){
        OkGo.<UpImageBean>post(url)
                .headers("device","1")
                .params("file",new File(path))
                .execute(new JsonCallBack<UpImageBean>() {
                    @Override
                    public void onSuccess(Response<UpImageBean> response) {
                        subscriber.onNext(response.body().getData());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<UpImageBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

    public void getAllCity(final Subscriber subscriber, String url){
        OkGo.<CityBean>post(url)
                .params("key","af4be48d219da2a3d607709e90fa8b6f")
                .params("subdistrict","2")
                .execute(new JsonCallBack<CityBean>() {
                    @Override
                    public void onSuccess(Response<CityBean> response) {
                        subscriber.onNext(response.body().getDistricts().get(0).getDistricts());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<CityBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

    public void updateIntro(final Subscriber subscriber, String url,String name,String sex,String outYear,String workTime
            ,String phone,String email,String city
            ,String school,String major,String beginDate,String graduateDate,String maxEdu1
            ,String company,String position,String enterDate,String endDate,String workContent
            ,String wantPos,String workType,String wantCity,String wantMoney
            ,String introduct,String studentId
            ,String headImg
            ,String studentEducationId,String workId,String hopeJobId){
        PostRequest postRequest = OkGo.<EditIntroBean>post(url).headers("device","1");
        postRequest = params(postRequest,"studentId",studentId);
        postRequest = params(postRequest,"name",name);
        postRequest = params(postRequest,"sex",sex);//1、男 2、女
        postRequest = paramsLong(postRequest,"birthDate",outYear);//出生日期
        postRequest = params(postRequest,"workYear",workTime);//工作年限
        postRequest = params(postRequest,"phone",phone);
        postRequest = params(postRequest,"email",email);
        postRequest = params(postRequest,"city",city);

        postRequest = params(postRequest,"studentEducationId",studentEducationId);
        postRequest = params(postRequest,"schoolName",school);
        postRequest = params(postRequest,"major",major);
        postRequest = paramsLong(postRequest,"beginTime",beginDate);
        postRequest = paramsLong(postRequest,"endTime",graduateDate);
        postRequest = params(postRequest,"education",maxEdu1);

        postRequest = params(postRequest,"workId",workId);
        postRequest = params(postRequest,"enterpriseName",company);//公司名称
        postRequest = params(postRequest,"job",position);
        postRequest = paramsLong(postRequest,"workBeginTime",enterDate);
        postRequest = paramsLong(postRequest,"workEndTime",endDate);
        postRequest = params(postRequest,"jobContent",workContent);

        postRequest = params(postRequest,"hopeJobId",hopeJobId);
        postRequest = params(postRequest,"jobName",wantPos);
        postRequest = params(postRequest,"workType",workType);//工作类型 1、兼职 2、全职
        postRequest = params(postRequest,"expectCity",wantCity);
        postRequest = params(postRequest,"expectSalary",wantMoney);

        postRequest = params(postRequest,"myEvaluate",introduct);

        postRequest = params(postRequest,"headImg",headImg);

        postRequest.execute(new JsonCallBack<EditIntroBean>() {
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

    private PostRequest params(PostRequest postRequest,String key,String value){
        if(!value.equals("")){
            postRequest.params(key,value);
        }
        return postRequest;
    }

    private PostRequest paramsLong(PostRequest postRequest,String key,String value){
        if(!value.equals("")){
            long val = Long.parseLong(value);
            postRequest.params(key,val);
        }
        return postRequest;
    }

}
