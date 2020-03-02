package cn.com.qc.leeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tandong.sa.tools.AssistTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.CompanyAdapter;
import cn.com.qc.javabean.CompanyInfo;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;

/**
 * 收藏的公司
 */

public class CollectCompanyActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private List<CompanyInfo> companyInfoList;
    private CompanyAdapter companyAdapter;
    private static final String typeURL = "mobile/getMyCollect.do";
    private int pageNumber = 0;
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectcompany);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        assistTool = new AssistTool(this);
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        companyInfoList = new ArrayList<>();
        initData(pageNumber);
        companyAdapter = new CompanyAdapter(companyInfoList, this);
        swipeTarget.setAdapter(companyAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
    }

    private void initData(int pageNumber) {
        final String imgbasepath = assistTool.getPreferenceString("imgbasepath");
        OkGo.<String>post(URLConst.BASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .headers("localToken", localToken)
                .params("userId", studentId)
                .params("collectType", 1)
                .params("pageNumber", pageNumber)
                .params("pageSize", 6)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            System.out.println(response.body());
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                companyInfoList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String data1 = jsonObject1.getString("data");
                                    JSONObject jsonObject2 = new JSONObject(data1);
                                    CompanyInfo companyInfo = new CompanyInfo();
                                    companyInfo.setContent(jsonObject2.getString("industryName"));
                                    companyInfo.setId(jsonObject2.getString("id"));
                                    companyInfo.setImg(imgbasepath + jsonObject2.getString("logo"));
                                    companyInfo.setJob(jsonObject2.getString("name"));
                                    companyInfo.setLocation(jsonObject2.getString("city"));
                                    companyInfoList.add(companyInfo);
                                }
                                companyAdapter.addData(companyInfoList);
                            } else if (infoCode == -1) {
                                Tools.toast(CollectCompanyActivity.this, "登录状态过期，请重新登录");
                                Intent intent = new Intent(CollectCompanyActivity.this, LoginActivity.class);
                                startActivity(intent);
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

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                companyAdapter.clear();
                pageNumber = 0;
                initData(pageNumber);
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 100);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNumber++;
                initData(pageNumber);
                swipeToLoadLayout.setLoadingMore(false);
            }
        }, 100);
    }
}
