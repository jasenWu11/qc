package cn.com.qc.viewholder;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;

/**
 * Created by lenovo on 2017/9/25.
 */

public abstract class YBaseViewHolder<T> extends RecyclerView.ViewHolder{

    public YBaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public YBaseViewHolder(ViewGroup parent, @LayoutRes int res){
        super(LayoutInflater.from(parent.getContext()).inflate(res,parent,false));
        ButterKnife.bind(this,itemView);
    }

    public void setData(T data){}

    public <V extends View> V $(@IdRes int idRes){
        return (V) itemView.findViewById(idRes);
    }

    public <R extends RecyclerView.Adapter> R getOwnAdapter(){
        RecyclerView recyclerView = getOwnRecyclerView();
        return recyclerView == null?null: (R) recyclerView.getAdapter();
    }

    public RecyclerView getOwnRecyclerView(){
        try {
            Field field = RecyclerView.ViewHolder.class.getDeclaredField("mOwnerRecyclerView");
            field.setAccessible(true);
            return (RecyclerView) field.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
