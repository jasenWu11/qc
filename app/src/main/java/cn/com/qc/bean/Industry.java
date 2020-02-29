package cn.com.qc.bean;

import java.util.List;

/**行业类型及工作类型
 * Created by lenovo on 2017/10/16.
 */

public class Industry {

    /*"createDate": 1510179633000,
            "id": "1",
            "imgUrl": "/images/defultIndustry.png",
            "isShow": "1",
            "name": "金融",
            "remark": "金融行业xxxxxx"*/

    String id;

    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* List<SuredType> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SuredType> getList() {
        return list;
    }

    public void setList(List<SuredType> list) {
        this.list = list;
    }

    public class SuredType{

        String id;

        String indid;

        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIndid() {
            return indid;
        }

        public void setIndid(String indid) {
            this.indid = indid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }*/

}
