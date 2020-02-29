package cn.com.qc.bean;

/**
 * Created by lenovo on 2017/12/21.
 */

public class LoginBean {

    /*{
        "data": {
        "province":"省",
                "area":"区",
                "city": "市",
                "headImg": "http://192.168.3.113:8080/qicheng/images/headImg.jpg",
                "id": "730cc0f9-20e4-46d3-940b-08840ba5f2dd",
                "name": "用户1735",
                "token": "4477e11c3dd547848f471d92366df099"
    },
        "hidemsg": "",
            "status": 1,
            "msg": "登录成功！"
    }*/

    String hidemsg;

    String status;

    String msg;

    LoginData data;

    String token = "logined";

    public String getHidemsg() {
        return hidemsg;
    }

    public void setHidemsg(String hidemsg) {
        this.hidemsg = hidemsg;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getmsg() {
        return msg;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public class LoginData{
        String province;
        String area;
        String city;
        String headImg;
        String id;
        String realName;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return realName;
        }

        public void setName(String name) {
            this.realName = name;
        }
    }

}
