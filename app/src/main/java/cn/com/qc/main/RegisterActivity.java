package cn.com.qc.main;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.bean.ThirdUserInfo;
import cn.com.qc.help.Helps;
import cn.com.qc.help.NetUrl;
import cn.com.qc.yinter.LoginThirdAuthorizeListener;
import cn.com.qc.yinter.RegisterDataListener;
import cn.com.qc.ypresenter.LoginPresenter;
import cn.com.qc.ypresenter.RegisterPresenter;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by lenovo on 2017/12/7.
 */

public class RegisterActivity extends YBaseActivity<RegisterPresenter,RegisterActivity> implements RegisterDataListener{

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.pw)
    EditText pw;
    @BindView(R.id.checkBox)
    CheckBox checkBox;

    @Override
    public RegisterPresenter getPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public int getResId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.wx,R.id.xl,R.id.qq,R.id.send,R.id.complete})
    public void authorize(View view){
        switch (view.getId()){
            case R.id.wx:

                break;
            case R.id.xl:

                break;
            case R.id.qq:

                break;
//            case R.id.send:
//                sendCode();
//                break;
            case R.id.complete:
                complete();
                break;
        }
    }

//    private void sendCode(){
//        String phone = this.phone.getText().toString().trim();
//        if(phone.equals("")){
//            Helps.toast(this,"电话号码不能为空");
//            return;
//        }
//        p.sendCode(NetUrl.DNS + NetUrl.SendCode,phone,1);
//    }

    private void complete(){
        String phone = this.phone.getText().toString().trim();
        if(phone.equals("")){
            Helps.toast(this,"电话号码不能为空");
            return;
        }
//        String code = this.code.getText().toString().trim();
//        if(code.equals("")){
//            Helps.toast(this,"验证码不能为空");
//            return;
//        }
        String password = this.pw.getText().toString().trim();
        if(password.equals("")){
            Helps.toast(this,"密码不能为空");
            return;
        }

        if(password.length() < 6||password.length() > 16){
            Helps.toast(this,"密码长度为6-16位之间");
            return;
        }

        if(!checkBox.isChecked()){
            Helps.toast(this,"须同意用户协议才能注册");
            return;
        }

        p.register(NetUrl.DNS + NetUrl.Register,phone,password);
    }

//    @Override
//    public void onSendCodeError() {
//        Helps.toast(this,"验证码发送失败");
//    }

//    @Override
//    public void onSendCodeSuccess(String infoCode, String message) {
//        Helps.toast(this,message);
//    }

    @Override
    public void onRegisterError() {
        Helps.toast(this,"注册失败");
    }

    @Override
    public void onRegisterSuccess(String status, String msg) {
        Helps.toast(this,msg);
        if(status.equals("0")){
            openActivity(LoginActivity.class);
            finish();
        }
    }


}
