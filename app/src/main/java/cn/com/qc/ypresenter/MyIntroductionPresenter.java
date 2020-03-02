package cn.com.qc.ypresenter;

import java.util.List;

import cn.com.qc.bean.MyIntroductionBean;
import cn.com.qc.help.ServerApi;
import cn.com.qc.main.MyIntroductionActivity;
import cn.com.qc.ymodel.MyIntroductionModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2017/11/15.
 */

public class MyIntroductionPresenter extends BasePresenter<MyIntroductionActivity,MyIntroductionModel> {

    @Override
    public MyIntroductionModel getModel() {
        return new MyIntroductionModel();
    }

    public void getAllDatas(final String url, final String phone, final String token){
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<MyIntroductionBean>() {
            @Override
            public void call(Subscriber<? super MyIntroductionBean> subscriber) {
                m.getAllDatas(subscriber,url,phone,token);
            }
        }, new Observer<MyIntroductionBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MyIntroductionBean introductionBean) {
                MyIntroductionBean.MyIntroductionD d = introductionBean.getData();
                List<MyIntroductionBean.Education> educations = d.getEnducation();
                List<MyIntroductionBean.HopeJob> hopeJobs = d.getHopeJob();
                MyIntroductionBean.Student students = d.getStudent();
                List<MyIntroductionBean.Work> works = d.getWork();
                v.onEdu(educations);
                v.onHopeJob(hopeJobs);
                v.onStudent(students);
                v.onWork(works);
            }
        });
        list.add(subscription);
    }

}
