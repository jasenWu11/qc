package cn.com.qc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;
import cn.com.qc.javabean.CompanyInfo;
import cn.com.qc.leeactivity.CompanyInfosActivity;

public class CompanyAdapter extends BaseAdapter {
    private List<CompanyInfo> list;
    private Context context;

    public CompanyAdapter(List<CompanyInfo> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.companyfragment_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).getImg())
                .into(viewHolder.img);
        viewHolder.job.setText("公司名称：" + list.get(position).getJob());
        viewHolder.content.setText(list.get(position).getContent());
        viewHolder.location.setText(list.get(position).getLocation());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CompanyInfosActivity.class);
                intent.putExtra("id", list.get(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void addData(List<CompanyInfo> companyInfoList) {
        for (CompanyInfo companyInfo : companyInfoList) {
            this.list.add(companyInfo);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.job)
        TextView job;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.location)
        TextView location;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
