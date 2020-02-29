package cn.com.qc.adapter;

import android.content.Context;
import android.view.ViewGroup;

import cn.com.qc.R;
import cn.com.qc.bean.SearchCompanyBean;
import cn.com.qc.viewholder.SearchViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchAdapter extends YBaseAdapter<SearchCompanyBean.SearchData> {

    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(parent, R.layout.search_recycler_item);
    }

    public void clear(){
        contentList.clear();
    }
}
