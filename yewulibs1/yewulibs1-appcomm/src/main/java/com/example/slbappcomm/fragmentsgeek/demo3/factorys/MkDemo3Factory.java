package com.example.slbappcomm.fragmentsgeek.demo3.factorys;


import androidx.collection.SparseArrayCompat;

import com.example.slbappcomm.R;
import com.example.libbase.base.SlbBaseFragment;
import com.example.slbappcomm.fragmentsgeek.demo3.configs.MkDemo3Config;
import com.example.slbappcomm.fragmentsgeek.demo3.fragments.MkDemo3Fragment10;
import com.example.slbappcomm.fragmentsgeek.demo3.fragments.MkDemo3Fragment11;
import com.example.slbappcomm.fragmentsgeek.demo3.fragments.MkDemo3Fragment20;
import com.example.slbappcomm.fragmentsgeek.demo3.fragments.MkDemo3Fragment21;

/**
 * 首页模块fragment的工厂, 首页模块有需要更换的，可以在此修改，格式为id->Fragment.class<br />
 * Created by shining on 2016/8/1.
 */

public class MkDemo3Factory {

    public static void setup() {
        MkDemo3Config.PAGE_COUNT = 2;
        MkDemo3Config.PAGE_LAYOUT_ID = "activity_mk_demo3_layout_pager_item_";
        MkDemo3Config.DEFAULT_PAGE_INDEX = 0;
        registerFragments(MkDemo3Config.getFragments());
    }

    private static void registerFragments(SparseArrayCompat<Class<? extends SlbBaseFragment>> sIndexFragments) {

        sIndexFragments.put(R.id.fragment_demo3_pager_index_0_0, MkDemo3Fragment10.class);//第一屏 layout1
        sIndexFragments.put(R.id.fragment_demo3_pager_index_0_1, MkDemo3Fragment11.class);//第一屏 layout2

        sIndexFragments.put(R.id.fragment_demo3_pager_index_1_0, MkDemo3Fragment20.class);//第二屏 layout1
        sIndexFragments.put(R.id.fragment_demo3_pager_index_1_1, MkDemo3Fragment21.class);//第二屏 layout2

    }
}
