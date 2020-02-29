package cn.com.qc.bean;

import java.util.List;

/**资讯返回数据
 * Created by lenovo on 2017/10/16.
 */

public class Information {

     /*"id": "3",
             "imgUrl": "地址3",
             "synopsis": "这是一个资讯3",
             "title": "资讯3"*/

    String id;

    String imgUrl;

    String synopsis;

    String title;

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
