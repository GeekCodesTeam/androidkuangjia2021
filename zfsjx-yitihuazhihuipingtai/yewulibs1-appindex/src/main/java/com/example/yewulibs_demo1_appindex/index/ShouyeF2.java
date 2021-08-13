package com.example.yewulibs_demo1_appindex.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geek.libbase.base.SlbBaseLazyFragmentNew;
import com.example.yewulibs_demo1_appindex.R;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;

public class ShouyeF2 extends SlbBaseLazyFragmentNew implements View.OnClickListener {

    private String tablayoutId;
    private TextView tv_center_content;
    private TextView shouyef2_tv0, shouyef2_tv2, shouyef2_tv3, shouyef2_tv4,
            shouyef2_tv5, shouyef2_tv6, shouyef2_tv7, shouyef2_tv8, shouyef2_tv9, shouyef2_tv10,
            shouyef2_tv11, shouyef2_tv12, shouyef2_tv13, shouyef2_tv14, shouyef2_tv15, shouyef2_tv16, shouyef2_tv17;
    private MessageReceiverIndex mMessageReceiver;


    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("ShouyeF2".equals(intent.getAction())) {
                    //TODO 发送广播bufen
                    Intent msgIntent = new Intent();
                    msgIntent.setAction("ShouyeF2");
                    msgIntent.putExtra("query1", 0);
                    LocalBroadcastManagers.getInstance(getActivity()).sendBroadcast(msgIntent);
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void call(Object value) {
        tablayoutId = (String) value;
        ToastUtils.showLong("call->" + tablayoutId);
        MyLogUtil.e("tablayoutId->", "call->" + tablayoutId);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManagers.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        // 从缓存中拿出头像bufen
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        MyLogUtil.e("TAG", "ProjectFragment isVisibleToUser = " + isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
//        Bundle arg = getArguments();
        if (getArguments() != null) {
            tablayoutId = getArguments().getString("tablayoutId");
            MyLogUtil.e("tablayoutId->", "onCreate->" + tablayoutId);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shouyef2;
    }

    @Override
    protected void setup(final View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        tv_center_content = rootView.findViewById(R.id.shouyef2_tv1);
        shouyef2_tv0 = rootView.findViewById(R.id.shouyef2_tv0);
        shouyef2_tv2 = rootView.findViewById(R.id.shouyef2_tv2);
        shouyef2_tv3 = rootView.findViewById(R.id.shouyef2_tv3);
        shouyef2_tv4 = rootView.findViewById(R.id.shouyef2_tv4);
        shouyef2_tv5 = rootView.findViewById(R.id.shouyef2_tv5);
        shouyef2_tv6 = rootView.findViewById(R.id.shouyef2_tv6);
        shouyef2_tv7 = rootView.findViewById(R.id.shouyef2_tv7);
        shouyef2_tv8 = rootView.findViewById(R.id.shouyef2_tv8);
        shouyef2_tv9 = rootView.findViewById(R.id.shouyef2_tv9);
        shouyef2_tv10 = rootView.findViewById(R.id.shouyef2_tv10);
        shouyef2_tv11 = rootView.findViewById(R.id.shouyef2_tv11);
        shouyef2_tv12 = rootView.findViewById(R.id.shouyef2_tv12);
        shouyef2_tv13 = rootView.findViewById(R.id.shouyef2_tv13);
        shouyef2_tv14 = rootView.findViewById(R.id.shouyef2_tv14);
        shouyef2_tv15 = rootView.findViewById(R.id.shouyef2_tv15);
        shouyef2_tv16 = rootView.findViewById(R.id.shouyef2_tv16);
        shouyef2_tv17 = rootView.findViewById(R.id.shouyef2_tv17);
        shouyef2_tv2.setOnClickListener(this);
        shouyef2_tv3.setOnClickListener(this);
        shouyef2_tv4.setOnClickListener(this);
        shouyef2_tv5.setOnClickListener(this);
        shouyef2_tv6.setOnClickListener(this);
        shouyef2_tv0.setOnClickListener(this);
        shouyef2_tv7.setOnClickListener(this);
        shouyef2_tv8.setOnClickListener(this);
        shouyef2_tv9.setOnClickListener(this);
        shouyef2_tv10.setOnClickListener(this);
        shouyef2_tv11.setOnClickListener(this);
        shouyef2_tv12.setOnClickListener(this);
        shouyef2_tv13.setOnClickListener(this);
        shouyef2_tv14.setOnClickListener(this);
        shouyef2_tv15.setOnClickListener(this);
        shouyef2_tv16.setOnClickListener(this);
        shouyef2_tv17.setOnClickListener(this);
        tv_center_content.setText("分类");
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ShouyeF2");
        LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
        donetwork();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shouyef2_tv0) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.JingChengActivity"));
        } else if (id == R.id.shouyef2_tv2) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.BasicsDisplayWebActDemo"));
        } else if (id == R.id.shouyef2_tv3) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SmartRefreshWebActDemo"));
        } else if (id == R.id.shouyef2_tv4) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.webview.DemoWebviewMainActivity"));
        } else if (id == R.id.shouyef2_tv5) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.JsWebActDemo"));
        } else if (id == R.id.shouyef2_tv6) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.TablayouActDemo"));
        } else if (id == R.id.shouyef2_tv7) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".com.haier.system"));
        } else if (id == R.id.shouyef2_tv8) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.WIFIMainActivity"));
        } else if (id == R.id.shouyef2_tv9) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.MainActivity_BlueTooth"));
        } else if (id == R.id.shouyef2_tv10) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.JavaDemoActivity"));
        } else if (id == R.id.shouyef2_tv11) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.PayDemoActivity"));
        } else if (id == R.id.shouyef2_tv12) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.JavaDemoActivity"));
        } else if (id == R.id.shouyef2_tv13) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShareIndexActivity"));
        } else if (id == R.id.shouyef2_tv14) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.MobshareActivity"));
        } else if (id == R.id.shouyef2_tv15) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ClickEffectDemo"));
        } else if (id == R.id.shouyef2_tv16) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.GlideMainActivity"));
        } else if (id == R.id.shouyef2_tv17) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.CityPickerActivity"));
        }
    }

    /**
     * 第一次进来加载bufen
     */
    private void donetwork() {
        retryData();
    }

    // 刷新
    private void retryData() {
//        mEmptyView.loading();
//        presenter1.getLBBannerData("0");
//        refreshLayout1.finishRefresh();
//        emptyview1.success();
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

            return;
        }
        ToastUtils.showLong("下拉刷新啦");
    }

    /**
     * 当切换底部的时候通知每个fragment切换的id是哪个bufen
     *
     * @param cateId
     */
    public void give_id(String cateId) {
//        ToastUtils.showLong("下拉刷新啦");
        MyLogUtil.e("tablayoutId->", "give_id->" + cateId);
    }

    /**
     * --------------------------------业务逻辑分割线----------------------------------
     */


}
