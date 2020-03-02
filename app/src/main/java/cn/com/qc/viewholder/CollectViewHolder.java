package cn.com.qc.viewholder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import cn.com.qc.bean.CollectBean;

/**
 * Created by lenovo on 2017/11/30.
 */

public class CollectViewHolder extends YBaseViewHolder<CollectBean> {

    public CollectViewHolder(View itemView) {
        super(itemView);
    }

    public CollectViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    @Override
    public void setData(CollectBean data) {

    }

}
