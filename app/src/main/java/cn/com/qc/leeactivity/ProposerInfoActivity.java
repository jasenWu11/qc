package cn.com.qc.leeactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.ProposerInfoAdapter;
import cn.com.qc.javabean.ProposerInfo;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;

/**
 * 申请该工作的人-废弃
 */

public class ProposerInfoActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private List<ProposerInfo> list;
    private ProposerInfoAdapter adapter;
    private int pageNumber = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposerinfo);
        ButterKnife.bind(this);
        list = new ArrayList<>();
//        initData(pageNumber);
//        list.add(new ProposerInfo("学锋志愿", "110", "9.5", "150"));
//        list.add(new ProposerInfo("学锋志愿", "110", "9.5", "150"));
//        list.add(new ProposerInfo("学锋志愿", "110", "9.5", "150"));
//        list.add(new ProposerInfo("学锋志愿", "110", "9.5", "150"));
//        adapter = new ProposerInfoAdapter(list, this);
//        swipeTarget.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    private void initData(int pageNumber) {

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
//                adapter.clear();
//                pageNumber = 1;
//                initData(pageNumber);
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 100);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
//                pageNumber++;
//                initData(pageNumber);
                swipeToLoadLayout.setLoadingMore(false);
            }
        }, 100);
    }
}
