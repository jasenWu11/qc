package cn.com.qc.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import cn.com.qc.R;
import cn.com.qc.bean.PositionBean;
import cn.com.qc.viewholder.PositionViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/9/27.
 */

public class PositionAdapter extends YBaseAdapter<PositionBean.Job> {


    public PositionAdapter(Context context) {
        super(context);
    }

    @Override
    public YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PositionViewHolder(parent, R.layout.companyinfo_position_recycler_item);
    }

}
