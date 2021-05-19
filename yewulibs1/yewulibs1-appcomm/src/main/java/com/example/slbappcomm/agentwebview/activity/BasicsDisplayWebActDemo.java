package com.example.slbappcomm.agentwebview.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.slbappcomm.R;
import com.example.slbappcomm.base.BaseActWebActivity;

public class BasicsDisplayWebActDemo extends BaseActWebActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basics_display_webview;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.ll_base_container);
    }

    @Nullable
    @Override
    public String getUrl() {
        return "https://www.baidu.com";
    }
}
