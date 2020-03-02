package cn.com.qc.viewholder;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.qc.R;
import cn.com.qc.bean.FindBean;
import cn.com.qc.bean.FindHotCompany;
import cn.com.qc.bean.FindHotInfo;
import cn.com.qc.ypresenter.FindPresenter;

/**
 * Created by lenovo on 2017/10/16.
 */

public class FindViewHolder extends YBaseViewHolder<FindBean> {

    public FindViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    TextView text;

    TextView companyName,posNum,peopleNum,workAddress;
    ImageView icon;

    ImageView img;
    TextView title,brief,watchNum;

    @Override
    public void setData(FindBean data) {
        int type = data.getType();
        if(type == 1){
            text = $(R.id.text);
            text.setText("热门兼职");
            text.setClickable(false);
        }else if(type == 2){
            companyName = $(R.id.companyName);
            posNum = $(R.id.posNum);
            peopleNum = $(R.id.peopleNum);
            workAddress = $(R.id.workAddress);
            icon = $(R.id.icon);

            FindHotCompany.Company c = (FindHotCompany.Company) data.getT();
            companyName.setText("公司名称:" + c.getName());
            posNum.setText("工作岗位:" + c.getJobNumber());
            peopleNum.setText("招聘人数:" + c.getPersonNumber());
            workAddress.setText("工作地点:" + c.getAddress());
        }else if(type ==3){
            text = $(R.id.text);
            text.setText("热门资讯");
            text.setClickable(false);
        }else{
            img = $(R.id.img);
            title = $(R.id.title);
            brief = $(R.id.brief);
            watchNum = $(R.id.watchNum);

            FindHotCompany.Infomation h = (FindHotCompany.Infomation) data.getT();
            title.setText("标题名称:" + h.getTitle());
            brief.setText("副标题/简介:" + h.getSynopsis());
            watchNum.setText(" " + h.getLook());//\t
            new FindPresenter().intoLocal(itemView.getContext(),h.getImgUrl(),img,R.mipmap.zixun);
        }
    }

}
