package cn.com.qc.adapter;

import android.content.Context;
import android.content.Intent;
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
import cn.com.qc.javabean.CompanyInfos;
import cn.com.qc.leeactivity.JobDetailsActivity;

public class CompanyInfosAdapter extends BaseAdapter {
    private List<CompanyInfos> list;
    private Context context;

    public CompanyInfosAdapter(List<CompanyInfos> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.companyinfos_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.job.setText(list.get(position).getJob());
        viewHolder.date.setText("日期：" + list.get(position).getDate());
        viewHolder.location.setText("地点：" + list.get(position).getLocation());
        viewHolder.people.setText("人数：" + list.get(position).getPeople());
        viewHolder.price.setText(list.get(position).getPrice() + "/日");
        int paysize = list.get(position).getPaysize();
        if (paysize == 1) {
            viewHolder.imageView.setImageResource(R.mipmap.ri);
        } else if (paysize == 2) {
            viewHolder.imageView.setImageResource(R.mipmap.zhou);
        } else if (paysize == 3) {
            viewHolder.imageView.setImageResource(R.mipmap.yue);
        } else if (paysize == 0) {
            viewHolder.imageView.setImageResource(R.mipmap.ri);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("id", list.get(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void addData(List<CompanyInfos> companyInfosList) {
        for (CompanyInfos companyInfos : companyInfosList) {
            this.list.add(companyInfos);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.job)
        TextView job;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.people)
        TextView people;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
