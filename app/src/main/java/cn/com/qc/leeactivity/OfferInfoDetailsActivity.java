package cn.com.qc.leeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tandong.sa.tools.AssistTool;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;

/**
 * 邀约信息
 */

public class OfferInfoDetailsActivity extends BaseActivity {
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.offercontent)
    TextView offercontent;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.jujue)
    Button jujue;
    @BindView(R.id.jieshou)
    Button jieshou;
    private String id = "";
    private String status = "";
    private static final String typeURL = "mobile/replyInvite.do";
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerinfodetails);
        Tools.initSystemBarTint(this);
        ButterKnife.bind(this);
        assistTool = new AssistTool(this);
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        id = getIntent().getStringExtra("id");
        status = getIntent().getStringExtra("status");
        String companyS = getIntent().getStringExtra("companyS");
        company.setText(companyS);
        String contentS = getIntent().getStringExtra("contentS");
        offercontent.setText(contentS);
        String locationS = getIntent().getStringExtra("locationS");
        location.setText(locationS);
        String phoneS = getIntent().getStringExtra("phoneS");
        phone.setText(phoneS);
        if (status.equals("1")) {
            jujue.setVisibility(View.VISIBLE);
            jieshou.setVisibility(View.VISIBLE);
        }
        if (status.equals("2")) {
            jujue.setVisibility(View.VISIBLE);
            jujue.setText("已接受，点击拒绝");
            jieshou.setVisibility(View.GONE);
        }
        if (status.equals("3")) {
            jujue.setVisibility(View.GONE);
            jieshou.setVisibility(View.VISIBLE);
            jieshou.setText("已拒绝，点击接受");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
    }

    @OnClick({R.id.back, R.id.jujue, R.id.jieshou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.jujue:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    Intent intent = new Intent(OfferInfoDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    OkGo.<String>post(URLConst.BASEURL(typeURL))
                            .tag(this)
                            .headers("device", "1")
                            .params("token", localToken)
                            .params("studentId", studentId)
                            .params("inviteId", id)
                            .params("option", -1)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body());
                                        int infoCode = jsonObject.getInt("infoCode");
                                        if (infoCode == 1) {
                                            Tools.toast(OfferInfoDetailsActivity.this, "已拒绝邀请");
                                            jujue.setClickable(false);
                                        } else if (infoCode == -1) {
                                            Tools.toast(OfferInfoDetailsActivity.this, "登录状态过期，请重新登录");
                                            Intent intent = new Intent(OfferInfoDetailsActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
                break;
            case R.id.jieshou:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    Intent intent = new Intent(OfferInfoDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    OkGo.<String>post(URLConst.BASEURL(typeURL))
                            .tag(this)
                            .headers("device", "1")
                            .params("token", localToken)
                            .params("studentId", studentId)
                            .params("inviteId", id)
                            .params("option", 1)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body());
                                        int infoCode = jsonObject.getInt("infoCode");
                                        if (infoCode == 1) {
                                            Tools.toast(OfferInfoDetailsActivity.this, "已接受邀请");
                                            jieshou.setClickable(false);
                                        } else if (infoCode == -1) {
                                            Tools.toast(OfferInfoDetailsActivity.this, "登录状态过期，请重新登录");
                                            Intent intent = new Intent(OfferInfoDetailsActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
                break;
        }
    }
}
