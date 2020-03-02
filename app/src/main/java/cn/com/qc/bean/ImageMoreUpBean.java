package cn.com.qc.bean;

/**
 * Created by lenovo on 2017/12/27.
 */

public class ImageMoreUpBean {

    /*{
        "data":[
        "图片路径"，
        "图片路径"
    ]
        "infoCode": 1,
            "message": "上传成功！"
    }*/

    int infoCode;

    String message;

    String[] data;

    public int getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(int infoCode) {
        this.infoCode = infoCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
