package cn.com.qc.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/12/15.
 */

public class MyIntroductionBean {

    MyIntroductionD data;

    String hideMessage;

    String infoCode;

    String message;

    public MyIntroductionD getData() {
        return data;
    }

    public void setData(MyIntroductionD data) {
        this.data = data;
    }

    public String getHideMessage() {
        return hideMessage;
    }

    public void setHideMessage(String hideMessage) {
        this.hideMessage = hideMessage;
    }

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class MyIntroductionD{
        List<Education> enducation;
        List<HopeJob> hopeJob;
        Student student;
        List<Work> work;

        public List<Education> getEnducation() {
            return enducation;
        }

        public void setEnducation(List<Education> enducation) {
            this.enducation = enducation;
        }

        public List<HopeJob> getHopeJob() {
            return hopeJob;
        }

        public void setHopeJob(List<HopeJob> hopeJob) {
            this.hopeJob = hopeJob;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public List<Work> getWork() {
            return work;
        }

        public void setWork(List<Work> work) {
            this.work = work;
        }
    }

    public class Education{
        String beginTime;
        String createDate;
        String education;
        String id;
        String isDel;
        String major;
        String schoolName;
        String status;
        String studentId;
        String endTime;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsDel() {
            return isDel;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }

    public class HopeJob{
        String createDate;
        String expectSalary;
        String id;
        String isDel;
        String jobId;
        String jobName;
        String studentId;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getExpectSalary() {
            return expectSalary;
        }

        public void setExpectSalary(String expectSalary) {
            this.expectSalary = expectSalary;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsDel() {
            return isDel;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }

    public class Student {
        String id;//主键
        String phone;//电话，用户账号
        String password;//密码
        String name;//姓名
        Integer age;//年龄
        String sex;//性别 1、男  2、女
        String birthDate;//出生日期
        Integer height;//身高 单位：cm
        Double weight;//体重 单位：kg
        String headImg;//头像
        String token;//登录令牌
        String device;//设备类型 1、安卓  2、ios
        String deviceId;//设备id
        String hobbys;//爱好
        String myEvaluate;//自我评价
        Integer isDel;//是否删除 0、未删除  1、删除
        Integer status;//登录状态 0、未登录 1、 登录
        String province;//省
        String city;//市
        String area;//区域
        String email;//邮箱
        String workYear;

        public String getWorkYear() {
            return workYear;
        }

        public void setWorkYear(String workYear) {
            this.workYear = workYear;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getHobbys() {
            return hobbys;
        }

        public void setHobbys(String hobbys) {
            this.hobbys = hobbys;
        }

        public String getMyEvaluate() {
            return myEvaluate;
        }

        public void setMyEvaluate(String myEvaluate) {
            this.myEvaluate = myEvaluate;
        }

        public Integer getIsDel() {
            return isDel;
        }

        public void setIsDel(Integer isDel) {
            this.isDel = isDel;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getAge() {

            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    public class Work{
        String beginTime;
        String city;
        String createTime;
        String endTime;
        String enterpriseName;
        String id;
        String isDel;
        String job;
        String jobContent;
        String studentId;

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsDel() {
            return isDel;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getJobContent() {
            return jobContent;
        }

        public void setJobContent(String jobContent) {
            this.jobContent = jobContent;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
    }
}
