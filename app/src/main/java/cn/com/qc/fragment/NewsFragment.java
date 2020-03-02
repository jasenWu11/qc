package cn.com.qc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.adapter.NewsInfoAdapter;
import cn.com.qc.javabean.NewsInfo;
import cn.com.qc.leeactivity.BiddingInfoActivity;
import cn.com.qc.leeactivity.OfferInfoActivity;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;

/**
 * 消息
 */

public class NewsFragment extends Fragment implements OnLoadMoreListener {
    @BindView(R.id.yaoyuesms)
    TextView yaoyuesms;
    @BindView(R.id.yaoqing)
    RelativeLayout yaoqing;
    @BindView(R.id.shenqingsms)
    TextView shenqingsms;
    @BindView(R.id.shenqing)
    RelativeLayout shenqing;
    @BindView(R.id.kaigongsms)
    TextView kaigongsms;
    @BindView(R.id.remen)
    TextView remen;
    @BindView(R.id.tixing)
    RelativeLayout tixing;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    Unbinder unbinder;
    private List<NewsInfo> newsInfoList;
    private NewsInfoAdapter newsInfoAdapter;
    private int pageNumber = 0;
    private Intent intent;
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";
    private static final String typeURL = "mobile/recommendJob.do";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        assistTool = new AssistTool(getActivity());
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        remen.setVisibility(View.GONE);
        newsInfoList = new ArrayList<>();
        initData(pageNumber);
        newsInfoAdapter = new NewsInfoAdapter(newsInfoList, getActivity());
        swipeTarget.setAdapter(newsInfoAdapter);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
    }

    private void initData(int pageNumber) {
        if (studentId.equals(null) || studentId.equals("")) {

        } else {
            OkGo.<String>post(URLConst.BASEURL(typeURL))
                    .tag(this)
                    .headers("device", "1")
                    .params("studentId", studentId)
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
                                        newsInfoList = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                            NewsInfo newsInfo = new NewsInfo();
                                            newsInfo.setLocation(jsonObject2.getString("address"));
                                            newsInfo.setCompany(jsonObject2.getString("enterpriseName"));
                                            newsInfo.setId(jsonObject2.getString("id"));
                                            newsInfo.setIsCollect(jsonObject2.getInt("isCollect"));
                                            newsInfo.setJob(jsonObject2.getString("jobName"));
                                            newsInfo.setPeople(jsonObject2.getInt("peopleNumber"));
                                            newsInfo.setPrice(jsonObject2.getInt("salary"));
                                            newsInfoList.add(newsInfo);
                                        }
                                        newsInfoAdapter.addData(newsInfoList);
                                    }
                                    remen.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.yaoqing, R.id.shenqing, R.id.tixing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yaoqing:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), OfferInfoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.shenqing:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), BiddingInfoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tixing:
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), BiddingInfoActivity.class);
                    startActivity(intent);
                }
                break;
        }
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
