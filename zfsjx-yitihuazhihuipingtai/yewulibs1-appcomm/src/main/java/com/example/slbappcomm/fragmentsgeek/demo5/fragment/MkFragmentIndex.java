package com.example.slbappcomm.fragmentsgeek.demo5.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.slbappcomm.R;


public class MkFragmentIndex extends Fragment {

    private String tablayoutId;
    private Context mContext;
    private View vRoot;
    private TextView tv1;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        mContext = getActivity();
//        Bundle arg = getArguments();
        if (getArguments() != null) {
            tablayoutId = getArguments().getString("tablayoutId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (vRoot == null) {
            vRoot = inflater.inflate(R.layout.activity_mk_demo5_fragment_index, container, false);
        }
        tv1 = (TextView) vRoot.findViewById(R.id.tv1);

//        initData();
        return vRoot;
    }

    /**
     * 第一次进来加载bufen
     */
    public void initData() {
//        which_page = 1;
        //categoryId
//        doNetWorkContent(which_page);
    }

}
