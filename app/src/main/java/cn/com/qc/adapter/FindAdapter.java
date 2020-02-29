package cn.com.qc.adapter;

import android.content.Context;
import android.view.ViewGroup;

import cn.com.qc.R;
import cn.com.qc.bean.FindBean;
import cn.com.qc.viewholder.FindViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/10/16.
 */

public class FindAdapter extends YBaseAdapter<FindBean> {

    public FindAdapter(Context context) {
        super(context);
    }

    @Override
    public YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        YBaseViewHolder holder = null;
        if(viewType == 1){
            holder = new FindViewHolder(parent, R.layout.find_recycler_text_item);
            return holder;
        }else if(viewType == 2){
            holder = new FindViewHolder(parent, R.layout.find_recycler_company_item);
            return holder;
        }else if(viewType == 3){
            holder = new FindViewHolder(parent, R.layout.find_recycler_text_item);
            return holder;
        }
        holder = new FindViewHolder(parent,R.layout.find_recycler_info_item);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        int type = contentList.get(position).getType();
        if(type == 1){
            return 1;
        }else if(type == 2){
            return 2;
        }else if(type == 3){
            return 3;
        }else{
            return 4;
        }
    }

}
