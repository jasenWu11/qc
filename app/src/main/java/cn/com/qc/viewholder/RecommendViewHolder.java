package cn.com.qc.viewholder;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cn.com.qc.R;
import cn.com.qc.bean.Company;
import cn.com.qc.bean.HomeBean;
import cn.com.qc.bean.HomeReturnDataBean;
import cn.com.qc.bean.Information;
import cn.com.qc.ypresenter.HomePresenter;

/**
 * Created by lenovo on 2017/9/25.
 */

public class RecommendViewHolder extends YBaseViewHolder<Information> {

    public RecommendViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    @Override
    public void setData(Information data) {
        recommendTitle.setText(data.getTitle());
        recommendBrief.setText(data.getSynopsis());
        String imgUrl = data.getImgUrl();
        new HomePresenter().intoLocal(itemView.getContext(),imgUrl,recommendImg,R.mipmap.recommendicon);
    }

    @BindView(R.id.recommendImg)
    ImageView recommendImg;
    @BindView(R.id.recommendTitle)
    TextView recommendTitle;
    @BindView(R.id.recommendBrief)
    TextView recommendBrief;

}
