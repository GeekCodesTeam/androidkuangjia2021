package com.example.yewulibs_demo1_appindex.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.bizyewu1.bean.VersionInfoBean;
import com.example.bizyewu1.presenter.CheckverionPresenter;
import com.example.bizyewu1.view.CheckverionView;
import com.geek.libbase.base.SlbBaseActivity;
import com.geek.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappcomm.utils.BanbenCommonUtils;
import com.example.yewulibs_demo1_appindex.R;
import com.example.yewulibs_demo1_appindex.services.LocationService;
import com.geek.libutils.app.BaseAppManager;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.github.commonlibs.libupdateapputilsold.util.UpdateAppReceiver;
import com.github.commonlibs.libupdateapputilsold.util.UpdateAppUtils;
import com.haier.cellarette.libwebview.base.WebStatusBarUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;

import constant.DownLoadBy;
import constant.UiType;
import listener.OnBtnClickListener;
import model.UiConfig;
import model.UpdateConfig;
import ui.SpanUtils;

public class ShouyeNewActivity extends SlbBaseActivity implements CheckverionView {

    private TextView tv1;
    private TextView tv2;
    private RecyclerView recyclerView;
    private ShouyeFooterAdapter mAdapter;
    private int current_pos = 0;
    private String tag_ids;
    private FragmentManager mFragmentManager;
    public String id = BanbenCommonUtils.dizhi1_comm;
    public String id1 = id + "/index";
    public String id2 = id + "/development";
//    public String id3 = id + "/focus";
//    public String id4 = id + "/topic";
//    public String id5 = id + "/userCenter";
    private List<ShouyeFooterBean> mList;
    private static final String LIST_TAG1 = "list11";
//    private static final String LIST_TAG2 = "list22";
//    private static final String LIST_TAG3 = "list33";
//    private static final String LIST_TAG4 = "list44";
//    private static final String LIST_TAG5 = "list55";
    //
    private UpdateAppReceiver updateAppReceiver;// 强制升级
    private CheckverionPresenter presenter;
    private String apkPath = "";
    private int serverVersionCode = 0;
    private String serverVersionName = "";
    private String updateInfoTitle = "";
    private String updateInfo = "";
    private String md5 = "";
    private String appPackageName = "";
    private TextView check_view;
    private LinearLayout menu_group;
    private Boolean checked = false;
    //
    private WebViewItem1 mFragment1; //单位项目
//    private WebViewItem2 mFragment2; //发展总览
//    private WebViewItem3 mFragment3; //重点关注
//    private WebViewItem4 mFragment4; //专题推送
//    private WebViewItem5 mFragment5; //我的

    private LocationService mLocationService;

    private MessageReceiverIndex mMessageReceiver;

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("ShouyeActivity".equals(intent.getAction())) {
                    //点击item
                    if (intent.getIntExtra("id", -1) != -1) {
                        current_pos = intent.getIntExtra("id", -1);
                        footer_onclick();
                    }
                    if (!TextUtils.isEmpty(intent.getStringExtra("id1"))) {
                        current_pos = 0;
                        footer_onclick();
                        //
                        Intent msgIntent = new Intent();
                        msgIntent.putExtra("id1", intent.getStringExtra("id1"));
                        msgIntent.setAction("WebViewItem1");
                        LocalBroadcastManagers.getInstance(context).sendBroadcast(msgIntent);
                    }
                    if (TextUtils.equals("goLogin", intent.getStringExtra("h5"))) {
                        onToLogin();
                    }
//                    if (TextUtils.equals("check", intent.getStringExtra("h5"))) {
//                        if (menu_group.getVisibility() == View.VISIBLE) {
//                            menu_group.setVisibility(View.GONE);
//                        } else {
//                            menu_group.setVisibility(View.VISIBLE);
//                        }
//                    }
                    if (!TextUtils.isEmpty(intent.getStringExtra("color"))) {
                        WebStatusBarUtil.setColorNoTranslucent(ShouyeNewActivity.this, Color.parseColor(intent.getStringExtra("color")));
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onResume() {
        updateAppReceiver.setBr(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (updateAppReceiver != null) {
            updateAppReceiver.desBr(this);
        }
        if (presenter != null) {
            presenter.onDestory();
        }
        LocalBroadcastManagers.getInstance(getApplicationContext()).unregisterReceiver(mMessageReceiver);
//        LocationUtils.unregister();
        super.onDestroy();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        // 解决fragment布局重叠错乱
        if (savedInstanceState != null) {
            mFragment1 = (WebViewItem1) mFragmentManager.findFragmentByTag(LIST_TAG1);
//            mFragment2 = (WebViewItem2) mFragmentManager.findFragmentByTag(LIST_TAG2);
//            mFragment3 = (WebViewItem3) mFragmentManager.findFragmentByTag(LIST_TAG3);
//            mFragment4 = (WebViewItem4) mFragmentManager.findFragmentByTag(LIST_TAG4);
//            mFragment5 = (WebViewItem5) mFragmentManager.findFragmentByTag(LIST_TAG5);
        }
        super.onCreate(savedInstanceState);
    }


    private static final int MY_PERMISSION_REQUEST_CONSTANT = 123456;

    /**
     * 请求权限的回调：这里判断权限是否添加成功
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CONSTANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("main", "添加权限成功");
                }
                return;
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shouye;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findview();
        onclick();
        doNetWork();
        // 接口bufen
        updateAppReceiver = new UpdateAppReceiver();
        apkPath = "";
        updateInfoTitle = "";
        updateInfo = "";
        serverVersionCode = AppUtils.getAppVersionCode();
        serverVersionName = AppUtils.getAppVersionName();
        appPackageName = AppUtils.getAppPackageName();
        md5 = AppUtils.getAppSignaturesMD5(appPackageName).get(0);
        presenter = new CheckverionPresenter();
        presenter.onCreate(this);
//        presenter.checkVerion("android", serverVersionCode + "", appPackageName, serverVersionName);
        presenter.checkVerion("android");
        //TODO MOBID TEST
//        startService(new Intent(this, MOBIDservices.class));
        // 位置
//        bindService(new Intent(this, LocationService.class), conn, Context.BIND_AUTO_CREATE);
        XXPermissions.with(this)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.ACCESS_BACKGROUND_LOCATION)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
//                            ToastUtils.showLong("获取定位权限成功");
                            //

                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
//                            ToastUtils.showLong("被永久拒绝授权，请手动授予定位权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(ShouyeNewActivity.this, permissions);
                            return;
                        }

                        if (permissions.size() == 1 && Permission.ACCESS_BACKGROUND_LOCATION.equals(permissions.get(0))) {
//                            ToastUtils.showLong("没有授予后台定位权限，请您选择\"始终允许\"");
                        } else {
                            ToastUtils.showLong("获取定位权限失败");
                        }
                    }
                });
    }
//    private void set_location2() {
//        LocUtil.getLocation(this, new LocListener() {
//
//            @Override
//            public void success(LocationBean model) {
//                String lastLatitude = String.valueOf(model.getLatitude());
//                String lastLongitude = String.valueOf(model.getLongitude());
//                String aaaa = lastLatitude + "," + lastLongitude;
//                MmkvUtils.getInstance().set_common("经纬度", JSON.toJSONString(model));
//                MyLogUtil.e("LocUtil21", aaaa);
//            }
//
//            @Override
//            public void fail(int msg) {
//                String aaa = msg + "";
//            }
//        });
//    }

    private void doNetWork() {
        mList = new ArrayList<>();
        addList();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setContacts(mList);
        mAdapter.notifyDataSetChanged();
        current_pos = 0;
        footer_onclick();
    }

    private void onclick() {
        mAdapter.setOnItemClickLitener(new ShouyeFooterAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击item
                current_pos = position;
                footer_onclick();
            }
        });
    }

    private void findview() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        check_view = findViewById(R.id.check_view);
        menu_group = findViewById(R.id.menu_group);
        tv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BanbenCommonUtils.changeUrl2(ShouyeNewActivity.this);
                return false;
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBrowserUpdate();
            }
        });
        check_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footer_onclick();

//                if (checked) {//全屏状态
//                    WebStatusBarUtil.setColorNoTranslucent(ShouyeNewActivity.this, Color.parseColor("#3394FD"));
//                    menu_group.setVisibility(View.VISIBLE);
//                    checked = false;
//                } else {//非全屏状态
//                    WebStatusBarUtil.setColorNoTranslucent(ShouyeNewActivity.this, Color.parseColor("#002B70"));
//                    menu_group.setVisibility(View.GONE);
//                    checked = true;
//                }

            }
        });
//        startHiddenManager = new StartHiddenManager(left, right, intent, new StartHiddenManager.OnClickFinish() {
//            @Override
//            public void onFinish() {
//
//            }
//        });
        recyclerView = findViewById(R.id.recycler_view1);
        mAdapter = new ShouyeFooterAdapter(this);
        // 动态设置首页bufen
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ShouyeActivity");
        LocalBroadcastManagers.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, filter);
    }

    private String apkUrl = "https://www.pgyer.com/DHz2";// http://118.24.148.250:8080/yk/update_signed.apk
    private String updateTitle = "发现新版本V2.0.0";
    private String updateContent = "1、党建同心屏重构版\n2、支持自定义UI\n3、增加md5校验\n4、更多功能等你探索";
    private UpdateConfig updateConfigbrowser;//浏览器使用
    private UiConfig uiConfigbrowser;//浏览器UI配置

    /**
     * 浏览器更新
     */
    private void showBrowserUpdate() {
        /*浏览器Config配置权限使用*/
        updateConfigbrowser = new UpdateConfig();
        updateConfigbrowser.setCheckWifi(true);
        updateConfigbrowser.setDownloadBy(DownLoadBy.BROWSER);
        updateConfigbrowser.setServerVersionName("2.0.0");

        /*浏览器UI配置*/
        uiConfigbrowser = new UiConfig();
        uiConfigbrowser.setUiType(UiType.PLENTIFUL);
        // 使用SpannableString
        SpanUtils spanUtils = new SpanUtils(this);
        SpannableStringBuilder span = spanUtils
                .appendLine("1、党建同心屏重构版")
                .appendLine("2、支持自定义UI").setForegroundColor(Color.RED)
                .appendLine("3、增加md5校验").setForegroundColor(Color.parseColor("#88e16531")).setFontSize(20, true)
                .appendLine("4、更多功能等你探索").setBoldItalic()
                .appendLine().appendImage(R.drawable.default_unknow_image).setBoldItalic()
                .create();

        update.UpdateAppUtils
                .getInstance()
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateContent(span)
                .updateConfig(updateConfigbrowser)
                .uiConfig(uiConfigbrowser)
                // 设置 取消 按钮点击事件
                .setCancelBtnClickListener(new OnBtnClickListener() {
                    @Override
                    public boolean onClick() {
                        ToastUtils.showLong("取消");
                        return false;
                    }
                })
                // 设置 立即更新 按钮点击事件
                .setUpdateBtnClickListener(new OnBtnClickListener() {
                    @Override
                    public boolean onClick() {
                        ToastUtils.showLong("立即更新");
                        return false;
                    }
                }).update();
    }

    //点击item
    private void footer_onclick() {
//        set_location2();
        final ShouyeFooterBean model = (ShouyeFooterBean) mAdapter.getItem(current_pos);
        if (model.isEnselect()) {
            // 不切换当前的item点击 刷新当前页面
            showFragment(model.getText_id(), true);
        } else {
            // 切换到另一个item
            if (model.getText_id().equalsIgnoreCase(id2)) {
//                SlbLoginUtil2.get().loginTowhere(ShouyeActivity.this, new Runnable() {
//                    @Override
//                    public void run() {
//                        set_footer_change(model);
//                        showFragment(model.getText_id(), false);
//                    }
//                });
                set_footer_change(model);
                showFragment(model.getText_id(), false);
            } else {
                set_footer_change(model);
                showFragment(model.getText_id(), false);
            }
        }
    }

    private void set_footer_change(ShouyeFooterBean model) {
        //设置为选中
        initList();
        model.setEnselect(true);
        mAdapter.setContacts(mList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime;

    private void exit() {
        ShouyeFooterBean model = (ShouyeFooterBean) mAdapter.getItem(0);
        if (model != null && !tag_ids.equals(model.getText_id())) {
            set_footer_change(model);
            showFragment(model.getText_id(), false);
        } else {
            if ((System.currentTimeMillis() - exitTime) < 1500) {
//                RichText.recycle();
                BaseAppManager.getInstance().closeApp();
            } else {
                ToastUtils.showLong("再次点击退出程序哟 ~");
                exitTime = System.currentTimeMillis();
            }
        }
    }

    private void addList() {
        mList.add(new ShouyeFooterBean(id1, "单位项目", R.drawable.index_tab1_select, R.drawable.index_tab1_normal, false));
//        mList.add(new ShouyeFooterBean(id2, "发展总览", R.drawable.index_tab2_select, R.drawable.index_tab2_normal, false));
//        mList.add(new ShouyeFooterBean(id3, "重点关注", R.drawable.index_tab3__select, R.drawable.index_tab3_normal, false));
//        mList.add(new ShouyeFooterBean(id4, "专题推送", R.drawable.index_tab4_select, R.drawable.index_tab4_normal, false));
//        mList.add(new ShouyeFooterBean(id5, "我的", R.drawable.index_tab5__select, R.drawable.index_tab5_normal, false));
    }

    private void initList() {
        for (int i = 0; i < mList.size(); i++) {
            ShouyeFooterBean item = mList.get(i);
            if (item.isEnselect()) {
                item.setEnselect(false);
            }
        }
    }

    private void showFragment(final String tag, final boolean isrefresh) {
        tag_ids = tag;
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        if (tag.equalsIgnoreCase(id1)) {
            if (mFragment1 == null) {
                mFragment1 = new WebViewItem1();
                Bundle args = new Bundle();
                args.putString("url_key", tag);
                mFragment1.setArguments(args);
                transaction.add(R.id.container, mFragment1, LIST_TAG1);
            } else {
                transaction.show(mFragment1);
                mFragment1.getCate(tag, isrefresh);
            }
        }
//        else if (tag.equalsIgnoreCase(id2)) {
//            if (mFragment2 == null) {
//                mFragment2 = new WebViewItem2();
//                Bundle args = new Bundle();
//                args.putString("url_key", tag);
//                mFragment2.setArguments(args);
//                transaction.add(R.id.container, mFragment2, LIST_TAG2);
//            } else {
//                transaction.show(mFragment2);
//                mFragment2.getCate(tag, isrefresh);
//            }
//        } else if (tag.equalsIgnoreCase(id3)) {
//            if (mFragment3 == null) {
//                mFragment3 = new WebViewItem3();
//                Bundle args = new Bundle();
//                args.putString("url_key", tag);
//                mFragment3.setArguments(args);
//                transaction.add(R.id.container, mFragment3, LIST_TAG3);
//            } else {
//                transaction.show(mFragment3);
//                mFragment3.getCate(tag, isrefresh);
//            }
//        } else if (tag.equalsIgnoreCase(id4)) {
//            if (mFragment4 == null) {
//                mFragment4 = new WebViewItem4();
//                Bundle args = new Bundle();
//                args.putString("url_key", tag);
//                mFragment4.setArguments(args);
//                transaction.add(R.id.container, mFragment4, LIST_TAG5);
//            } else {
//                transaction.show(mFragment4);
//                mFragment4.getCate(tag, isrefresh);
//            }
//        } else if (tag.equalsIgnoreCase(id5)) {
//            if (mFragment5 == null) {
//                mFragment5 = new WebViewItem5();
//                Bundle args = new Bundle();
//                args.putString("url_key", tag);
//                mFragment5.setArguments(args);
//                transaction.add(R.id.container, mFragment5, LIST_TAG5);
//            } else {
//                transaction.show(mFragment5);
//                mFragment5.getCate(tag, isrefresh);
//            }
//        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mFragment1 != null) {
            transaction.hide(mFragment1);
        }
//        if (mFragment2 != null) {
//            transaction.hide(mFragment2);
//        }
//        if (mFragment3 != null) {
//            transaction.hide(mFragment3);
//        }
//        if (mFragment4 != null) {
//            transaction.hide(mFragment4);
//        }
//        if (mFragment5 != null) {
//            transaction.hide(mFragment5);
//        }
    }

    /**
     * fragment间通讯bufen
     *
     * @param value 要传递的值
     * @param tag   要通知的fragment的tag
     */
    public void callFragment(Object value, String... tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;
        for (String item : tag) {
            if (TextUtils.isEmpty(item)) {
                continue;
            }

            fragment = fm.findFragmentByTag(item);
            if (fragment instanceof SlbBaseLazyFragmentNew) {
                ((SlbBaseLazyFragmentNew) fragment).call(value);
            }
        }
    }

//    @Override
//    public Resources getResources() {
//        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
//        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
//        AutoSizeCompat.autoConvertDensity((super.getResources()), 375, true);//如果有自定义需求就用这个方法
//        return super.getResources();
//    }

    @Override
    public void OnUpdateVersionSuccess(VersionInfoBean versionInfoBean) {
        apkPath = versionInfoBean.getTargetUrl();
        serverVersionCode = Integer.valueOf(versionInfoBean.getTargetUrl());
        serverVersionName = versionInfoBean.getTargetUrl();
        updateInfoTitle = versionInfoBean.getTargetUrl();
        updateInfo = versionInfoBean.getTargetUrl();
        if (TextUtils.equals(versionInfoBean.getTargetUrl(), "1")) {
            // 强制检查更新bufen
            UpdateAppUtils.from(ShouyeNewActivity.this)
                    .serverVersionCode(serverVersionCode)
                    .serverVersionName(serverVersionName)
                    .downloadPath("apk/" + getPackageName() + ".apk")
                    .showProgress(true)
                    .isForce(true)
                    .apkPath(apkPath)
                    .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP)    //default
                    .checkBy(UpdateAppUtils.CHECK_BY_VERSION_CODE) //default
                    .updateInfoTitle(updateInfoTitle)
                    .updateInfo(updateInfo.replace("|", "\n"))
                    .showNotification(true)
//                    .needFitAndroidN(true)
//                    .updateInfoTitle("新版本已准备好")
//                    .updateInfo("版本：1.01" + "    " + "大小：10.41M\n" + "1.修改已知问题\n2.加入动态绘本\n3.加入小游戏等你来学习升级")
                    .update();
        } else {
            // 检查更新bufen
            UpdateAppUtils.from(ShouyeNewActivity.this)
                    .serverVersionCode(serverVersionCode)
                    .serverVersionName(serverVersionName)
                    .downloadPath("apk/" + getPackageName() + ".apk")
                    .showProgress(true)
                    .apkPath(apkPath)
                    .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP)    //default
                    .checkBy(UpdateAppUtils.CHECK_BY_VERSION_CODE) //default
                    .updateInfoTitle(updateInfoTitle)
                    .updateInfo(updateInfo.replace("|", "\n"))
                    .showNotification(true)
//                    .needFitAndroidN(true)
//                    .updateInfoTitle("新版本已准备好")
//                    .updateInfo("版本：1.01" + "    " + "大小：10.41M\n" + "1.修改已知问题\n2.加入动态绘本\n3.加入小游戏等你来学习升级")
                    .update();
        }
    }

    @Override
    public void OnUpdateVersionNodata(String bean) {

    }

    @Override
    public void OnUpdateVersionFail(String msg) {

    }

    private void onToLogin() {
        if (ActivityUtils.getActivityList().size() == 1) {
            startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SlbLoginActivity"));
        }
        finish();
    }
}
