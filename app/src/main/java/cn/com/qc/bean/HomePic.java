package cn.com.qc.bean;

import java.util.List;

/**轮播图
 * Created by lenovo on 2017/10/10.
 */

public class HomePic {

    /*"activityId": "0",
            "createDate": 1510263417000,
            "id": "1",
            "imgUrl": "/images/headImg.jpg",
            "isMain": 1*/

    String id;
    String imgUrl;

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
}
