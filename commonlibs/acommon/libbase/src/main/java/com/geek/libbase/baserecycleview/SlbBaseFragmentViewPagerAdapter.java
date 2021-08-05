package com.geek.libbase.baserecycleview;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class SlbBaseFragmentViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private FragmentManager fm;

    public SlbBaseFragmentViewPagerAdapter(FragmentManager fm, Context context, List<String> titles, List<Fragment> fragmentList, int behavior) {
        super(fm, behavior);
        this.fm = fm;
        this.mContext = context;
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