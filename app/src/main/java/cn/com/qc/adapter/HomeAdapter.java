package cn.com.qc.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cn.com.qc.R;
import cn.com.qc.bean.HomeBean;
import cn.com.qc.help.Helps;
import cn.com.qc.view.DisableGridLayoutManager;
import cn.com.qc.view.DividerItemDecoration;
import cn.com.qc.view.GridDividerItemDecoration;
import cn.com.qc.viewholder.HomeViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/10/11.
 */

public class HomeAdapter extends YBaseAdapter<HomeBean> implements View.OnClickListener {

    public HomeAdapter(Context context, List<HomeBean> list) {
        super(context, list);
        hotAdapter = new HotAdapter(context);
        recommendAdapter = new RecommendAdapter(context);
    }

    public HomeAdapter(Context context) {
        super(context);
        hotAdapter = new HotAdapter(context);
        recommendAdapter = new RecommendAdapter(context);
    }

    public void notifyIndex(int index,HomeBean bean){
        contentList.set(index,bean);
        notifyItemChanged(index);
    }

    public void allClear(){
        contentList.clear();
        hotAdapter.getContentList().clear();
        recommendAdapter.getContentList().clear();
        notifyDataSetChanged();
    }

    ViewPager viewPager;

    @Override
    public void onClick(View v) {
        onViewClick.click(v);
    }

    public interface OnViewClick{
        void click(View view);
    }

    OnViewClick onViewClick;

    public void setOnViewClick(OnViewClick onViewClick){
        this.onViewClick = onViewClick;
    }

    public TextView businessType,tradeType,workType,dateText;

    LinearLayout dateContainer;

    private void setOnClickListener(View v){
        if(v.isClickable()) {
            v.setOnClickListener(this);
        }
    }

    Button sousuo;

    @Override
    public YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        HomeViewHolder holder = null;
        if(viewType == 1){
            holder = new HomeViewHolder(parent, R.layout.home_viewpager);
            viewPager = holder.$(R.id.viewPager);
            initViewPager();
        }else if(viewType == 2){
            holder = new HomeViewHolder(parent, R.layout.home_type_choose);
            dateContainer = holder.$(R.id.dateContainer);
            businessType = holder.$(R.id.businessType);
            tradeType = holder.$(R.id.tradeType);
            workType = holder.$(R.id.workType);
            dateText = holder.$(R.id.date);
            sousuo = holder.$(R.id.sousuo);
            if(onViewClick != null){
                setOnClickListener(businessType);
                setOnClickListener(tradeType);
                setOnClickListener(workType);
                setOnClickListener(dateContainer);
                setOnClickListener(sousuo);
            }
        }else if(viewType == 3){
            holder = new HomeViewHolder(parent, R.layout.home_hot_information);
            hotRecyclerView = holder.$(R.id.hotRecyclerView);
            initHotRecyclerView();
        }else if(viewType == 4){
            holder = new HomeViewHolder(parent, R.layout.home_recommend);
            recommendRecyclerView = holder.$(R.id.recommendRecyclerView);
            initRecommendRecyclerView();
        }
        return holder;
    }

    public LoopViewPagerAdapter loopViewPagerAdapter;

    public void initViewPager(){
        loopViewPagerAdapter = new LoopViewPagerAdapter(viewPager);
        viewPager.addOnPageChangeListener(loopViewPagerAdapter);
        viewPager.setAdapter(loopViewPagerAdapter);
        loopViewPagerAdapter.start();
    }

    public HotAdapter hotAdapter;

    RecyclerView hotRecyclerView;

    private void initHotRecyclerView(){
        hotRecyclerView.setLayoutManager(new DisableGridLayoutManager(context,2));
        hotRecyclerView.addItemDecoration(new GridDividerItemDecoration(context));
        hotRecyclerView.setAdapter(hotAdapter);
        hotAdapter.setOnItemClickListener(onItemClickListener);
    }

    RecyclerView recommendRecyclerView;

    public RecommendAdapter recommendAdapter;

    private void initRecommendRecyclerView(){
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        recommendRecyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recommendRecyclerView.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(onItemClickListener);
    }

    public enum Type{
        HomeImg,Search,Hot,Recommend
    }

    @Override
    public int getItemViewType(int position) {
        Type type = contentList.get(position).getType();
        if(type == Type.HomeImg){
            return 1;
        }else if(type == Type.Search){
            return 2;
        }else if(type == Type.Hot){
            return 3;
        }else if(type == Type.Recommend){
            return 4;
        }
        return super.getItemViewType(position);
    }
}
