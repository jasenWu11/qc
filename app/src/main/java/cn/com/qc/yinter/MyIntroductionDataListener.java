package cn.com.qc.yinter;

import java.util.List;

import cn.com.qc.bean.MyIntroductionBean;

/**
 * Created by lenovo on 2017/12/15.
 */

public interface MyIntroductionDataListener {

    void onEdu(List<MyIntroductionBean.Education> educations);

    void onHopeJob(List<MyIntroductionBean.HopeJob> hopeJobs);

    void onStudent(MyIntroductionBean.Student student);

    void onWork(List<MyIntroductionBean.Work> works);

    void onError();

}
