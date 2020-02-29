package cn.com.qc.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.adapter.FindAdapter;
import cn.com.qc.adapter.YBaseAdapter;
import cn.com.qc.app.App;
import cn.com.qc.bean.FindBean;
import cn.com.qc.bean.FindHotCompany;
import cn.com.qc.help.NetUrl;
import cn.com.qc.leeactivity.CompanyInfosActivity;
import cn.com.qc.view.DividerItemDecoration;
import cn.com.qc.viewholder.YBaseViewHolder;
import cn.com.qc.yinter.BaseInter;
import cn.com.qc.yinter.FindDataListener;
import cn.com.qc.ypresenter.FindPresenter;

public class FindFragment extends Fragment implements BaseInter, FindDataListener {

    @BindView(R.id.returns)
    LinearLayout returns;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;

    Unbinder unbinder;

    FindPresenter findPresenter;

    App app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        findPresenter = new FindPresenter();
        findPresenter.attach(this);
        init();
        return view;
    }

    AlertDialog alertDialog;

    public void init() {
        title.setText("发现");
        returns.setVisibility(View.INVISIBLE);
        initRecycler();
        app = (App) getActivity().getApplication();
    //    alertDialog = Helps.showDialog(getActivity());
        initData();
    }

    private void initData() {
        findPresenter.getHotCompany(NetUrl.DNS + NetUrl.Find, app.city);
    }

    FindAdapter findAdapter;

    private void initRecycler() {
        findAdapter = new FindAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), OrientationHelper.VERTICAL));
        recyclerView.setAdapter(findAdapter);
        findAdapter.setOnItemClickListener(onItemClickListener);
    }

    YBaseAdapter.OnItemClickListener onItemClickListener = new YBaseAdapter.OnItemClickListener() {
        @Override
        public <h extends YBaseViewHolder> void itemClick(int position, h holder) {
            List<FindBean> list = findAdapter.getContentList();
            FindBean bean = list.get(position);
            int type = bean.getType();
            if(type == 2){
                Intent intent = new Intent(getActivity(), CompanyInfosActivity.class);
                intent.putExtra("id",((FindHotCompany.Company)bean.getT()).getId());
                startActivity(intent);
            }else if(type == 4){

            }
        }
    };

    @Override
    public void onSuccessHotCompany(FindHotCompany.FindData datas) {
        if (datas != null) {
            List<FindBean> list = findAdapter.getContentList();
            List<FindHotCompany.Company> companys = datas.getEnterpriseHot();
            List<FindHotCompany.Infomation> infomations = datas.getInformationHot();
            FindBean findBean;
            if (companys != null && companys.size() > 0) {
                findBean = new FindBean(1);
                list.add(findBean);

                Iterator<FindHotCompany.Company> cIter = companys.iterator();
                while (cIter.hasNext()) {
                    FindHotCompany.Company company = cIter.next();
                    findBean = new FindBean(2, company);
                    list.add(findBean);
                }
            }

            if(infomations != null&&  infomations.size() > 0){
                findBean = new FindBean(3);
                list.add(findBean);

                Iterator<FindHotCompany.Infomation> iIter = infomations.iterator();
                while (iIter.hasNext()) {
                    FindHotCompany.Infomation company = iIter.next();
                    findBean = new FindBean(4, company);
                    list.add(findBean);
                }
            }

            findAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {}

}
