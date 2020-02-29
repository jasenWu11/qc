package cn.com.qc.leeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tandong.sa.tools.AssistTool;

import org.json.JSONArray;
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
 * 我的开工提醒
 */

public class WorkInfoActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.phone)
    TextView phone;
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";
    private String deviceId = "";
    private static final String typeURL = "mobile/MyJobRemind.do";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workinfo);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        assistTool = new AssistTool(this);
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        deviceId = assistTool.getPreferenceString("deviceId");
        OkGo.<String>post(URLConst.BASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .headers("deviceId", deviceId)
                .params("token", localToken)
                .params("studentId", studentId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                location.setText("工作地点：" + jsonObject1.getString("address"));
                                jsonObject1.getString("applyId");
                                company.setText("公司名称：" + jsonObject1.getString("enterpriseName"));
                                job.setText("工作岗位：" + jsonObject1.getString("jobName"));
                                time.setText("工作时间：" + jsonObject1.getString("jobTime"));
                                phone.setText("联系电话：" + jsonObject1.getString("phone"));
                                tip.setText("到岗提醒：" + jsonObject1.getString("remindTime"));
                            } else if (infoCode == -1) {
                                Tools.toast(WorkInfoActivity.this, "登录状态过期，请重新登录");
                                Intent intent = new Intent(WorkInfoActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
        }
    }
}
