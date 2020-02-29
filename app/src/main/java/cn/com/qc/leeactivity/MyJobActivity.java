package cn.com.qc.leeactivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.MyJobViewPagerAdapter;
import cn.com.qc.fragment.MyJobFragment;
import cn.com.qc.fragment.MyProposerFragment;
import cn.com.qc.fragment.MySettlementFragment;
import cn.com.qc.fragment.MyUnliquidatedFragment;
import cn.com.qc.main.BaseActivity;
import cn.com.qc.utils.Tools;

/**
 * 我的兼职
 */

public class MyJobActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private MyJobViewPagerAdapter myJobViewPagerAdapter;
    private String[] titles = new String[]{"已申请", "待上岗", "待结算", "已结算"};
    private List<Fragment> fragments = new ArrayList<>();
    private int pos = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myjob);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        pos = getIntent().getIntExtra("pos", 0);
        for (String tab : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        tabLayout.setOnTabSelectedListener(this);
        fragments.add(new MyProposerFragment());
        fragments.add(new MyJobFragment());
        fragments.add(new MyUnliquidatedFragment());
        fragments.add(new MySettlementFragment());
        myJobViewPagerAdapter = new MyJobViewPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(myJobViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(pos).select();
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
