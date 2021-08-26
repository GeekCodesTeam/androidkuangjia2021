package com.geek.appindex.index;

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
import com.geek.appindex.R;
import com.geek.libbase.base.SlbBaseLazyFragmentNew;
import com.geek.libbase.plugin.PluginManager;
import com.geek.libbase.utils.ApkDownloadUtils;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.haier.cellarette.baselibrary.toasts3.MProgressBarDialog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

public class ShouyeF3 extends SlbBaseLazyFragmentNew {

    private String tablayoutId;
    private TextView tv_center_content;
    private MessageReceiverIndex mMessageReceiver;

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("ShouyeF3".equals(intent.getAction())) {
                    //TODO 发送广播bufen
                    Intent msgIntent = new Intent();
                    msgIntent.setAction("ShouyeF3");
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
        return R.layout.fragment_shouyef3;
    }

    @Override
    protected void setup(final View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        tv_center_content = rootView.findViewById(R.id.shouyef3_tv1);
        tv_center_content.setText("业务");
        tv_center_content.setVisibility(View.GONE);
        test(rootView);
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ShouyeF3");
        LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
        donetwork();
//        ShouyeFooterBean bean = new Gson().fromJson("{}", ShouyeFooterBean.class);
//        MyLogUtil.e("sssssssssss", bean.toString());
//        MyLogUtil.e("sssssssssss", bean.getText_id());
//        tv_center_content.setText(bean.getText_id());
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

    private void test(View rootView) {
        rootView.findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActViewPageYewuList1"));
            }
        });
        rootView.findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActYewuList1"));
            }
        });
        rootView.findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActYewu3"));
            }
        });
        rootView.findViewById(R.id.tv4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.NkMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SaomaAct3"));
            }
        });
        rootView.findViewById(R.id.tv6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SearchListActivity");
                intent.putExtra("search_key", "搜索");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.DKMainActivity");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.PolyvActList1");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.TencentIMSplashActivity");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.XpopupMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LanguageAct"));
            }
        });
        rootView.findViewById(R.id.tv12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.CoordinatorLayoutAct"));
            }
        });
        rootView.findViewById(R.id.tv13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ElmAct"));
            }
        });
        rootView.findViewById(R.id.tv14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LoginActDemo"));
            }
        });
        rootView.findViewById(R.id.tv15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.webview.DemoWebviewMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.AgentwebAct"));
            }
        });
        rootView.findViewById(R.id.tv17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.MainActivitylibxuanzeqi"));
            }
        });
        rootView.findViewById(R.id.tv18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.RiliMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.GlideMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.BigImageViewPagerAct"));
            }
        });
        rootView.findViewById(R.id.tv21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.MarqueeViewLibraryAct"));
            }
        });
        rootView.findViewById(R.id.tv22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.PictureSelectorSimpleActivity"));
            }
        });
        rootView.findViewById(R.id.tv23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LiandongDemoAct"));
            }
        });
        rootView.findViewById(R.id.tv24).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.ac.github.DemoUpdateAppMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv25).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".com.recycleviewalluses.act"));
            }
        });
        rootView.findViewById(R.id.tv26).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.expandableview"));
            }
        });
        rootView.findViewById(R.id.tv27).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ZhiwenActtivity"));
            }
        });
        rootView.findViewById(R.id.tv28).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.LunboMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv29).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.AssetsMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv30).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.BtnActivity"));
            }
        });
        rootView.findViewById(R.id.tv31).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.phone.networkview"));
            }
        });
        rootView.findViewById(R.id.tv32).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.MainActivitySwitchButtonK"));
            }
        });
        rootView.findViewById(R.id.tv33).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.SCardViewAct"));
            }
        });
        rootView.findViewById(R.id.tv34).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.MnMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv35).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.ExpandableTextViewAct"));
            }
        });
        rootView.findViewById(R.id.tv36).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApkDownloadUtils.get().initFileDownLoader();
                String url = "http://cdn2.cdn.haier-jiuzhidao.com/appplugin-bxn_nation-release.apk";
                String savepath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "plugin" + ".apk";
                String pkgname = "com.geek.appplugin";
                String pkgname_jump = "com.geek.appplugin.Main1Activity";
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
                                try {
                                    PluginManager.INSTANCE.loadApk(getActivity(), savepath, pkgname);
                                    PluginManager.INSTANCE.startActivity(getActivity(), pkgname_jump);
                                } catch (Exception var4) {
                                    var4.printStackTrace();
                                }
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
            }
        });
    }

}
