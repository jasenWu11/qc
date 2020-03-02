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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
 * 我的兼职详情
 */

public class MyJobDetailsActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.yishenqingimg)
    ImageView yishenqingimg;
    @BindView(R.id.yishenqing)
    TextView yishenqing;
    @BindView(R.id.yishanggangimg)
    ImageView yishanggangimg;
    @BindView(R.id.yishanggang)
    TextView yishanggang;
    @BindView(R.id.daijiesuanimg)
    ImageView daijiesuanimg;
    @BindView(R.id.daijiesuan)
    TextView daijiesuan;
    @BindView(R.id.yijiesuanimg)
    ImageView yijiesuanimg;
    @BindView(R.id.yijiesuan)
    TextView yijiesuan;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.details)
    TextView details;
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
    @BindView(R.id.fenxiang)
    LinearLayout fenxiang;
    @BindView(R.id.zixun)
    LinearLayout zixun;
    @BindView(R.id.morejob)
    RelativeLayout morejob;
    private static final String typeURL = "mobile/getPTJobInfo.do";
    private String id = "";
    private int step = 0;
    private String enterpriseId = "";
    private String phone = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myjobdetails);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        id = getIntent().getStringExtra("id");
        step = getIntent().getIntExtra("step", 0);
        System.out.println("步骤：" + step);
        if (step == 0) {
            yishenqingimg.setImageResource(R.mipmap.yishenqing);
            yishenqing.setTextColor(getResources().getColor(R.color.appcolor));
            yishanggangimg.setImageResource(R.mipmap.weishenqing);
            daijiesuanimg.setImageResource(R.mipmap.weishenqing);
            yijiesuanimg.setImageResource(R.mipmap.weishenqing);
            money.setText("已申请");
        } else if (step == 1) {
            yishenqingimg.setImageResource(R.mipmap.yishenqing);
            yishenqing.setTextColor(getResources().getColor(R.color.appcolor));
            yishanggangimg.setImageResource(R.mipmap.yishenqing);
            yishanggang.setTextColor(getResources().getColor(R.color.appcolor));
            daijiesuanimg.setImageResource(R.mipmap.weishenqing);
            yijiesuanimg.setImageResource(R.mipmap.weishenqing);
            money.setText("待上岗");
        } else if (step == 2) {
            yishenqingimg.setImageResource(R.mipmap.yishenqing);
            yishenqing.setTextColor(getResources().getColor(R.color.appcolor));
            yishanggangimg.setImageResource(R.mipmap.yishenqing);
            yishanggang.setTextColor(getResources().getColor(R.color.appcolor));
            daijiesuanimg.setImageResource(R.mipmap.yishenqing);
            daijiesuan.setTextColor(getResources().getColor(R.color.appcolor));
            yijiesuanimg.setImageResource(R.mipmap.weishenqing);
            money.setText("待结算");
        } else if (step == 3) {
            yishenqingimg.setImageResource(R.mipmap.yishenqing);
            yishenqing.setTextColor(getResources().getColor(R.color.appcolor));
            yishanggangimg.setImageResource(R.mipmap.yishenqing);
            yishanggang.setTextColor(getResources().getColor(R.color.appcolor));
            daijiesuanimg.setImageResource(R.mipmap.yishenqing);
            daijiesuan.setTextColor(getResources().getColor(R.color.appcolor));
            yijiesuanimg.setImageResource(R.mipmap.yishenqing);
            yijiesuan.setTextColor(getResources().getColor(R.color.appcolor));
            money.setText("已结算");
        }
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
                                phone = jsonObject1.getString("phone");
                            } else if (infoCode == -1) {
                                Tools.toast(MyJobDetailsActivity.this, "登录状态过期，请重新登录");
                                Intent intent = new Intent(MyJobDetailsActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.back, R.id.fenxiang, R.id.zixun, R.id.morejob})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.fenxiang:
                break;
            case R.id.zixun:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_call, null);
                Button shangjia = (Button) contentView.findViewById(R.id.shangjia);
                shangjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(MyJobDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(MyJobDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
            case R.id.morejob:
                Intent intent = new Intent(MyJobDetailsActivity.this, CompanyInfosActivity.class);
                intent.putExtra("id", enterpriseId);
                startActivity(intent);
                break;
        }
    }
}
