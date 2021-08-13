package com.just.agentweb.agentwebview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.R;
import com.just.agentweb.agentwebview.activity.BaseActWebFragment;

public class F2NewItem1 extends BaseActWebFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newitem1;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);

    }

    @Nullable
    @Override
    protected void getJsInterface() {
        if (mAgentWeb != null) {
            //注入对象
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, getActivity()));
        }
        super.getJsInterface();
    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) getActivity().findViewById(R.id.ll_base_container1);
    }

    @Override
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

    public class AndroidInterface {
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }

        @JavascriptInterface
        public void BackToAndroid(final String str) {
            ToastUtils.showShort("调用了android方法" + str);
        }
    }
}