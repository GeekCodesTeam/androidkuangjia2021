package com.example.slbappindex.index;

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
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappindex.R;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;

import java.lang.reflect.Method;

public class ShouyeF1 extends SlbBaseLazyFragmentNew implements View.OnClickListener {

    private String tablayoutId;
    private TextView textView, tv_center_content101, tv_center_content1, tv_center_content2, tv_center_content3, tv_center_content4,
            tv_center_content5, tv_center_content6, tv_center_content7, tv_center_content8, tv_center_content9, tv_center_content10,
            tv_center_content11, tv_center_content12, tv_center_content13, tv_center_content14, tv_center_content15, tv_center_content16,
            tv_center_content17, tv_center_content18, tv_center_content19, tv_center_content20, tv_center_content21, tv_center_content22,
            tv_center_content23, tv_center_content24, tv_center_content25, tv_center_content26, tv_center_content27, tv_center_content28,
            tv_center_content29, tv_center_content30, tv_center_content31, tv_center_content32, tv_center_content33, tv_center_content34,
            tv_center_content35, tv_center_content36, tv_center_content37;
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
        if (!isHarmonyOS()) {
            textView.setText("Android系统");
        } else {
            textView.setText("HarmonyOS系统");
        }
        //
        tv_center_content101 = rootView.findViewById(R.id.shouyef1_tv101);
        tv_center_content1 = rootView.findViewById(R.id.shouyef1_tv1);
        tv_center_content2 = rootView.findViewById(R.id.shouyef1_tv2);
        tv_center_content3 = rootView.findViewById(R.id.shouyef1_tv3);
        tv_center_content4 = rootView.findViewById(R.id.shouyef1_tv4);
        tv_center_content5 = rootView.findViewById(R.id.shouyef1_tv5);
        tv_center_content6 = rootView.findViewById(R.id.shouyef1_tv6);
        tv_center_content7 = rootView.findViewById(R.id.shouyef1_tv7);
        tv_center_content8 = rootView.findViewById(R.id.shouyef1_tv8);
        tv_center_content9 = rootView.findViewById(R.id.shouyef1_tv9);
        tv_center_content10 = rootView.findViewById(R.id.shouyef1_tv10);
        tv_center_content11 = rootView.findViewById(R.id.shouyef1_tv11);
        tv_center_content12 = rootView.findViewById(R.id.shouyef1_tv12);
        tv_center_content13 = rootView.findViewById(R.id.shouyef1_tv13);
        tv_center_content14 = rootView.findViewById(R.id.shouyef1_tv14);
        tv_center_content15 = rootView.findViewById(R.id.shouyef1_tv15);
        tv_center_content16 = rootView.findViewById(R.id.shouyef1_tv16);
        tv_center_content17 = rootView.findViewById(R.id.shouyef1_tv17);
        tv_center_content18 = rootView.findViewById(R.id.shouyef1_tv18);
        tv_center_content19 = rootView.findViewById(R.id.shouyef1_tv19);
        tv_center_content20 = rootView.findViewById(R.id.shouyef1_tv20);
        tv_center_content21 = rootView.findViewById(R.id.shouyef1_tv21);
        tv_center_content22 = rootView.findViewById(R.id.shouyef1_tv22);
        tv_center_content23 = rootView.findViewById(R.id.shouyef1_tv23);
        tv_center_content24 = rootView.findViewById(R.id.shouyef1_tv24);
        tv_center_content25 = rootView.findViewById(R.id.shouyef1_tv25);
        tv_center_content26 = rootView.findViewById(R.id.shouyef1_tv26);
        tv_center_content27 = rootView.findViewById(R.id.shouyef1_tv27);
        tv_center_content28 = rootView.findViewById(R.id.shouyef1_tv28);
        tv_center_content29 = rootView.findViewById(R.id.shouyef1_tv29);
        tv_center_content30 = rootView.findViewById(R.id.shouyef1_tv30);
        tv_center_content31 = rootView.findViewById(R.id.shouyef1_tv31);
        tv_center_content32 = rootView.findViewById(R.id.shouyef1_tv32);
        tv_center_content33 = rootView.findViewById(R.id.shouyef1_tv33);
        tv_center_content34 = rootView.findViewById(R.id.shouyef1_tv34);
        tv_center_content35 = rootView.findViewById(R.id.shouyef1_tv35);
        tv_center_content36 = rootView.findViewById(R.id.shouyef1_tv36);
        tv_center_content37 = rootView.findViewById(R.id.shouyef1_tv37);
        tv_center_content101.setOnClickListener(this);
        tv_center_content1.setOnClickListener(this);
        tv_center_content2.setOnClickListener(this);
        tv_center_content3.setOnClickListener(this);
        tv_center_content4.setOnClickListener(this);
        tv_center_content5.setOnClickListener(this);
        tv_center_content6.setOnClickListener(this);
        tv_center_content7.setOnClickListener(this);
        tv_center_content8.setOnClickListener(this);
        tv_center_content9.setOnClickListener(this);
        tv_center_content10.setOnClickListener(this);
        tv_center_content11.setOnClickListener(this);
        tv_center_content12.setOnClickListener(this);
        tv_center_content13.setOnClickListener(this);
        tv_center_content14.setOnClickListener(this);
        tv_center_content15.setOnClickListener(this);
        tv_center_content16.setOnClickListener(this);
        tv_center_content17.setOnClickListener(this);
        tv_center_content18.setOnClickListener(this);
        tv_center_content19.setOnClickListener(this);
        tv_center_content20.setOnClickListener(this);
        tv_center_content21.setOnClickListener(this);
        tv_center_content22.setOnClickListener(this);
        tv_center_content23.setOnClickListener(this);
        tv_center_content24.setOnClickListener(this);
        tv_center_content25.setOnClickListener(this);
        tv_center_content26.setOnClickListener(this);
        tv_center_content27.setOnClickListener(this);
        tv_center_content28.setOnClickListener(this);
        tv_center_content29.setOnClickListener(this);
        tv_center_content29.setOnClickListener(this);
        tv_center_content30.setOnClickListener(this);
        tv_center_content31.setOnClickListener(this);
        tv_center_content32.setOnClickListener(this);
        tv_center_content33.setOnClickListener(this);
        tv_center_content34.setOnClickListener(this);
        tv_center_content35.setOnClickListener(this);
        tv_center_content36.setOnClickListener(this);
        tv_center_content37.setOnClickListener(this);

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
        if (id == R.id.shouyef1_tv101) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.QuanxianActivity"));
        } else if (id == R.id.shouyef1_tv1) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LiandongDemoAct"));
        } else if (id == R.id.shouyef1_tv2) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LikeButtonActivity"));
        } else if (id == R.id.shouyef1_tv3) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.handler"));
        } else if (id == R.id.shouyef1_tv4) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.EmptyViewMainActivity"));
        } else if (id == R.id.shouyef1_tv5) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.networkview"));
        } else if (id == R.id.shouyef1_tv6) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".com.RecycleViewMainActivity.act"));
        } else if (id == R.id.shouyef1_tv7) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".com.caranimation.act"));
        } else if (id == R.id.shouyef1_tv8) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".com.recycleviewalluses.act"));
        } else if (id == R.id.shouyef1_tv9) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".com.ZuniScrollViewAct.act"));
        } else if (id == R.id.shouyef1_tv10) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.RingActivity"));
        } else if (id == R.id.shouyef1_tv11) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.changelanguage"));
        } else if (id == R.id.shouyef1_tv12) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.ScrollViewAct"));
        } else if (id == R.id.shouyef1_tv13) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.anroomcrash"));
        } else if (id == R.id.shouyef1_tv14) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.MusicActivity"));
        } else if (id == R.id.shouyef1_tv15) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.expandableview"));
        } else if (id == R.id.shouyef1_tv16) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.AssetsMainActivity"));
        } else if (id == R.id.shouyef1_tv17) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.ShoppingCarActivity"));
        } else if (id == R.id.shouyef1_tv18) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.BtnActivity"));
        } else if (id == R.id.shouyef1_tv19) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.MnMainActivity"));
        } else if (id == R.id.shouyef1_tv20) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.BannerViewDemoActivity"));
        } else if (id == R.id.shouyef1_tv21) {
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.AdCommActivity");
//            intent.putExtra("id1","1");
            intent.putExtra("url1", "https://www.baidu.com/");
            startActivity(intent);
        } else if (id == R.id.shouyef1_tv22) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.AdCommImgActivity"));
        } else if (id == R.id.shouyef1_tv23) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.RiliActDemo"));
        } else if (id == R.id.shouyef1_tv24) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SaomaActDemo"));
        } else if (id == R.id.shouyef1_tv25) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.UploadImgMainActivity"));
        } else if (id == R.id.shouyef1_tv26) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.UploadPicActivity"));
        } else if (id == R.id.shouyef1_tv27) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.VideoPlayActivity"));
        } else if (id == R.id.shouyef1_tv28) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ViewPage1MainActivity"));
        } else if (id == R.id.shouyef1_tv29) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ViewPage1TabActivity"));
        } else if (id == R.id.shouyef1_tv30) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.BannerDemoActivity"));
        } else if (id == R.id.shouyef1_tv31) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SlbLoginInfoActivity"));
        } else if (id == R.id.shouyef1_tv32) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LunboMainActivity"));
        } else if (id == R.id.shouyef1_tv33) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.JianpanAct"));
        } else if (id == R.id.shouyef1_tv34) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.MainActivitySwitchButtonK"));
        } else if (id == R.id.shouyef1_tv35) {
            // 从Android启动鸿蒙组件
//            Intent intent = new Intent();
//            ComponentName componentName = new ComponentName("your harmony app's bundleName name","your ability's full name");
//            intent.setComponent(componentName);
//            intent.putExtras(bundle);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            AbilityUtils.startAbility(context, intent);
        } else if (id == R.id.shouyef1_tv36) {
            // 鸿蒙启动Android组件
//            Intent intent = new Intent();
//            Operation operation = new Intent.OperationBuilder()
//                    .withDeviceId("")
//                    .withBundleName("your android app’s packagename")
//                    .withAbilityName("your android app’s activity fullname")
//                    .withFlags(Intent.FLAG_NOT_OHOS_COMPONENT)
//                    .build();
//            intent.setOperation(operation);
//            startAbility(intent);
        }else if (id == R.id.shouyef1_tv37) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ChangeIconActivity"));
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
