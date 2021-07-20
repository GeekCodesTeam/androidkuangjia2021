package com.example.slbappindex.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.libbase.plugins.PluginConst;
import com.example.libbase.plugins.PluginManager;
import com.example.libbase.utils.ApkDownloadUtils;
import com.example.libbase.widgets.GridSpacingItemDecoration;
import com.example.slbappindex.R;
import com.example.slbappindex.adapters.ShouyeF4Adapter1;
import com.example.slbappindex.adapters.ShouyeF4Bean1;
import com.geek.libglide47.base.util.DisplayUtil;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.shortcut.ShortcutUtils;
import com.haier.cellarette.baselibrary.toasts3.MProgressBarDialog;
import com.haier.cellarette.libwebview.hois2.HiosHelper;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShouyeF4 extends SlbBaseLazyFragmentNew {

    private String tablayoutId;
    private MessageReceiverIndex mMessageReceiver;
    //
    private RecyclerView recyclerView1;
    private ShouyeF4Adapter1 mAdapter1;
    private List<ShouyeF4Bean1> mList1;

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("ShouyeF4".equals(intent.getAction())) {
                    //TODO 发送广播bufen
                    Intent msgIntent = new Intent();
                    msgIntent.setAction("ShouyeF4");
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shouyef4;
    }

    @Override
    protected void setup(final View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        recyclerView1 = rootView.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 4, RecyclerView.VERTICAL, false));
        recyclerView1.addItemDecoration(new GridSpacingItemDecoration(4, (int) (DisplayUtil.getScreenWidth(getActivity()) * 8f / 375), true));
        mList1 = new ArrayList<>();
        mAdapter1 = new ShouyeF4Adapter1();
        recyclerView1.setAdapter(mAdapter1);
        //
        onclick();
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ShouyeF4");
        LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
        donetwork();
    }

    private void onclick() {
        mAdapter1.setOnItemClickListener((adapter, view, position) -> {
            //item click
            ToastUtils.showLong(position + "item click");
        });
        mAdapter1.setOnItemChildClickListener((adapter, view, position) -> {
            ShouyeF4Bean1 bean1 = mList1.get(position);
            int i = view.getId();
            if (i == R.id.iv1) {
                if (bean1.getTab_tag().contains("http")) {
                    //
                    HiosHelper.resolveAd(getActivity(), getActivity(), bean1.getTab_tag());
                } else if (bean1.getTab_tag().contains("act1")) {
                    //
                    ShortcutUtils.addShortcut(getActivity(), false,
                            bean1.getTab_id(),
                            bean1.getTab_name(),
                            R.drawable.mn_icon_staues_test);
                } else if (bean1.getTab_tag().contains("act2")) {
                    //
                    ShortcutUtils.addIcon((AppCompatActivity) getActivity(),
                            bean1.getTab_id(),
                            bean1.getId(),
                            bean1.getTab_name(),
                            R.drawable.mn_icon_staues_test);
                } else if (bean1.getTab_tag().contains("hios")) {
                    //
                    Intent intent = new Intent(new Intent(bean1.getTab_id()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("dataability://cs.znclass.com/" + AppUtils.getAppPackageName() + ".hs.act.slbapp.JavaDemoActivity"));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                } else if (bean1.getTab_tag().contains("others")) {
                    if (!AppUtils.isAppInstalled(bean1.getTab_id())) {
                        ToastUtils.showLong("未安装此应用服务");
                        return;
                    }
                    AppUtils.launchApp(bean1.getTab_id());
                } else if (bean1.getTab_tag().contains("load")) {
                    // test
                    ApkDownloadUtils.get().initFileDownLoader();
                    String url = bean1.getTab_id();
                    String savepath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "Cellarette" + ".apk";
                    String pkgname = "com.fosung.lighthouse.ezhibu";
                    String pkgname2 = "com.fosung.lighthouse.ezhibu.EzhibuAct";
                    String pkgname_jump = "dataability://cs.znclass.com/" +
                            "com.fosung.lighthouse.ezhibu.hs.act.slbapp.EzhibuAct?query3=aaaa&query2=45464&query1=pc";
                    ApkDownloadUtils.get().zujian_loading(url, savepath, pkgname, pkgname_jump, new ApkDownloadUtils.OnLoadingStatus() {
                        public MProgressBarDialog mProgressBarDialog;

                        @Override
                        public void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                            //新建一个Dialog
                            mProgressBarDialog = new MProgressBarDialog.Builder(getActivity())
                                    .setStyle(MProgressBarDialog.MProgressBarDialogStyle_Circle)
                                    .build();
                        }

                        @Override
                        public void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                            MyLogUtil.e("ssssssss", Integer.parseInt((100 * soFarBytes / totalBytes) + "") + "");
                            if (mProgressBarDialog != null) {
                                mProgressBarDialog.showProgress(Integer.parseInt((100 * soFarBytes / totalBytes) + ""), "加载组件：" + (Integer.parseInt((100 * soFarBytes / totalBytes) + "") + 1) + "%", true);
                            }
                        }

                        @Override
                        public void completed(BaseDownloadTask task) {
                            //
                            if (mProgressBarDialog != null) {
                                mProgressBarDialog.showProgress(100, "完成", true);
                            }
                            getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (mProgressBarDialog != null) {
                                        mProgressBarDialog.dismiss();
                                    }
                                    // test
//                                File saveFile = new File(savepath);
//                                Intent i = new Intent(Intent.ACTION_VIEW);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                FileProvider7.setIntentDataAndType(MainAct.this, i, "application/vnd.android.package-archive", saveFile, true);
//                                startActivity(i);
                                    // test2
//                                String savepath = LoadUtils.setpath1(MainAct.this,"libyewu-ezhibu-bxn_nation-release.apk");
                                    PluginManager.getInstance().init(getActivity());
                                    PluginManager.getInstance().loadPluginApk(savepath);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(PluginConst.query1, "MainAct传值");
                                    bundle.putString(PluginConst.DEX_PATH, savepath);
                                    bundle.putString(PluginConst.REALLY_ACTIVITY_NAME, "com.fosung.lighthouse.ezhibu.EzhibuAct");
                                    bundle.putInt(PluginConst.LAUNCH_MODEL, PluginConst.LaunchModel.STANDARD);
                                    PluginManager.getInstance().startActivity(getActivity(), bundle);
                                }
                            }, 100);
                        }

                        @Override
                        public void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                        }

                        @Override
                        public void error(BaseDownloadTask task, Throwable e) {
                            //
                            mProgressBarDialog.showProgress(100, "完成", true);
                            mProgressBarDialog.dismiss();
                        }
                    });
//                    if (!AppUtils.isAppInstalled(pkgname)) {
//
//                        return;
//                    }
//                    PluginManager.getInstance().init(getActivity());
//                    PluginManager.getInstance().loadPluginApk(savepath);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(PluginConst.query1, "MainAct传值");
//                    bundle.putString(PluginConst.DEX_PATH, savepath);
//                    bundle.putString(PluginConst.REALLY_ACTIVITY_NAME, "com.fosung.lighthouse.ezhibu.EzhibuAct");
//                    bundle.putInt(PluginConst.LAUNCH_MODEL, PluginConst.LaunchModel.STANDARD);
//                    PluginManager.getInstance().startActivity(getActivity(), bundle);
                } else if (bean1.getTab_tag().contains("down")) {
                    ApkDownloadUtils.get().initFileDownLoader();
                    String url = bean1.getTab_id();
                    String savepath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "Cellarette" + ".apk";
                    String pkgname = "com.fosung.lighthouse.ezhibu";
                    String pkgname2 = "com.fosung.lighthouse.ezhibu.EzhibuActApk";
                    String pkgname_jump = "dataability://cs.znclass.com/" +
                            "com.fosung.lighthouse.ezhibu.hs.act.slbapp.EzhibuActApk?query3=aaaa&query2=45464&query1=pc";
                    if (!AppUtils.isAppInstalled(pkgname)) {
                        ApkDownloadUtils.get().zujian_loading(url, savepath, pkgname, pkgname_jump, new ApkDownloadUtils.OnLoadingStatus() {
                            public MProgressBarDialog mProgressBarDialog;

                            @Override
                            public void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                                //新建一个Dialog
                                mProgressBarDialog = new MProgressBarDialog.Builder(getActivity())
                                        .setStyle(MProgressBarDialog.MProgressBarDialogStyle_Circle)
                                        .build();
                            }

                            @Override
                            public void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                                MyLogUtil.e("ssssssss", Integer.parseInt((100 * soFarBytes / totalBytes) + "") + "");
                                if (mProgressBarDialog != null) {
                                    mProgressBarDialog.showProgress(Integer.parseInt((100 * soFarBytes / totalBytes) + ""), "加载组件：" + (Integer.parseInt((100 * soFarBytes / totalBytes) + "") + 1) + "%", true);
                                }
                            }

                            @Override
                            public void completed(BaseDownloadTask task) {
                                //
                                if (mProgressBarDialog != null) {
                                    mProgressBarDialog.showProgress(100, "完成", true);
                                }
                                getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mProgressBarDialog != null) {
                                            mProgressBarDialog.dismiss();
                                        }
                                        // test
                                        File saveFile = new File(savepath);
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        FileProvider7.setIntentDataAndType(getActivity(), i, "application/vnd.android.package-archive", saveFile, true);
                                        startActivity(i);
                                    }
                                }, 100);
                            }

                            @Override
                            public void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                            }

                            @Override
                            public void error(BaseDownloadTask task, Throwable e) {
                                //
                                mProgressBarDialog.showProgress(100, "完成", true);
                                mProgressBarDialog.dismiss();
                            }
                        });
                        return;
                    }
                    // 方法2
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(pkgname_jump));
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 第一次进来加载bufen
     */
    private void donetwork() {
        retryData();
    }

    // 刷新
    private void retryData() {
        //TODO test
        mList1 = new ArrayList<>();
        mList1.add(new ShouyeF4Bean1("11", "http://cdn2.cdn.haier-jiuzhidao.com/libyewu-ezhibu-bxn_nation-release.apk", "down", "灯塔1.0", false));
        mList1.add(new ShouyeF4Bean1("22", "com.example.gsyvideoplayer.GSYMainActivity", "act1", "华为智慧屏", false));
        mList1.add(new ShouyeF4Bean1("33", "http://cdn2.cdn.haier-jiuzhidao.com/libyewu-ezhibu-bxn_nation-release.apk", "load", "灯塔2.0", false));
        mList1.add(new ShouyeF4Bean1("44", "com.example.gsyvideoplayer.GSYMainActivity", "act2", "鸿蒙智慧屏", false));
        mList1.add(new ShouyeF4Bean1("55", "com.tencent.mm", "others", "微信", false));
        mList1.add(new ShouyeF4Bean1("66", AppUtils.getAppPackageName() + ".hs.act.slbapp.JavaDemoActivity", "hios", "灯塔通平台", false));
        mList1.add(new ShouyeF4Bean1("77", "http://zwfw.sd.gov.cn", "http", "华为会议", false));
        mList1.add(new ShouyeF4Bean1("88", "com.tencent.tmgp.sgame", "others", "王者荣耀", false));
        mList1.add(new ShouyeF4Bean1("66", AppUtils.getAppPackageName() + ".hs.act.slbapp.ChangeIconActivity", "hios", "党的节日", false));

        mAdapter1.setNewData(mList1);
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
