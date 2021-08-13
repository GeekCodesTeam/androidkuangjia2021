package com.example.slbappcomm.fragmentsgeek.demo5.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.example.slbappcomm.R;


public class MkFragmentContent extends Fragment {

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
            vRoot = LayoutInflater.from(mContext).inflate(R.layout.activity_mk_demo5_fragment_content, container, false);
        }
        tv1 = (TextView) vRoot.findViewById(R.id.tv1);

        initData();
        return vRoot;
    }

    /**
     * 第一次进来加载bufen
     */
    private void initData() {
//        doNetWork_toubu();
//        which_page = 1;
//        //categoryId
//        doNetWorkContent2(which_page);
        ToastUtils.showLong("内容" + tablayoutId);
    }


    /**
     * 底部点击bufen
     *
     * @param cateId
     */
    public void getCate(String cateId) {
//        doNetWork_toubu();
//        GAdaptor = new RecycleViewAdapter1(mContext);
//        recyclerView.setAdapter(GAdaptor);
//        which_page = 1;
//        doNetWorkContent2(which_page);
//        isTou_onclick = false;
        ToastUtils.showLong("内容" + cateId);
    }

}
