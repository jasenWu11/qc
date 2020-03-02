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
import cn.com.qc.javabean.MoreJob;

public class MoreJobAdapter extends BaseAdapter {
    private List<MoreJob> list;
    private Context context;
    private static final int TYPE_HEAD = 0; //头布局类型
    private static final int TYPE_LIST = 1; //列表类型

    public MoreJobAdapter(List<MoreJob> list, Context context) {
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
                    convertView = LayoutInflater.from(context).inflate(R.layout.morejob_head, parent, false);
                    viewHolderHead = new ViewHolderHead(convertView);
                    convertView.setTag(viewHolderHead);
                    break;
                case TYPE_LIST:
                    convertView = LayoutInflater.from(context).inflate(R.layout.morejob_list, parent, false);
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

                break;
            case TYPE_LIST:
                viewHolderList.job.setText(list.get(position - 1).getJob());
                viewHolderList.date.setText("日期：" + list.get(position - 1).getDate());
                viewHolderList.location.setText("地点：" + list.get(position - 1).getLocation());
                viewHolderList.money.setText(list.get(position - 1).getMoney() + "/日");
                viewHolderList.size.setText(list.get(position - 1).getSize());
                break;
        }
        return convertView;
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    static class ViewHolderHead {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.companyname)
        TextView companyname;
        @BindView(R.id.size)
        TextView size;
        @BindView(R.id.guimo)
        TextView guimo;
        @BindView(R.id.xingzhi)
        TextView xingzhi;
        @BindView(R.id.hangye)
        TextView hangye;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.content)
        TextView content;

        ViewHolderHead(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderList {
        @BindView(R.id.job)
        TextView job;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.size)
        TextView size;

        ViewHolderList(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
