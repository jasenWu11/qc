package cn.com.qc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;
import cn.com.qc.javabean.PopwindowListS;

public class PopwindowListSAdapter extends BaseAdapter {
    private List<PopwindowListS> list;
    private Context context;

    public PopwindowListSAdapter(List<PopwindowListS> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.popwindowlist_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item.setText(list.get(position).getItem());
        return convertView;
    }

    public void addData(List<PopwindowListS> popwindowListS) {
        for (PopwindowListS listS : popwindowListS) {
            this.list.add(listS);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.item)
        TextView item;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
