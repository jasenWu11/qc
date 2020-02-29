package cn.com.qc.main;

import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.help.Helps;
import cn.com.qc.help.NetUrl;
import cn.com.qc.yinter.ForgetDataListener;
import cn.com.qc.ypresenter.ForgetPresenter;

/**
 * Created by lenovo on 2017/12/20.
 */

public class ForgetActivity extends YBaseActivity<ForgetPresenter,ForgetActivity> implements ForgetDataListener {

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.pw)
    EditText pw;

    @Override
    public ForgetPresenter getPresenter() {
        return new ForgetPresenter();
    }

    @Override
    public int getResId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.send,R.id.complete})
    public void click(View view){
        switch (view.getId()){
            case R.id.send:
                sendCode();
                break;
            case R.id.complete:
                complete();
                break;
        }
    }

    private void sendCode(){
        String phone = this.phone.getText().toString().trim();
        if(phone.equals("")){
            Helps.toast(this,"手机号码不能为空");
            return;
        }
        p.sendCode(NetUrl.DNS + NetUrl.SendCode,phone,2);
    }

    private void complete(){
        String phone = this.phone.getText().toString().trim();
        String code = this.code.getText().toString().trim();
        String password = pw.getText().toString().trim();
        if(phone.equals("")){
            Helps.toast(this,"手机号码不能为空");
            return;
        }
        if(code.equals("")){
            Helps.toast(this,"验证码不能为空");
            return;
        }
        if(password.equals("")){
            Helps.toast(this,"密码不能为空");
            return;
        }
        p.complete(NetUrl.DNS + NetUrl.Forget,phone,password,code);
    }

    @Override
    public void onSendCodeError() {
        Helps.toast(this,"验证码发送失败");
    }

    @Override
    public void onSendCodeSuccess(String infoCode, String message) {
        Helps.toast(this,message);
    }

    @Override
    public void onCompleteError() {
        Helps.toast(this,"密码重置失败");
    }

    @Override
    public void onCompleteSuccess(String infoCode, String message) {
        Helps.toast(this,message);
        if(infoCode.equals("1")){
            openActivity(LoginActivity.class);
            finish();
        }
    }
}
