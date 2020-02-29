package cn.com.qc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;
import cn.com.qc.javabean.CompanyRecruit;
import cn.com.qc.leeactivity.CompanyInfoActivity;

public class CompanyRecruitAdapter extends BaseAdapter {
    private List<CompanyRecruit> list;
    private Context context;
    private static final int TYPE_HEAD = 0; //头布局类型
    private static final int TYPE_LIST = 1; //列表类型

    public CompanyRecruitAdapter(List<CompanyRecruit> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolderHead viewHolderHead = null;
        ViewHolderList viewHolderList = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_HEAD:
                    convertView = LayoutInflater.from(context).inflate(R.layout.companyrecruit_head, parent, false);
                    viewHolderHead = new ViewHolderHead(convertView);
                    convertView.setTag(viewHolderHead);
                    break;
                case TYPE_LIST:
                    convertView = LayoutInflater.from(context).inflate(R.layout.companyrecruit_list, parent, false);
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
                // TODO
                break;
            case TYPE_LIST:
                Glide.with(context)
                        .load(list.get(position - 1).getImg())
                        .into(viewHolderList.img);
                viewHolderList.title.setText(list.get(position - 1).getTitle());
                viewHolderList.date.setText(list.get(position - 1).getDate());
                viewHolderList.content.setText("招聘岗位：" + list.get(position - 1).getContent());
                int company = list.get(position - 1).getCompany();
                if (company < 1000) {
                    viewHolderList.company.setText("企业：" + company);
                } else if (company > 1000) {
                    viewHolderList.company.setText("企业：1000+");
                }
                int job = list.get(position - 1).getJob();
                if (job < 1000) {
                    viewHolderList.job.setText("职位：" + job);
                } else if (job > 1000) {
                    viewHolderList.job.setText("职位：1000+");
                }
                int people = list.get(position - 1).getPeople();
                if (people < 1000) {
                    viewHolderList.people.setText("竞聘人数：" + people);
                } else if (people > 1000) {
                    viewHolderList.people.setText("竞聘人数：1000+");
                }
                viewHolderList.enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CompanyInfoActivity.class);
                        intent.putExtra("id", list.get(position - 1).getId());
                        intent.putExtra("company", list.get(position - 1).getCompany() + "");
                        intent.putExtra("job", list.get(position - 1).getJob() + "");
                        intent.putExtra("people", list.get(position - 1).getPeople() + "");
                        context.startActivity(intent);
                    }
                });
                break;
        }
        return convertView;
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addData(List<CompanyRecruit> companyRecruitList) {
        for (CompanyRecruit list : companyRecruitList) {
            this.list.add(list);
        }
        notifyDataSetChanged();
    }

    static class ViewHolderList {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.enter)
        Button enter;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.job)
        TextView job;
        @BindView(R.id.people)
        TextView people;

        ViewHolderList(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderHead {
        @BindView(R.id.img)
        ImageView img;

        ViewHolderHead(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
