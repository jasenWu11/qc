package cn.com.qc.leeactivity;

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
import cn.com.qc.adapter.CompanyRecruitAdapter;
import cn.com.qc.javabean.CompanyRecruit;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;

/**
 * 专区
 */

public class CompanyRecruitActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private List<CompanyRecruit> companyRecruitList;
    private CompanyRecruitAdapter companyRecruitAdapter;
    private int pageNumber = 0;
    private static final String typeURL = "mobile/getActivityList.do";
    private AssistTool assistTool;
    private String id = "0";
    private String city = "北京市";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyrecruit);
        Tools.initSystemBarTint(this);
        ButterKnife.bind(this);
        assistTool = new AssistTool(this);
        id = getIntent().getStringExtra("id");
        companyRecruitList = new ArrayList<>();
        initData(pageNumber);
        companyRecruitAdapter = new CompanyRecruitAdapter(companyRecruitList, this);
        swipeTarget.setAdapter(companyRecruitAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    private void initData(int pageNumber) {
        final String imgbasepath = assistTool.getPreferenceString("imgbasepath");
        OkGo.<String>post(URLConst.BASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .params("activityId", id)
                .params("city", city)
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
                                JSONObject jsonObject1 = new JSONObject(data);
                                String list = jsonObject1.getString("list");
                                JSONArray jsonArray = new JSONArray(list);
                                if (jsonArray.length() == 0) {

                                } else {
                                    companyRecruitList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        CompanyRecruit companyRecruit = new CompanyRecruit();
                                        String beginDate = jsonObject2.getString("beginDate");
                                        String endDate = jsonObject2.getString("endDate");
                                        companyRecruit.setDate(Tools.getStrTime(beginDate) + "/" + Tools.getStrTime(endDate));
                                        companyRecruit.setCompany(jsonObject2.getInt("enterpriseNum"));
                                        companyRecruit.setId(jsonObject2.getString("id"));
                                        String img = jsonObject2.getString("img");
                                        companyRecruit.setImg(imgbasepath + img);
                                        JSONArray jobNameList = jsonObject2.getJSONArray("jobNameList");
                                        String str = "";
                                        for (int l = 0; l < jobNameList.length(); l++) {
                                            if (l != jobNameList.length() - 1) {
                                                str += jobNameList.getString(l) + "、";
                                            } else {
                                                str += jobNameList.getString(l);
                                            }
                                        }
                                        companyRecruit.setContent(str);
                                        companyRecruit.setJob(jsonObject2.getInt("jobNum"));
                                        companyRecruit.setPeople(jsonObject2.getInt("personNum"));
                                        companyRecruit.setTitle(jsonObject2.getString("title"));
                                        companyRecruitList.add(companyRecruit);
                                    }
                                    companyRecruitAdapter.addData(companyRecruitList);
                                }
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
                companyRecruitAdapter.clear();
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
