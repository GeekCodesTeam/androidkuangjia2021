package com.example.slbappcomm.fragmentsgeek.demo4.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.slbappcomm.R;
import com.example.libbase.base.SlbBaseLazyFragmentNew;


/**
 * Created by shining on 2017/8/14.
 */

public class MkDemo4Fragment20 extends SlbBaseLazyFragmentNew {

    private TextView tv1;

    @Override
    public void call(Object value) {
        String ids = (String) value;
        tv1.setText(ids);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mk_demo4_fragment20;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        tv1= (TextView) rootView.findViewById(R.id.tv20);
    }
}
