package cn.com.qc.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchPositionBean {

    String infoCode;

    String message;

    SearchPositionD data;

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

    public SearchPositionD getData() {
        return data;
    }

    public void setData(SearchPositionD data) {
        this.data = data;
    }

    public class SearchPositionD{
        List<SearchPositionData> list;
        String pageCount;
        String pageNumber;
        String pageSize;
        String total;

        public List<SearchPositionData> getList() {
            return list;
        }

        public void setList(List<SearchPositionData> list) {
            this.list = list;
        }

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public String getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(String pageNumber) {
            this.pageNumber = pageNumber;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public class SearchPositionData {
        private String id;
        private String title;
        private String jobName;//岗位名称
        private String address;//工作地点
        private Integer salary;//薪资
        private String beginDate;//招聘日期
        private String endDate;//结束日期
        private String enterpriseName;//公司
        private String peopleNumber;//人数
        private String activityId;//专场id

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getSalary() {
            return salary;
        }

        public void setSalary(Integer salary) {
            this.salary = salary;
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

        public String getPeopleNumber() {
            return peopleNumber;
        }

        public void setPeopleNumber(String peopleNumber) {
            this.peopleNumber = peopleNumber;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
    }


}
