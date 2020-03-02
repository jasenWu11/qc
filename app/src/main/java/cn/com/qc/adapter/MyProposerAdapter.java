package cn.com.qc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;
import cn.com.qc.javabean.MyProposer;
import cn.com.qc.leeactivity.MyJobDetailsActivity;

public class MyProposerAdapter extends BaseAdapter {
    private List<MyProposer> list;
    private Context context;

    public MyProposerAdapter(List<MyProposer> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.myproposerfragment_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.job.setText("职位名称：" + list.get(position).getJob());
        viewHolder.company.setText("公司名称：" + list.get(position).getCompany());
        viewHolder.date.setText("日期：" + list.get(position).getDate());
        viewHolder.location.setText("地点：" + list.get(position).getLocation());
        int size = list.get(position).getSize();
        if (size == 1) {
            viewHolder.size.setText("日结");
        } else if (size == 2) {
            viewHolder.size.setText("周结");
        } else if (size == 3) {
            viewHolder.size.setText("月结");
        } else if (size == 0) {
            viewHolder.size.setVisibility(View.GONE);
        }
        viewHolder.title.setText(list.get(position).getTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyJobDetailsActivity.class);
                intent.putExtra("id", list.get(position).getJobId());
                intent.putExtra("step", list.get(position).getStep());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void addData(List<MyProposer> myProposerList) {
        for (MyProposer myProposer : myProposerList) {
            this.list.add(myProposer);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.job)
        TextView job;
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.size)
        TextView size;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
