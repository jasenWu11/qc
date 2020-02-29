package cn.com.qc.yinter;

import cn.com.qc.bean.ImageMoreUpBean;

/**
 * Created by lenovo on 2017/12/27.
 */

public interface CardCheckDataListener {

    void onError();

    void onUpImageMoreSuccess(ImageMoreUpBean data);

    void  onCommitSuccess(String infoCode,String message);

}
