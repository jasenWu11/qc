package cn.com.qc.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/10/16.
 */

public class PositionBean {

    String message;

    int infoCode;

    CompanyInfo data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(int infoCode) {
        this.infoCode = infoCode;
    }

    public CompanyInfo getData() {
        return data;
    }

    public void setData(CompanyInfo data) {
        this.data = data;
    }

    public class CompanyInfo {
        String createDate;
        String describe1;
        String enterpriseType;
        String enterpriseTypeId;
        String id;
        String isHot;
        String isMarket;
        String look;
        String max;
        String min;
        String name;
        String phone;
        String updateTime;
        String address;
        String industryName;

        public String getIndustryName() {
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        List<Job> enterpriseJobList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getDescribe1() {
            return describe1;
        }

        public void setDescribe1(String describe1) {
            this.describe1 = describe1;
        }

        public String getEnterpriseType() {
            return enterpriseType;
        }

        public void setEnterpriseType(String enterpriseType) {
            this.enterpriseType = enterpriseType;
        }

        public String getEnterpriseTypeId() {
            return enterpriseTypeId;
        }

        public void setEnterpriseTypeId(String enterpriseTypeId) {
            this.enterpriseTypeId = enterpriseTypeId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsHot() {
            return isHot;
        }

        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }

        public String getIsMarket() {
            return isMarket;
        }

        public void setIsMarket(String isMarket) {
            this.isMarket = isMarket;
        }

        public String getLook() {
            return look;
        }

        public void setLook(String look) {
            this.look = look;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<Job> getEnterpriseJobList() {
            return enterpriseJobList;
        }

        public void setEnterpriseJobList(List<Job> enterpriseJobList) {
            this.enterpriseJobList = enterpriseJobList;
        }
    }

    public class Job{
        String activityId;
        String address;
        String beginDate;
        String endDate;
        String enterpriseName;
        String id;
        String jobName;
        String peopleNumber;
        String salary;
        String title;
        String dayReport;

        public String getDayReport() {
            return dayReport;
        }

        public void setDayReport(String dayReport) {
            this.dayReport = dayReport;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
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

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getPeopleNumber() {
            return peopleNumber;
        }

        public void setPeopleNumber(String peopleNumber) {
            this.peopleNumber = peopleNumber;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
