package cn.com.qc.leeactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.CompanyInfosAdapter;
import cn.com.qc.javabean.CompanyInfos;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.CustomListView;
import cn.com.qc.utils.Tools;

/**
 * 公司信息
 */

public class CompanyInfosActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.companysize)
    TextView companysize;
    @BindView(R.id.companypeople)
    TextView companypeople;
    @BindView(R.id.companystyle)
    TextView companystyle;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.listView)
    CustomListView listView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private List<CompanyInfos> companyInfosList;
    private CompanyInfosAdapter companyInfosAdapter;
    private static final String typeURL = "mobile/getEnterpriseById.do";
    private String id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyinfos);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        id = getIntent().getStringExtra("id");
        scrollView.scrollTo(0, 0);
        companyInfosList = new ArrayList<>();
        initData();
        companyInfosAdapter = new CompanyInfosAdapter(companyInfosList, this);
        listView.setAdapter(companyInfosAdapter);
    }

    private void initData() {
        OkGo.<String>post(URLConst.BASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .params("enterpriseId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            System.out.println(response.body());
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("infoCode");
                            if (infoCode == 1) {
                                String data = jsonObject.getString("data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                location.setText(" 地址：" + jsonObject1.getString("address"));
                                content.setText(jsonObject1.getString("describe1"));
                                companystyle.setText(" " + jsonObject1.getString("industryName"));
                                int isMarket = jsonObject1.getInt("isMarket");
                                if (isMarket == 0) {
                                    companysize.setText(" 未上市公司");
                                } else if (isMarket == 1) {
                                    companysize.setText(" 上市公司");
                                }
                                companypeople.setText(" " + jsonObject1.getInt("min") + "-" + jsonObject1.getInt("max") + "人");
                                company.setText(jsonObject1.getString("name"));
                                JSONArray enterpriseJobList = jsonObject1.getJSONArray("enterpriseJobList");
                                companyInfosList = new ArrayList<>();
                                for (int i = 0; i < enterpriseJobList.length(); i++) {
                                    JSONObject jsonObject2 = enterpriseJobList.getJSONObject(i);
                                    CompanyInfos companyInfos = new CompanyInfos();
                                    companyInfos.setLocation(jsonObject2.getString("address"));
                                    String beginDate = jsonObject2.getString("beginDate");
                                    String endDate = jsonObject2.getString("endDate");
                                    companyInfos.setDate(Tools.getStrTime(beginDate));
                                    companyInfos.setId(jsonObject2.getString("id"));
                                    companyInfos.setPeople(jsonObject2.getString("peopleNumber"));
                                    companyInfos.setPrice(jsonObject2.getInt("salary"));
                                    companyInfos.setJob(jsonObject2.getString("title"));
                                    companyInfos.setPaysize(jsonObject2.getInt("dayReport"));
                                    companyInfosList.add(companyInfos);
                                }
                                companyInfosAdapter.addData(companyInfosList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
