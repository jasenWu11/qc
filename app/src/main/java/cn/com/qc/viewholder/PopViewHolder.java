package cn.com.qc.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;

/**
 * Created by lenovo on 2017/9/27.
 */

public class PopViewHolder {

    @BindView(R.id.text)
    TextView text;

    public PopViewHolder(View itemView){
        ButterKnife.bind(this,itemView);
    }

    public void setData(String s){
        text.setText(s);
    }

}
