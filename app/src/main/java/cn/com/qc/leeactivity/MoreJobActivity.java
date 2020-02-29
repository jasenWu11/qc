package cn.com.qc.leeactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.MoreJobAdapter;
import cn.com.qc.javabean.MoreJob;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.utils.CustomListView;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;

/**
 * 更多工作-废弃
 */

public class MoreJobActivity extends BaseActivity implements OnLoadMoreListener {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.swipe_target)
    CustomListView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private List<MoreJob> list;
    private MoreJobAdapter adapter;
    private int pageNumber = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morejob);
        ButterKnife.bind(this);
        list = new ArrayList<>();
//        list.add(new MoreJob("标题", "10月1日-10月8日", "北京", 100, "日结"));
//        list.add(new MoreJob("标题", "10月1日-10月8日", "北京", 100, "日结"));
//        list.add(new MoreJob("标题", "10月1日-10月8日", "北京", 100, "日结"));
//        list.add(new MoreJob("标题", "10月1日-10月8日", "北京", 100, "日结"));
//        adapter = new MoreJobAdapter(list, this);
//        swipeTarget.setAdapter(adapter);
//        swipeToLoadLayout.setOnLoadMoreListener(this);
//        initData(pageNumber);
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
