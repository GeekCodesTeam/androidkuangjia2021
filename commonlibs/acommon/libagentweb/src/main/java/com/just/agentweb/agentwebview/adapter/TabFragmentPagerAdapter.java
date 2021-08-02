package com.just.agentweb.agentwebview.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private FragmentManager fm;

    public TabFragmentPagerAdapter(FragmentManager fm, Context mContext, List<Fragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.mContext = mContext;
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    public TabFragmentPagerAdapter(FragmentManager fm, Context mContext, List<String> titles, List<Fragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.mContext = mContext;
        this.fragmentList = fragmentList;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}