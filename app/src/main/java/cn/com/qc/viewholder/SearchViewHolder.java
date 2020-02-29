package cn.com.qc.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.com.qc.R;
import cn.com.qc.bean.SearchCompanyBean;
import cn.com.qc.ypresenter.SearchPresenter;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchViewHolder extends YBaseViewHolder<SearchCompanyBean.SearchData> {

    public SearchViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.type)
    TextView type;

    @Override
    public void setData(SearchCompanyBean.SearchData data) {
        name.setText(data.getName());
        type.setText(data.getEnterpriseType());
        new SearchPresenter().into(itemView.getContext(),data.getLogo(),image,R.mipmap.recommendicon);
    }

}
