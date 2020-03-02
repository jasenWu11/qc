package cn.com.qc.bean;

import java.util.List;

/**首页返回数据
 * Created by lenovo on 2017/10/16.
 */

public class HomeReturnDataBean {

    String hideMessage;

    String message;

    int infoCode;

    ReturnData data;

    public String getHideMessage() {
        return hideMessage;
    }

    public void setHideMessage(String hideMessage) {
        this.hideMessage = hideMessage;
    }

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

    public ReturnData getData() {
        return data;
    }

    public void setData(ReturnData data) {
        this.data = data;
    }

    /**
     * 首页返回数据
     */
    public class ReturnData{
        /*
         *企业类型
         */
        List<CompanyType> enterpriseTypes;
        /**
         * 首页轮播图
         */
        List<HomePic> homeImgs;

        /**
         *行业类型
         */
        List<Industry> industryTypes;

        /**
         * 工作类型
         */
        List<Job> jobEntityes;

        /**
         * 资讯
         */
        List<Information> informations;

        public List<CompanyType> getEnterpriseTypes() {
            return enterpriseTypes;
        }

        public void setEnterpriseTypes(List<CompanyType> enterpriseTypes) {
            this.enterpriseTypes = enterpriseTypes;
        }

        public List<HomePic> getHomeImgs() {
            return homeImgs;
        }

        public void setHomeImgs(List<HomePic> homeImgs) {
            this.homeImgs = homeImgs;
        }

        public List<Industry> getIndustryTypes() {
            return industryTypes;
        }

        public void setIndustryTypes(List<Industry> industryTypes) {
            this.industryTypes = industryTypes;
        }

        public List<Job> getJobEntityes() {
            return jobEntityes;
        }

        public void setJobEntityes(List<Job> jobEntityes) {
            this.jobEntityes = jobEntityes;
        }

        public List<Information> getInformations() {
            return informations;
        }

        public void setInformations(List<Information> informations) {
            this.informations = informations;
        }
    }

}
