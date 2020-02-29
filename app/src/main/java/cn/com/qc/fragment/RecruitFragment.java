package cn.com.qc.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.adapter.JobAdapter;
import cn.com.qc.help.NetUrl;
import cn.com.qc.javabean.JobInfo;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;

/**
 * 招聘
 */

public class RecruitFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    Unbinder unbinder;
    private List<JobInfo> jobInfoList;
    private JobAdapter jobAdapter;
    private int pageNumber = 0;
    private String city = "东莞";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recruit_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        jobInfoList = new ArrayList<>();
        initData(pageNumber);
        jobAdapter = new JobAdapter(jobInfoList, getActivity());
        swipeTarget.setAdapter(jobAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    private void initData(int pageNumber) {
        OkGo.<String>post(NetUrl.DNS + NetUrl.Joblist)
                .tag(this)
                //.params("WorkPlace", city)
                .params("pageIndex", pageNumber)
                .params("pageSize", 6)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("status");
                            if (infoCode == 0) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                JSONArray jsonArray = jsonObject1.getJSONArray("records");
                                System.out.print("返回的数据是"+jsonArray);
                                if (jsonArray.length() == 0) {

                                } else {
                                    jobInfoList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        JobInfo jobInfo = new JobInfo();
                                        jobInfo.setLocation(jsonObject2.getString("workPlace"));
                                        jobInfo.setDate(jsonObject2.getString("startTime"));
//                                        String beginDate = jsonObject2.getString("createTime");
//                                        jobInfo.setDate(Tools.getStrTime(beginDate));
                                        jobInfo.setCompany(jsonObject2.getString("cname"));
                                        jobInfo.setId(jsonObject2.getString("id"));
                                        jobInfo.setJob(jsonObject2.getString("title"));
                                        jobInfo.setPrice(jsonObject2.getInt("minMoney") + "-"+jsonObject2.getInt("maxMoney"));
                                        jobInfoList.add(jobInfo);
                                    }
                                    jobAdapter.addData(jobInfoList);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                jobAdapter.clear();
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
