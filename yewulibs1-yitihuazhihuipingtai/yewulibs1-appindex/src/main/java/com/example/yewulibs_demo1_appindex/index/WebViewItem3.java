package com.example.yewulibs_demo1_appindex.index;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.example.libbase.agentwebview.activity.BaseActWebFragment;
import com.example.yewulibs_demo1_appindex.R;
import com.geek.libutils.app.MyLogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class WebViewItem3 extends BaseActWebFragment {

    private SmartRefreshLayout smarkLayout;
    private boolean is_first;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_webview3;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        smarkLayout = rootView.findViewById(R.id.smarkLayout);
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
                }, 0);
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
                MyLogUtil.e("ssssss", "" + mAgentWeb.getWebCreator().getWebView().getScrollY());
                return false;
//                return mAgentWeb.getWebCreator().getWebView().getScrollY() == 0;
            }

            @Override
            public boolean canLoadmore(View content) {
                return false;
            }
        });
        smarkLayout.autoRefresh();
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
        return (ViewGroup) getActivity().findViewById(R.id.ll_base_container3);
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

    /**
     * 底部点击bufen
     *
     * @param cateId
     * @param isrefresh
     */
    public void getCate(String cateId, boolean isrefresh) {

        if (!isrefresh) {
            // 从缓存中拿出头像bufen
            mAgentWeb.getUrlLoader().reload();
            return;
        }
        smarkLayout.autoRefresh();
    }

}