package cn.com.qc.viewholder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.com.qc.R;
import cn.com.qc.bean.PositionBean;

/**
 * Created by lenovo on 2017/9/27.
 */

public class PositionViewHolder extends YBaseViewHolder<PositionBean.Job> {

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.salary)
    TextView salary;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.needNum)
    TextView needNum;
    @BindView(R.id.location)
    TextView location;

    public PositionViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    @Override
    public void setData(PositionBean.Job data) {
        position.setText(data.getTitle());
        salary.setText(data.getSalary() + "元/日");
        date.setText("日期:" + data.getBeginDate() + "/" + data.getEndDate());
        needNum.setText("人数需求:" + data.getPeopleNumber());
        location.setText("地点:" + data.getAddress());
        String dayReport = data.getDayReport();//1.日结 2.周结 3.月结
    }
}
