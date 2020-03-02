package cn.com.qc.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.CollectAdapter;
import cn.com.qc.adapter.YBaseAdapter;
import cn.com.qc.bean.CollectBean;
import cn.com.qc.view.DividerItemDecoration;
import cn.com.qc.viewholder.CollectViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;
import cn.com.qc.ypresenter.CollectPresenter;

/**
 * Created by lenovo on 2017/11/30.
 */

public class CollectActivity extends YBaseActivity<CollectPresenter,CollectActivity> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;

    @Override
    public CollectPresenter getPresenter() {
        return new CollectPresenter();
    }

    @Override
    public int getResId() {
        return R.layout.activity_collect;
    }

    @Override
    public void init() {
        title.setText("收藏职位");
        initRecyclerView();

        List<CollectBean> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        collectAdapter.setData(list);

    }

    CollectAdapter collectAdapter;
    private void initRecyclerView(){
        collectAdapter = new CollectAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(collectAdapter);
        collectAdapter.setOnItemClickListener(onItemClickListener);
    }

    YBaseAdapter.OnItemClickListener onItemClickListener = new YBaseAdapter.OnItemClickListener() {
        @Override
        public <h extends YBaseViewHolder> void itemClick(int position, h holder) {

        }
    };

    @OnClick(R.id.returns)
    public void click(View view){
        switch (view.getId()){
            case R.id.returns:
                finish();
                break;
        }
    }

}
