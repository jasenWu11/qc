package cn.com.qc.yinter;

import cn.com.qc.bean.LoginBean;
import cn.com.qc.bean.ThirdUserInfo;

/**授权后，返回信息的接口
 * Created by lenovo on 2017/12/7.
 */

public interface LoginThirdAuthorizeListener {

    void authorizeComplete(ThirdUserInfo userInfo);

    void onLoginError();

    void onLoginSuccess(LoginBean loginBean);

}
