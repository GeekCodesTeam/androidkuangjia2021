package com.example.yewulibs_demo1_appindex.index;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.liblocations.LocListener;
import com.example.liblocations.LocUtil;
import com.example.liblocations.LocationBean;
import com.example.yewulibs_demo1_appindex.R;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.LocationUtils;
import com.geek.libutils.data.MmkvUtils;
import com.just.agentweb.agentwebview.activity.BaseActWebFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class WebViewItem1 extends BaseActWebFragment {

    private SmartRefreshLayout refreshLayout1;
    private boolean is_first;
    private boolean isSuccess;
    private MessageReceiverIndex mMessageReceiver;


    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("WebViewItem1".equals(intent.getAction())) {
                    // 给js传数据
                    mAgentWeb.getJsAccessEntrace().quickCallJs("ToJsData1", intent.getStringExtra("id1"));
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void onDestroyView() {
        LocalBroadcastManagers.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroyView();
    }

//    @Override
//    public void call(Object value) {
//        String tablayoutId = (String) value;
//        // 给js传数据
//        mAgentWeb.getJsAccessEntrace().quickCallJs("ToJsData1", tablayoutId);
//
//        MyLogUtil.e("tablayoutId->", "call->" + tablayoutId);
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_webview1;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAgentWeb != null) {
            mAgentWeb.getJsAccessEntrace().quickCallJs("refreshLoc()");
        }
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        refreshLayout1 = rootView.findViewById(R.id.smarkLayout);
        refreshLayout1.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout1.finishRefresh();
                        if (!is_first) {
                            is_first = true;
                            return;
                        }
                        set_location2();
                        mAgentWeb.getUrlLoader().reload();
                        mAgentWeb.getJsAccessEntrace().quickCallJs("refreshLoc()");
                        // 给js传数据
//                        mAgentWeb.getJsAccessEntrace().quickCallJs("ToJsData", "Hello ! js~我给你数据了");
//                        mAgentWeb.getJsAccessEntrace().quickCallJs("get_token", MmkvUtils.getInstance().get_common("用户token"));

                    }
                }, 0);
            }
        });
        //使上拉加载具有弹性效果
        refreshLayout1.setEnableAutoLoadmore(false);
        //禁止越界拖动（1.0.4以上版本）
        refreshLayout1.setEnableOverScrollDrag(false);
        //关闭越界回弹功能
        refreshLayout1.setEnableOverScrollBounce(false);
        refreshLayout1.setScrollBoundaryDecider(new ScrollBoundaryDecider() {
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
        refreshLayout1.autoRefresh();
//        if (mAgentWeb != null) {
//            //注入对象
//            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, getActivity()));
//        }
//        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
//        new XPopup.Builder(getActivity()).asConfirm("我是标题", "我是内容",
//                new OnConfirmListener() {
//                    @Override
//                    public void onConfirm() {
//                        ToastUtils.showLong("click confirm");
//                    }
//                })
//                .show();
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("WebViewItem1");
        LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);

    }

    private void set_location2() {
        LocUtil.getLocation(getActivity(), new LocListener() {

            @Override
            public void success(LocationBean model) {
                String lastLatitude = String.valueOf(model.getLatitude());
                String lastLongitude = String.valueOf(model.getLongitude());
                String aaaa = lastLatitude + "," + lastLongitude;
                MmkvUtils.getInstance().set_common("经纬度", JSON.toJSONString(model));
                MyLogUtil.e("LocUtil2", aaaa + "详细信息：" + JSON.toJSONString(model));
                String isToH5Success = MmkvUtils.getInstance().get_common("H5是否取得经纬度");
                if (TextUtils.equals("false", isToH5Success)) {
//                    MyLogUtil.e("LocUtil2", "你刚才没拿到，我现在有信息了，再来取一次吧");
                    mAgentWeb.getJsAccessEntrace().quickCallJs("changeLoc()");
                } else if (TextUtils.equals("true", isToH5Success)) {
//                    MyLogUtil.e("LocUtil2", "你刚才成功拿到经纬度了，不需要再来拿了");
                }
            }

            @Override
            public void fail(int msg) {
                String aaa = msg + "";
            }
        });
    }


    private void set_location() {
        // 定位bufen
        if (Build.VERSION.SDK_INT >= 6.0) {//Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                /**动态添加权限：ACCESS_FINE_LOCATION*/
//                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSION_REQUEST_CONSTANT);
            }
        }
        isSuccess = LocationUtils.register(0L, 0L, new LocationUtils.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                String lastLatitude = String.valueOf(location.getLatitude());
                String lastLongitude = String.valueOf(location.getLongitude());
                String aaaa = lastLatitude + "," + lastLongitude;
                if (isSuccess) {
                    MmkvUtils.getInstance().set_common("经纬度", aaaa);
                    mAgentWeb.getUrlLoader().reload();
                }
                MyLogUtil.e("sssssssss1", aaaa);
            }

            @Override
            public void onLocationChanged(Location location) {
                String lastLatitude = String.valueOf(location.getLatitude());
                String lastLongitude = String.valueOf(location.getLongitude());
                String aaaa = lastLatitude + "," + lastLongitude;
                if (isSuccess) {
                    MmkvUtils.getInstance().set_common("经纬度", aaaa);
                    mAgentWeb.getUrlLoader().reload();
                }
                MyLogUtil.e("sssssssss2", aaaa);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
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

    @Nullable
    protected String getUrl() {
        Bundle bundle = this.getArguments();
        String target = bundle.getString("url_key");
        LogUtils.e("targetaaaaaaa=" + target);
        if (TextUtils.isEmpty(target)) {
            target = "http://www.jd.com/";
        }
        set_location2();
//        target = "file:///android_asset/html/hello11.html";
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
            mAgentWeb.getJsAccessEntrace().quickCallJs("refreshLoc()");
            return;
        }
        refreshLayout1.autoRefresh();
    }

}