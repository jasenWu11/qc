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
import cn.com.qc.javabean.OfferInfo;
import cn.com.qc.leeactivity.OfferInfoDetailsActivity;

public class OfferInfoAdapter extends BaseAdapter {
    private List<OfferInfo> list;
    private Context context;

    public OfferInfoAdapter(List<OfferInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.offerinfo_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.job.setText("招聘职位：" + list.get(position).getJob());
        viewHolder.company.setText("公司名称：" + list.get(position).getCompany());
        viewHolder.location.setText("工作地点：" + list.get(position).getLocation());
        viewHolder.date.setText("时间：" + list.get(position).getDate());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OfferInfoDetailsActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("status", list.get(position).getStatus());
                intent.putExtra("companyS", list.get(position).getCompany());
                intent.putExtra("contentS", list.get(position).getContent());
                intent.putExtra("locationS", list.get(position).getLocation());
                intent.putExtra("phoneS", list.get(position).getPhone());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void addData(List<OfferInfo> offerInfoList) {
        for (OfferInfo offerInfo : offerInfoList) {
            this.list.add(offerInfo);
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
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.date)
        TextView date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
