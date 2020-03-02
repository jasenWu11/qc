package cn.com.qc.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.SearchAdapter;
import cn.com.qc.adapter.SearchConditionAdapter;
import cn.com.qc.adapter.SearchPosAdapter;
import cn.com.qc.adapter.YBaseAdapter;
import cn.com.qc.bean.SearchCompanyBean;
import cn.com.qc.bean.SearchConditionBean;
import cn.com.qc.bean.SearchPositionBean;
import cn.com.qc.help.NetUrl;
import cn.com.qc.leeactivity.CompanyDetailsActivity;
import cn.com.qc.leeactivity.CompanyInfoActivity;
import cn.com.qc.view.DividerItemDecoration;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.viewholder.YBaseViewHolder;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;
import cn.com.qc.yinter.SearchDataListener;
import cn.com.qc.ypresenter.SearchPresenter;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchActivity extends YBaseActivity<SearchPresenter, SearchActivity> implements YBaseAdapter.OnItemClickListener, OnRefreshListener, OnLoadMoreListener, SearchDataListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.returns)
    LinearLayout returns;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView swipe_target;

    @Override
    public SearchPresenter getPresenter() {
        return new SearchPresenter();
    }

    @Override
    public int getResId() {
        return R.layout.activity_search;
    }

    String city, searchType, searchBody;
    int pageNumber = 0;
    int pageSize = 10;
    boolean ifSearchJob;

    String enterpriseTypeId,industryId,jobEntityId;
    long beginDate;

    @Override
    public void init() {
        title.setText("搜索");
        initSwipe();
        Bundle bundle = getIntent().getExtras();
        city = bundle.getString("city");
        ifSearchJob = bundle.getBoolean("ifSearchJob",false);
        if(ifSearchJob){
            initRecyclerView();
            enterpriseTypeId = bundle.getString("enterpriseTypeId");
            industryId = bundle.getString("industryId");
            jobEntityId = bundle.getString("jobEntityId");
            beginDate = bundle.getLong("beginDate");
            p.getSearchConditionDatas(NetUrl.DNS + NetUrl.HomeCondition,enterpriseTypeId,industryId,jobEntityId,city,beginDate,pageSize,pageNumber);
            return;
        }

        searchType = bundle.getString("searchType");
        searchBody = bundle.getString("searchBody");
        initRecyclerView();
        if(searchType.equals("1")){
            p.getSearchPositionDatas(NetUrl.DNS + NetUrl.HomeSearch,city,searchType,searchBody,pageNumber,pageSize);
        }else{
            p.getSearcheDatas(NetUrl.DNS + NetUrl.HomeSearch,city,searchType,searchBody,pageNumber,pageSize);
        }
    }

    SearchAdapter searchAdapter;

    SearchPosAdapter searchPosAdapter;

    SearchConditionAdapter searchConditionAdapter;

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        swipe_target.setLayoutManager(layoutManager);

        if(ifSearchJob){
            searchConditionAdapter = new SearchConditionAdapter(this);
            swipe_target.setAdapter(searchConditionAdapter);
            searchConditionAdapter.setOnItemClickListener(this);
            return;
        }

        if(searchType.equals("2")){
            swipe_target.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            searchAdapter = new SearchAdapter(this);
            swipe_target.setAdapter(searchAdapter);
            searchAdapter.setOnItemClickListener(this);
        }else{
            searchPosAdapter = new SearchPosAdapter(this);
            swipe_target.setAdapter(searchPosAdapter);
            searchPosAdapter.setOnItemClickListener(this);
        }

    }

    private void initSwipe() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @OnClick({R.id.returns})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.returns:
                finish();
                break;
        }
    }

    @Override
    public <h extends YBaseViewHolder> void itemClick(int position, h holder) {
        if(ifSearchJob){
            String id = searchConditionAdapter.getItem(position).getId();
            openActivity(CompanyDetailsActivity.class,"id",id);
            return;
        }

        if(searchType.equals("1")){
            String id = searchPosAdapter.getItem(position).getId();
            openActivity(CompanyDetailsActivity.class,"id",id);
        }else{
            String id = searchAdapter.getItem(position).getId();
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            openActivity(CompanyInfoActivity.class,bundle);
        }
    }

    int orientation = 1;

    @Override
    public void onRefresh() {
        orientation = 1;
        if(searchType.equals("1")){
            p.getSearchPositionDatas(NetUrl.DNS + NetUrl.HomeSearch,city,searchType,searchBody,0,pageSize);
        }else{
            p.getSearcheDatas(NetUrl.DNS + NetUrl.HomeSearch,city,searchType,searchBody,0,pageSize);
        }
    }

    @Override
    public void onLoadMore() {
        orientation = 2;
        if(searchType.equals("1")){
            p.getSearchPositionDatas(NetUrl.DNS + NetUrl.HomeSearch,city,searchType,searchBody,pageNumber + 1,pageSize);
        }else{
            p.getSearcheDatas(NetUrl.DNS + NetUrl.HomeSearch,city,searchType,searchBody,pageNumber + 1,pageSize);
        }
    }

    @Override
    public void onSuccess(List<SearchCompanyBean.SearchData> datas) {
        if(datas != null){
            if(orientation == 1){
                swipeToLoadLayout.setRefreshing(false);
                searchAdapter.clear();
                pageNumber = 0;
            }else if(orientation == 2){
                swipeToLoadLayout.setLoadingMore(false);
                if(datas.size() > 0){
                    pageNumber++;
                }
            }
            searchAdapter.setData(datas);
            return;
        }

        updateOrientation();
    }

    @Override
    public void onError() {
        updateOrientation();
    }

    @Override
    public void onPosSuccess(List<SearchPositionBean.SearchPositionData> datas) {
        if(datas != null){
            if(orientation == 1){
                swipeToLoadLayout.setRefreshing(false);
                searchPosAdapter.getContentList().clear();
                pageNumber = 0;
            }else if(orientation == 2){
                swipeToLoadLayout.setLoadingMore(false);
                if(datas.size() > 0){
                    pageNumber++;
                }
            }
            searchPosAdapter.setData(datas);
            return;
        }

        updateOrientation();
    }

    @Override
    public void onConditionSuccess(List<SearchConditionBean.SearchCoditionData> datas) {
        if(datas != null){
            if(orientation == 1){
                swipeToLoadLayout.setRefreshing(false);
                searchConditionAdapter.getContentList().clear();
                pageNumber = 0;
            }else if(orientation == 2){
                swipeToLoadLayout.setLoadingMore(false);
                if(datas.size() > 0){
                    pageNumber++;
                }
            }
            searchConditionAdapter.setData(datas);
            return;
        }

        updateOrientation();
    }

    //更新刷新状态
    private void updateOrientation(){
        if(orientation == 1){
            swipeToLoadLayout.setRefreshing(false);
        }else if(orientation == 2){
            swipeToLoadLayout.setLoadingMore(false);
        }
        orientation = 1;
    }

}
