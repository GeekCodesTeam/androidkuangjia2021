package com.example.slbappcomm.fragmentsgeek.demo4.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.example.slbappcomm.R;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappcomm.fragmentsgeek.demo5.MkDemo5Activity;

/**
 * Created by shining on 2017/8/14.
 */

public class MkDemo4Fragment21 extends SlbBaseLazyFragmentNew {

    private TextView tv1;

    @Override
    public void call(Object value) {
        String ids = (String) value;
        tv1.setText(ids);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mk_demo4_fragment21;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        tv1= (TextView) rootView.findViewById(R.id.tv21);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MkDemo5Activity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        ToastUtil.showToastCenter("刷新操作~");
    }
}
