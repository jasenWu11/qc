package cn.com.qc.leeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
 * 兼职详情
 */

public class JobDetailsActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.job_details)
    TextView jobDetails;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.content2)
    TextView content2;
    @BindView(R.id.yishenqing)
    TextView yishenqing;
    @BindView(R.id.minge)
    RelativeLayout minge;
    @BindView(R.id.keshenqing)
    TextView keshenqing;
    @BindView(R.id.shenqing)
    TextView shenqing;
    @BindView(R.id.share)
    LinearLayout share;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    private static final String typeURL1 = "mobile/getPTJobInfo.do";
    private static final String typeURL2 = "mobile/addApply.do";
    private String id = "";
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetails);
        Tools.initSystemBarTint(this);
        ButterKnife.bind(this);
        assistTool = new AssistTool(this);
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        final String imgbasepath = assistTool.getPreferenceString("imgbasepath");
        id = getIntent().getStringExtra("id");
        OkGo.<String>post(URLConst.BASEURL(typeURL1))
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
                                location.setText("工作地点：" + jsonObject1.getString("address"));
                                yishenqing.setText("已申请名额：" + jsonObject1.getString("applyCount"));
                                date.setText("工作时期：" + Tools.getStrTime(jsonObject1.getString("beginDate")) + "-" + Tools.getStrTime(jsonObject1.getString("endDate")));
                                int paysize = jsonObject1.getInt("dayReport");
                                if (paysize == 1) {
                                    imageView.setImageResource(R.mipmap.ri);
                                } else if (paysize == 2) {
                                    imageView.setImageResource(R.mipmap.zhou);
                                } else if (paysize == 3) {
                                    imageView.setImageResource(R.mipmap.yue);
                                } else if (paysize == 0) {
                                    imageView.setImageResource(R.mipmap.ri);
                                }
                                jobDetails.setText(jsonObject1.getString("jobName") + ",工作时间" + jsonObject1.getString("duration") + "个小时");
                                company.setText(jsonObject1.getString("enterpriseName"));
                                time.setText("工作时间：" + jsonObject1.getString("jobTime"));
                                Glide.with(JobDetailsActivity.this)
                                        .load(imgbasepath + jsonObject1.getString("logo"))
                                        .into(img);
                                content.setText("工作内容：" + jsonObject1.getString("mainContent"));
                                content2.setText("其他附属内容：" + jsonObject1.getString("otherConten"));
                                keshenqing.setText("可申请名额：" + jsonObject1.getString("peopleNumber"));
                                price.setText(jsonObject1.getString("salary") + "/日");
                            } else if (infoCode == -1) {
                                Tools.toast(JobDetailsActivity.this, "登录状态过期，请重新登录");
                                Intent intent = new Intent(JobDetailsActivity.this, LoginActivity.class);
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

    @OnClick({R.id.back, R.id.minge, R.id.keshenqing, R.id.shenqing, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.minge:
                //TODO
                break;
            case R.id.keshenqing:
                break;
            case R.id.shenqing:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    Intent intent = new Intent(JobDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    OkGo.<String>post(URLConst.BASEURL(typeURL2))
                            .tag(this)
                            .headers("localToken", localToken)
                            .headers("device", "1")
                            .headers("studentId", studentId)
                            .params("PTJobId", id)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body());
                                        int infoCode = jsonObject.getInt("infoCode");
                                        if (infoCode == 1) {
                                            Tools.toast(JobDetailsActivity.this, "您的申请已提交");
                                        } else if (infoCode == -1) {
                                            Tools.toast(JobDetailsActivity.this, "登录状态过期，请重新登录");
                                            Intent intent = new Intent(JobDetailsActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
                break;
            case R.id.share:
                break;
        }
    }
}
