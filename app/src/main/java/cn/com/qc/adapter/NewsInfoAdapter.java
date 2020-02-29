package cn.com.qc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tandong.sa.tools.AssistTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.qc.R;
import cn.com.qc.javabean.NewsInfo;
import cn.com.qc.leeactivity.MyCollectionJobDetailsActivity;
import cn.com.qc.main.LoginActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;

public class NewsInfoAdapter extends BaseAdapter {
    private List<NewsInfo> list;
    private Context context;
    private AssistTool assistTool;
    private String localToken = "";
    private String studentId = "";
    private static final String typeURL1 = "mobile/addCollect.do";
    private static final String typeURL2 = "mobile/deleteCollect.do";

    public NewsInfoAdapter(List<NewsInfo> list, Context context) {
        this.list = list;
        this.context = context;
        assistTool = new AssistTool(context);
        localToken = assistTool.getPreferenceString("localToken");
        studentId = assistTool.getPreferenceString("studentId");
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.newsfragment_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.job.setText("招聘职位：" + list.get(position).getJob());
        viewHolder.company.setText("公司名称：" + list.get(position).getCompany());
        viewHolder.people.setText("招聘人数：" + list.get(position).getPeople());
        viewHolder.location.setText("工作地点：" + list.get(position).getLocation());
        final int isCollect = list.get(position).getIsCollect();
        if (isCollect == 1) {
            viewHolder.guanzhu.setText("已收藏");
        } else if (isCollect == 0) {
            viewHolder.guanzhu.setText("收藏");
        }
        viewHolder.guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localToken.equals(null) || localToken.equals("") || studentId.equals(null) || studentId.equals("")) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    if (list.get(position).getIsCollect() == 0) {
                        OkGo.<String>post(URLConst.BASEURL(typeURL1))
                                .tag(this)
                                .headers("device", "1")
                                .headers("localToken", localToken)
                                .params("userId", studentId)
                                .params("objectId", list.get(position).getId())
                                .params("collectType", 2)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body());
                                            int infoCode = jsonObject.getInt("infoCode");
                                            if (infoCode == 1) {
                                                Tools.toastS(context, "成功添加收藏");
                                                viewHolder.guanzhu.setText("已收藏");
                                                list.get(position).setIsCollect(1);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    } else if (list.get(position).getIsCollect() == 1) {
                        OkGo.<String>post(URLConst.BASEURL(typeURL2))
                                .tag(this)
                                .headers("device", "1")
                                .headers("localToken", localToken)
                                .params("userId", studentId)
                                .params("objectId", list.get(position).getId())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body());
                                            int infoCode = jsonObject.getInt("infoCode");
                                            if (infoCode == 1) {
                                                Tools.toastS(context, "已取消收藏");
                                                viewHolder.guanzhu.setText("收藏");
                                                list.get(position).setIsCollect(0);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyCollectionJobDetailsActivity.class);
                intent.putExtra("id", list.get(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void addData(List<NewsInfo> newsInfoList) {
        for (NewsInfo newsInfo : newsInfoList) {
            this.list.add(newsInfo);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.job)
        TextView job;
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.people)
        TextView people;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.guanzhu)
        Button guanzhu;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
