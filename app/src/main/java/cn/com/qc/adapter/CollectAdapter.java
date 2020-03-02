package cn.com.qc.adapter;

import android.content.Context;
import android.view.ViewGroup;

import cn.com.qc.R;
import cn.com.qc.bean.CollectBean;
import cn.com.qc.viewholder.CollectViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/11/30.
 */

public class CollectAdapter extends YBaseAdapter<CollectBean> {

    public CollectAdapter(Context context) {
        super(context);
    }

    @Override
    public YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectViewHolder(parent, R.layout.collect_recycler_item);
    }

}
