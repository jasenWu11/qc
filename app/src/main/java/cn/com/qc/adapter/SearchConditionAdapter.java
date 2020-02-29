package cn.com.qc.adapter;

import android.content.Context;
import android.view.ViewGroup;

import cn.com.qc.R;
import cn.com.qc.bean.SearchConditionBean;
import cn.com.qc.bean.SearchPositionBean;
import cn.com.qc.viewholder.SearchConditionViewHolder;
import cn.com.qc.viewholder.SearchPosViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchConditionAdapter extends YBaseAdapter<SearchConditionBean.SearchCoditionData> {

    public SearchConditionAdapter(Context context) {
        super(context);
    }

    @Override
    public YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchConditionViewHolder(parent, R.layout.search_recycler_pos_item);
    }
}
