package com.just.agentweb.agentwebview.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.just.agentweb.base.BaseAgentWebFragment;


public abstract class BaseActWebFragment extends BaseAgentWebFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        setup(rootView, savedInstanceState);
        //网络监听
        return rootView;
    }

    protected abstract int getLayoutId();


    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
