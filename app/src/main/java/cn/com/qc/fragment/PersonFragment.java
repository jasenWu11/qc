package cn.com.qc.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tandong.sa.tools.AssistTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.app.App;
import cn.com.qc.help.Helps;
import cn.com.qc.help.NetUrl;
import cn.com.qc.leeactivity.CollectCompanyActivity;
import cn.com.qc.leeactivity.CollectJobActivity;
import cn.com.qc.leeactivity.MyJobActivity;
import cn.com.qc.leeactivity.Questionnaire;
import cn.com.qc.main.CardCheckActivity;
import cn.com.qc.main.CollectActivity;
import cn.com.qc.main.ConnectUsActivity;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.main.MyIntroductionActivity;
import cn.com.qc.main.RegisterActivity;
import cn.com.qc.view.CropCircleTransformation;
import cn.com.qc.yinter.BaseInter;
import cn.com.qc.yinter.PersonDataListener;
import cn.com.qc.ypresenter.BasePresenter;
import cn.com.qc.ypresenter.LoginPresenter;
import cn.com.qc.ypresenter.PersonPresener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Lee on 2017/9/21.
 */

public class PersonFragment extends Fragment implements BaseInter, PersonDataListener {

    Unbinder unbinder;

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.loginLinear)
    LinearLayout loginLinear;

    App app;

    PersonPresener p;

    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";
    private Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        p = new PersonPresener();
        p.attach(this);

        assistTool = new AssistTool(getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getActivity().getApplication();
    }

    String imgUrl;

    @Override
    public void onResume() {
        super.onResume();
        initAppData();
        if (app.id != null) {

            loginLinear.setVisibility(View.GONE);
            userName.setVisibility(View.VISIBLE);
            userName.setText(app.userName);
            int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, getResources().getDisplayMetrics());

            if (imgUrl != null) {
                if (!imgUrl.equals(app.headImg)) {
                    p.intoCircle(getActivity(), app.headImg, icon, R.mipmap.head_img, w, w);
                    imgUrl = app.headImg;
                }
            } else {
                p.intoCircle(getActivity(), app.headImg, icon, R.mipmap.head_img, w, w);
            }
        }
    }

    private void initAppData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        app.phone = sharedPreferences.getString("phone", null);
        app.token = sharedPreferences.getString("token", null);
        app.id = sharedPreferences.getString("id", null);
        app.headImg = sharedPreferences.getString("headImg", null);
        app.userName = sharedPreferences.getString("userName", null);
    }

    private void checkIfLogin(Class clazz) {
        if (app.token == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            return;
        }
        Intent cardIntent = new Intent(getActivity(), clazz);
        startActivity(cardIntent);
    }

    @OnClick({R.id.myIntro, R.id.login, R.id.register, R.id.myCheck, R.id.myCollect, R.id.quit,
            R.id.alreadyApply, R.id.waitJob, R.id.waitPay, R.id.alreadyPay, R.id.myPos, R.id.connectR
            ,R.id.myRecruit,R.id.myNature})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.myIntro:
                checkIfLogin(MyIntroductionActivity.class);
                //    Intent introIntent = new Intent(getActivity(), MyIntroductionActivity.class);
                //    startActivity(introIntent);
                break;
            case R.id.myPos:
                System.out.println(localToken+'和'+studentId);
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), CollectCompanyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.login:
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.register:
                Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.myCheck:
                checkIfLogin(CardCheckActivity.class);
                //    Intent cardIntent = new Intent(getActivity(), CardCheckActivity.class);
                //    startActivity(cardIntent);
                break;
            case R.id.myCollect:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), CollectJobActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.quit:
                if (app.token != null) {
                    p.logout(NetUrl.DNS + NetUrl.Logout);
                } else {
                    Helps.toast(getActivity(), "用户尚未登录");
                }
                break;
            case R.id.alreadyApply:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), MyJobActivity.class);
                    intent.putExtra("pos", 0);
                    startActivity(intent);
                }
                break;
            case R.id.waitJob:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), MyJobActivity.class);
                    intent.putExtra("pos", 1);
                    startActivity(intent);
                }
                break;
            case R.id.waitPay:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), MyJobActivity.class);
                    intent.putExtra("pos", 2);
                    startActivity(intent);
                }
                break;
            case R.id.alreadyPay:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), MyJobActivity.class);
                    intent.putExtra("pos", 3);
                    startActivity(intent);
                }
                break;
            case R.id.myNature:
                intent = new Intent(getActivity(), Questionnaire.class);
                startActivity(intent);
                break;
            case R.id.connectR:
                Intent connectIntent = new Intent(getActivity(), ConnectUsActivity.class);
                startActivity(connectIntent);
                break;
            case R.id.myRecruit:
                Helps.toast(getActivity(),"该功能尚未开通");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        p.deAttach();
    }

    private void quit() {
        app.id = null;
        app.userName = null;
        app.headImg = null;
        app.token = null;
        loginLinear.setVisibility(View.VISIBLE);
        userName.setVisibility(View.GONE);
        int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, getResources().getDisplayMetrics());
        p.intoCircle(getActivity(), app.headImg, icon, R.mipmap.head_img, w, w);
        delete();
    }

    private void delete() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onLogoutError() {
        Helps.toast(getContext(), "退出失败");
    }

    @Override
    public void onLogoutSuccess(String infoCode, String message) {
        Helps.toast(getContext(), "成功退出");
        quit();
        assistTool.deleteKey("localToken");
        assistTool.deleteKey("studentId");
    }
}
