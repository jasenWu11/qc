package cn.com.qc.main;

import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tandong.sa.tools.AssistTool;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.app.App;
import cn.com.qc.bean.LoginBean;
import cn.com.qc.bean.ThirdUserInfo;
import cn.com.qc.help.Helps;
import cn.com.qc.help.NetUrl;
import cn.com.qc.yinter.LoginThirdAuthorizeListener;
import cn.com.qc.ypresenter.LoginPresenter;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by lenovo on 2017/12/7.
 */

public class LoginActivity extends YBaseActivity<LoginPresenter, LoginActivity> implements LoginThirdAuthorizeListener {

    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.pw)
    EditText pw;

    App app;
    private AssistTool assistTool;

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    public int getResId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        app = (App) getApplication();
        assistTool = new AssistTool(this);
    }

    @OnClick({R.id.wx, R.id.xl, R.id.qq, R.id.register, R.id.forget, R.id.enter})
    public void authorize(View view) {
        switch (view.getId()) {
            case R.id.wx:
                p.authorize(Wechat.NAME, this);
                break;
            case R.id.xl:
                p.authorize(SinaWeibo.NAME, this);
                break;
            case R.id.qq:
                p.authorize(QQ.NAME, this);
                break;
            case R.id.register:
                openActivity(RegisterActivity.class);
                break;
            case R.id.forget:
                openActivity(ForgetActivity.class);
                break;
            case R.id.enter:
                enter();
                break;
        }
    }

    private void enter() {
        String phone = this.phone.getText().toString().trim();
        if (phone.equals("")) {
            Helps.toast(this, "手机号码不能为空");
            return;
        }
        String password = pw.getText().toString().trim();
        if (password.equals("")) {
            Helps.toast(this, "密码不能为空");
            return;
        }
        if (!checkBox.isChecked()) {
            Helps.toast(this, "须同意用户协议");
            return;
        }
        p.enter(NetUrl.DNS + NetUrl.Login, phone, password);
    }

    @Override
    public void authorizeComplete(ThirdUserInfo userInfo) {
        if (userInfo != null) {

        }
    }

    @Override
    public void onLoginError() {
        Helps.toast(this, "登录失败");
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        Helps.toast(this, loginBean.getmsg());
        if (loginBean.getstatus().equals("0")) {

            String phone = this.phone.getText().toString().trim();

            LoginBean.LoginData loginData = loginBean.getData();
            String id = loginData.getId();
            String area = loginData.getArea();
            String city = loginData.getCity();
            String headImg = loginData.getHeadImg();
            String name = loginData.getName();
            String province = loginData.getProvince();
            String token = loginBean.getToken();
            app.id = id;
            app.token = token;
            app.headImg = headImg;
            app.userName = name;
            remain(phone, token, id, headImg, name);
            assistTool.savePreferenceString("localToken", token);
            assistTool.savePreferenceString("studentId", id);
            openActivity(MainActivity.class);
            finish();
        }
    }

    private void remain(String phone, String token, String id, String headImg, String userName) {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", phone);
        editor.putString("token", token);
        editor.putString("id", id);
        editor.putString("headImg", headImg);
        editor.putString("userName", userName);
        editor.commit();
    }

}
