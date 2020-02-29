package cn.com.qc.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/10/17.
 */

public class FindHotCompany {

    /*{
        "data": {
        "enterpriseHot": [
        {
            "address": "北京市大兴",
                "id": "3",
                "jobNumber": 0,
                "name": "牛牛集团",
                "personNumber": 0
        },
        {
            "address": "北京市朝阳",
                "id": "1",
                "jobNumber": 4,
                "name": "青青草原科技",
                "personNumber": 124
        }
        ],
        "informationHot": [
        {
            "context": "null",
                "createDate": 1507415947000,
                "id": "3",
                "imgUrl": "/images/headImg.jpg",
                "isDel": 0,
                "look": 40,
                "synopsis": "这是一个资讯3",
                "title": "资讯3"
        },
        {
            "context": "null",
                "createDate": 1506983943000,
                "id": "2",
                "imgUrl": "/images/97e4d7eecb734aa8975eef82f3201580.png",
                "isDel": 0,
                "look": 33,
                "synopsis": "这是一个资讯2",
                "title": "资讯2"
        }
        ]
    },
        "hideMessage": "",
            "infoCode": 1,
            "message": "数据获取成功!"
    }*/

    String hideMessage;

    String message;

    int infoCode;

    FindData data;

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

    public FindData getData() {
        return data;
    }

    public void setData(FindData data) {
        this.data = data;
    }

    public class FindData{
        List<Company> enterpriseHot;
        List<Infomation> informationHot;

        public List<Company> getEnterpriseHot() {
            return enterpriseHot;
        }

        public void setEnterpriseHot(List<Company> enterpriseHot) {
            this.enterpriseHot = enterpriseHot;
        }

        public List<Infomation> getInformationHot() {
            return informationHot;
        }

        public void setInformationHot(List<Infomation> informationHot) {
            this.informationHot = informationHot;
        }
    }

    public class Infomation{
        /*"context": "null",
                "createDate": 1507415947000,
                "id": "3",
                "imgUrl": "/images/headImg.jpg",
                "isDel": 0,
                "look": 40,
                "synopsis": "这是一个资讯3",
                "title": "资讯3"*/

        String context;

        long createDate;

        String id;

        String imgUrl;

        int isDel;

        int look;

        String synopsis;

        String title;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
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

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public int getLook() {
            return look;
        }

        public void setLook(int look) {
            this.look = look;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class Company{
        /*"address": "北京市大兴",
                "id": "3",
                "jobNumber": 0,
                "name": "牛牛集团",
                "personNumber": 0*/

        String address;

        String id;

        int jobNumber;

        String name;

        int personNumber;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getJobNumber() {
            return jobNumber;
        }

        public void setJobNumber(int jobNumber) {
            this.jobNumber = jobNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPersonNumber() {
            return personNumber;
        }

        public void setPersonNumber(int personNumber) {
            this.personNumber = personNumber;
        }
    }

}
