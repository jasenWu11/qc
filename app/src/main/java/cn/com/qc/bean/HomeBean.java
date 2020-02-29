package cn.com.qc.bean;

import java.util.List;

import cn.com.qc.adapter.HomeAdapter;

/**
 * Created by lenovo on 2017/10/11.
 */

public class HomeBean<T> {

    /**
     * recyclerView的item类型（1、轮播图，2、搜索工作条件，3、热门，4、资讯）
     */
    HomeAdapter.Type type;

    /**
     * 数据源
     */
    List<T> t;

    HomeCondition homeCondition;

    public HomeBean(HomeAdapter.Type type, HomeCondition homeCondition) {
        this.type = type;
        this.homeCondition = homeCondition;
    }

    public HomeBean(HomeAdapter.Type type, List<T> t) {
        this.type = type;
        this.t = t;
    }

    public HomeAdapter.Type getType() {
        return type;
    }

    public void setType(HomeAdapter.Type type) {
        this.type = type;
    }

    public List<T> getT() {
        return t;
    }

    public void setT(List<T> t) {
        this.t = t;
    }

    public HomeCondition getHomeCondition() {
        return homeCondition;
    }

    public void setHomeCondition(HomeCondition homeCondition) {
        this.homeCondition = homeCondition;
    }

}
