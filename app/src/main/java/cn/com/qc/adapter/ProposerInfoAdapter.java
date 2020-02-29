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
import cn.com.qc.javabean.ProposerInfo;

public class ProposerInfoAdapter extends BaseAdapter {
    private List<ProposerInfo> list;
    private Context context;

    public ProposerInfoAdapter(List<ProposerInfo> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.proposerinfo_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(context)
//                .load(list.get(position).getImg())
//                .into(viewHolder.img);
        viewHolder.name.setText("姓名：" + list.get(position).getName());
        viewHolder.phone.setText("联系方式：" + list.get(position).getPhone());
        viewHolder.score.setText("职评分：" + list.get(position).getScore());
        viewHolder.time.setText("兼职累计时长：" + list.get(position).getTime());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.score)
        TextView score;
        @BindView(R.id.time)
        TextView time;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
