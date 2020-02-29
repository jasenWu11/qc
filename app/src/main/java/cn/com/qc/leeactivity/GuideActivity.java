package cn.com.qc.leeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.ImagePagerAdapter;
import cn.com.qc.main.MainActivity;
import cn.com.qc.utils.Tools;

/**
 * 引导界面
 */

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.enter)
    Button enter;
    private List<ImageView> imageViews;
    private int[] images = new int[]{R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        Tools.initSystemBarTint(this);
        imageViews = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(images[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        viewPager.setAdapter(new ImagePagerAdapter(imageViews));
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == imageViews.size() - 1) {
            enter.setVisibility(View.VISIBLE);
        } else {
            enter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.enter:
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
}
