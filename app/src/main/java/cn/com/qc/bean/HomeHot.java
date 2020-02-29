package cn.com.qc.bean;

import java.util.List;

/**热点公司
 * Created by lenovo on 2017/9/25.
 */

public class HomeHot {

    /*"createDate": 1510179633000,
            "id": "1",
            "imgUrl": "/images/defultIndustry.png",
            "isShow": "1",
            "name": "金融",
            "remark": "金融行业xxxxxx"*/

    String message;

    String hideMessage;

    String infoCode;

    List<HomeHotData> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<HomeHotData> getData() {
        return data;
    }

    public void setData(List<HomeHotData> data) {
        this.data = data;
    }

    public class HomeHotData{
        String createDate;

        String id;

        String imgUrl;

        String isShow;

        String name;

        String remark;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

}
