package cn.com.qc.leeactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
 * 我收藏的兼职详情
 */

public class MyCollectionJobDetailsActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.date2)
    TextView date2;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.morejob)
    RelativeLayout morejob;
    @BindView(R.id.fenxiang)
    LinearLayout fenxiang;
    @BindView(R.id.zixun)
    LinearLayout zixun;
    @BindView(R.id.toudi)
    Button toudi;
    private String id = "";
    private String enterpriseId = "";
    private static final String typeURL1 = "mobile/getPTJobInfo.do";
    private static final String typeURL2 = "mobile/addApply.do";
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";
    private Intent intent;
    private String phone = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollectionjobdetails);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        id = getIntent().getStringExtra("id");
        assistTool = new AssistTool(this);
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
    }

    private void initData() {
        OkGo.<String>post(URLConst.BASEURL(typeURL1))
                .tag(this)
                .headers("device", "1")
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        System.out.println(response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                job.setText("职位名称：" + jsonObject1.getString("jobName"));
                                location.setText("地点：" + jsonObject1.getString("address"));
                                int dayReport = jsonObject1.getInt("dayReport");
                                if (dayReport == 1) {
                                    size.setText("日结");
                                } else if (dayReport == 2) {
                                    size.setText("周结");
                                } else if (dayReport == 3) {
                                    size.setText("月结");
                                } else if (dayReport == 0) {
                                    size.setVisibility(View.GONE);
                                }
                                date2.setText("日期：" + Tools.getStrTime(jsonObject1.getString("beginDate")) + "/" + Tools.getStrTime(jsonObject1.getString("beginDate")));
                                company.setText("公司名称：" + jsonObject1.getString("enterpriseName"));
                                enterpriseId = jsonObject1.getString("enterpriseId");
                                content.setText(jsonObject1.getString("mainContent"));
                                money.setText(jsonObject1.getString("salary") + "/日");
                                phone = jsonObject1.getString("phone");
                            } else if (infoCode == -1) {
                                Tools.toast(MyCollectionJobDetailsActivity.this, "登录状态过期，请重新登录");
                                Intent intent = new Intent(MyCollectionJobDetailsActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.back, R.id.morejob, R.id.zixun, R.id.toudi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.morejob:
                intent = new Intent(MyCollectionJobDetailsActivity.this, CompanyInfosActivity.class);
                intent.putExtra("id", enterpriseId);
                startActivity(intent);
                break;
            case R.id.zixun:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_call, null);
                Button shangjia = (Button) contentView.findViewById(R.id.shangjia);
                shangjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(MyCollectionJobDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                Button kefu = (Button) contentView.findViewById(R.id.kefu);
                kefu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(MyCollectionJobDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                final PopupWindow popupWindow = new PopupWindow(contentView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                contentView.setFocusableInTouchMode(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                break;
            case R.id.toudi:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    intent = new Intent(MyCollectionJobDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    OkGo.<String>post(URLConst.BASEURL(typeURL2))
                            .tag(this)
                            .headers("localToken", localToken)
                            .headers("device", "1")
                            .params("studentId", studentId)
                            .params("PTJobId", id)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body());
                                        int infoCode = jsonObject.getInt("infoCode");
                                        if (infoCode == 1) {
                                            Tools.toast(MyCollectionJobDetailsActivity.this, "您的申请已提交");
                                            toudi.setText("已投递");
                                        } else if (infoCode == -1) {
                                            Tools.toast(MyCollectionJobDetailsActivity.this, "登录状态过期，请重新登录");
                                            Intent intent = new Intent(MyCollectionJobDetailsActivity.this, LoginActivity.class);
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
