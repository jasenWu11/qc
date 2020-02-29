package cn.com.qc.main;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.app.App;
import cn.com.qc.bean.MyIntroductionBean;
import cn.com.qc.bean.UserIntroInfo;
import cn.com.qc.help.NetUrl;
import cn.com.qc.yinter.MyIntroductionDataListener;
import cn.com.qc.ypresenter.MyIntroductionPresenter;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

/**
 * Created by lenovo on 2017/11/15.
 */

public class MyIntroductionActivity extends YBaseActivity<MyIntroductionPresenter,MyIntroductionActivity> implements MyIntroductionDataListener {

    @BindView(R.id.bar)
    public RelativeLayout bar;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.nick)
    TextView nick;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.edu)
    TextView edu;
    @BindView(R.id.workTime)
    TextView workTime;
    @BindView(R.id.outYear)
    TextView outYear;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.connectPhone)
    TextView connectPhone;
    @BindView(R.id.connectEmail)
    TextView connectEmail;

    @BindView(R.id.workLinear)
    LinearLayout workLinear;
    @BindView(R.id.selfIntroLinear)
    LinearLayout selfIntroLinear;
    @BindView(R.id.eduLinear)
    LinearLayout eduLinear;

    @Override
    public MyIntroductionPresenter getPresenter() {
        return new MyIntroductionPresenter();
    }

    @Override
    public int getResId() {
        return R.layout.activity_self_info;
    }

    //18237191735 4477e11c3dd547848f471d92366df099

    App app;

    @Override
    public void init() {
        userIntroInfo = new UserIntroInfo();
        app = (App) getApplication();
        p.getAllDatas(NetUrl.DNS + NetUrl.GetInfo,app.phone,app.token);
    }

    @OnClick({R.id.returns,R.id.set})
    public void click(View view){
        switch (view.getId()){
            case  R.id.returns:
                finish();
                break;
            case R.id.set:
                Bundle bundle = new Bundle();
                bundle.putString("phone",app.phone);
                bundle.putString("studentId",app.id);
                bundle.putSerializable("object",userIntroInfo);
                openActivity(EditIntroductionActivity.class,bundle);
                break;
        }
    }

    UserIntroInfo userIntroInfo;

    @Override
    public void onEdu(List<MyIntroductionBean.Education> educations) {
        if(educations != null&&educations.size() > 0){
            MyIntroductionBean.Education edu = educations.get(0);

            String schName = edu.getSchoolName();
            String educa = edu.getEducation();
            String maj = edu.getMajor();
            String beginT = edu.getBeginTime();
            String endT = edu.getEndTime();

            String studentId = edu.getId();

            if((schName != null&&!schName.equals(""))||(educa != null&&!educa.equals(""))
                    ||(maj != null&&!maj.equals(""))||(beginT != null&&!beginT.equals(""))
                    ||(endT != null&&!endT.equals(""))){
                View view = LinearLayout.inflate(this,R.layout.edu_item,null);
                TextView beginTime = (TextView) view.findViewById(R.id.beginTime);
                TextView endTiem = (TextView) view.findViewById(R.id.endTiem);
                TextView schoolName = (TextView) view.findViewById(R.id.schoolName);
                //    TextView education = (TextView) view.findViewById(R.id.education);
                TextView major = (TextView) view.findViewById(R.id.major);

                setText(schoolName,"院校名称：",schName);
                setText(this.edu,"最高学历：",educa);
                setText(major,"专业：",maj);

                try{
                    setText(beginTime,"入学时间：",new SimpleDateFormat("yyyy年MM月dd日").format(new Date(Long.parseLong(beginT))));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }

                try{
                    setText(endTiem,"毕业时间：",new SimpleDateFormat("yyyy年MM月dd日").format(new Date(Long.parseLong(endT))));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
                eduLinear.addView(view);
            }

            userIntroInfo.setHasValue(true);
            userIntroInfo.setSchoolName(schName);
            userIntroInfo.setEdu(educa);
            userIntroInfo.setMajor(maj);
            userIntroInfo.setEduBeginTime(beginT);
            userIntroInfo.setEduEndTime(endT);
            //------------------------------
            userIntroInfo.setStudentId(studentId);
        }
    }

    @BindView(R.id.exceptWorkLinear)
    LinearLayout exceptWorkLinear;

    @Override
    public void onHopeJob(List<MyIntroductionBean.HopeJob> hopeJobs) {
        if(hopeJobs != null&&hopeJobs.size() > 0){
            MyIntroductionBean.HopeJob hopeJob = hopeJobs.get(0);
            String expectSalary = hopeJob.getExpectSalary();
            String jobName = hopeJob.getJobName();

            String studentId = hopeJob.getId();

            if((expectSalary != null&&!expectSalary.equals(""))||(jobName != null&&!jobName.equals(""))){
                View view = LinearLayout.inflate(this,R.layout.hopejob_item,null);
                TextView position = (TextView) view.findViewById(R.id.position);
                TextView salary = (TextView) view.findViewById(R.id.salary);
                exceptWorkLinear.addView(view);

                setText(position,"职位：",jobName);
                setText(salary,"薪资：",expectSalary);
            }

            userIntroInfo.setHasValue(true);
            userIntroInfo.setExceptPosition(jobName);
            userIntroInfo.setExceptSalary(expectSalary);
            //------------------------------
            userIntroInfo.setHopeJobId(studentId);
        }
    }

    @Override
    public void onStudent(MyIntroductionBean.Student student) {
        if(student != null){
            String name = student.getName();
            String city = student.getCity();
            String headImg = student.getHeadImg();
            int width = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP,58,getResources().getDisplayMetrics());
            p.intoCircle(this,headImg,icon, R.mipmap.head_img,width,width);
            String sex = student.getSex();
            String birthDate = student.getBirthDate();

            String email = student.getEmail();
            String phone = student.getPhone();
            String myEvaluate = student.getMyEvaluate();
            String workYear = student.getWorkYear();

            setText(this.nick,"",name);
            setText(this.name,"姓名：",name);
            setText(this.city,"所在城市：",city);
            setText(this.workTime,"工作年限：",workYear);

            if(sex == null){
                this.sex.setText("性别：");
            }else{
                if(sex.equals("1")){
                    this.sex.setText("性别：" + "男");
                    userIntroInfo.setSex("男");
                }else{
                    this.sex.setText("性别：" + "女");
                    userIntroInfo.setSex("女");
                }
            }

            try{
                setText(this.outYear,"出生年份：",new SimpleDateFormat("yyyy年MM月dd日").format(new Date(Long.parseLong(birthDate))));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

            setText(this.connectEmail,"联系邮箱：",email);
            setText(this.connectPhone,"联系电话：",phone);

            if(myEvaluate != null&&!myEvaluate.equals("")){
                View view = LinearLayout.inflate(this,R.layout.student_intro_item,null);
                TextView intro = (TextView) view.findViewById(R.id.intro);
                setText(intro,"",myEvaluate);
                selfIntroLinear.addView(view);
                userIntroInfo.setMyEvaluate(myEvaluate);
            }

            userIntroInfo.setHasValue(true);
            userIntroInfo.setHeadImg(headImg);
            userIntroInfo.setName(name);
            userIntroInfo.setOutYear(workYear);
            userIntroInfo.setBirthDate(birthDate);
            userIntroInfo.setCity(city);
            userIntroInfo.setConnectPhone(phone);
            userIntroInfo.setConnectEmail(email);
        }
    }

    @Override
    public void onWork(List<MyIntroductionBean.Work> works) {
        if(works != null&&works.size() > 0){
            MyIntroductionBean.Work work = works.get(0);

            String enterpriseName = work.getEnterpriseName();
            String job = work.getJob();
            String beginTime = work.getBeginTime();
            String endTime = work.getEndTime();
            String jobContent = work.getJobContent();

            String studentId = work.getId();

            if((enterpriseName != null&&!enterpriseName.equals(""))||(job != null&&!job.equals(""))
                    ||(beginTime != null&&!beginTime.equals(""))||(endTime != null&&!endTime.equals(""))
                    ||(jobContent != null&&!jobContent.equals(""))){
                View view = LinearLayout.inflate(this,R.layout.work_experience_item,null);
                TextView company = (TextView) view.findViewById(R.id.company);
                TextView position = (TextView) view.findViewById(R.id.position);
                TextView beginDate = (TextView) view.findViewById(R.id.enterDate);
                TextView endDate = (TextView) view.findViewById(R.id.endDate);
                TextView workContent = (TextView) view.findViewById(R.id.workContent);

                setText(company,"就职公司：",enterpriseName);
                setText(position,"职位：",job);
                try{
                    setText(beginDate,"入职时间：",new SimpleDateFormat("yyyy年MM月dd日").format(new Date(Long.parseLong(beginTime))));
                }catch (NumberFormatException e){
                    e.printStackTrace();

                }
                try{
                    setText(endDate,"离职时间：",new SimpleDateFormat("yyyy年MM月dd日").format(new Date(Long.parseLong(endTime))));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
                workLinear.addView(view);
                setText(workContent,"工作内容：",jobContent);
            }

            userIntroInfo.setHasValue(true);
            userIntroInfo.setCompany(enterpriseName);
            userIntroInfo.setPosition(job);
            userIntroInfo.setBeginTime(beginTime);
            userIntroInfo.setEndTime(endTime);
            userIntroInfo.setJobContent(jobContent);
            //--------------------------------
            userIntroInfo.setWorkId(studentId);
        }
    }

    @Override
    public void onError() {

    }

    private void setText(TextView textView, String t,String content){
        if(content == null){
            textView.setText(t);
        }else{
            textView.setText(t + content);
        }
    }

}
