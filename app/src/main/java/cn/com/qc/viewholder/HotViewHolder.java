package cn.com.qc.viewholder;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.com.qc.R;
import cn.com.qc.bean.HomeHot;
import cn.com.qc.ypresenter.HomePresenter;

/**
 * Created by lenovo on 2017/9/25.
 */

public class HotViewHolder extends YBaseViewHolder<HomeHot.HomeHotData> {

    public HotViewHolder(View itemView) {
        super(itemView);
    }

    public HotViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.briefText)
    TextView briefText;
    @BindView(R.id.briefImg)
    ImageView briefImg;

    @Override
    public void setData(HomeHot.HomeHotData data) {
        title.setText(data.getName());
        briefText.setText(data.getRemark());
        String url = data.getImgUrl();
        new HomePresenter().into(itemView.getContext(),url,briefImg,R.mipmap.hoticon);
    }
}
