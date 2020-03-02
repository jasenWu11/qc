package cn.com.qc.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.qc.help.Helps;
import cn.com.qc.viewholder.YBaseViewHolder;

/**
 * Created by lenovo on 2017/9/25.
 */

public abstract class YBaseAdapter<T> extends RecyclerView.Adapter<YBaseViewHolder<T>>{

    List<T> contentList;

    Context context;

    public YBaseAdapter(Context context){
        this.context = context;
        contentList = new ArrayList<>();
    }

    public YBaseAdapter(Context context,List<T> list){
        this.context = context;
        contentList = new ArrayList<>(list);
    }

    public YBaseAdapter(Context context,T[] t){
        this.context = context;
        contentList = new ArrayList<>(Arrays.asList(t));
    }

    public void setData(List<T> dataList){
        contentList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<T> getContentList(){
        return contentList;
    }

    @Override
    public YBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final YBaseViewHolder holder = OnCreateViewHolder(parent,viewType);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.itemClick(holder.getAdapterPosition(),holder);
                }
            }
        });
        return holder;
    }

    public abstract YBaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(YBaseViewHolder holder, int position) {
        holder.setData(getItem(position));
    }

    public T getItem(int position){
        return contentList.get(position);
    }

    @Override
    public int getItemCount() {
        return contentList == null?0:contentList.size();
    }

    public interface OnItemClickListener{
        <h extends YBaseViewHolder> void itemClick(int position,h holder);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
