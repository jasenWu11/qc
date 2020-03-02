package cn.com.qc.bean;

import java.util.ArrayList;
import java.util.List;

/**返回的各种搜索条件
 * Created by lenovo on 2017/10/16.
 */

public class HomeCondition {

    /**
     * 企业类型
     */
    List<CompanyType> companyTypes;

    /**
     * 行业类型
     */
    List<Industry> industrys;

    /**
     * 工作类型
     */
    List<Job> jobs;

    public HomeCondition(){
        companyTypes = new ArrayList<>();
        industrys = new ArrayList<>();
        jobs = new ArrayList<>();
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<CompanyType> getCompanyTypes() {
        return companyTypes;
    }

    public void setCompanyTypes(List<CompanyType> companyTypes) {
        this.companyTypes = companyTypes;
    }

    public List<Industry> getIndustrys() {
        return industrys;
    }

    public void setIndustrys(List<Industry> industrys) {
        this.industrys = industrys;
    }
}
