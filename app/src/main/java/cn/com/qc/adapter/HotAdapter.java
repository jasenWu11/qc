package cn.com.qc.adapter;

import android.content.Context;
import android.view.ViewGroup;

import cn.com.qc.R;
import cn.com.qc.bean.HomeHot;
import cn.com.qc.viewholder.HotViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/9/25.
 */

public class HotAdapter extends YBaseAdapter<HomeHot.HomeHotData> {

    public HotAdapter(Context context) {
        super(context);
    }

    @Override
    public YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotViewHolder(parent, R.layout.home_hot_recycler_item);
    }

}
