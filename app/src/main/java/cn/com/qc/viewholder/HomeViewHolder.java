package cn.com.qc.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.qc.adapter.HomeAdapter;
import cn.com.qc.bean.HomeBean;
import cn.com.qc.bean.HomeCondition;
import cn.com.qc.bean.HomePic;
import cn.com.qc.bean.HomeHot;
import cn.com.qc.bean.HomeReturnDataBean;
import cn.com.qc.bean.Information;

/**
 * Created by lenovo on 2017/10/11.
 */

public class HomeViewHolder extends YBaseViewHolder<HomeBean> {

    public HomeViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    @Override
    public void setData(HomeBean data) {
        HomeAdapter.Type type = data.getType();
        if(type == HomeAdapter.Type.HomeImg){
            setViewPagerData(data.getT());
        }else if(type == HomeAdapter.Type.Search){
            setSearchData(data.getHomeCondition());
        }else if(type == HomeAdapter.Type.Hot){
            setHotRecyclerData(data.getT());
        }else if(type == HomeAdapter.Type.Recommend){
            setRecommendRecycler(data.getT());
        }
    }

    public void setViewPagerData(List<HomePic> homeImgBean){
        List<HomePic> picList = homeImgBean;
        if(picList == null){
            picList = new ArrayList<>();
        }
        if(picList.size() == 0){
            picList.add(null);
        }
        HomeAdapter adapter = getOwnAdapter();
        adapter.loopViewPagerAdapter.setList(picList);
    }

    public void setSearchData(HomeCondition homeCondition){

    }

    private <V extends View> void setViewClickAble(V v,Object[] objects){
        if(objects == null||objects.length == 0){
            v.setClickable(false);
        }else{
            v.setClickable(true);
        }
    }

    public void setHotRecyclerData(List<HomeHot.HomeHotData> hotBean){
        List<HomeHot.HomeHotData> companyList = hotBean;
        if(companyList == null){
            companyList = new ArrayList<>();
        }
        HomeAdapter adapter = getOwnAdapter();
        adapter.hotAdapter.setData(companyList);
    }

    public void setRecommendRecycler(List<Information> informations){
        HomeAdapter adapter = getOwnAdapter();
        adapter.recommendAdapter.setData(informations);
    }

}
