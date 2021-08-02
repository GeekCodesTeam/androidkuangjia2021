package com.fosung.lighthouse.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.base.SlbBaseActivity;
import com.example.libbase.plugins.LoadUtils;
import com.example.libbase.plugins.PluginConst;
import com.example.libbase.plugins.PluginManager;
import com.example.libbase.utils.ApkDownloadUtils;
import com.geek.libutils.app.MyLogUtil;
import com.haier.cellarette.baselibrary.toasts3.MProgressBarDialog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.util.Random;

public class MainAct extends SlbBaseActivity {

    public MProgressBarDialog mProgressBarDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_dengtaguanliduan;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int numcode = (int) ((Math.random() * 9 + 1) * 100000);
        String aaaa = "V1"+System.currentTimeMillis() + numcode + "aakey";
        findViewById(R.id.tv1).setOnClickListener(v -> {
            test1();
        });
        findViewById(R.id.tv2).setOnClickListener(v -> {
            test2();
        });
        findViewById(R.id.tv3).setOnClickListener(v -> {
            test3();
        });
        findViewById(R.id.tv4).setOnClickListener(v -> {
            // HIOS
            if (!AppUtils.isAppInstalled("com.fosung.lighthouse.tongxunlu")) {
                ToastUtils.showLong("请先安装通讯录组件~");
                return;
            }
            // 方法2
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("dataability://cs.znclass.com/" +
                            "com.fosung.lighthouse.tongxunlu.hs.act.slbapp.TongxunluAct?query3=aaaa&query2=45464&query1=pc"));
            startActivity(intent);
        });
        findViewById(R.id.tv5).setOnClickListener(v -> {
            // HIOS
            if (!AppUtils.isAppInstalled("com.fosung.lighthouse.vpndenglu")) {
                ToastUtils.showLong("请先安装VPN登录组件~");
                return;
            }
            // 方法2
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("dataability://cs.znclass.com/" +
                            "com.fosung.lighthouse.vpndenglu.hs.act.slbapp.VpndengluAct?query3=aaaa&query2=45464&query1=pc"));
            startActivity(intent);
        });
        findViewById(R.id.tv6).setOnClickListener(v -> {
            // HIOS
            if (!AppUtils.isAppInstalled("com.fosung.lighthouse.yonghudenglu")) {
                ToastUtils.showLong("请先安装用户登录组件~");
                return;
            }
            // 方法2
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("dataability://cs.znclass.com/" +
                            "com.fosung.lighthouse.yonghudenglu.hs.act.slbapp.YonghudengluAct?query3=aaaa&query2=45464&query1=pc"));
            startActivity(intent);
        });
    }

    public void test1() {
        // test1
        ApkDownloadUtils.get().initFileDownLoader();
//            String url = "https://ip3501727548.mobgslb.tbcache.com/fs08/2020/12/20/8/110_9735b48a09cbfe495f138201aa78e3ec.apk?fname=%E8%85%BE%E8%AE%AF%E6%96%B0%E9%97%BB&productid=2011&packageid=400964468&pkg=com.tencent.news&vcode=6361&yingid=wdj_web&pos=detail-ndownload-com.tencent.news&appid=280347&apprd=280347&iconUrl=http%3A%2F%2Fandroid-artworks.25pp.com%2Ffs08%2F2020%2F12%2F21%2F1%2F110_205b543f1a7b8f019e240f0f841bec73_con.png&did=19faf86fa212b232c23791f221a215e2&md5=030693066c95ced4f805edb7911bd916&ali_redirect_domain=alissl.ucdl.pp.uc.cn&ali_redirect_ex_ftag=fa35a7a50589ab0834939893ee826c42fa4f4851c60badda&ali_redirect_ex_tmining_ts=1608532468&ali_redirect_ex_tmining_expire=3600&ali_redirect_ex_hot=100";
//            String url = "http://10.254.23.60:8199/file/libyewu-ezhibu-bxn_nation-debug.apk";
        String url = "http://cdn2.cdn.haier-jiuzhidao.com/libyewu-ezhibu-bxn_nation-release.apk";
        String savepath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "Cellarette" + ".apk";
        String pkgname = "com.fosung.lighthouse.ezhibu";
        String pkgname2 = "com.fosung.lighthouse.ezhibu.EzhibuAct";
        String pkgname_jump = "dataability://cs.znclass.com/" +
                "com.fosung.lighthouse.ezhibu.hs.act.slbapp.EzhibuAct?query3=aaaa&query2=45464&query1=pc";
        if (!AppUtils.isAppInstalled(pkgname)) {
            ApkDownloadUtils.get().zujian_loading(url, savepath, pkgname, pkgname_jump, new ApkDownloadUtils.OnLoadingStatus() {
                @Override
                public void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    //新建一个Dialog
                    mProgressBarDialog = new MProgressBarDialog.Builder(MainAct.this)
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
                    getWindow().getDecorView().postDelayed(new Runnable() {
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
                            PluginManager.getInstance().init(MainAct.this);
                            PluginManager.getInstance().loadPluginApk(savepath);
                            Bundle bundle = new Bundle();
                            bundle.putString(PluginConst.query1, "MainAct传值");
                            bundle.putString(PluginConst.DEX_PATH, savepath);
                            bundle.putString(PluginConst.REALLY_ACTIVITY_NAME, "com.fosung.lighthouse.ezhibu.EzhibuAct");
                            bundle.putInt(PluginConst.LAUNCH_MODEL, PluginConst.LaunchModel.STANDARD);
                            PluginManager.getInstance().startActivity(MainAct.this, bundle);
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

    public void test2() {
        // test2
        String savepath = LoadUtils.setpath1(MainAct.this, "libyewu-ezhibu-bxn_nation-release.apk");
        PluginManager.getInstance().init(MainAct.this);
        PluginManager.getInstance().loadPluginApk(savepath);
        Bundle bundle = new Bundle();
        bundle.putString(PluginConst.query1, "MainAct传值");
        bundle.putString(PluginConst.DEX_PATH, savepath);
        bundle.putString(PluginConst.REALLY_ACTIVITY_NAME, "com.fosung.lighthouse.ezhibu.EzhibuAct");
        bundle.putInt(PluginConst.LAUNCH_MODEL, PluginConst.LaunchModel.STANDARD);
        PluginManager.getInstance().startActivity(MainAct.this, bundle);
    }

    public void test3() {
        // test3
        ApkDownloadUtils.get().initFileDownLoader();
        String url = "http://cdn2.cdn.haier-jiuzhidao.com/libyewu-ezhibu-bxn_nation-release.apk";
        String savepath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "Cellarette" + ".apk";
        String pkgname = "com.fosung.lighthouse.ezhibu";
        String pkgname2 = "com.fosung.lighthouse.ezhibu.EzhibuAct";
        String pkgname_jump = "dataability://cs.znclass.com/" +
                "com.fosung.lighthouse.ezhibu.hs.act.slbapp.EzhibuAct?query3=aaaa&query2=45464&query1=pc";
        if (!AppUtils.isAppInstalled(pkgname)) {
            ApkDownloadUtils.get().zujian_loading(url, savepath, pkgname, pkgname_jump, new ApkDownloadUtils.OnLoadingStatus() {
                public MProgressBarDialog mProgressBarDialog;

                @Override
                public void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    //新建一个Dialog
                    mProgressBarDialog = new MProgressBarDialog.Builder(MainAct.this)
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
                    getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mProgressBarDialog != null) {
                                mProgressBarDialog.dismiss();
                            }
                            // test
                            File saveFile = new File(savepath);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            FileProvider7.setIntentDataAndType(MainAct.this, i, "application/vnd.android.package-archive", saveFile, true);
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

    @Override
    public void onDestroy() {
        ApkDownloadUtils.get().onDestroy();
        super.onDestroy();
    }

}
