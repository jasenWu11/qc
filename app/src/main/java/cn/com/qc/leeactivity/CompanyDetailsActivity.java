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
import cn.com.qc.help.NetUrl;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;

/**
 * 职位详情
 */

public class CompanyDetailsActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.company2)
    TextView company2;
    @BindView(R.id.trade)
    TextView trade;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.apply)
    Button apply;
    private static final String typeURL1 = "mobile/getJobInfo.do";
    private static final String typeURL2 = "apply/add.do";
    private String id = "";
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydetails);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        assistTool = new AssistTool(this);
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        id = getIntent().getStringExtra("id");
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
    }

    private void initData() {
        OkGo.<String>post(NetUrl.DNS + NetUrl.Jobinfo)
                .tag(this)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("status");
                            if (infoCode == 0) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                location.setText("地点：" + jsonObject1.getString("workPlace"));
                               String startTime = jsonObject1.getString("startTime");
                               String endTime = jsonObject1.getString("endTime");
                               String date_text = "日期：";
                               System.out.println(startTime+"和"+endTime);
                               if(startTime!=null&&startTime!=""&&startTime!="null"){
                                   date_text = "日期：" + startTime;
                               }
                               if(endTime!=null&&endTime!=""&&endTime!="null"){
                                   date_text = date_text +  " 到 " + endTime;
                               }
//                              date.setText("日期：" + Tools.getStrTime(beginDate) + "/" + Tools.getStrTime(endDate));
                                date.setText(date_text);
                                company.setText("公司名称：" + jsonObject1.getString("cname"));
                                company2.setText("公司名称：" + jsonObject1.getString("cname"));
                                trade.setText("行业：" + jsonObject1.getString("professionName"));
                                job.setText("职位名称：" + jsonObject1.getString("title"));
                                introduce.setText(jsonObject1.getString("jobDescribe"));
                                price.setText(jsonObject1.getInt("minMoney") +"-"+jsonObject1.getInt("maxMoney") + "/日");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.back, R.id.apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.apply:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    OkGo.<String>post(NetUrl.DNS + NetUrl.applyJob)
                            .tag(this)
                            .params("JobId", id)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body());
                                        System.out.println("输出结果"+jsonObject);
                                        int infoCode = jsonObject.getInt("status");
                                        if (infoCode == 0) {
                                            Tools.toast(CompanyDetailsActivity.this, "您的申请已提交!");
                                            apply.setBackgroundColor(getResources().getColor(R.color.cc));
                                            apply.setClickable(false);
                                        }else{
                                            String msg = jsonObject.getString("msg");
                                            Tools.toast(CompanyDetailsActivity.this, msg);
                                            apply.setBackgroundColor(getResources().getColor(R.color.cc));
                                            apply.setClickable(false);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
        }
    }
}
