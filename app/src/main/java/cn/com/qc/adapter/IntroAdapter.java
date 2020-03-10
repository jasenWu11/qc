package cn.com.qc.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;
import cn.com.qc.javabean.IntroInfo;
import cn.com.qc.leeactivity.CompanyDetailsActivity;
import cn.com.qc.leeactivity.WatchIntroActivity;

public class IntroAdapter extends BaseAdapter {
    private List<IntroInfo> list;
    private Context context;

    public IntroAdapter(List<IntroInfo> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.introactivity_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.index.setText("序号：" + list.get(position).getIndex());
        viewHolder.fileName.setText("文件名称：" + list.get(position).getFileName());
        viewHolder.date.setText("日期：" + list.get(position).getCreateTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pdf_url = list.get(position).getPaths()+list.get(position).getFileId();
                Intent intent = new Intent(context, WatchIntroActivity.class);
                intent.putExtra("pdf_url", pdf_url);
                intent.putExtra("fileName", list.get(position).getFileName());
                context.startActivity(intent);
                System.out.println("查看简历文件"+pdf_url);
            }
        });
        return convertView;
    }

    public void addData(List<IntroInfo> introInfo) {
        for (IntroInfo IntroInfo : introInfo) {
            this.list.add(IntroInfo);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.index)
        TextView index;
        @BindView(R.id.fileName)
        TextView fileName;
        @BindView(R.id.date)
        TextView date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private void openPDF(File file) {
        if (file.exists()) {
            Log.d("打开","打开");
            Uri path1 = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path1, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                context.startActivity(intent);
            }
            catch (Exception e) {
                Log.d("打开失败","打开失败");
            }
        }
    }
}
