package cn.com.qc.leeactivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tandong.sa.tools.AssistTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.CompanyAdapter;
import cn.com.qc.adapter.JobAdapter;
import cn.com.qc.adapter.PeopleAdapter;
import cn.com.qc.adapter.PopwindowListAdapter;
import cn.com.qc.adapter.PopwindowListSAdapter;
import cn.com.qc.javabean.CompanyInfo;
import cn.com.qc.javabean.JobInfo;
import cn.com.qc.javabean.PeopleInfo;
import cn.com.qc.javabean.PopwindowList;
import cn.com.qc.javabean.PopwindowListS;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.CustomListView;
import cn.com.qc.utils.Tools;

/**
 * 专场
 */

public class CompanyInfoActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.companycount)
    TextView companycount;
    @BindView(R.id.jobcount)
    TextView jobcount;
    @BindView(R.id.peoplecount)
    TextView peoplecount;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.people)
    TextView people;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.location2)
    TextView location2;
    @BindView(R.id.jobname)
    EditText jobname;
    @BindView(R.id.jobname2)
    EditText jobname2;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.price2)
    TextView price2;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.money2)
    EditText money2;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.search2)
    Button search2;
    @BindView(R.id.searchview)
    LinearLayout searchview;
    @BindView(R.id.searchview2)
    LinearLayout searchview2;
    @BindView(R.id.swipe_target)
    CustomListView swipeTarget;
    private List<CompanyInfo> companyInfoList;
    private CompanyAdapter companyAdapter;
    private List<JobInfo> jobInfoList;
    private JobAdapter jobAdapter;
    private List<PeopleInfo> peopleInfoList;
    private PeopleAdapter peopleAdapter;
    private List<PopwindowList> popwindowLists;
    private PopwindowListAdapter popwindowListAdapter;
    private List<PopwindowListS> popwindowListS;
    private PopwindowListSAdapter popwindowListSAdapter;
    private static final String typeURL1 = "mobile/getEnterpriseByActivityId.do";
    private static final String typeURL2 = "mobile/getActivityPartTimeJob.do";
    private static final String typeURL3 = "mobile/getActivityStudent.do";
    private static final String typeURL4 = "mobile/getActivityJobNameList.do";
    private AssistTool assistTool;
    private String id = "1";
    private int minSalary = 0;
    private int maxSalary = 100000;
    private String jobId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyinfo);
        Tools.initSystemBarTint(this);
        ButterKnife.bind(this);
        assistTool = new AssistTool(this);
        id = getIntent().getStringExtra("id");
        System.out.println("这是id：" + id);
        String company = getIntent().getStringExtra("company");
        companycount.setText("企业：" + company);
        String job = getIntent().getStringExtra("job");
        jobcount.setText("职位：" + job);
        String people = getIntent().getStringExtra("people");
        peoplecount.setText("竞聘人数：" + people);
        searchview.setVisibility(View.GONE);
        searchview2.setVisibility(View.GONE);
        companyInfoList = new ArrayList<>();
        initData1();
        companyAdapter = new CompanyAdapter(companyInfoList, this);
        swipeTarget.setAdapter(companyAdapter);
    }

    @OnClick({R.id.back, R.id.company, R.id.job, R.id.people, R.id.location, R.id.jobname, R.id.price, R.id.search,
            R.id.location2, R.id.jobname2, R.id.price2, R.id.search2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.company:
                searchview.setVisibility(View.GONE);
                searchview2.setVisibility(View.GONE);
                companyInfoList = new ArrayList<>();
                initData1();
                companyAdapter = new CompanyAdapter(companyInfoList, this);
                swipeTarget.setAdapter(companyAdapter);
                break;
            case R.id.job:
                searchview.setVisibility(View.VISIBLE);
                searchview2.setVisibility(View.GONE);
                jobInfoList = new ArrayList<>();
                initData2();
                jobAdapter = new JobAdapter(jobInfoList, this);
                swipeTarget.setAdapter(jobAdapter);
                break;
            case R.id.people:
                searchview.setVisibility(View.GONE);
                searchview2.setVisibility(View.VISIBLE);
                peopleInfoList = new ArrayList<>();
                initData3();
                peopleAdapter = new PeopleAdapter(peopleInfoList, this);
                swipeTarget.setAdapter(peopleAdapter);
                break;
            case R.id.location:
                View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_location_list, null);
                ListView listView = (ListView) contentView.findViewById(R.id.listView);
                popwindowLists = new ArrayList<>();
                popwindowLists.add(new PopwindowList("朝阳"));
                popwindowLists.add(new PopwindowList("海淀"));
                popwindowLists.add(new PopwindowList("东城"));
                popwindowLists.add(new PopwindowList("西城"));
                popwindowLists.add(new PopwindowList("崇文"));
                popwindowLists.add(new PopwindowList("宣武"));
                popwindowLists.add(new PopwindowList("丰台"));
                popwindowLists.add(new PopwindowList("通州"));
                popwindowLists.add(new PopwindowList("石景山"));
                popwindowLists.add(new PopwindowList("房山"));
                popwindowLists.add(new PopwindowList("昌平"));
                popwindowLists.add(new PopwindowList("大兴"));
                popwindowLists.add(new PopwindowList("顺义"));
                popwindowLists.add(new PopwindowList("密云"));
                popwindowLists.add(new PopwindowList("怀柔"));
                popwindowLists.add(new PopwindowList("延庆"));
                popwindowLists.add(new PopwindowList("平谷"));
                popwindowLists.add(new PopwindowList("门头沟"));
                popwindowLists.add(new PopwindowList("燕郊"));
                popwindowListAdapter = new PopwindowListAdapter(popwindowLists, this);
                listView.setAdapter(popwindowListAdapter);
                PopupWindow popupWindow = new PopupWindow(contentView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        200);
                contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int popupWidth = contentView.getMeasuredWidth();
                int popupHeight = contentView.getMeasuredHeight();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        jobname.setText(popwindowLists.get(i).getItem().toString());
                    }
                });
                int[] location = new int[2];
                contentView.setFocusableInTouchMode(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight + 90);
                break;
            case R.id.jobname:
                break;
            case R.id.price:
                View contentView2 = LayoutInflater.from(this).inflate(R.layout.popwindow_location_list, null);
                ListView listView2 = (ListView) contentView2.findViewById(R.id.listView);
                popwindowLists = new ArrayList<>();
                popwindowLists.add(new PopwindowList("0-50"));
                popwindowLists.add(new PopwindowList("50-100"));
                popwindowLists.add(new PopwindowList("100-150"));
                popwindowLists.add(new PopwindowList("150-"));
                popwindowListAdapter = new PopwindowListAdapter(popwindowLists, this);
                listView2.setAdapter(popwindowListAdapter);
                PopupWindow popupWindow2 = new PopupWindow(contentView2,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        200);
                contentView2.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int popupWidth2 = contentView2.getMeasuredWidth();
                int popupHeight2 = contentView2.getMeasuredHeight();
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String s = popwindowLists.get(i).getItem().toString();
                        money.setText(s);
                        if (i == 0) {
                            minSalary = 0;
                            maxSalary = 50;
                        } else if (i == 1) {
                            minSalary = 50;
                            maxSalary = 100;
                        } else if (i == 2) {
                            minSalary = 100;
                            maxSalary = 150;
                        } else if (i == 3) {
                            minSalary = 150;
                            maxSalary = 10000;
                        }
                    }
                });
                int[] location2 = new int[2];
                contentView2.setFocusableInTouchMode(true);
                popupWindow2.setBackgroundDrawable(new BitmapDrawable());
                popupWindow2.setOutsideTouchable(true);
                view.getLocationOnScreen(location2);
                popupWindow2.showAtLocation(view, Gravity.NO_GRAVITY, (location2[0] + view.getWidth() / 2) - popupWidth2 / 2, location2[1] - popupHeight2 + 90);
                break;
            case R.id.search:
                jobAdapter.clear();
                jobInfoList = new ArrayList<>();
                initData2();
                jobAdapter = new JobAdapter(jobInfoList, this);
                swipeTarget.setAdapter(jobAdapter);
                break;
            case R.id.location2:
                View contentView3 = LayoutInflater.from(this).inflate(R.layout.popwindow_location_list, null);
                ListView listView3 = (ListView) contentView3.findViewById(R.id.listView);
                popwindowLists = new ArrayList<>();
                popwindowLists.add(new PopwindowList("朝阳"));
                popwindowLists.add(new PopwindowList("海淀"));
                popwindowLists.add(new PopwindowList("东城"));
                popwindowLists.add(new PopwindowList("西城"));
                popwindowLists.add(new PopwindowList("崇文"));
                popwindowLists.add(new PopwindowList("宣武"));
                popwindowLists.add(new PopwindowList("丰台"));
                popwindowLists.add(new PopwindowList("通州"));
                popwindowLists.add(new PopwindowList("石景山"));
                popwindowLists.add(new PopwindowList("房山"));
                popwindowLists.add(new PopwindowList("昌平"));
                popwindowLists.add(new PopwindowList("大兴"));
                popwindowLists.add(new PopwindowList("顺义"));
                popwindowLists.add(new PopwindowList("密云"));
                popwindowLists.add(new PopwindowList("怀柔"));
                popwindowLists.add(new PopwindowList("延庆"));
                popwindowLists.add(new PopwindowList("平谷"));
                popwindowLists.add(new PopwindowList("门头沟"));
                popwindowLists.add(new PopwindowList("燕郊"));
                popwindowListAdapter = new PopwindowListAdapter(popwindowLists, this);
                listView3.setAdapter(popwindowListAdapter);
                PopupWindow popupWindow3 = new PopupWindow(contentView3,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        200);
                contentView3.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int popupWidth3 = contentView3.getMeasuredWidth();
                int popupHeight3 = contentView3.getMeasuredHeight();
                listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        jobname2.setText(popwindowLists.get(i).getItem().toString());
                    }
                });
                int[] location3 = new int[2];
                contentView3.setFocusableInTouchMode(true);
                popupWindow3.setBackgroundDrawable(new BitmapDrawable());
                popupWindow3.setOutsideTouchable(true);
                view.getLocationOnScreen(location3);
                popupWindow3.showAtLocation(view, Gravity.NO_GRAVITY, (location3[0] + view.getWidth() / 2) - popupWidth3 / 2, location3[1] - popupHeight3 + 90);
                break;
            case R.id.jobname2:
                break;
            case R.id.price2:
                View contentView4 = LayoutInflater.from(this).inflate(R.layout.popwindow_location_list, null);
                ListView listView4 = (ListView) contentView4.findViewById(R.id.listView);
                popwindowListS = new ArrayList<>();
                initData4();
                popwindowListSAdapter = new PopwindowListSAdapter(popwindowListS, this);
                listView4.setAdapter(popwindowListSAdapter);
                PopupWindow popupWindow4 = new PopupWindow(contentView4,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        200);
                contentView4.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int popupWidth4 = contentView4.getMeasuredWidth();
                int popupHeight4 = contentView4.getMeasuredHeight();
                listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String s = popwindowListS.get(i).getItem().toString();
                        jobId = popwindowListS.get(i).getId().toString();
                        money2.setText(s);
                    }
                });
                int[] location4 = new int[2];
                contentView4.setFocusableInTouchMode(true);
                popupWindow4.setBackgroundDrawable(new BitmapDrawable());
                popupWindow4.setOutsideTouchable(true);
                view.getLocationOnScreen(location4);
                popupWindow4.showAtLocation(view, Gravity.NO_GRAVITY, (location4[0] + view.getWidth() / 2) - popupWidth4 / 2, location4[1] - popupHeight4 + 45);
                break;
            case R.id.search2:
                peopleAdapter.clear();
                peopleInfoList = new ArrayList<>();
                initData3();
                peopleAdapter = new PeopleAdapter(peopleInfoList, this);
                swipeTarget.setAdapter(peopleAdapter);
                break;
        }
    }

    private void initData1() {
        final String imgbasepath = assistTool.getPreferenceString("imgbasepath");
        OkGo.<String>post(URLConst.BASEURL(typeURL1))
                .tag(this)
                .headers("device", "1")
                .params("activityId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                companyInfoList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    CompanyInfo companyInfo = new CompanyInfo();
                                    companyInfo.setLocation(jsonObject1.getString("city"));
                                    companyInfo.setId(jsonObject1.getString("id"));
                                    companyInfo.setContent(jsonObject1.getString("industryName"));
                                    companyInfo.setImg(imgbasepath + jsonObject1.getString("logo"));
                                    companyInfo.setJob(jsonObject1.getString("name"));
                                    companyInfoList.add(companyInfo);
                                }
                                companyAdapter.addData(companyInfoList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData2() {
        OkGo.<String>post(URLConst.BASEURL(typeURL2))
                .tag(this)
                .headers("device", "1")
                .params("activityId", id)
                .params("minSalary", minSalary)
                .params("maxSalary", maxSalary)
                .params("area", jobname.getText().toString())
                .params("pageSize", 100000)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                String list = jsonObject1.getString("list");
                                JSONArray jsonArray = new JSONArray(list);
                                jobInfoList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    JobInfo jobInfo = new JobInfo();
                                    jobInfo.setLocation(jsonObject2.getString("address"));
                                    String beginDate = jsonObject2.getString("beginDate");
                                    jobInfo.setDate(Tools.getStrTime(beginDate));
                                    jobInfo.setCompany(jsonObject2.getString("enterpriseName"));
                                    jobInfo.setId(jsonObject2.getString("id"));
                                    jobInfo.setPrice(jsonObject2.getString("salary"));
                                    jobInfo.setJob(jsonObject2.getString("title"));
                                    jobInfoList.add(jobInfo);
                                }
                                jobAdapter.addData(jobInfoList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData3() {
        final String imgbasepath = assistTool.getPreferenceString("imgbasepath");
        OkGo.<String>post(URLConst.BASEURL(typeURL3))
                .tag(this)
                .headers("device", "1")
                .params("activityId", id)
                .params("jobId", jobId)
                .params("area", jobname2.getText().toString())
                .params("pageSize", 100000)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                String list = jsonObject1.getString("list");
                                JSONArray jsonArray = new JSONArray(list);
                                peopleInfoList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    PeopleInfo peopleInfo = new PeopleInfo();
                                    peopleInfo.setImg(imgbasepath + jsonObject2.getString("headImg"));
                                    peopleInfo.setWork(jsonObject2.getString("jobName"));
                                    peopleInfo.setJob(jsonObject2.getString("name"));
                                    peopleInfo.setPrice(jsonObject2.getString("salary"));
                                    peopleInfoList.add(peopleInfo);
                                }
                                peopleAdapter.addData(peopleInfoList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData4() {
        OkGo.<String>post(URLConst.BASEURL(typeURL4))
                .tag(this)
                .headers("device", "1")
                .params("activityId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                popwindowListS = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    PopwindowListS listS = new PopwindowListS();
                                    listS.setId(jsonObject1.getString("id"));
                                    listS.setItem(jsonObject1.getString("name"));
                                    popwindowListS.add(listS);
                                }
                                popwindowListSAdapter.addData(popwindowListS);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
