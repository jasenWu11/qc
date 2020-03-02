package cn.com.qc.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.othershe.calendarview.CalendarView;
import com.othershe.calendarview.DateBean;
import com.othershe.calendarview.listener.CalendarViewAdapter;
import com.othershe.calendarview.listener.OnMonthItemClickListener;
import com.othershe.calendarview.listener.OnPagerChangeListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.adapter.HomeAdapter;
import cn.com.qc.adapter.YBaseAdapter;
import cn.com.qc.app.App;
import cn.com.qc.bean.CompanyType;
import cn.com.qc.bean.HomeBean;
import cn.com.qc.bean.HomeCondition;
import cn.com.qc.bean.HomeHot;
import cn.com.qc.bean.Industry;
import cn.com.qc.bean.Job;
import cn.com.qc.help.Helps;
import cn.com.qc.help.NetUrl;
import cn.com.qc.leeactivity.CompanyInfosActivity;
import cn.com.qc.leeactivity.CompanyRecruitActivity;
import cn.com.qc.main.SearchActivity;
import cn.com.qc.viewholder.HotViewHolder;
import cn.com.qc.viewholder.RecommendViewHolder;
import cn.com.qc.viewholder.YBaseViewHolder;
import cn.com.qc.yinter.BaseInter;
import cn.com.qc.yinter.HomeDataListener;
import cn.com.qc.yinter.HomeTypeChooseInter;
import cn.com.qc.ypresenter.HomePresenter;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class HomeFragment extends Fragment implements HomeTypeChooseInter, BaseInter, HomeDataListener, TextView.OnEditorActionListener {

    Unbinder unbinder;

    @BindView(R.id.swipe_target)
    RecyclerView swipe_target;
    @BindView(R.id.home_chooseCity)
    LinearLayout home_chooseCity;
    @BindView(R.id.home_city)
    TextView home_city;
    @BindView(R.id.searchEdit)
    EditText searchEdit;
    @BindView(R.id.cityTv)
    TextView cityTv;

    HomePresenter presenter;

    App app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new HomePresenter();
        presenter.attach(this);
        init();
        return view;
    }

    HomeAdapter.OnViewClick OnViewClick = new HomeAdapter.OnViewClick() {
        @Override
        public void click(View view) {
            int id = view.getId();
            boolean isShow = false;
            switch (id) {
                case R.id.dateContainer:
                    toastDate(view);
                    break;
                case R.id.businessType:
                    isShow = companyTypeList == null ? false : true && cType.size() == 0 ? false : true;
                    Helps.showPopWindow(getActivity(), isShow, cType, homeAdapter.businessType.getWidth()
                            , homeAdapter.businessType, HomeFragment.this, R.layout.popwindow_text_layout1, 4);
                    break;
                case R.id.tradeType:
                    isShow = tType == null ? false : true && tType.size() == 0 ? false : true;
                    Helps.showPopWindow(getActivity(), isShow, tType, homeAdapter.tradeType.getWidth()
                            , homeAdapter.tradeType, HomeFragment.this, R.layout.popwindow_text_layout1, 4);
                    break;
                case R.id.workType:
                    isShow = wType == null ? false : true && wType.size() == 0 ? false : true;
                    Helps.showPopWindow(getActivity(), isShow, wType, homeAdapter.workType.getWidth()
                            , homeAdapter.workType, HomeFragment.this, R.layout.popwindow_text_layout1, 4);
                    break;
                case R.id.sousuo:
                    search();
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                initLocation();
            }else{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Helps.toast(getActivity(),"该应用需要定位权限");
                        getActivity().finish();
                    }
                });
            }
        }
    }

    private void search() {
        if (app.city.equals("")) {
            return;
        }

        long beginDate = 0;

        String data = homeAdapter.dateText.getText().toString();

        if (data.equals("")) {
            beginDate = 0;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            Date date = null;
            try {
                date = format.parse(data);
                beginDate = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Bundle bundle = new Bundle();
        bundle.putBoolean("ifSearchJob", true);
        bundle.putString("enterpriseTypeId", businessId);
        bundle.putString("industryId", tradeId);
        bundle.putString("jobEntityId", workId);
        bundle.putString("city", app.city);
        bundle.putLong("beginDate", beginDate);
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    AlertDialog alertDialog;

    private void init() {
        //    alertDialog = Helps.showDialog(getActivity());
        app = (App) getActivity().getApplication();
        initData();
        initRecyclerView();
        searchEdit.setOnEditorActionListener(this);
    }

    HomeAdapter homeAdapter;

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        swipe_target.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(getActivity());
        swipe_target.setAdapter(homeAdapter);
        homeAdapter.setOnViewClick(OnViewClick);
        homeAdapter.setOnItemClickListener(onItemClickListener);
    }

    private void initData() {
        initSearchTitle();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                initLocation();
            }
        }else{
            if(checkPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)||
                    checkPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)){
                initLocation();
            }else{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Helps.toast(getActivity(),"该应用需要定位权限");
                        getActivity().finish();
                    }
                });
            }
        }
    }


    private boolean checkPermissionGranted(String permission) {
        return getActivity().checkPermission(permission, android.os.Process.myPid(), android.os.Process.myPid()) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 设置发起定位的模式和相关参数
     */
    public AMapLocationClientOption mLocationOption = null;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    /*
     *初始化高德地图
     */
    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        setLocationOption();
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    private void setLocationOption() {
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mLocationOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mLocationOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mLocationOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            //判断AMapLocation对象不为空，当定位错误码类型为0时定位成功
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    String city = amapLocation.getCity();//城市信息
                    int index = city.indexOf("市");
                    if (index != -1 && index != city.length() - 1) {

                    }
                    app.city = city;
                    cityTv.setText(city);
                    cityTv.setVisibility(View.VISIBLE);
                    presenter.getAllDatas(NetUrl.DNS + NetUrl.HomeData, city);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    app.city = "";
                    Helps.cancelDialog(alertDialog);
                }
            } else {
                Helps.cancelDialog(alertDialog);
            }

        }
    };

    List<String> searchTitle;

    private void initSearchTitle() {
        searchTitle = new ArrayList<>();
        searchTitle.add("公司");
        searchTitle.add("职位");
    }


    @OnClick({R.id.home_chooseCity})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.home_chooseCity:
                Helps.showPopWindow(getActivity(), true, searchTitle, home_chooseCity.getWidth(), home_chooseCity, this, R.layout.popwindow_text_layout, 6);
                break;
        }
    }

    /**
     * 公司类型
     */
    List<CompanyType> companyTypeList = null;
    List<String> cType;

    /**
     * 行业类型
     */
    List<Industry> industryList = null;
    List<String> tType;

    /**
     * 工作类型
     */
    List<Job> jobList = null;
    List<String> wType;

    /**
     * 成功获取全部数据（除资讯）
     *
     * @param homeBean
     */
    public void onSuccessReturnAllData(List<HomeBean> homeBean) {
        homeAdapter.allClear();
        try {
            List<HomeHot.HomeHotData> hots = homeBean.get(2).getT();
            if (hots == null || hots.size() == 0) {
                homeBean.remove(2);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        homeAdapter.setData(homeBean);

        HomeCondition homeCondition = homeBean.get(1).getHomeCondition();
        companyTypeList = homeCondition.getCompanyTypes();
        Iterator<CompanyType> cIt = companyTypeList.iterator();
        cType = new ArrayList<>();
        while (cIt.hasNext()) {
            CompanyType c = cIt.next();
            cType.add(c.getName());
        }

        industryList = homeCondition.getIndustrys();
        Iterator<Industry> iIt = industryList.iterator();
        tType = new ArrayList<>();
        while (iIt.hasNext()) {
            Industry industry = iIt.next();
            tType.add(industry.getName());
        }

        jobList = homeCondition.getJobs();
        Iterator<Job> jIt = jobList.iterator();
        wType = new ArrayList<>();
        while (jIt.hasNext()) {
            Job industry = jIt.next();
            wType.add(industry.getName());
        }

        Helps.cancelDialog(alertDialog);
    }

    public void onErrorReturnAllData() {
        Helps.cancelDialog(alertDialog);
    }

    String businessId, tradeId, workId;

    @Override
    public void onItemClick(View view, String s, int pos) {
        if (view == home_chooseCity) {
            home_city.setText(s);
        } else if (view == homeAdapter.businessType) {
            homeAdapter.businessType.setText(s);
            businessId = companyTypeList.get(pos).getId();
        } else if (view == homeAdapter.tradeType) {
            homeAdapter.tradeType.setText(s);
            tradeId = industryList.get(pos).getId();
        } else if (view == homeAdapter.workType) {
            homeAdapter.workType.setText(s);
            workId = jobList.get(pos).getId() + "";
        }
    }

    public void toastDate(View v) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_calendar, null);
        PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int width = v.getWidth();
        LinearLayout calendarContainer = (LinearLayout) view.findViewById(R.id.calendarContainer);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) calendarContainer.getLayoutParams();
        params.width = width;
        calendarContainer.setLayoutParams(params);
        toastCalendar(view, pop);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        pop.showAsDropDown(v, 0, 5);
        Helps.backgroundAlpha(getActivity(), 0.9f);

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Helps.backgroundAlpha(getActivity(), 1);
            }
        });
    }

    CalendarView calendarView;

    TextView title;

    private void toastCalendar(View view, final PopupWindow pop) {
        title = (TextView) view.findViewById(R.id.title);
        calendarView = (CalendarView) view.findViewById(R.id.calendar);
        view.findViewById(R.id.lastMonth).setOnClickListener(onClickListener);
        view.findViewById(R.id.nextMonth).setOnClickListener(onClickListener);
        final DateBean d = calendarView.getDateInit();
        title.setText(d.getSolar()[0] + "年 " + d.getSolar()[1] + "月" + d.getSolar()[2] + "日");
        calendarView.setOnCalendarViewAdapter(R.layout.item_layout, new CalendarViewAdapter() {
            @Override
            public TextView[] convertView(View view, DateBean date) {
                TextView solarDay = (TextView) view.findViewById(R.id.solar_day);
                TextView lunarDay = (TextView) view.findViewById(R.id.lunar_day);
                return new TextView[]{solarDay, lunarDay};
            }
        });
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月" + date[2] + "日");
            }
        });
        calendarView.setOnItemClickListener(new OnMonthItemClickListener() {
            @Override
            public void onMonthItemClick(View view, DateBean date) {
                title.setText(date.getSolar()[0] +
                        "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");

                pop.dismiss();

                homeAdapter.dateText.setText(date.getSolar()[0] +
                        "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");

            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lastMonth:
                    lastMonth();
                    break;
                case R.id.nextMonth:
                    nextMonth();
                    break;
            }
        }
    };

    public void lastMonth() {
        calendarView.lastMonth();
    }

    public void nextMonth() {
        calendarView.nextMonth();
    }

    YBaseAdapter.OnItemClickListener onItemClickListener = new YBaseAdapter.OnItemClickListener() {
        @Override
        public <h extends YBaseViewHolder> void itemClick(int position, h holder) {
            if (holder instanceof HotViewHolder) {
                Intent intent = new Intent(getActivity(), CompanyRecruitActivity.class);
                intent.putExtra("id", homeAdapter.hotAdapter.getItem(position).getId());
                startActivity(intent);
            } else if (holder instanceof RecommendViewHolder) {
                Intent intent = new Intent(getActivity(), CompanyInfosActivity.class);
                intent.putExtra("id", homeAdapter.recommendAdapter.getItem(position).getId() + "");
                startActivity(intent);
            }
        }

    };

    @Override
    public void onResume() {
        super.onResume();
        if (homeAdapter.loopViewPagerAdapter != null) {
            homeAdapter.loopViewPagerAdapter.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (homeAdapter.loopViewPagerAdapter != null) {
            homeAdapter.loopViewPagerAdapter.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }

        if (homeAdapter.loopViewPagerAdapter != null) {
            homeAdapter.loopViewPagerAdapter.stop();
        }
        presenter.deAttach();

        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String searchCondition = searchEdit.getText().toString();
            if (app.city.equals("")) {
                return false;
            }
            if (!searchCondition.trim().equals("")) {
                String choose = home_city.getText().toString();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("city", app.city);
                //1:职位 2:公司 searchType  searchBody  city
                if (choose.equals("公司")) {
                    bundle.putString("searchType", "2");
                } else {
                    bundle.putString("searchType", "1");
                }
                bundle.putString("searchBody", searchCondition);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        return false;
    }

}
