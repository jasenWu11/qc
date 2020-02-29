package cn.com.qc.bean;

/**
 * Created by lenovo on 2017/12/15.
 */

public class UpImageBean {

       /*"data":"图片路径"
               "infoCode": 1,
               "message": "上传成功！"*/
    String data;

    String infoCode;

    String message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
}
