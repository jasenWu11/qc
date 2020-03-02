package cn.com.qc.leeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

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
 * 我的工作信息详情
 */

public class JobInfoDetailsActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.gongsi)
    RelativeLayout gongsi;
    @BindView(R.id.introduce)
    TextView introduce;
    private String id = "";
    private String enterpriseId = "";
    private static final String typeURL = "mobile/getPTJobInfo.do";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobinfodetails);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        id = getIntent().getStringExtra("id");
        initData();
    }

    private void initData() {
        OkGo.<String>post(URLConst.BASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                String beginDate = jsonObject1.getString("beginDate");
                                String endDate = jsonObject1.getString("endDate");
                                date.setText("日期：" + Tools.getStrTime(beginDate) + "/" + Tools.getStrTime(endDate));
                                location.setText("地点：" + jsonObject1.getString("address"));
                                job.setText("职位名称：" + jsonObject1.getString("jobName"));
                                introduce.setText(jsonObject1.getString("mainContent"));
                                price.setText(jsonObject1.getInt("salary") + "/日");
                                company.setText("公司名称：" + jsonObject1.getString("enterpriseName"));
                                enterpriseId = jsonObject1.getString("enterpriseId");
                            } else if (infoCode == -1) {
                                Tools.toast(JobInfoDetailsActivity.this, "登录状态过期，请重新登录");
                                Intent intent = new Intent(JobInfoDetailsActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.back, R.id.gongsi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.gongsi:
                Intent intent = new Intent(JobInfoDetailsActivity.this, CompanyInfosActivity.class);
                intent.putExtra("id", enterpriseId);
                startActivity(intent);
                break;
        }
    }
}
