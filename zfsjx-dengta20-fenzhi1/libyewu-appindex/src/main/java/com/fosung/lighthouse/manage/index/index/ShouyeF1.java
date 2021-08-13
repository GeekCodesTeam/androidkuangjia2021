package com.fosung.lighthouse.manage.index.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fosung.lighthouse.manage.index.R;
import com.geek.libbase.base.SlbBaseLazyFragmentNew;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.haier.cellarette.libwebview.base.UrlEncodeUtils;
import com.haier.cellarette.libwebview.hois2.HiosHelper;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

public class ShouyeF1 extends SlbBaseLazyFragmentNew implements View.OnClickListener {

    private String tablayoutId;
    private TextView textView;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv10;
    private TextView tv11;
    private TextView tv12;
    private TextView tv13;
    private TextView tv14;
    private TextView tv15;
    private TextView tv16;
    private TextView tv17;
    private MessageReceiverIndex mMessageReceiver;


    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("ShouyeF1".equals(intent.getAction())) {
                    //TODO 发送广播bufen
                    Intent msgIntent = new Intent();
                    msgIntent.setAction("ShouyeF1");
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
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
//        Bundle arg = getArguments();
        if (getArguments() != null) {
            tablayoutId = getArguments().getString("tablayoutId");
            MyLogUtil.e("tablayoutId->", "onCreate->" + tablayoutId);
        }
    }

    private static final String HARMONY_OS = "harmony";

    /**
     * check the system is harmony os
     *
     * @return true if it is harmony os
     */
    public static boolean isHarmonyOS() {
        try {
            Class clz = Class.forName("com.huawei.system.BuildEx");
            Method method = clz.getMethod("getOsBrand");
            return HARMONY_OS.equals(method.invoke(clz));
        } catch (ClassNotFoundException e) {
            Log.e("TAG", "occured ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("TAG", "occured NoSuchMethodException");
        } catch (Exception e) {
            Log.e("TAG", "occur other problem");
        }
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shouyef1;
    }

    @Override
    protected void setup(final View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        //
        textView = rootView.findViewById(R.id.textView);
        tv1 = rootView.findViewById(R.id.tv1);
        tv2 = rootView.findViewById(R.id.tv2);
        tv3 = rootView.findViewById(R.id.tv3);
        tv4 = rootView.findViewById(R.id.tv4);
        tv5 = rootView.findViewById(R.id.tv5);
        tv6 = rootView.findViewById(R.id.tv6);
        tv7 = rootView.findViewById(R.id.tv7);
        tv8 = rootView.findViewById(R.id.tv8);
        tv9 = rootView.findViewById(R.id.tv9);
        tv10 = rootView.findViewById(R.id.tv10);
        tv11 = rootView.findViewById(R.id.tv11);
        tv12 = rootView.findViewById(R.id.tv12);
        tv13 = rootView.findViewById(R.id.tv13);
        tv14 = rootView.findViewById(R.id.tv14);
        tv15 = rootView.findViewById(R.id.tv15);
        tv16 = rootView.findViewById(R.id.tv16);
        tv17 = rootView.findViewById(R.id.tv17);
        if (!isHarmonyOS()) {
            textView.setText("Android系统");
        } else {
            textView.setText("HarmonyOS系统");
        }
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv10.setOnClickListener(this);
        tv11.setOnClickListener(this);
        tv12.setOnClickListener(this);
        tv13.setOnClickListener(this);
        tv14.setOnClickListener(this);
        tv15.setOnClickListener(this);
        tv16.setOnClickListener(this);
        tv17.setOnClickListener(this);
        //
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ShouyeF1");
        LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
        donetwork();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv1) {
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.PLVEntranceActivity");
            startActivity(intent);
        } else if (id == R.id.tv2) {
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.TencentIMSplashActivity");
            startActivity(intent);
        } else if (id == R.id.tv3) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ZhiwenActtivity"));
        } else if (id == R.id.tv4) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActViewPageYewuList1"));
        } else if (id == R.id.tv5) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActYewuList1"));
        } else if (id == R.id.tv6) {
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SearchListActivity");
            intent.putExtra("search_key", "搜索");
            startActivity(intent);
        } else if (id == R.id.tv7) {
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.DKMainActivity");
            startActivity(intent);
        } else if (id == R.id.tv8) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SaomaAct3"));
        } else if (id == R.id.tv9) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LunboMainActivity"));
        } else if (id == R.id.tv10) {
            String aaa = "http://lzzhdj.com.cn:94/#/login";
            try {
                HiosHelper.resolveAd(getActivity(), getActivity(), UrlEncodeUtils.encodeUrl(aaa));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.tv11) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.XpopupMainActivity"));
        } else if (id == R.id.tv12) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.ScreenAct"));
        } else if (id == R.id.tv13) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.ChoutiActivity"));
        } else if (id == R.id.tv14) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LanguageAct"));
        } else if (id == R.id.tv15) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LoginActDemo"));
        } else if (id == R.id.tv16) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.CoordinatorLayoutAct"));
        }else if (id == R.id.tv17) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ElmAct"));
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
