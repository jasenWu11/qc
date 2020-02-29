package cn.com.qc.yinter;

/**
 * Created by lenovo on 2017/12/21.
 */

public interface ForgetDataListener {

    void onSendCodeError();

    void onSendCodeSuccess(String infoCode,String message);

    void onCompleteError();

    void onCompleteSuccess(String infoCode,String message);

}
