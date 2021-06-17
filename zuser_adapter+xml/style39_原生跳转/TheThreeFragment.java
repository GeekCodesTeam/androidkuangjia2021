/**
 * Copyright  2015,  Smart  Haier
 * All  rights  reserved.
 * Description:目前功能为首页食材管理操作
 * Author:  geek
 * Date:  2016年11月1日14:05:03
 * FileName:  IndexFoodFragmentNew.java
 * History:
 * Author:geek
 * Modification:
 */
package com.haiersmart.sfnation.fragment.index;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.haiersmart.sfnation.R;
import com.haiersmart.sfnation.adapter.IndexTheThreeListViewAdapterNew;
import com.haiersmart.sfnation.application.FridgeApplication;
import com.haiersmart.sfnation.bizutils.DataProvider;
import com.haiersmart.sfnation.bizutils.LeaveMsgUtils;
import com.haiersmart.sfnation.bizutils.MobEventHelper;
import com.haiersmart.sfnation.bizutils.ToastUtil;
import com.haiersmart.sfnation.constant.ConstantUtil;
import com.haiersmart.sfnation.constant.VersionInfo;
import com.haiersmart.sfnation.domain.TheThreeModel;
import com.haiersmart.sfnation.service.TheThreeService;
import com.haiersmart.sfnation.ui.EHaierWebViewActivity;
import com.haiersmart.sfnation.ui.cookbook.FoodManagerActivity;
import com.haiersmart.sfnation.ui.cookbook.FoodManagerNewBanActivity;
import com.haiersmart.sfnation.ui.cookbooknew.CookBookActivityNew;
import com.haiersmart.sfnation.ui.delivery.MyDeliveryActivity;
import com.haiersmart.sfnation.ui.ec.ShopIndexActivity;
import com.haiersmart.sfnation.ui.fm.FMMainActivity;
import com.haiersmart.sfnation.ui.neteasecloud.NetsMainActivity;
import com.haiersmart.sfnation.ui.tool.TimerActivity;
import com.haiersmart.sfnation.ui.youku.YouKuMainActivityNew;
import com.haiersmart.user.sdk.UserUtils;
import com.unisound.uhomedemo.AISpeechUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

;

/**
 * @function: 首页->第三版
 * @description: 目前功能为第三版的操作
 * @history: 1. 2016年10月31日13:49:07
 * Author:geek
 * modification:
 */
public class TheThreeFragment extends BaseIndexFragment implements View.OnClickListener {

    private static final String TAG_SHUN_GUANG = "UI2_index_sc";

    public static final String PackgetName_YOUJIA = "com.demo.u.testu";//优家
    public static final String PackgetName_PQ = "com.demo.tips.testtips";//便签
    public static final String PackgetName_DAOJIA = "com.demo.home.testbackhome";//到家服务
    public static final String PackgetName_TV = "com.demo.tv.testtv";//电视同屏
    public static final String LYB = "liuyanban";
    public static final String PackgetName_VOICE = "baidu_voice";

    //基本写法
    private LinearLayout index_food_rl1;//ceng
    private RecyclerView recycler_view1;//横向列表bufen
    private IndexTheThreeListViewAdapterNew mAdaptor;//横向列表适配器bufen
    private List<TheThreeModel> myData;//横向列表数据源bufen

    /**
     * R.layoutbufen
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_thethreefragment_new;
    }

    /**
     * 相当于onCreatebufen
     *
     * @param savedInstanceState
     */
    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        findviews(rootView);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recycler_view1.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));

        //数据列表bufen
        setupData();
        mAdaptor = new IndexTheThreeListViewAdapterNew(this.getActivity());
        mAdaptor.setContacts(myData);
        recycler_view1.setAdapter(mAdaptor);

        setRecyclerListener();
    }

    /**
     * 初始化控件bufen
     */
    private void findviews(View rootView) {
        index_food_rl1 = (LinearLayout) rootView.findViewById(R.id.index_food_rl1);//cengbufen
        recycler_view1 = (RecyclerView) rootView.findViewById(R.id.recycler_view_scgl);//食材管理横向列表bufen
    }

    private void pop_tanchu(final TheThreeModel listItem) {
        UserUtils.get().loginToDo(TheThreeFragment.this.getActivity(), new Runnable() {
            @Override
            public void run() {
//                ToastUtil.showToastLong("登录成功！");
                Login_jumpOther(listItem);
            }
        });
//        if (UserUtils.get().isUserLogin()) {
//            Login_jumpOther(listItem);
//        } else {
//            PopLogin.showLoginDialog(TheThreeFragment.this.getActivity(), new PopLogin.OnLoginListener() {
//                @Override
//                public void onLogin(boolean success) {
//                    if (!success) {
//                        return;
//                    }
//                    ToastUtil.showToastLong("登录成功！");
//                    Login_jumpOther(listItem);
//                }
//            });
//        }
    }

    private void Login_jumpOther(TheThreeModel listItem) {
        //传值到bufen
        ApplicationInfo appInfo = FridgeApplication.get().getApplicationInfo();
        try {
            if ((listItem.getGetclassname()).startsWith(appInfo.packageName)) {
                //本应用
                Class c = Class.forName(listItem.getGetclassname());
                Intent intent = new Intent(getActivity(), c);
//                if (listItem.getGetclassname().equals(TimerActivity.class.getName())) {
//                    intent.putExtra(ConstantUtil.JUMPTOSETWHICH, SystemSettingActivity.TO_TIMER);//TO_TIMER
//                }
                startActivity(intent);
            } else {
                //第三方应用
//                        TheThreeAppUtil.openApp(TheThreeFragment.this.getActivity(), "包名");
                openApp(listItem.getGetclassname());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化点击事件bufen
     */
    private void setRecyclerListener() {
        //点击食材管理itembufen
        mAdaptor.setOnItemClickLitener(new IndexTheThreeListViewAdapterNew.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, final int position) {
                TheThreeModel listItem = (TheThreeModel)
                        mAdaptor.getItem(position);
                //友盟统计bufen
                MobEventHelper.onEvent(TheThreeFragment.this, listItem.getStatistics());

                if (listItem.getGetclassname().equals(FoodManagerNewBanActivity.class.getName())) {
                    //食材管理bufen
                    pop_tanchu(listItem);
                    return;
                }

                if (listItem.getGetclassname().equals(MyDeliveryActivity.class.getName())) {
                    //宅配送bufen
                    pop_tanchu(listItem);
                    return;
                }

                if (LYB.equals(listItem.getGetclassname())) {
                    LeaveMsgUtils.start(getContext());
                    return;
                }

                if (EHaierWebViewActivity.class.getName().equals(listItem.getGetclassname())) {
                    pop_tanchu(listItem);
                    return;
                }

                if (PackgetName_VOICE.equals(listItem.getGetclassname())) {
                    if (AISpeechUtil.iSVoiceOpen()) {
                        AISpeechUtil.reWakeup(true);
                    } else {
                        ToastUtil.showToastCenter("请打开语音开关");
                    }
                    return;
                }

//                if (listItem.getGetclassname().equals(PackgetName_YOUJIA)) {
//                    //优家bufen
//                    pop_tanchu(listItem);
//                    return;
//                }
//                if (listItem.getGetclassname().equals(PackgetName_PQ)) {
//                    //家庭相册bufen
//                    pop_tanchu(listItem);
//                    return;
//                }
                Login_jumpOther(listItem);
            }
        });
    }

    public void setupData() {
        Context ctx = FridgeApplication.get();
        ApplicationInfo appInfo = ctx.getApplicationInfo();
        int resID1 = ctx.getResources().getIdentifier("thethree_a1", "drawable", appInfo.packageName);
        int resID2 = ctx.getResources().getIdentifier("thethree_a2", "drawable", appInfo.packageName);
        int resID3 = ctx.getResources().getIdentifier("thethree_a3", "drawable", appInfo.packageName);
        int resID4 = ctx.getResources().getIdentifier("thethree_a4", "drawable", appInfo.packageName);
        int resID5 = ctx.getResources().getIdentifier("thethree_a5", "drawable", appInfo.packageName);
        int resID6 = ctx.getResources().getIdentifier("thethree_a6", "drawable", appInfo.packageName);
        int resID7 = ctx.getResources().getIdentifier("thethree_a7", "drawable", appInfo.packageName);
        int resID8 = ctx.getResources().getIdentifier("thethree_a8", "drawable", appInfo.packageName);
//        int resID9 = FridgeApplication.get().getResources().getIdentifier("thethree_a9", "drawable", appInfo.packageName);
        int resID11 = ctx.getResources().getIdentifier("thethree_a11", "drawable", appInfo.packageName);
        int resID12 = FridgeApplication.get().getResources().getIdentifier("thethree_a12", "drawable", appInfo.packageName);
        int resID13 = FridgeApplication.get().getResources().getIdentifier("thethree_a13", "drawable", appInfo.packageName);

        myData = new ArrayList<>();
        myData.add(new TheThreeModel("安心购商城", resID3, ShopIndexActivity.class.getName(), "UI2_index_sc"));
        myData.add(new TheThreeModel("食材管理", resID4, FoodManagerNewBanActivity.class.getName(), "UI2_index_food_module"));
        // 绿安版和冰世纪版不显示宅配送和顺逛
        if (ConstantUtil.VERSION_INFO != VersionInfo.LVAN
                && ConstantUtil.VERSION_INFO != VersionInfo.ICENOTE) {
//            myData.add(new TheThreeModel("宅配送", resID9, MyDeliveryActivity.class.getName(), "UI2_index_house_distribution"));
//            myData.add(new TheThreeModel("顺逛商城", resID11, EHaierWebViewActivity.class.getName(), TAG_SHUN_GUANG));
        }
        myData.add(new TheThreeModel("香哈菜谱", resID1, CookBookActivityNew.class.getName(), "UI2_index_xiangha_menu"));
        myData.add(new TheThreeModel("定时器", resID2, TimerActivity.class.getName(), "UI2_index_timer"));

//        myData.add(new TheThreeModel("优家", resID5, PackgetName_YOUJIA, "UI2_index_U+"));
        myData.add(new TheThreeModel("网易云音乐", resID6, NetsMainActivity.class.getName(), "UI2_index_nets_music"));
        myData.add(new TheThreeModel("优酷视频", resID7, YouKuMainActivityNew.class.getName(), "UI2_index_youku_right"));
        myData.add(new TheThreeModel("喜马拉雅FM", resID8, FMMainActivity.class.getName(), "UI2_index_FM_music"));
        if (DataProvider.getFridge_mode().contains("476")) {
            myData.add(new TheThreeModel("留言板", resID12, LYB, "UI2_index_liuyanban"));//留言板
        }
        myData.add(new TheThreeModel("小度语音助手", resID13, PackgetName_VOICE, "UI2_index_voice"));//语音

    }

  @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_food_tv1content:
                //如果未联网跳转WIFI控制页面bufen
                if (!isNetworkConnected()) {
                    startSetting();
                    return;
                }
                //判断登录是否跳转bufen
                targetToIfLogin(FoodManagerActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 检查系统应用程序，并打开
     */

    private void openApp(String packagename) {
        //应用过滤条件
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager mPackageManager = TheThreeFragment.this.getActivity().getPackageManager();
        List<ResolveInfo> mAllApps = mPackageManager.queryIntentActivities(mainIntent, 0);
        //按报名排序
        Collections.sort(mAllApps, new ResolveInfo.DisplayNameComparator(mPackageManager));

        for (ResolveInfo res : mAllApps) {
            //该应用的包名和主Activity
            String pkg = res.activityInfo.packageName;
            String cls = res.activityInfo.name;

            // 打开QQ
            if (pkg.contains(packagename)) {
                ComponentName componet = new ComponentName(pkg, cls);
                Intent intent = new Intent();
                intent.setComponent(componet);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Intent intent0 = new Intent(TheThreeFragment.this.getActivity(), TheThreeService.class);
                getActivity().startService(intent0);
            } else {
                ToastUtil.showToastCenter("敬请期待");
            }
        }
    }
}
