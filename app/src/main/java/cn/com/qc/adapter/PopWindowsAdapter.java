package cn.com.qc.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.qc.R;
import cn.com.qc.viewholder.PopViewHolder;

/**
 * Created by lenovo on 2017/9/27.
 */

public class PopWindowsAdapter extends BaseAdapter {

    List<String> list;

    Context context;

    int resId;

    public PopWindowsAdapter(Context context, List<String> list, @LayoutRes int resId){
        this.context = context;
        this.list = new ArrayList<>(list);
        this.resId = resId;
    }

    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(resId,parent,false);
            vh = new PopViewHolder(convertView);
            convertView.setTag(vh);
        }
        vh = (PopViewHolder) convertView.getTag();
        vh.setData(list.get(position));
        return convertView;
    }

}
