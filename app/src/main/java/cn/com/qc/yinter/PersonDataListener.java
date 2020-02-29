package cn.com.qc.yinter;

/**
 * Created by lenovo on 2017/12/22.
 */

public interface PersonDataListener {

    void onLogoutError();

    void onLogoutSuccess(String infoCode,String message);

}
