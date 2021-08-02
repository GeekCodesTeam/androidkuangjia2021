/*
 * *********************************************************
 *   author   yangqilin
 *   company  fosung
 *   email    yql19911010@gmail.com
 *   date     18-12-25 下午4:46
 * ********************************************************
 */

/*
 * **********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     16-10-8 下午3:55
 * *********************************************************
 */

/*    
 * 
 * @author		: WangLin  
 * @Company: 	：FCBN
 * @date		: 2015年5月13日 
 * @version 	: V1.0
 */
package com.fosung.lighthouse.jiaoyuziyuan.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.fosung.lighthouse.jiaoyuziyuan.activity.EduResourceDetailAct;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailInfoFragment;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailRecordFragment;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailReviewFragment;
import com.zcolin.frame.app.BaseActivity;

import java.util.ArrayList;


/**
 * 首页上部tab ViewPager适配器
 */
public class EduResourceDetailPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> list;

    private BaseActivity eduAct;

    public EduResourceDetailPagerAdapter(BaseActivity eduAct, ArrayList<String> list, FragmentManager fm) {
        super(fm);
        this.list = list;
        this.eduAct = eduAct;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position < 3) {
            EduDetailInfoFragment fragment = (EduDetailInfoFragment) super.instantiateItem(container, position);
            return fragment;
        } else if (position == 4){
            EduDetailReviewFragment fragment = (EduDetailReviewFragment) super.instantiateItem(container, position);
            return fragment;
        }else {
            EduDetailRecordFragment fragment = (EduDetailRecordFragment) super.instantiateItem(container, position);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return  ((EduResourceDetailAct) eduAct).getFragByPosition(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
