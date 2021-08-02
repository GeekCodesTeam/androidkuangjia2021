package com.just.agentweb.agentwebview.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.just.agentweb.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class SmartRefreshWebActDemo extends BaseActWebActivity {
    private SmartRefreshLayout smarkLayout;
    private boolean is_first;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_webview;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        smarkLayout = findViewById(R.id.smarkLayout);
        smarkLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smarkLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smarkLayout.finishRefresh();
                        if (!is_first) {
                            is_first = true;
                            return;
                        }
                        mAgentWeb.getUrlLoader().reload();
                    }
                }, 2000);
            }
        });
        //使上拉加载具有弹性效果
        smarkLayout.setEnableAutoLoadMore(false);
        //禁止越界拖动（1.0.4以上版本）
        smarkLayout.setEnableOverScrollDrag(false);
        //关闭越界回弹功能
        smarkLayout.setEnableOverScrollBounce(false);
        smarkLayout.setScrollBoundaryDecider(new ScrollBoundaryDecider() {
            @Override
            public boolean canRefresh(View content) {
                //webview滚动到顶部才可以下拉刷新
                Log.e("ssssss", "" + mAgentWeb.getWebCreator().getWebView().getScrollY());
                return mAgentWeb.getWebCreator().getWebView().getScrollY() > 0;
            }

            @Override
            public boolean canLoadMore(View content) {
                return false;
            }
        });
        smarkLayout.autoRefresh();

    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.ll_base_container);
    }

    @Override
    public String getUrl() {
//        Bundle bundle = this.getArguments();
//        String target = bundle.getString("url_key");
        String target = "";
        //1
        // 根据HIOS协议三方平台跳转
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        if (appLinkIntent != null) {
            String appLinkAction = appLinkIntent.getAction();
//            if (appLinkAction != null) {
            Uri appLinkData = appLinkIntent.getData();
            if (appLinkData != null) {
                target = appLinkData.getQueryParameter("query1");
            }
//            }
        }
        Log.e("targetaaaaaaa=", target);
        if (TextUtils.isEmpty(target)) {
            target = "http://www.jd.com/";
        }
        return target;
    }
}
