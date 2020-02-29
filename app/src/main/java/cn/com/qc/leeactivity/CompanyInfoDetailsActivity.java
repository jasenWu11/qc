package cn.com.qc.leeactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;

/**
 * 我的工作申请详情页中的公司信息-废弃
 */

public class CompanyInfoDetailsActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.companystyle)
    TextView companystyle;
    @BindView(R.id.companytype)
    TextView companytype;
    @BindView(R.id.companysize)
    TextView companysize;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.companyaddress)
    TextView companyaddress;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.sms)
    TextView sms;
    private static final String typeURL = "mobile/getPTJobInfo.do";
    private String id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyinfodetails);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        id = getIntent().getStringExtra("id");
//        initDate();
    }

    private void initDate() {
        OkGo.<String>post(URLConst.BASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .params("enterpriseId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                companytype.setText("经营性质：" + jsonObject1.getString("enterpriseType"));
                                companysize.setText("公司规模：" + jsonObject1.getInt("min") + "-" + jsonObject1.getInt("max"));
                                introduce.setText(jsonObject1.getString("describe1"));
                                phone.setText("联系电话：" + jsonObject1.getString("phone"));
                                sms.setText("电子邮箱：" + jsonObject1.getString("industryName"));
                                job.setText("职位名称：" + jsonObject1.getString("jobName"));
                                companystyle.setText("所属行业：" + jsonObject1.getString("industryName"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
