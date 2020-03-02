package cn.com.qc.yinter;

/**
 * Created by lenovo on 2017/12/20.
 */

public interface RegisterDataListener {

    void onRegisterError();

    void onRegisterSuccess(String infoCode,String message);

}
