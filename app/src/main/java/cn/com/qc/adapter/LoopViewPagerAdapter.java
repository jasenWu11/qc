package cn.com.qc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.qc.R;
import cn.com.qc.bean.HomePic;
import cn.com.qc.javabean.CompanyRecruit;
import cn.com.qc.leeactivity.CompanyRecruitActivity;
import cn.com.qc.ypresenter.HomePresenter;

/**
 * Created by lenovo on 2017/4/13.
 */

public class LoopViewPagerAdapter extends BaseLoopPagerAdapter {

    private final List<HomePic> mHeroes;

    private int mLastPosition;

    public LoopViewPagerAdapter(ViewPager viewPager) {
        super(viewPager);
        mHeroes = new ArrayList<>();
    }

    public void setList(List<HomePic> heroes) {
        mHeroes.clear();
        mHeroes.addAll(heroes);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getPagerCount() {
        return mHeroes.size();
    }

    @Override
    public HomePic getItem(int position) {
        return mHeroes.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_viewpager_item, parent, false);
            holder = new ViewHolder();
            holder.ivBanner = (ImageView) convertView.findViewById(R.id.ivBanner);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HomePic character = mHeroes.get(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = parent.getContext();
                Intent intent = new Intent(context, CompanyRecruitActivity.class);
                intent.putExtra("id",character.getId());
                context.startActivity(intent);
            }
        });

        new HomePresenter().intoImageView(parent.getContext(),character.getImgUrl(),holder.ivBanner,R.mipmap.daohang);

    //    Glide.with(parent.getContext()).load(character.getImgUrl()).into(holder.ivBanner);
        return convertView;
    }

    @Override
    public void onPageItemSelected(int position) {
        mLastPosition = position;
    }

    public static class ViewHolder {
        ImageView ivBanner;
    }
}
