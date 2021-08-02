package com.example.slbappcomm.fragmentsgeek.demo1.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappcomm.R;
import com.example.slbappcomm.fragmentsgeek.demo1.MkDemo1Activity;


public class MkDemo1Fragment1 extends SlbBaseLazyFragmentNew {


    @Override
    public void call(Object value) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mk_demo1_fragment1;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        rootView.findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToFragment("demo1的fragment1页面");
            }
        });
    }

    /**
     * 页面传值操作部分
     *
     * @param id1
     */
    private void SendToFragment(String id1) {
        //举例
//        IndexFoodFragmentUpdateIds iff = new IndexFoodFragmentUpdateIds();
//        iff.setFood_definition_id(id1);
//        iff.setFood_name(id2);
        if (getActivity() != null && getActivity() instanceof MkDemo1Activity) {
            ((MkDemo1Activity) getActivity()).callFragment(id1, MkDemo1Fragment2.class.getName());
        }
    }
}
