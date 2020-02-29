package cn.com.qc.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import cn.com.qc.R;
import cn.com.qc.bean.SearchConditionBean;
import cn.com.qc.bean.SearchPositionBean;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchConditionViewHolder extends YBaseViewHolder<SearchConditionBean.SearchCoditionData> {

    public SearchConditionViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.salary)
    TextView salary;

    @Override
    public void setData(SearchConditionBean.SearchCoditionData data) {
        position.setText("职位名称:" + data.getJobName());
        name.setText("公司名称:" + data.getEnterpriseName());
        address.setText("地点:" + data.getAddress());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new java.sql.Date(Long.parseLong(data.getEndDate())));
        time.setText("日期:" + date);
        salary.setText(data.getSalary());
    }

}
