package com.example.slbappcomm.fragmentsgeek.demo2.fragments;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.example.slbappcomm.R;
import com.example.libbase.base.SlbBaseLazyFragmentNew;

/**
 * Created by shining on 2017/8/14.
 */

public class MkDemo2Fragment2 extends SlbBaseLazyFragmentNew {

    @Override
    public void call(Object value) {
        String ids = (String) value;
        ToastUtils.showLong(ids);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mk_demo2_fragment2;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);

    }
}
