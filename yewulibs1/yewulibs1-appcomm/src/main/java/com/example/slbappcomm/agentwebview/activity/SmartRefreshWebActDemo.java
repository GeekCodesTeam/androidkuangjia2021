package com.example.slbappcomm.agentwebview.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.slbappcomm.R;
import com.example.slbappcomm.base.BaseActWebActivity;
import com.geek.libutils.app.MyLogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
        smarkLayout.setEnableAutoLoadmore(false);
        //禁止越界拖动（1.0.4以上版本）
        smarkLayout.setEnableOverScrollDrag(false);
        //关闭越界回弹功能
        smarkLayout.setEnableOverScrollBounce(false);
        smarkLayout.setScrollBoundaryDecider(new ScrollBoundaryDecider() {
            @Override
            public boolean canRefresh(View content) {
                //webview滚动到顶部才可以下拉刷新
                MyLogUtil.e("ssssss",""+mAgentWeb.getWebCreator().getWebView().getScrollY());
                return mAgentWeb.getWebCreator().getWebView().getScrollY() > 0;
            }

            @Override
            public boolean canLoadmore(View content) {
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
        return "https://www.baidu.com";
    }
}
