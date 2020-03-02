package cn.com.qc.adapter;

import android.content.Context;
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
import cn.com.qc.javabean.PeopleInfo;

public class PeopleAdapter extends BaseAdapter {
    private List<PeopleInfo> list;
    private Context context;

    public PeopleAdapter(List<PeopleInfo> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.peoplefragment_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).getImg())
                .into(viewHolder.img);
        viewHolder.job.setText("姓名：" + list.get(position).getJob());
        viewHolder.work.setText("岗位：" + list.get(position).getWork());
        viewHolder.price.setText(list.get(position).getPrice() + "/月");
        return convertView;
    }

    public void addData(List<PeopleInfo> peopleInfoList) {
        for (PeopleInfo peopleInfo : peopleInfoList) {
            this.list.add(peopleInfo);
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
        @BindView(R.id.work)
        TextView work;
        @BindView(R.id.price)
        TextView price;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
