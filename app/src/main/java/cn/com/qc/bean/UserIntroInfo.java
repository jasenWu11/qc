package cn.com.qc.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/12/21.
 */

public class UserIntroInfo implements Serializable{

    String headImg;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    String name;

    String sex;//1或2

    String edu;

    String outYear;

    String birthDate;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    String city;

    String connectPhone;

    String connectEmail;


    //工作
    String workId;

    String company;

    String position;

    String beginTime;

    String endTime;

    String jobContent;


    //教育
    String studentId;

    String schoolName;

    String major;

    String eduBeginTime;

    String eduEndTime;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEduBeginTime() {
        return eduBeginTime;
    }

    public void setEduBeginTime(String eduBeginTime) {
        this.eduBeginTime = eduBeginTime;
    }

    public String getEduEndTime() {
        return eduEndTime;
    }

    public void setEduEndTime(String eduEndTime) {
        this.eduEndTime = eduEndTime;
    }



    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getHopeJobId() {
        return hopeJobId;
    }

    public void setHopeJobId(String hopeJobId) {
        this.hopeJobId = hopeJobId;
    }
    //期望
    String hopeJobId;

    String exceptPosition;

    String exceptSalary;


    String myEvaluate;


    boolean hasValue = false;

    public boolean isHasValue() {
        return hasValue;
    }

    public void setHasValue(boolean hasValue) {
        this.hasValue = hasValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getOutYear() {
        return outYear;
    }

    public void setOutYear(String outYear) {
        this.outYear = outYear;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConnectPhone() {
        return connectPhone;
    }

    public void setConnectPhone(String connectPhone) {
        this.connectPhone = connectPhone;
    }

    public String getConnectEmail() {
        return connectEmail;
    }

    public void setConnectEmail(String connectEmail) {
        this.connectEmail = connectEmail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getMyEvaluate() {
        return myEvaluate;
    }

    public void setMyEvaluate(String myEvaluate) {
        this.myEvaluate = myEvaluate;
    }

    public String getExceptPosition() {
        return exceptPosition;
    }

    public void setExceptPosition(String exceptPosition) {
        this.exceptPosition = exceptPosition;
    }

    public String getExceptSalary() {
        return exceptSalary;
    }

    public void setExceptSalary(String exceptSalary) {
        this.exceptSalary = exceptSalary;
    }

}
