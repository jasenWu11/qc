package cn.com.qc.bean;

/**工作类型
 * Created by lenovo on 2017/12/7.
 */

public class Job {

   /* "createTime": 1510179748000,
            "id": "1",
            "industryId": "3",
            "name": "打字员"*/

    long createTime;

    String id;

    String industryId;

    String name;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
