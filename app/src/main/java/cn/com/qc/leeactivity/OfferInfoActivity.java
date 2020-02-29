package cn.com.qc.leeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import cn.com.qc.adapter.OfferInfoAdapter;
import cn.com.qc.javabean.OfferInfo;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;

/**
 * 我的工作邀约
 */

public class OfferInfoActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private List<OfferInfo> offerInfoList;
    private OfferInfoAdapter offerInfoAdapter;
    private static final String typeURL = "mobile/getMyInvite.do";
    private int pageNumber = 0;
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";
    private String deviceId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerinfo);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        assistTool = new AssistTool(this);
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        deviceId = assistTool.getPreferenceString("deviceId");
        offerInfoList = new ArrayList<>();
        initData(pageNumber);
        offerInfoAdapter = new OfferInfoAdapter(offerInfoList, this);
        swipeTarget.setAdapter(offerInfoAdapter);
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
        OkGo.<String>post(URLConst.BASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .headers("deviceId", deviceId)
                .headers("localToken", localToken)
                .params("userId", studentId)
                .params("pageNumber", pageNumber)
                .params("pageSize", 6)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                if (jsonArray.length() == 0) {

                                } else {
                                    offerInfoList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        OfferInfo offerInfo = new OfferInfo();
                                        offerInfo.setLocation(jsonObject2.getString("address"));
                                        offerInfo.setDate(Tools.getStrTime(jsonObject2.getString("createDate")));
                                        offerInfo.setId(jsonObject2.getString("id"));
                                        offerInfo.setCompany(jsonObject2.getString("enterpriseName"));
                                        offerInfo.setJob(jsonObject2.getString("jobName"));
                                        offerInfo.setContent(jsonObject2.getString("content"));
                                        offerInfo.setPhone(jsonObject2.getString("phone"));
                                        offerInfo.setStatus(jsonObject2.getInt("status"));
                                        offerInfoList.add(offerInfo);
                                    }
                                    offerInfoAdapter.addData(offerInfoList);
                                }
                            } else if (infoCode == -1) {
                                Tools.toast(OfferInfoActivity.this, "登录状态过期，请重新登录");
                                Intent intent = new Intent(OfferInfoActivity.this, LoginActivity.class);
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
                offerInfoAdapter.clear();
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
