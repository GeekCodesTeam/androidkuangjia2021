package com.just.agentweb.agentwebview.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.just.agentweb.R;
import com.just.agentweb.agentwebview.activity.BaseActWebFragment;

public class F2NewItem2 extends BaseActWebFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newitem2;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) getActivity().findViewById(R.id.ll_base_container2);
    }

    @Nullable
    protected String getUrl() {
        Bundle bundle = this.getArguments();
        String target = bundle.getString("url_key");
        LogUtils.e("targetaaaaaaa=" + target);
        if (TextUtils.isEmpty(target)) {
            target = "http://www.jd.com/";
        }
        return target;
    }
}