package cn.com.qc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;
import cn.com.qc.javabean.CompanyInfo;

/**
 * Created by Lee on 2017/9/27.
 */

public class CompanyInfoAdapter extends BaseAdapter {
    private List<CompanyInfo> list;
    private Context context;
    private static final int TYPE_HEAD = 0; //头布局类型
    private static final int TYPE_LIST = 1; //列表类型

    public CompanyInfoAdapter(List<CompanyInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_LIST;
        }
    }

    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolderHead viewHolderHead = null;
        ViewHolderList viewHolderList = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_HEAD:
                    convertView = LayoutInflater.from(context).inflate(R.layout.companyinfo_head, parent, false);
                    viewHolderHead = new ViewHolderHead(convertView);
                    convertView.setTag(viewHolderHead);
                    break;
                case TYPE_LIST:
                    convertView = LayoutInflater.from(context).inflate(R.layout.companyinfo_list, parent, false);
                    viewHolderList = new ViewHolderList(convertView);
                    convertView.setTag(viewHolderList);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_HEAD:
                    viewHolderHead = (ViewHolderHead) convertView.getTag();
                    break;
                case TYPE_LIST:
                    viewHolderList = (ViewHolderList) convertView.getTag();
                    break;
            }
        }
        switch (type) {
            case TYPE_HEAD:
                viewHolderHead.companycount.setText("企业：" + "100");
                viewHolderHead.jobcount.setText("职位：" + "100");
                viewHolderHead.peoplecount.setText("竞聘人数：" + "100");
                viewHolderHead.company.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                viewHolderHead.job.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                viewHolderHead.people.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case TYPE_LIST:
//                Glide.with(context)
//                        .load(list.get(position - 1).getImg())
//                        .into(viewHolderList.img);
        //        viewHolderList.title.setText(list.get(position - 1).getTitle());
                viewHolderList.location.setText(list.get(position - 1).getLocation());
                viewHolderList.content.setText(list.get(position - 1).getContent());
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
        }
        return convertView;
    }

    static class ViewHolderHead {
        @BindView(R.id.companycount)
        TextView companycount;
        @BindView(R.id.jobcount)
        TextView jobcount;
        @BindView(R.id.peoplecount)
        TextView peoplecount;
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.job)
        TextView job;
        @BindView(R.id.people)
        TextView people;

        ViewHolderHead(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderList {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.location)
        TextView location;

        ViewHolderList(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
