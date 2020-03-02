package cn.com.qc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.adapter.MyProposerAdapter;
import cn.com.qc.javabean.MyProposer;
import cn.com.qc.leeactivity.WorkInfoActivity;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;

/**
 * 我的兼职-待上岗
 */

public class MyJobFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    Unbinder unbinder;
    private List<MyProposer> myProposerList;
    private MyProposerAdapter myProposerAdapter;
    private static final String typeURL = "mobile/getMyApplyJob.do";
    private int pageNumber = 0;
    private String studentId;
    private AssistTool assistTool;
    private String localToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myproposer_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        assistTool = new AssistTool(getActivity());
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
        myProposerList = new ArrayList<>();
        initData(pageNumber);
        myProposerAdapter = new MyProposerAdapter(myProposerList, getActivity());
        swipeTarget.setAdapter(myProposerAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
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
        OkGo.<String>post(URLConst.BASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .headers("localToken", localToken)
                .params("studentId", studentId)
                .params("dataType", 1)
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
                                    myProposerList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        MyProposer myProposer = new MyProposer();
                                        myProposer.setLocation(jsonObject2.getString("address"));
                                        myProposer.setDate(Tools.getStrTime(jsonObject2.getString("createDate")));
                                        myProposer.setCompany(jsonObject2.getString("enterpriseName"));
                                        myProposer.setJobId(jsonObject2.getString("jobId"));
                                        myProposer.setJob(jsonObject2.getString("jobName"));
                                        myProposer.setSize(jsonObject2.getInt("dayReport"));
                                        myProposer.setTitle("已申请");
                                        myProposer.setStep(1);
                                        myProposerList.add(myProposer);
                                    }
                                    myProposerAdapter.addData(myProposerList);
                                }
                            } else if (infoCode == -1) {
                                Tools.toast(getActivity(), "登录状态过期，请重新登录");
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
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
                myProposerAdapter.clear();
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
