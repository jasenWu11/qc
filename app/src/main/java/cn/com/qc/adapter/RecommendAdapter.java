package cn.com.qc.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import cn.com.qc.R;
import cn.com.qc.bean.HomeBean;
import cn.com.qc.bean.HomeReturnDataBean;
import cn.com.qc.bean.Information;
import cn.com.qc.viewholder.RecommendViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/9/25.
 */

public class RecommendAdapter extends YBaseAdapter<Information> {

    public RecommendAdapter(Context context, List<Information> list) {
        super(context, list);
    }

    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendViewHolder(parent, R.layout.home_recommend_recycler_item);
    }

}
