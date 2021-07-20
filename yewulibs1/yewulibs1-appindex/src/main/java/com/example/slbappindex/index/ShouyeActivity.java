package com.example.slbappindex.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.bizyewu1.bean.VersionInfoBean;
import com.example.bizyewu1.presenter.CheckverionPresenter;
import com.example.bizyewu1.view.CheckverionView;
import com.example.libbase.base.SlbBaseActivity;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappindex.R;
import com.geek.libutils.app.BaseAppManager;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.shortcut.ShortcutUtils;
import com.geek.libutils.shortcut.core.Shortcut;
import com.github.commonlibs.libupdateapputilsold.util.UpdateAppReceiver;
import com.github.commonlibs.libupdateapputilsold.util.UpdateAppUtils;
import com.pgyer.pgyersdk.PgyerSDKManager;

import java.util.ArrayList;
import java.util.List;

public class ShouyeActivity extends SlbBaseActivity implements CheckverionView {

    private ImageView iv1;
    private RecyclerView recyclerView;
    private ShouyeFooterAdapter mAdapter;
    private int current_pos = 0;
    private String tag_ids;
    private FragmentManager mFragmentManager;
    public static final String id1 = "11";
    public static final String id2 = "22";
    public static final String id3 = "33";
    public static final String id4 = "44";
    private List<ShouyeFooterBean> mList;
    private static final String LIST_TAG1 = "list11";
    private static final String LIST_TAG2 = "list22";
    private static final String LIST_TAG3 = "list33";
    private static final String LIST_TAG4 = "list44";
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
    //
    private ShouyeF1 mFragment1; //
    private ShouyeF2 mFragment2; //
    private ShouyeF3 mFragment3; //
    private ShouyeF4 mFragment4; //

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
                    if (!TextUtils.isEmpty(intent.getStringExtra("mobid"))) {
                        mAdapter.setHongdiao_count(intent.getStringExtra("mobid"));
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private final Shortcut.Callback callback = new Shortcut.Callback() {
        @Override
        public void onAsyncCreate(String id, String label) {
            ShortcutUtils.dismissTryTipDialog();
            if (!Build.MANUFACTURER.equalsIgnoreCase("huawei") && !Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
                MyLogUtil.e("创建成功，id = " + id + ", label = " + label);
            } else {
                Log.d("TAG", "onAsyncCreate: " + "系统会提示");
            }
        }
    };

    @Override
    protected void onResume() {
        updateAppReceiver.setBr(this);
        PgyerSDKManager.checkSoftwareUpdate(this);
        Shortcut.getSingleInstance().addShortcutCallback(callback);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (updateAppReceiver != null) {
            updateAppReceiver.desBr(this);
        }
        Shortcut.getSingleInstance().removeShortcutCallback(callback);
        ShortcutUtils.dismissPermissionTipDialog();
        ShortcutUtils.dismissTryTipDialog();
        if (presenter != null) {
            presenter.onDestory();
        }
        LocalBroadcastManagers.getInstance(getApplicationContext()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        // 解决fragment布局重叠错乱
        if (savedInstanceState != null) {
            mFragment1 = (ShouyeF1) mFragmentManager.findFragmentByTag(LIST_TAG1);
            mFragment2 = (ShouyeF2) mFragmentManager.findFragmentByTag(LIST_TAG2);
            mFragment3 = (ShouyeF3) mFragmentManager.findFragmentByTag(LIST_TAG3);
            mFragment4 = (ShouyeF4) mFragmentManager.findFragmentByTag(LIST_TAG4);
        }
        super.onCreate(savedInstanceState);
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

    }

    private void doNetWork() {
        mList = new ArrayList<>();
        addList();
        recyclerView.setLayoutManager(new GridLayoutManager(this, mList.size(), RecyclerView.VERTICAL, false));
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
        iv1 = findViewById(R.id.iv1);

        recyclerView = findViewById(R.id.recycler_view1);
        mAdapter = new ShouyeFooterAdapter(this);
        // 动态设置首页bufen
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ShouyeActivity");
        LocalBroadcastManagers.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, filter);
    }

    //点击item
    private void footer_onclick() {
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
        mList.add(new ShouyeFooterBean(id1, "首页", R.drawable.index_tab1_normal, R.drawable.index_tab1_select, false));
        mList.add(new ShouyeFooterBean(id2, "分类", R.drawable.index_tab2_normal, R.drawable.index_tab2_select, false));
        mList.add(new ShouyeFooterBean(id3, "业务", R.drawable.index_tab3_normal, R.drawable.index_tab3__select, false));
        mList.add(new ShouyeFooterBean(id4, "我的", R.drawable.index_tab3_normal, R.drawable.index_tab3__select, false));
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
                mFragment1 = new ShouyeF1();
                Bundle args = new Bundle();
                args.putString("tablayoutId", tag);
                mFragment1.setArguments(args);
                transaction.add(R.id.container, mFragment1, LIST_TAG1);
            } else {
                transaction.show(mFragment1);
                mFragment1.getCate(tag, isrefresh);
            }
        } else if (tag.equalsIgnoreCase(id2)) {
            if (mFragment2 == null) {
                mFragment2 = new ShouyeF2();
                Bundle args = new Bundle();
                args.putString("tablayoutId", tag);
                mFragment2.setArguments(args);
                transaction.add(R.id.container, mFragment2, LIST_TAG2);
            } else {
                transaction.show(mFragment2);
                mFragment2.getCate(tag, isrefresh);
            }
        } else if (tag.equalsIgnoreCase(id3)) {
            if (mFragment3 == null) {
                mFragment3 = new ShouyeF3();
                Bundle args = new Bundle();
                args.putString("tablayoutId", tag);
                mFragment3.setArguments(args);
                transaction.add(R.id.container, mFragment3, LIST_TAG3);
            } else {
                transaction.show(mFragment3);
                mFragment3.getCate(tag, isrefresh);
            }
        } else if (tag.equalsIgnoreCase(id4)) {
            if (mFragment4 == null) {
                mFragment4 = new ShouyeF4();
                Bundle args = new Bundle();
                args.putString("tablayoutId", tag);
                mFragment4.setArguments(args);
                transaction.add(R.id.container, mFragment4, LIST_TAG4);
            } else {
                transaction.show(mFragment4);
                mFragment4.getCate(tag, isrefresh);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mFragment1 != null) {
            transaction.hide(mFragment1);
            mFragment1.give_id(tag_ids);
        }
        if (mFragment2 != null) {
            transaction.hide(mFragment2);
            mFragment2.give_id(tag_ids);
        }
        if (mFragment3 != null) {
            transaction.hide(mFragment3);
            mFragment3.give_id(tag_ids);
        }
        if (mFragment4 != null) {
            transaction.hide(mFragment4);
            mFragment4.give_id(tag_ids);
        }
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

    @Override
    public void OnUpdateVersionSuccess(VersionInfoBean versionInfoBean) {
        apkPath = versionInfoBean.getTargetUrl();
        serverVersionCode = Integer.valueOf(versionInfoBean.getTargetUrl());
        serverVersionName = versionInfoBean.getTargetUrl();
        updateInfoTitle = versionInfoBean.getTargetUrl();
        updateInfo = versionInfoBean.getTargetUrl();
        if (TextUtils.equals(versionInfoBean.getTargetUrl(), "1")) {
            // 强制检查更新bufen
            UpdateAppUtils.from(ShouyeActivity.this)
                    .serverVersionCode(serverVersionCode)
                    .serverVersionName(serverVersionName)
                    .downloadPath("apks/" + getPackageName() + ".apk")
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
            UpdateAppUtils.from(ShouyeActivity.this)
                    .serverVersionCode(serverVersionCode)
                    .serverVersionName(serverVersionName)
                    .downloadPath("apks/" + getPackageName() + ".apk")
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
}
